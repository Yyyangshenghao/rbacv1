package cn.wolfcode.rbac;

import cn.wolfcode.rbac.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtils redisUtils;
    @Test
    public void test1(){
        redisUtils.set("name","xiaohei");
    }
    @Test
    public void test2(){
        System.out.println(redisUtils.get("name"));
    }
}
