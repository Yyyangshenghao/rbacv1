package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.domain.Class;
import cn.wolfcode.rbac.domain.query.QueryObject;
import cn.wolfcode.rbac.domain.vo.PageResult;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/class")
@CrossOrigin(allowCredentials="true")
public class ClassApiController {
    @Autowired
    private IClassService classService;

    @GetMapping("/list")
    @ResponseBody
    public R list(QueryObject qo){
        PageResult<Class> pageResult = classService.selectByPage(qo);
        return R.ok(pageResult);
    }

    @GetMapping("/listForTeacher/{teacherId}")
    @ResponseBody
    public R list(@PathVariable Long teacherId){
        List<Class> selectResult = classService.selectById(teacherId);
        return R.ok(selectResult);
    }

}
