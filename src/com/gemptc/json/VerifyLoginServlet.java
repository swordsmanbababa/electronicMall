package com.gemptc.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.gemptc.dao.UserDao;
import com.gemptc.dao.UserDaoImp;
import com.gemptc.entity.User;
import com.gemptc.thirdcode.GeetestConfig;
import com.gemptc.thirdcode.GeetestLib;
import com.gemptc.util.JSONResult;



/**
 * ʹ��post��ʽ��������֤���, request���б������challenge, validate, seccode
 */
@WebServlet("/gt/ajax-validate2")
public class VerifyLoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), 
				GeetestConfig.isnewfailback());
			
		String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
		String validate = request.getParameter(GeetestLib.fn_geetest_validate);
		String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
		
		//��session�л�ȡgt-server״̬
		int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
		
		//��session�л�ȡuserid
		String userid = (String)request.getSession().getAttribute("userid");
		
		//�Զ������,��ѡ�����
		HashMap<String, String> param = new HashMap<String, String>(); 
		param.put("user_id", userid); //��վ�û�id
		param.put("client_type", "web"); //web:�����ϵ��������h5:�ֻ��ϵ�������������ƶ�Ӧ������ȫ���õ�web_view��native��ͨ��ԭ��SDKֲ��APPӦ�õķ�ʽ
		param.put("ip_address", "127.0.0.1"); //�����û�������֤ʱ��Я����IP
		
		System.out.println(param);
		
		int gtResult = 0;

		if (gt_server_status_code == 1) {
			//gt-server��������gt-server���ж�����֤
				
			gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
			System.out.println(gtResult);
		} else {
			// gt-server����������£�����failbackģʽ��֤
				
			System.out.println("failback:use your own server captcha validate");
			gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
			System.out.println(gtResult);
		}
		if (gtResult == 1) {
			// ��֤�ɹ�
			// �ߺ��� ��¼���߼�   ��¼ȥ��֤�û���������
			String username = request.getParameter("name");
			String password = request.getParameter("password");
			
			try {
				UserDao dao = new UserDaoImp();
				User user = dao.searchUserByUserName(username);
				if(user!=null) {
					if(user.getU_password().equals(password)) {
						JSONResult.JSONReturnWithData("0", "��¼�ɹ�", response);
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
		else {
			// ��֤ʧ��
			JSONResult.JSONReturnWithData("4", "��֤����֤ʧ��", response);
		}
	}
}
