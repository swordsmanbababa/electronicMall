package com.gemptc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//Utils ������  �������ݿ������
public class JDBCUtils {
	
	public static Connection getConnection() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		//JAVA�ķ������  ���ַ�������ȡ����
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopdb?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false", "root", "");
	    
		return conn;
	}
	
	public static void release(ResultSet rs,PreparedStatement psmt,Connection conn) throws Exception {

		if(rs!=null) {
			rs.close();
		}
		if(psmt!=null) {
			psmt.close();
		}
		if(conn!=null){
			conn.close();
		}		
	}
	

}
