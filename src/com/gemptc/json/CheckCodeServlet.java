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
//У����֤��
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
		// ����һ����֤����ͻ���
		response.setContentType("application/json;charset=utf-8");
		String code = (String)request.getSession().getAttribute("checkcode_session");
		//��ʱ���������ݿ�   �޸�ΪDao��Ļ�ȡ��֤�� �û��� �п��ܲ�����  selectCodeFromName()
		//��ʱ���������ݿ�   ���õ����ֻ���ȡ��֤��  �ֻ����ǲ���Ψһ   selectCodeFromPhone()
		String passedCode = request.getParameter("code");
		Map<String,String> map = new HashMap<String, String>();
		if( code == null) {
			//����û��������֤��
			map.put("retcode", "-1");
			map.put("data", "��֤ʧ��û����֤��");
			response.getWriter().write(JSONObject.fromObject(map).toString());
		}else {
			if(code.equals(passedCode)) {
				map.put("retcode", "0");
				map.put("data", "��֤�ɹ�");
			}else {
				map.put("retcode", "-2");
				map.put("data", "��֤ʧ��");
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
