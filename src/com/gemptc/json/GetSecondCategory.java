package com.gemptc.json;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gemptc.dao.CategoryDao;
import com.gemptc.dao.CategoryDaoImp;
import com.gemptc.entity.Category;
import com.gemptc.util.JSONResult;

import net.sf.json.JSONObject;

@WebServlet("/getSecondCategory")
public class GetSecondCategory extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String f_id_json=req.getParameter("f_id");
//		String f_id_string=JSONObject.fromObject(f_id_json).toString();
		int f_id=Integer.parseInt(f_id_json);
		resp.setContentType("text/html;charset=UTF-8");
		CategoryDao dao = new CategoryDaoImp();
		try {
			ArrayList<Category> allCategory = dao.getSecondCategory(f_id);
			if(allCategory.size()>0) {
				JSONResult.JSONReturnWithData("0", allCategory, resp);
			}else {
				JSONResult.JSONReturnWithData("1", "暂无分类信息", resp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONResult.JSONReturnWithData("2", "服务器繁忙", resp);
		}
	}

}
