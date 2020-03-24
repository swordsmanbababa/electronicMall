package com.gemptc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gemptc.entity.User;
import com.gemptc.util.DruidDataSourceUtils;
import com.gemptc.util.JDBCUtils;

public class UserDaoImp implements UserDao {

	@Override
	public User searchUserByUserName(String username) throws Exception {
		//采用JDBC连接数据库实现
		Connection conn = DruidDataSourceUtils.getConnection();
		String sql = "SELECT * FROM t_admin WHERE u_name=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, username);
		ResultSet rs = psmt.executeQuery();
		User resultUser = null;
		if(rs.next()) {
			resultUser = new User();
			resultUser.setU_id(rs.getInt("u_id"));
			resultUser.setU_name(rs.getString("u_name"));
			resultUser.setU_addtime(rs.getString("u_addtime"));
			resultUser.setU_email(rs.getString("u_email"));
			resultUser.setU_status(rs.getString("u_status"));
			resultUser.setU_password(rs.getString("u_password"));
			resultUser.setU_telephone(rs.getString("u_telephone"));
		}
		DruidDataSourceUtils.release(rs, psmt, conn);
		return resultUser;
	}

	@Override
	public User searchFrontUserByUserName(String username) throws Exception {
		//采用JDBC连接数据库实现
		Connection conn = DruidDataSourceUtils.getConnection();
		String sql = "SELECT * FROM t_user WHERE u_name=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, username);
		ResultSet rs = psmt.executeQuery();
		User resultUser = null;
		if(rs.next()) {
			resultUser = new User();
			resultUser.setU_id(rs.getInt("u_id"));
			resultUser.setU_name(rs.getString("u_name"));
			resultUser.setU_addtime(rs.getString("u_addtime"));
			resultUser.setU_email(rs.getString("u_email"));
			resultUser.setU_status(rs.getString("u_status"));
			resultUser.setU_password(rs.getString("u_password"));
			resultUser.setU_telephone(rs.getString("u_telephone"));
		}
		DruidDataSourceUtils.release(rs, psmt, conn);
		return resultUser;
	}

	@Override
	public boolean userRegister(User user) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = DruidDataSourceUtils.getConnection();
		String sql = "INSERT INTO t_user (u_name, u_password) VALUES (?, ?)";
		PreparedStatement psmt = conn.prepareStatement(sql);
		System.out.println("________________"+user.getU_password());
		psmt.setString(1, user.getU_name());
		psmt.setString(2, user.getU_password());
		int insertResult = psmt.executeUpdate();
		if(insertResult>0) {
			return true;
		}	
		JDBCUtils.release(null, psmt, conn);
		return false;
	}

}
