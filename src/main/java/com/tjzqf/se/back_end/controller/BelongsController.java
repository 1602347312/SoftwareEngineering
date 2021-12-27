package com.tjzqf.se.back_end.controller;


import com.tjzqf.se.Result;
import com.tjzqf.se.back_end.entity.Belongs;
import com.tjzqf.se.back_end.service.BelongsService;
import com.tjzqf.se.back_end.service.impl.BelongsServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.tjzqf.se.back_end.controller.BaseController;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/back_end/belongs")
public class BelongsController extends BaseController {

    @Resource
    private BelongsServiceImpl belongsService;

    /**
     * 多表联查，一对多
     * @return
     */
    @GetMapping("getAllClasses")
    public Result<?> getAllClasses(int user_id){
        return Result.success(belongsService.getAllClasses(user_id));
    }

    @GetMapping("test")
    public Result<?> test(){
        return Result.success();
    }
}

