package cn.wolfcode.rbac.service.impl;

import cn.wolfcode.rbac.domain.Class;
import cn.wolfcode.rbac.domain.query.QueryObject;
import cn.wolfcode.rbac.domain.vo.PageResult;
import cn.wolfcode.rbac.mapper.ClassMapper;
import cn.wolfcode.rbac.service.IClassService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements IClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public PageResult<Class> selectByPage(QueryObject qo) {
        PageHelper.startPage(qo.getPageNum(),qo.getPageSize());
        PageInfo<Class> pageInfo=new PageInfo<>(classMapper.selectAll());
        PageResult<Class> pageResult=new PageResult<>();
        BeanUtils.copyProperties(pageInfo,pageResult);
        return pageResult;
    }

    @Override
    public List<Class> selectById(Long teacherId) {
        List<Class> classList = classMapper.selectById(teacherId);
        return classList;
    }
}
