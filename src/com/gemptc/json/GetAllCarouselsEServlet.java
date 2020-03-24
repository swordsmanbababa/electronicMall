package com.gemptc.json;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gemptc.dao.CarouselDao;
import com.gemptc.dao.CarouselDaoImp;
import com.gemptc.dao.UserDao;
import com.gemptc.dao.UserDaoImp;
import com.gemptc.entity.Carousel;
import com.gemptc.entity.User;
import com.gemptc.util.JSONResult;

@WebServlet("/getAllCarouselJSON")
public class GetAllCarouselsEServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		CarouselDao dao=new CarouselDaoImp();
		try {
			List<Carousel> array=dao.searchAllCarouses();
			System.out.println(""+array);
			if(array.size()>0) {
				JSONResult.JSONReturnWithData("0", array, response);
			}else {
				JSONResult.JSONReturnWithData("1", "ÔÝÎÞÂÖ²¥ÐÅÏ¢", response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
