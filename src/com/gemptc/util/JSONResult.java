package com.gemptc.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.gemptc.entity.ResultObj;

import net.sf.json.JSONObject;

public class JSONResult {
	public static void JSONReturnWithData(String retcode,Object data,HttpServletResponse response) {
		 try {
			ResultObj result = new ResultObj();
			 result.setRetcode(retcode);
			 result.setData(data);
			//JAVA����Ҫ��JAVA����ת��ΪJSON�ַ���   ʵ��ǰ��˵����ݷ���
			 String resultStr = JSONObject.fromObject(result).toString();
			 response.getWriter().write(resultStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
