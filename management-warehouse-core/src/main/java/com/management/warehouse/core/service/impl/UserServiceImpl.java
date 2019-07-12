package com.management.warehouse.core.service.impl;

import com.management.warehouse.core.entity.User;
import com.management.warehouse.core.dao.UserMapper;
import com.management.warehouse.core.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiqiuwei
 * @since 2019-07-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
