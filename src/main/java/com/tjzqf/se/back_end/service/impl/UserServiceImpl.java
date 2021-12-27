package com.tjzqf.se.back_end.service.impl;

import com.tjzqf.se.back_end.entity.User;
import com.tjzqf.se.back_end.mapper.UserMapper;
import com.tjzqf.se.back_end.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqf
 * @since 2021-12-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
