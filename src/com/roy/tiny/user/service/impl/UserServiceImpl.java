package com.roy.tiny.user.service.impl;

import org.springframework.stereotype.Repository;

import com.roy.tiny.base.service.impl.BaseServiceImpl;
import com.roy.tiny.user.model.User;
import com.roy.tiny.user.service.UserService;

@Repository("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
}
