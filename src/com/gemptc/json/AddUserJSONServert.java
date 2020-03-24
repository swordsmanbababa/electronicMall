package com.gemptc.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gemptc.dao.ProductDao;
import com.gemptc.dao.ProductDaoImp;
import com.gemptc.dao.UserDao;
import com.gemptc.dao.UserDaoImp;
import com.gemptc.entity.Product;
import com.gemptc.entity.ResultObj;
import com.gemptc.entity.User;

import net.sf.json.JSONObject;

@WebServlet("/addUserJSON")
public class AddUserJSONServert extends HttpServlet{
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		ResultObj result = new ResultObj();
		result.setRetcode("4");
		result.setData("请采用POST提交数据");
		response.getWriter().write(JSONObject.fromObject(result).toString());
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String u_name = request.getParameter("u_name");
		String u_password = request.getParameter("u_password");
		System.out.println("pro_create:"+u_password);
		ResultObj result = new ResultObj();
		
		if(u_name!=null&&!u_name.trim().equals("")&&u_password!=null&&!u_password.trim().equals("")) {
			//插入数据库
			User insertUser=new User();
			insertUser.setU_name(u_name);
			insertUser.setU_password(u_password);
			UserDao dao=new UserDaoImp();
			
			try {
				if(dao.userRegister(insertUser)) {
					result.setRetcode("0");
					result.setData("插入成功");
					response.getWriter().write(JSONObject.fromObject(result).toString());
				}else {
					result.setRetcode("1");
					result.setData("插入失败");
					response.getWriter().write(JSONObject.fromObject(result).toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setRetcode("2");
				result.setData("服务器繁忙");
				response.getWriter().write(JSONObject.fromObject(result).toString());
			}
		}else {
			result.setRetcode("3");
			result.setData("没有提交参数");
			response.getWriter().write(JSONObject.fromObject(result).toString());
		}
	}
}
