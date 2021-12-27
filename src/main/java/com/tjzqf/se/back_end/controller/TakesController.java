package com.tjzqf.se.back_end.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjzqf.se.back_end.entity.Student;
import com.tjzqf.se.back_end.entity.Takes;
import com.tjzqf.se.back_end.service.impl.StudentServiceImpl;
import com.tjzqf.se.back_end.service.impl.TakesServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.tjzqf.se.back_end.controller.BaseController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zqf
 * @since 2021-12-26
 */
@Controller
@RequestMapping("/back_end/takes")
public class TakesController extends BaseController {
    @Resource
    private TakesServiceImpl takesService;
    @GetMapping("pageTestA/{page}/{size}")
    public Page<Takes> pageTestA(@PathVariable Integer page, @PathVariable Integer size){
        Page<Takes> iPage = new Page<Takes>(page, size);
        return takesService.getAll(iPage);
    }

}

