package com.gemptc.dao;

import com.gemptc.entity.User;

public interface UserDao {
	
	//查询的t_admin表
	public User searchUserByUserName(String username) throws Exception;
    //查询的t_user表
	public User searchFrontUserByUserName(String username) throws Exception;
	public boolean userRegister(User user)  throws Exception;

}
