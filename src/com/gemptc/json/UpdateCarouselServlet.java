package com.gemptc.json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gemptc.dao.CarouselDao;
import com.gemptc.dao.CarouselDaoImp;
import com.gemptc.dao.ProductDao;
import com.gemptc.dao.ProductDaoImp;
import com.gemptc.entity.Carousel;
import com.gemptc.entity.Product;
import com.gemptc.util.JSONResult;

@WebServlet("/updateCarousel")
public class UpdateCarouselServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		JSONResult.JSONReturnWithData("1", "必须采用POST方式提交订单", response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String car_id = request.getParameter("car_id");
	
		String pro_name = request.getParameter("pro_name");
		String pro_img = request.getParameter("pro_img");
		System.out.println("______________________"+pro_img);
		Carousel carousel=new Carousel(); 
		carousel.setCar_id(Integer.parseInt(car_id));
		carousel.setPro_name(pro_name);
		carousel.setPro_img(pro_img);
		
		// 判断获取的参数是否为空的逻辑
		CarouselDao dao = new CarouselDaoImp();
		try {
			if (dao.updateCarouselByCarID(carousel)) {
				JSONResult.JSONReturnWithData("0", "更新成功", response);
			} else {
				JSONResult.JSONReturnWithData("1", "更新失败了", response);
			}  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONResult.JSONReturnWithData("3", "服务器繁忙", response);
		}
	}
}
