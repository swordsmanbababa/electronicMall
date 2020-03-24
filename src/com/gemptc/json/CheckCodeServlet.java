package com.gemptc.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetCheckCodeServlet
 */
//校验验证码
@WebServlet("/checkCode")
public class CheckCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckCodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 返回一个验证码给客户端
		response.setContentType("application/json;charset=utf-8");
		String code = (String)request.getSession().getAttribute("checkcode_session");
		//暂时不采用数据库   修改为Dao层的获取验证码 用户名 有可能不存在  selectCodeFromName()
		//暂时不采用数据库   采用的是手机获取验证码  手机号是不是唯一   selectCodeFromPhone()
		String passedCode = request.getParameter("code");
		Map<String,String> map = new HashMap<String, String>();
		if( code == null) {
			//咱们没有生成验证码
			map.put("retcode", "-1");
			map.put("data", "验证失败没有验证码");
			response.getWriter().write(JSONObject.fromObject(map).toString());
		}else {
			if(code.equals(passedCode)) {
				map.put("retcode", "0");
				map.put("data", "验证成功");
			}else {
				map.put("retcode", "-2");
				map.put("data", "验证失败");
			}
			response.getWriter().write(JSONObject.fromObject(map).toString());			
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
