package com.gemptc.json;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gemptc.dao.UserDao;
import com.gemptc.dao.UserDaoImp;
import com.gemptc.entity.User;
import com.gemptc.util.JSONResult;

/**
 * Servlet implementation class QueryUserByNameServlet
 */
@WebServlet("/queryUserByName")
public class QueryUserByNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryUserByNameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		try {
			UserDao dao = new UserDaoImp();
			User user = dao.searchFrontUserByUserName(username);
			if(user!=null) {
				if(user.getU_password().equals(password)) {
					user.setU_password(""); //ȥ���û�������  Ȼ���͸��ͻ���
					JSONResult.JSONReturnWithData("0",user, response);
				}else {
					JSONResult.JSONReturnWithData("1", "�û��������", response);
				}
			}else {
				JSONResult.JSONReturnWithData("2", "�û���������", response);
			}			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JSONResult.JSONReturnWithData("3", "��������æ", response);
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
