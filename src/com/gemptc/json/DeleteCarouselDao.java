package com.gemptc.json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gemptc.dao.CarouselDao;
import com.gemptc.dao.CarouselDaoImp;
import com.gemptc.dao.ProductDao;
import com.gemptc.dao.ProductDaoImp;
import com.gemptc.entity.Product;
import com.gemptc.util.JSONResult;

public class DeleteCarouselDao extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String car_id = request.getParameter("car_id");
		if(car_id!=null&&!car_id.trim().equals("")) {
			CarouselDao dao = new CarouselDaoImp();
			try {
				boolean sec = dao.deleceCarouselsByCarID(car_id);
				   if(sec==true) {
						JSONResult.JSONReturnWithData("0", "删除成功", response);
					}else {
						JSONResult.JSONReturnWithData("1", "删除失败了", response);
					}
				
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSONResult.JSONReturnWithData("3", "数据库操作异常", response);
			}	
		}else {
			JSONResult.JSONReturnWithData("4", "没有给商品id参数", response);
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
