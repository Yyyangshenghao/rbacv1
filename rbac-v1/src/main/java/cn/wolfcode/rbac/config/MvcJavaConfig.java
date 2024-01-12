package cn.wolfcode.rbac.config;
import cn.wolfcode.rbac.web.interceptor.CheckLoginInterceptor;
import cn.wolfcode.rbac.web.interceptor.CheckPermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcJavaConfig implements WebMvcConfigurer {

    @Autowired
    private CheckLoginInterceptor loginInterceptor;
    @Autowired
    private CheckPermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // 拦截
                .excludePathPatterns("/api/login","/api/code","/api/logout","/favicon.ico"); // 排除
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/**") // 拦截
                .excludePathPatterns("/api/login","/api/code","/api/logout","/favicon.ico"); // 排除
    }
}
