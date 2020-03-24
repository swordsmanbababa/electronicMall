package com.gemptc.thirdcode;

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.GenericArrayType;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.print.DocFlavor.STRING;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Java SDK
 * 
 */
public class GeetestLib {

	protected final String verName = "4.0";
	protected final String sdkLang = "java";

	protected final String apiUrl = "http://api.geetest.com"; 
	
	protected final String registerUrl = "/register.php"; 
	protected final String validateUrl = "/validate.php";
	
	protected final String json_format = "1";
     
	/**
	 * ������֤������֤������ chllenge
	 */
	public static final String fn_geetest_challenge = "geetest_challenge";
	
	/**
	 * ������֤������֤������ validate
	 */
	public static final String fn_geetest_validate = "geetest_validate";
	
	/**
	 * ������֤������֤������ seccode
	 */
	public static final String fn_geetest_seccode = "geetest_seccode";

	/**
	 * ��Կ
	 */
	private String captchaId = "";

	/**
	 * ˽Կ
	 */
	private String privateKey = "";
	
	/**
	 * �Ƿ����µ�failback
	 */
	private boolean newFailback = false;
	
	/**
	 * �����ַ���
	 */
	private String responseStr = "";
	
	/**
	 * ���Կ��أ��Ƿ����������־
	 */
	public boolean debugCode = true;
	
	/**
	 * ������֤API����״̬Session Key
	 */
	public String gtServerStatusSessionKey = "gt_server_status";
	
	/**
	 * ���������캯��
	 * 
	 * @param captchaId
	 * @param privateKey
	 */
	public GeetestLib(String captchaId, String privateKey, boolean newFailback) {
		
		this.captchaId = captchaId;
		this.privateKey = privateKey;
		this.newFailback = newFailback;
	}
	
	/**
	 * ��ȡ������֤��ʼ�������ַ���
	 * 
	 * @return ��ʼ�����
	 */
	public String getResponseStr() {
		
		return responseStr;
		
	}
	
	public String getVersionInfo() {
		
		return verName;
		
	}

	/**
	 * Ԥ����ʧ�ܺ�ķ��ظ�ʽ��
	 * 
	 * @return
	 */
	private String getFailPreProcessRes() {

		Long rnd1 = Math.round(Math.random() * 100);
		Long rnd2 = Math.round(Math.random() * 100);
		String md5Str1 = md5Encode(rnd1 + "");
		String md5Str2 = md5Encode(rnd2 + "");
		String challenge = md5Str1 + md5Str2.substring(0, 2);
		
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("success", 0);
			jsonObject.put("gt", this.captchaId);
			jsonObject.put("challenge", challenge);
			jsonObject.put("new_captcha", this.newFailback);
			
		} catch (JSONException e) {
			
			gtlog("json dumps error");
			
		}
		
		return jsonObject.toString();
		
	}

	/**
	 * Ԥ����ɹ���ı�׼��
	 * 
	 */
	private String getSuccessPreProcessRes(String challenge) {
		
		gtlog("challenge:" + challenge);
		
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("success", 1);
			jsonObject.put("gt", this.captchaId);
			jsonObject.put("challenge", challenge);
			
		} catch (JSONException e) {
			
			gtlog("json dumps error");
			
		}
		
		return jsonObject.toString();
		
	}
	
	/**
	 * ��֤��ʼ��Ԥ����
	 *
	 * @return 1��ʾ��ʼ���ɹ���0��ʾ��ʼ��ʧ��
	 */
	public int preProcess(HashMap<String, String> data) {

		if (registerChallenge(data) != 1) {
			
			this.responseStr = this.getFailPreProcessRes();
			return 0;
			
		}
		
		return 1;

	}

	/**
	 * ��captchaID����ע�ᣬ����challenge
	 * 
	 * @return 1��ʾע��ɹ���0��ʾע��ʧ��
	 */
	private int registerChallenge(HashMap<String, String>data) {
		
		try {
			String userId = data.get("user_id");
			String clientType = data.get("client_type");
			String ipAddress = data.get("ip_address");
			
			String getUrl = apiUrl + registerUrl + "?";
			String param = "gt=" + this.captchaId + "&json_format=" + this.json_format;
			
			if (userId != null){
				param = param + "&user_id=" + userId;
			}
			if (clientType != null){
				param = param + "&client_type=" + clientType;
			}
			if (ipAddress != null){
				param = param + "&ip_address=" + ipAddress;
			}
			
			gtlog("GET_URL:" + getUrl + param);
			String result_str = readContentFromGet(getUrl + param);
			if (result_str == "fail"){
				
				gtlog("gtServer register challenge failed");
				return 0;
				
			}
			
			gtlog("result:" + result_str);
			JSONObject jsonObject = new JSONObject(result_str);
		    String return_challenge = jsonObject.getString("challenge");
		
			gtlog("return_challenge:" + return_challenge);
			
			if (return_challenge.length() == 32) {
				
				this.responseStr = this.getSuccessPreProcessRes(this.md5Encode(return_challenge + this.privateKey));
				
			    return 1;
			    
			}
			else {
				
				gtlog("gtServer register challenge error");
					
				return 0;
				
			}
		} catch (Exception e) {
			
			gtlog(e.toString());
			gtlog("exception:register api");
			
		}
		return 0;
	}
	
	/**
	 * �ж�һ��������ֵ�Ƿ�Ϊ��
	 * 
	 * @param gtObj
	 * @return
	 */
	protected boolean objIsEmpty(Object gtObj) {
		
		if (gtObj == null) {
			
			return true;
			
		}

		if (gtObj.toString().trim().length() == 0) {
			
			return true;
			
		}

		return false;
	}

	/**
	 * ���ͻ��˵������Ƿ�Ϸ�,����ֻҪ��һ��Ϊ�գ����жϲ��Ϸ�
	 * 
	 * @param request
	 * @return
	 */
	private boolean resquestIsLegal(String challenge, String validate, String seccode) {

		if (objIsEmpty(challenge)) {
			
			return false;
			
		}

		if (objIsEmpty(validate)) {
			
			return false;
			
		}

		if (objIsEmpty(seccode)) {
			
			return false;
			
		}

		return true;
	}
	
	
	/**
	 * ���������������ʹ�õ���֤��ʽ,��gt-server���ж�����֤,��ȡ��֤���
	 * 
	 * @param challenge
	 * @param validate
	 * @param seccode
	 * @return ��֤���,1��ʾ��֤�ɹ�0��ʾ��֤ʧ��
	 */
	public int enhencedValidateRequest(String challenge, String validate, String seccode, HashMap<String, String> data) {	
		
		if (!resquestIsLegal(challenge, validate, seccode)) {
			
			return 0;
			
		}
		
		gtlog("request legitimate");
		
		String userId = data.get("user_id");
		String clientType = data.get("client_type");
		String ipAddress = data.get("ip_address");
		
		String postUrl = this.apiUrl + this.validateUrl;
		String param = String.format("challenge=%s&validate=%s&seccode=%s&json_format=%s", 
				                     challenge, validate, seccode, this.json_format);
		
		if (userId != null){
			param = param + "&user_id=" + userId;
		}
		if (clientType != null){
			param = param + "&client_type=" + clientType;
		}
		if (ipAddress != null){
			param = param + "&ip_address=" + ipAddress;
		}
		
		gtlog("param:" + param);
		
		String response = "";
		try {
			
			if (validate.length() <= 0) {
				
				return 0;
				
			}

			if (!checkResultByPrivate(challenge, validate)) {
				
				return 0;
				
			}
			
			gtlog("checkResultByPrivate");
			
			response = readContentFromPost(postUrl, param);

			gtlog("response: " + response);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		String return_seccode = "";
		
		try {
			
			JSONObject return_map = new JSONObject(response);
			return_seccode = return_map.getString("seccode");
			gtlog("md5: " + md5Encode(return_seccode));

			if (return_seccode.equals(md5Encode(seccode))) {
				
				return 1;
				
			} else {
				
				return 0;
				
			}
			
		} catch (JSONException e) {
			
		
			gtlog("json load error");
			return 0;
			
		}
		
	}

	/**
	 * failbackʹ�õ���֤��ʽ
	 * 
	 * @param challenge
	 * @param validate
	 * @param seccode
	 * @return ��֤���,1��ʾ��֤�ɹ�0��ʾ��֤ʧ��
	 */
	public int failbackValidateRequest(String challenge, String validate, String seccode) {

		gtlog("in failback validate");

		if (!resquestIsLegal(challenge, validate, seccode)) {
			return 0;
		}
		gtlog("request legitimate");

		return 1;
	}

	/**
	 * ���debug��Ϣ����Ҫ����debugCode
	 * 
	 * @param message
	 */
	public void gtlog(String message) {
		if (debugCode) {
			System.out.println("gtlog: " + message);
		}
	}

	protected boolean checkResultByPrivate(String challenge, String validate) {
		String encodeStr = md5Encode(privateKey + "geetest" + challenge);
		return validate.equals(encodeStr);
	}
	
	/**
	 * ����GET���󣬻�ȡ���������ؽ��
	 * 
	 * @param getURL
	 * @return ���������ؽ��
	 * @throws IOException
	 */
	private String readContentFromGet(String URL) throws IOException {

		URL getUrl = new URL(URL);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();

		connection.setConnectTimeout(2000);// ��������������ʱ����λ�����룩
		connection.setReadTimeout(2000);// ���ô�������ȡ���ݳ�ʱ����λ�����룩

		// ����������������ӣ���δ��������
		connection.connect();
		
		if (connection.getResponseCode() == 200) {
			// �������ݵ���������ʹ��Reader��ȡ���ص�����
			StringBuffer sBuffer = new StringBuffer();

			InputStream inStream = null;
			byte[] buf = new byte[1024];
			inStream = connection.getInputStream();
			for (int n; (n = inStream.read(buf)) != -1;) {
				sBuffer.append(new String(buf, 0, n, "UTF-8"));
			}
			inStream.close();
			connection.disconnect();// �Ͽ�����
            
			return sBuffer.toString();	
		}
		else {
			
			return "fail";
		}
	}
	
	/**
	 * ����POST���󣬻�ȡ���������ؽ��
	 * 
	 * @param getURL
	 * @return ���������ؽ��
	 * @throws IOException
	 */
	private String readContentFromPost(String URL, String data) throws IOException {
		
		gtlog(data);
		URL postUrl = new URL(URL);
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();

		connection.setConnectTimeout(2000);// ��������������ʱ����λ�����룩
		connection.setReadTimeout(2000);// ���ô�������ȡ���ݳ�ʱ����λ�����룩
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		// ����������������ӣ���δ��������
		connection.connect();
		
		 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "utf-8");  
		 outputStreamWriter.write(data);  
		 outputStreamWriter.flush();
		 outputStreamWriter.close();
		
		if (connection.getResponseCode() == 200) {
			// �������ݵ���������ʹ��Reader��ȡ���ص�����
			StringBuffer sBuffer = new StringBuffer();

			InputStream inStream = null;
			byte[] buf = new byte[1024];
			inStream = connection.getInputStream();
			for (int n; (n = inStream.read(buf)) != -1;) {
				sBuffer.append(new String(buf, 0, n, "UTF-8"));
			}
			inStream.close();
			connection.disconnect();// �Ͽ�����
            
			return sBuffer.toString();	
		}
		else {
			
			return "fail";
		}
	}

	/**
	 * md5 ����
	 * 
	 * @time 2014��7��10�� ����3:30:01
	 * @param plainText
	 * @return
	 */
	private String md5Encode(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}

}
