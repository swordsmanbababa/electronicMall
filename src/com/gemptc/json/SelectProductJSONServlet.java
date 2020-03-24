package com.gemptc.json;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gemptc.dao.ProductDao;
import com.gemptc.dao.ProductDaoImp;
import com.gemptc.entity.Product;
import com.gemptc.entity.ResultObj;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class SelectProductJSONServlet
 */
@WebServlet("/selectProductJSON")
public class SelectProductJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectProductJSONServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductDao  dao = new ProductDaoImp();  
		//��̬
		//try...catch  ��  throws Exception��ʲô����  ������ģ�
		// ��Ҫ�жϸ���״̬ 
		// 1 ���ݿ�û������
		// 2 ��ѯ�쳣��
		// 3�������쳣��
		response.setContentType("text/html;charset=UTF-8");
		ResultObj result = new ResultObj();
		try {
			 List<Product> allProduct = dao.selectAllProduct();
			 if(allProduct.size()>0) {
				 result.setRetcode("0");
				 result.setData(allProduct);
				//JAVA����Ҫ��JAVA����ת��ΪJSON�ַ���   ʵ��ǰ��˵����ݷ���
				 String resultStr = JSONObject.fromObject(result).toString();
				 response.getWriter().write(resultStr);
			 }else {
				 result.setRetcode("1");
				 result.setData("���ݿ���ʱû������");
				 String resultStr = JSONObject.fromObject(result).toString();
				 response.getWriter().write(resultStr);
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setRetcode("2");
			result.setData("��������æ");
			String resultStr = JSONObject.fromObject(result).toString();
			response.getWriter().write(resultStr);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
