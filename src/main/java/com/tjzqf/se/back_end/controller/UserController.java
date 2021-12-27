package com.tjzqf.se.back_end.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.hutool.json.JSONObject;
import com.tjzqf.se.Result;
import com.tjzqf.se.back_end.entity.Belongs;
import com.tjzqf.se.back_end.entity.Class;
import com.tjzqf.se.back_end.entity.User;
import com.tjzqf.se.back_end.mapper.UserMapper;
import com.tjzqf.se.back_end.service.impl.BelongsServiceImpl;
import com.tjzqf.se.back_end.service.impl.ClassServiceImpl;
import com.tjzqf.se.back_end.util.JwtUtil;
import org.springframework.web.bind.annotation.*;
import com.tjzqf.se.back_end.annotation.PassToken;
import com.tjzqf.se.back_end.annotation.UserLoginToken;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zqf
 * @since 2021-12-27
 */
@RestController
@RequestMapping("/back_end/user")
public class UserController extends BaseController {

    @Resource
    UserMapper userMapper;
    @Resource
    ClassServiceImpl classService;
    @Resource
    BelongsServiceImpl belongsService;

    //登录
    @PassToken
    @PostMapping("/login")
    public Object login(User user){
        JSONObject jsonObject=new JSONObject();
        User res=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserId,user.getUserId()));
        if(res==null || !res.getPassword().equals(user.getPassword())){
            return Result.error("-1","用户名或密码输入错误");
        }else {
            String token = JwtUtil.getToken(user.getUserId(),user.getPassword());
            jsonObject.put("token", token);
            jsonObject.put("type", user.getType());
            return Result.success(jsonObject);
        }
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }

    //注册
    @PassToken
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        //判断用户名是否唯一
        User res=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserId,user.getUserId()));
        if(res !=null){
            return Result.error("-1","用户名重复");
        }
        if(user.getPassword()==null){
            return Result.error("-1","请输入密码");
        }
        userMapper.insert(user);
        return Result.success();
    }



    @GetMapping("/getClassList")
    public  Result<?> getClassList(int user_id, boolean type){



        if(!type){
            QueryWrapper<Class> wrapper = new QueryWrapper<>();
            wrapper.eq("class_teacher_id", user_id);
            List<Class> classes = classService.getBaseMapper().selectList(wrapper);
            if(!classes.isEmpty())
                return Result.success(classes);
            else return Result.error("-1", "没有班级数据");
        }
        else{
            List<Belongs> classes = belongsService.getAllClasses(user_id);
            if(!classes.isEmpty())
                return Result.success(classes);
            else return Result.error("-1", "没有班级数据");
        }
    }
}

