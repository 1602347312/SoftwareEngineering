package com.tjzqf.se.back_end.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjzqf.se.back_end.entity.Student;
import com.tjzqf.se.back_end.service.StudentService;
import com.tjzqf.se.back_end.service.impl.StudentServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.tjzqf.se.back_end.controller.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zqf
 * @since 2021-12-26
 */
@Controller
@RequestMapping("/back_end/student")
public class StudentController extends BaseController {


}

