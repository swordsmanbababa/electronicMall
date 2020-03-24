package com.gemptc.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gemptc.dao.ProductDao;
import com.gemptc.dao.ProductDaoImp;
import com.gemptc.entity.Category;
import com.gemptc.entity.Product;

public class TestAddProductData {

	
	
	
	public static void main(String[] args) throws Exception {
		//��JAVA����һ����ҳ   Ȼ�� ����HTML��ǩ  
		//��ȡHTML��ǩ����Ľڵ� 
		//Ȼ�󽫽ڵ������ֵ ��������  ��д���뵽�Լ������ݿ���
		
		//�Զ���ȡ���ݿ������������ ��webshop ���ݿ�  ת�� shopdb���ݿ���
		Connection conn = DruidDataSourceUtilsTest.getConnection();
		String sql = "SELECT * FROM product ";
		PreparedStatement psmt = conn.prepareStatement(sql);
		ResultSet rs = psmt.executeQuery();
		
		ProductDao dao = new ProductDaoImp();
		while(rs.next()) {
			Product p = new Product();
			p.setPro_image(rs.getString("pimage"));
			p.setPro_price(rs.getDouble("shop_price"));
			Category cate = new Category();
			cate.setC_id(rs.getInt("cid"));
			p.setCate(cate);
			p.setPro_create(rs.getString("pdate"));
			p.setPro_desc(rs.getString("pdesc"));
			p.setPro_name(rs.getString("pname"));
			dao.insertProduct(p);
		}
		System.out.println("�������");
		DruidDataSourceUtilsTest.release(rs, psmt, conn);
	}

}
