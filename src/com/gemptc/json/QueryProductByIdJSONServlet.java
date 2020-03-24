package com.gemptc.json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gemptc.dao.ProductDao;
import com.gemptc.dao.ProductDaoImp;
import com.gemptc.entity.Product;
import com.gemptc.util.JSONResult;

/**
 * Servlet implementation class QueryProductByIdJSONServlet
 */
@WebServlet("/queryProductByIdJSON")
public class QueryProductByIdJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryProductByIdJSONServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��Ҫ��ѯ����
		response.setContentType("text/html;charset=UTF-8");
		String pro_id = request.getParameter("pro_id");
		
		if(pro_id!=null&&!pro_id.trim().equals("")) {
			ProductDao dao = new ProductDaoImp();
			try {
				Product product = dao.selectProductById(Integer.parseInt(pro_id));
				if(product!=null) {
					JSONResult.JSONReturnWithData("0", product, response);
				}else {
					JSONResult.JSONReturnWithData("2", "��Ʒ������", response);
				}
			} catch (Exception e) {
				JSONResult.JSONReturnWithData("3", "��ѯ���ݿ��쳣", response);
			}
		}else {
			//���߿ͻ���һ��״̬��  �������������Ϣ   5�� ����    ��װΪ1�д���  �����һ����  �Զ���Ĺ���
			JSONResult.JSONReturnWithData("1", "û���ύpro_d�������߲���ֵΪ��", response);
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
