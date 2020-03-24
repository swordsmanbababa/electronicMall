package com.gemptc.json;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class GetProductByCateIdServlet
 */
@WebServlet("/getProductByCateId")
public class GetProductByCateIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProductByCateIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//ģ��������رȽ��������
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		String cate_id = request.getParameter("c_id");
		String sc_id = request.getParameter("sc_id");
		String start = request.getParameter("start");
		String length = request.getParameter("length");
		if(cate_id!=null&&!cate_id.trim().equals("")) {
			ProductDao dao = new ProductDaoImp();
			try {
				ArrayList<Product> arrayList = dao.getProductByCateId(cate_id,sc_id,start,length);
				//System.out.println(arrayList);
				if(arrayList.size()>0) {
					JSONResult.JSONReturnWithData("0", arrayList, response);
				}else {
					JSONResult.JSONReturnWithData("1", "������Ʒ��Ϣ", response);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSONResult.JSONReturnWithData("2", "��ѯ�쳣", response);
			}
		}else {
			JSONResult.JSONReturnWithData("3", "û���ύ�������", response);
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
