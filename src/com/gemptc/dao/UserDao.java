package com.gemptc.dao;

import com.gemptc.entity.User;

public interface UserDao {
	
	//��ѯ��t_admin��
	public User searchUserByUserName(String username) throws Exception;
    //��ѯ��t_user��
	public User searchFrontUserByUserName(String username) throws Exception;
	public boolean userRegister(User user)  throws Exception;

}
