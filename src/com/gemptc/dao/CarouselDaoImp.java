package com.gemptc.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gemptc.entity.Carousel;
import com.gemptc.entity.User;
import com.gemptc.util.JDBCUtils;

public class CarouselDaoImp implements CarouselDao{

	@Override
	public List<Carousel> searchAllCarouses() throws Exception {
		// TODO Auto-generated method stub
		 Connection conn = JDBCUtils.getConnection();
		 String sql = "SELECT * FROM t_lunbo";
		 PreparedStatement psmt = conn.prepareStatement(sql);
		 ResultSet rs = psmt.executeQuery();
		 ArrayList<Carousel> array=new ArrayList<Carousel>();
		while(rs.next()) {
			Carousel carousel=new Carousel();
			carousel.setCar_id(rs.getInt("car_id"));
			carousel.setPro_img(rs.getString("pro_img"));
			carousel.setPro_name(rs.getString("pro_name"));
			array.add(carousel);
		}
		return array;
	}

	@Override
	public boolean deleceCarouselsByCarID(String car_id) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = JDBCUtils.getConnection();
		String sql = "DELETE FROM t_lunbo WHERE car_id = ?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, car_id);
		int deleteResult = psmt.executeUpdate();
		JDBCUtils.release(null, psmt, conn);
		if(deleteResult>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateCarouselByCarID(Carousel carouse) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = JDBCUtils.getConnection();
		String sql = "UPDATE t_lunbo SET pro_name=?,pro_img=?  WHERE car_id=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, carouse.getPro_name());
		psmt.setString(2, carouse.getPro_img());
		psmt.setInt(3, carouse.getCar_id());
		System.out.println(psmt+"hvhvjhvjhv"+carouse.getCar_id());
		int updateResult = psmt.executeUpdate();
		JDBCUtils.release(null, psmt, conn);
		if(updateResult>0) {
			return true;
		}
		return false;
	}

	

}
