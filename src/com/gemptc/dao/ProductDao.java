package com.gemptc.dao;

import java.util.ArrayList;
import java.util.List;

import com.gemptc.entity.Order;
import com.gemptc.entity.OrderItem;
import com.gemptc.entity.Product;

//Dao    Data Access Object
public interface ProductDao {
	
	//�ӿ����涼�ǳ���ķ���  ��Ҫ��ȥʵ��
	public List<Product>selectAllProduct() throws Exception;

	public boolean insertProduct(Product insertProduct) throws Exception;
	
	public Product selectProductById(int pro_id) throws Exception;

	public boolean updateProduct(Product updateProduct) throws Exception;

	public boolean deleteProductById(String pro_id) throws Exception;

	//��ӷ���Ĳ�����Ϣ
//	public ArrayList<Product> getProductByCateId(String fc_id) throws Exception;

	//����Ҫ��ҳ�Ĳ���
	public ArrayList<Product> getProductByCateId(String cate_id,String sc_id,String start,String length) throws Exception;

	public ArrayList<Product> searchContent(String searchContent, String start, String length)throws Exception;
	
	public boolean insertOrder(Order order) throws Exception;
	
	public boolean insertOrderItem(OrderItem orderItem) throws Exception;

	//����ύ���� ���������2������
	public boolean submitOrder(Order order);
	
	public boolean updateOrderStateSuccess(String o_id) throws Exception;

	public List<Order> selectOrdersByUserId(String u_id,String start,String length) throws Exception;

	public List<OrderItem> selectOrderItemByOrderId(String o_id) throws Exception;
}
