package com.gemptc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

public class MyDataSourceUtils {
	private static DruidDataSource dataSource = (DruidDataSource) DruidDataSourceUtils.getDataSource();
	// ��ǰ���̶߳���  ��������  Ψһ��
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}
	
	//��ǰΨһ��Connectin����  ��ȡͬһ�����Ӷ���
	public static Connection getCurrentConnection() throws Exception{
		Connection conn = tl.get();  //�ӵ�ǰ�̶߳����л�ȡ���Ӷ��� 
		if(conn==null) {			//�ж��Ƿ�Ϊ��   Ϊ�վʹ����ӳ���һ��  �󶨵���ǰ�߳���
			conn = getConnection();   
			tl.set(conn);
		}
		return conn;
	}
	
	//��������
	public static void startTransaction() throws Exception{
		getCurrentConnection().setAutoCommit(false);
	}
	//�ύ����
	public static void commit() throws Exception{
		Connection conn = getCurrentConnection();
		conn.commit();
		//�����Ӵӵ�ǰ�����Ƴ���   Ȼ��ر�����
		tl.remove();
		conn.close();
	}
	//�ع�����
	public static void rollback() throws Exception{
		Connection conn = getCurrentConnection();
		conn.rollback();
		//�����Ӵӵ�ǰ�����Ƴ���   Ȼ��ر�����
		tl.remove();
	    conn.close();
	}
	
	public static void release(ResultSet rs,PreparedStatement psmt,Connection con){
    	 if(rs!=null) {
    		 try {
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
    	 }
    	 if(psmt!=null) {
    		 try {
				psmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	 }
    	 if(con!=null) {
    		 try {
    			 // ����close() ���� ʵ�� close ���ǹر� �ǹ黹һ�����ӵ����ӳ���
				con.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
    	 }
     }
}
