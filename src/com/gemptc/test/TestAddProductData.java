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
		//才JAVA请求一个网页   然后 解析HTML标签  
		//获取HTML标签里面的节点 
		//然后将节点里面的值 或者属性  填写插入到自己的数据库中
		
		//自动获取数据库里面的数据新 从webshop 数据库  转到 shopdb数据库中
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
		System.out.println("查入完成");
		DruidDataSourceUtilsTest.release(rs, psmt, conn);
	}

}
