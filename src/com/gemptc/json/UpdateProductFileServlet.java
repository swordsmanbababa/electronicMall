package com.gemptc.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.gemptc.dao.ProductDao;
import com.gemptc.dao.ProductDaoImp;
import com.gemptc.entity.Product;
import com.gemptc.util.JSONResult;
import com.gemptc.util.StringUUID;

/**
 * Servlet implementation class UpdateProductFileServlet
 */
@WebServlet("/updateProductFile")
public class UpdateProductFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProductFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		JSONResult.JSONReturnWithData("1", "����POST�ύ�ſ����ϴ�����", response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//��POST��ȡ�ϴ���ͼƬ����
		response.setContentType("text/html;charset=UTF-8");
		if(ServletFileUpload.isMultipartContent(request)){
			Map<String,Object> paraMap = new HashMap<String,Object>();
			DiskFileItemFactory cache = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(cache);
			upload.setHeaderEncoding("UTF-8");  //�����ļ����ı���ΪUTF-8
			try {
				@SuppressWarnings("unchecked")
				List<FileItem> fileItems = upload.parseRequest(request);
				for(FileItem item:fileItems) {
					if(item.isFormField()) { //��ͨ����
						paraMap.put(item.getFieldName(), item.getString("UTF-8"));
					}else {
						//��������ļ���  �����ļ��Ķ�������
						InputStream inStream = item.getInputStream();
						//�ж��Ƿ���ڶ�����  �Ƿ����ͼƬ�Ķ�����
						if(inStream.available()>0) {
							//���� д�뵽�����ļ���
							//�����ļ�����  UUID +ʱ��
							String tempFileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_sss").format(new Date());
							String fileName = tempFileName+StringUUID.getUUID();
							//��׺����
							String MIME = item.getContentType();
							int index = MIME.lastIndexOf("/");
							if(index!=-1) {
								fileName +="."+MIME.substring(index+1);
							}
							System.out.println("���ļ���:"+fileName);
							//д�����ļ�
							String path = StringUUID.getUploadDir();
							OutputStream outStream = new FileOutputStream(path+fileName);
							IOUtils.copy(inStream, outStream);
							inStream.close();
							outStream.close();
							item.delete();
							String originalPro_image = (String) paraMap.get("pro_image");
							paraMap.put("pro_image", fileName);
							
							//ɾ��ԭ���ͼƬ��Ϣ ��ȡԭ����ͼƬ����
							String originalFileName = path+originalPro_image;
							System.out.println("originalFileName:"+originalFileName);
							File tempFile = new File(originalFileName);
							if(tempFile.delete()) {
								System.out.println("ɾ��ԭ����ͼƬ�ɹ�");
							}else {
								System.out.println("ɾ��ԭ���ͼƬʧ��");
							}
						}	
					}
				}
				//��Mapת��ΪProduct
				Product updateProduct = new Product();
				BeanUtils.populate(updateProduct, paraMap);
				//�������ݿ�
				System.out.println(updateProduct);
				ProductDao dao = new ProductDaoImp();
				if(dao.updateProduct(updateProduct)) {
					response.sendRedirect(request.getContextPath()+"/admin/site/index.jsp");		
				}else {
					JSONResult.JSONReturnWithData("4", "�������ݿ�ʧ��", response);
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSONResult.JSONReturnWithData("3", "���������쳣", response);
			} 
		}else {
		   JSONResult.JSONReturnWithData("2", "�ϴ������ݲ��Ƕ��ļ��ı�", response);	
		}
	}

}
