package com.gemptc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gemptc.entity.Category;
import com.gemptc.entity.Order;
import com.gemptc.entity.OrderItem;
import com.gemptc.entity.Product;
import com.gemptc.util.DruidDataSourceUtils;
import com.gemptc.util.JDBCUtils;
import com.gemptc.util.MyDataSourceUtils;

public class ProductDaoImp implements ProductDao {

	@Override
	public List<Product> selectAllProduct() throws Exception {
		// TODO Auto-generated method stub
		// 连接数据库   采用mysql的jar包进行操作
        Connection conn = JDBCUtils.getConnection();
		String sql = "SELECT * FROM t_product WHERE pro_state=1";
		PreparedStatement psmt = conn.prepareStatement(sql);
		ResultSet rs = psmt.executeQuery();
		ArrayList<Product> array = new ArrayList<Product>();
		// 不是采用for循环  采用迭代器
		while(rs.next()) {
			Product p = new Product();
			p.setPro_id(rs.getInt("pro_id"));
			p.setPro_name(rs.getString("pro_name"));
			p.setPro_price(rs.getDouble("pro_price"));
			p.setPro_desc(rs.getString("pro_desc"));
			p.setPro_image(rs.getString("pro_image"));
			//查询数据多一个.0  可以手动去掉
			String tempTime = rs.getString("pro_create");
			//SimpDateFormat 解决也可以的
			if(tempTime!=null&&!tempTime.trim().equals("")) {
				p.setPro_create(tempTime.substring(0, tempTime.lastIndexOf(".")));
			}
			
			String c_id = rs.getString("c_id");
			String sqlCate = "SELECT * FROM t_category WHERE c_id=?";
			PreparedStatement catePsmt = conn.prepareStatement(sqlCate);
			catePsmt.setString(1, c_id);
			ResultSet cateRs = catePsmt.executeQuery();
			Category cate = null;
			while(cateRs.next()) {
				cate = new Category();
				cate.setC_id(cateRs.getInt("c_id"));
				cate.setC_name(cateRs.getString("c_name"));
			}
			p.setCate(cate);			
			array.add(p);
		}
	    JDBCUtils.release(rs, psmt, conn);
		return array;
	}

	private ArrayList<Product> testFakeProductData() {
		ArrayList<Product> arrayList = new ArrayList<Product>();
		for(int i=0;i<10;i++) {
			Product p = new Product();
			p.setPro_id(i+1);
			p.setPro_price(100+i);
			p.setPro_name("我的电脑"+i);
			p.setPro_desc("电脑描述"+i);
			arrayList.add(p);
		}
		return arrayList;
	}

	@Override
	public boolean insertProduct(Product insertProduct) throws Exception {
		//实现插入数据库的方法
		Connection conn = JDBCUtils.getConnection();
		String sql = "INSERT INTO t_product (pro_name,pro_price,pro_desc,pro_create,pro_image,c_id) VALUE(?,?,?,?,?,?)";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, insertProduct.getPro_name());
		psmt.setDouble(2, insertProduct.getPro_price());
		psmt.setString(3, insertProduct.getPro_desc());
		psmt.setString(4, insertProduct.getPro_create());
		psmt.setString(5, insertProduct.getPro_image());
		psmt.setInt(6, insertProduct.getCate().getC_id());
		int insertResult = psmt.executeUpdate();
		if(insertResult>0) {
			return true;
		}	
		JDBCUtils.release(null, psmt, conn);
		return false;
	}

	/*
	 * 根据商品id查询商品信息
	 * */
	@Override
	public Product selectProductById(int pro_id) throws Exception {
		Connection conn = JDBCUtils.getConnection();
		//查询的结果集只有一条数据
		String sql = "SELECT * FROM t_product WHERE pro_id=? AND pro_state=1";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setInt(1, pro_id);
		ResultSet rs = psmt.executeQuery();
		Product result = null;
		while(rs.next()) {
			result = new Product();
			result.setPro_id(rs.getInt("pro_id"));
			result.setPro_name(rs.getString("pro_name"));
			result.setPro_price(rs.getDouble("pro_price"));
			result.setPro_desc(rs.getString("pro_desc"));
			result.setPro_image(rs.getString("pro_image"));
			//查询数据多一个.0  可以手动去掉
			String tempTime = rs.getString("pro_create");
			//SimpDateFormat 解决也可以的
			if(tempTime!=null&&!tempTime.trim().equals("")) {
				result.setPro_create(tempTime.substring(0, tempTime.lastIndexOf(".")));
			}
		}
		JDBCUtils.release(rs, psmt, conn);
		return result;
	}

	//更新商品的信息
	@Override
	public boolean updateProduct(Product updateProduct) throws Exception {
		Connection conn = JDBCUtils.getConnection();
		String sql = "UPDATE t_product SET pro_name=?,pro_price=?,pro_desc=?,pro_create=?,pro_image=? WHERE pro_id=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, updateProduct.getPro_name());
		psmt.setDouble(2, updateProduct.getPro_price());
		psmt.setString(3, updateProduct.getPro_desc());
		psmt.setString(4,updateProduct.getPro_create());
		psmt.setString(5, updateProduct.getPro_image());
		System.out.println("now:"+updateProduct.getPro_image());
		psmt.setInt(6, updateProduct.getPro_id());
		System.out.println("psmt---------"+psmt);
		int updateResult = psmt.executeUpdate();
		JDBCUtils.release(null, psmt, conn);
		if(updateResult>0) {
			return true;
		}
		return false;
	}

	//删除商品根据商品的id
	@Override
	public boolean deleteProductById(String pro_id) throws Exception {
		Connection conn = JDBCUtils.getConnection();
		String sql = "UPDATE t_product SET pro_state=0 WHERE pro_id=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		System.out.println("ok");
		psmt.setString(1, pro_id);
		int deleteResult = psmt.executeUpdate();
		if(deleteResult>0) {
			return true;
		}
		return false;
	}

	/*
	 * @Override public ArrayList<Product> getProductByCateId(String cate_id)throws
	 * Exception{ // TODO Auto-generated method stub // 连接数据库 采用mysql的jar包进行操作
	 * Connection conn = JDBCUtils.getConnection(); String sql =
	 * "SELECT * FROM t_product WHERE pro_state=1 AND c_id=?"; PreparedStatement
	 * psmt = conn.prepareStatement(sql); psmt.setString(1, cate_id); ResultSet rs =
	 * psmt.executeQuery(); ArrayList<Product> array = new ArrayList<Product>(); //
	 * 不是采用for循环 采用迭代器 while(rs.next()) { Product p = new Product();
	 * p.setPro_id(rs.getInt("pro_id")); p.setPro_name(rs.getString("pro_name"));
	 * p.setPro_price(rs.getDouble("pro_price"));
	 * p.setPro_desc(rs.getString("pro_desc"));
	 * p.setPro_image(rs.getString("pro_image")); //查询数据多一个.0 可以手动去掉 String tempTime
	 * = rs.getString("pro_create"); //SimpDateFormat 解决也可以的
	 * if(tempTime!=null&&!tempTime.trim().equals("")) {
	 * p.setPro_create(tempTime.substring(0, tempTime.lastIndexOf("."))); }
	 * 
	 * String c_id = rs.getString("c_id"); String sqlCate =
	 * "SELECT * FROM t_category WHERE c_id=?"; PreparedStatement catePsmt =
	 * conn.prepareStatement(sqlCate); catePsmt.setString(1, c_id); ResultSet cateRs
	 * = catePsmt.executeQuery(); Category cate = null; while(cateRs.next()) { cate
	 * = new Category(); cate.setC_id(cateRs.getInt("c_id"));
	 * cate.setC_name(cateRs.getString("c_name")); } p.setCate(cate); array.add(p);
	 * } JDBCUtils.release(rs, psmt, conn); return array; }
	 */

	//添加分页参数后的查询方法
	@Override
	public ArrayList<Product> getProductByCateId(String cate_id,String sc_id, String start, String length) throws Exception {
		// TODO Auto-generated method stub
        Connection conn = JDBCUtils.getConnection();
		String sql = "SELECT * FROM t_product WHERE pro_state=1 AND c_id=? AND sc_id=? LIMIT ?,?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, cate_id);
		psmt.setString(2, sc_id);
		psmt.setInt(3, Integer.parseInt(start));
		psmt.setInt(4, Integer.parseInt(length));
		ResultSet rs = psmt.executeQuery();
		ArrayList<Product> array = new ArrayList<Product>();
		// 不是采用for循环  采用迭代器
		while(rs.next()) {
			Product p = new Product();
			p.setPro_id(rs.getInt("pro_id"));
			p.setPro_name(rs.getString("pro_name"));
			p.setPro_price(rs.getDouble("pro_price"));
			p.setPro_desc(rs.getString("pro_desc"));
			p.setPro_image(rs.getString("pro_image"));
			//查询数据多一个.0  可以手动去掉
			String tempTime = rs.getString("pro_create");
			//SimpDateFormat 解决也可以的
			if(tempTime!=null&&!tempTime.trim().equals("")) {
				p.setPro_create(tempTime.substring(0, tempTime.lastIndexOf(".")));
			}
			
			String c_id = rs.getString("c_id");
			String sqlCate = "SELECT * FROM t_category WHERE c_id=?";
			PreparedStatement catePsmt = conn.prepareStatement(sqlCate);
			catePsmt.setString(1, c_id);
			ResultSet cateRs = catePsmt.executeQuery();
			Category cate = null;
			while(cateRs.next()) {
				cate = new Category();
				cate.setC_id(Integer.parseInt(sc_id));
				cate.setF_id(Integer.parseInt(cate_id));
				cate.setC_name(cateRs.getString("c_name"));
			}
			p.setCate(cate);			
			array.add(p);
		}
	    JDBCUtils.release(rs, psmt, conn);
		return array;
	}

	@Override
	public ArrayList<Product> searchContent(String searchContent, String start, String length) throws Exception {
		Connection conn = DruidDataSourceUtils.getConnection();
		String sql = "SELECT * FROM t_product WHERE pro_name LIKE ? LIMIT ?,?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, "%"+searchContent+"%");
		psmt.setInt(2, Integer.parseInt(start));
		psmt.setInt(3, Integer.parseInt(length));
		ResultSet rs = psmt.executeQuery();
		ArrayList<Product> searchResult = new ArrayList<Product>();
		while(rs.next()) {
			Product p = new Product();
			p.setPro_id(rs.getInt("pro_id"));
			p.setPro_name(rs.getString("pro_name"));
			p.setPro_price(rs.getDouble("pro_price"));
			p.setPro_desc(rs.getString("pro_desc"));
			p.setPro_image(rs.getString("pro_image"));
			//查询数据多一个.0  可以手动去掉
			String tempTime = rs.getString("pro_create");
			//SimpDateFormat 解决也可以的
			if(tempTime!=null&&!tempTime.trim().equals("")) {
				p.setPro_create(tempTime.substring(0, tempTime.lastIndexOf(".")));
			}
			searchResult.add(p);
		}
		return searchResult;
	}
	
	@Override
	public boolean insertOrder(Order order) throws Exception {
		Connection conn = MyDataSourceUtils.getCurrentConnection();
		String sql = "INSERT INTO t_orders (o_id,o_total,o_state,o_address,o_name,o_telephone,u_id) VALUE(?,?,?,?,?,?,?)";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, order.getO_id());
		psmt.setDouble(2, order.getO_total());
		psmt.setInt(3, order.getO_state());
		psmt.setString(4, order.getO_address());
		psmt.setString(5, order.getO_name());
		psmt.setString(6, order.getO_tel());
		psmt.setInt(7, order.getUser().getU_id());
		int insertResult = psmt.executeUpdate();
		if(insertResult>0) {
			return true;
		}	
		//MyDataSourceUtils.release(null, psmt, conn);		
		return false;
	}

	@Override
	public boolean insertOrderItem(OrderItem orderItem) throws Exception {
		Connection conn = MyDataSourceUtils.getCurrentConnection();
		String sql = "INSERT INTO t_orderitem (o_itemid,o_count,o_subtotal,pro_id,o_id) VALUE(?,?,?,?,?)";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, orderItem.getO_itemid());
		psmt.setInt(2, orderItem.getO_count());
		psmt.setDouble(3, orderItem.getO_subtotal());
		psmt.setInt(4, orderItem.getProduct().getPro_id());
		psmt.setString(5, orderItem.getOrder().getO_id());
		int insertResult = psmt.executeUpdate();
		if(insertResult>0) {
			return true;
		}	
		//JDBCUtils.release(null, psmt, conn);
		return false;
	}

	@Override
	public boolean submitOrder(Order order) {
		// TODO Auto-generated method stub
		//暂时不考虑  事物   
	    //插入2组数据  第一组订单  第二组订单项
		try {
			MyDataSourceUtils.startTransaction();
			boolean isOrderSuccess = insertOrder(order);
			boolean isOrderItemSuccess = true;
			for(int i=0;i<order.getOrderItems().size();i++) {
				boolean singleOrderItem = insertOrderItem(order.getOrderItems().get(i));
				if(singleOrderItem==false) {
					isOrderItemSuccess = false;
					break;
				}
			}
			if(isOrderSuccess&&isOrderItemSuccess) {
				MyDataSourceUtils.commit();
				return true;
			}else {
				MyDataSourceUtils.rollback();
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				MyDataSourceUtils.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public boolean updateOrderStateSuccess(String o_id) throws Exception {
		Connection conn = JDBCUtils.getConnection();
		String sql = "UPDATE t_orders SET o_state =1 WHERE o_id=?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, o_id);
		int deleteResult = psmt.executeUpdate();
		JDBCUtils.release(null, psmt, conn);
		if(deleteResult>0) {
			return true;
		}
		return false;
	}
	//先查询订单号 在查询订单项
	public List<OrderItem> selectOrderItemByOrderId(String o_id) throws Exception{
		Connection conn = DruidDataSourceUtils.getConnection();
		String sql = "SELECT * FROM t_orderitem WHERE o_id = ? ";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, o_id);
		ResultSet rs = psmt.executeQuery();
		ArrayList<OrderItem> searchResult = new ArrayList<OrderItem>();
		while(rs.next()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setO_count(rs.getInt("o_count"));
			orderItem.setO_itemid(rs.getString("o_itemid"));
			orderItem.setO_subtotal(rs.getDouble("o_subtotal"));
			int pro_id = rs.getInt("pro_id");
			Product p = selectProductById(pro_id);
			orderItem.setProduct(p);
			searchResult.add(orderItem);
		}
		DruidDataSourceUtils.release(rs, psmt, conn);
		return searchResult;	
	}
	

	@Override
	public List<Order> selectOrdersByUserId(String u_id,String start, String length) throws Exception {
		Connection conn = DruidDataSourceUtils.getConnection();
		String sql = "SELECT * FROM t_orders WHERE u_id = ? ORDER BY o_ordertime desc LIMIT ?,?";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setInt(1, Integer.parseInt(u_id));
		psmt.setInt(2, Integer.parseInt(start));
		psmt.setInt(3, Integer.parseInt(length));
		ResultSet rs = psmt.executeQuery();
		ArrayList<Order> searchResult = new ArrayList<Order>();
		while(rs.next()) {
			Order o = new Order();
			o.setO_id(rs.getString("o_id"));
		    o.setO_address(rs.getString("o_address"));
		    o.setO_name(rs.getString("o_name"));
		    o.setO_ordertime(rs.getString("o_ordertime"));
		    o.setO_state(rs.getInt("o_state"));
		    o.setO_tel(rs.getString("o_telephone"));
		    o.setO_total(rs.getDouble("o_total"));
		    //查询订单项的数据
		    List<OrderItem> orderItems = selectOrderItemByOrderId(o.getO_id());
			o.setOrderItems(orderItems);
			searchResult.add(o);
		}
		DruidDataSourceUtils.release(rs, psmt, conn);
		return searchResult;
	}
}
