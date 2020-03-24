package com.gemptc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceUtils {
	private static String driverClass = null;
	private static String url = null;
	private static String name = null;
	private static String password = null;
	//��̬�����
	static {
		try {
			ClassLoader loader = DruidDataSourceUtils.class.getClassLoader();
			InputStream inputStream = loader.getResourceAsStream("./com/gemptc/util/db.properties");
			Properties props = new Properties();
			props.load(inputStream);
			url = props.getProperty("url");
			name = props.getProperty("name");
			password = props.getProperty("password");
			driverClass = props.getProperty("driverClass");
			System.out.println(url+"---"+name+"---"+password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static DruidDataSource dataSource = new DruidDataSource();

	public static DataSource getDataSource() {
		dataSource
		.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(name);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	//���� ��Ҫ  ����    
	// �������ӵĲ���   url  root  password
	// ��ȡ �����ļ�  һ���Զ�ȡ   ����Ҫÿ�ζ���ȡ�����ļ�
	public static Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
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
