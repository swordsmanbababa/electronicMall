package com.algorithm.yl.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import com.algorithm.yl.constants.CommonConstants;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**      
 * @文件名称: GetExcelInfo.java   
 * @类路径: com.algorithm.yl.util   
 * @描述: TODO   
 * @作者：77370
 * @时间：2018年10月19日 上午10:29:54     
 */
public class GetExcelInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		    GetExcelInfo obj = new GetExcelInfo();  
	        File file = new File("file/config/container_event.xls");  
	        File toTxtFile=new File("file/config/init.txt");
	        obj.readExcelWrite2TXT(file,toTxtFile);
	

	}

	  public void readExcelWrite2TXT(File file,File toTxtFile) {  
			int vm_num = CommonConstants.vm_num;
	     	int ProgenyCount_num=CommonConstants.ProgenyCount_num;
			int physical_node_num=CommonConstants.physical_node_num;
			Double []vmMem = new Double[vm_num];
			Double []vmCpu = new Double[vm_num];

	        // 创建文件输出流  
			FileWriter writer=null;
	        try {    
	       		writer=new FileWriter(toTxtFile);
	    	    
	            // 创建输入流，读取Excel  
	            InputStream is = new FileInputStream(file.getAbsolutePath());  
	            // jxl提供的Workbook类  
	            Workbook wb = Workbook.getWorkbook(is);  
	            // Excel的页签数量  
	                // 为第0页创建一个Sheet对象  
	                Sheet sheet = wb.getSheet(0);  
	                // sheet.getRows()返回该页的总行数  
	                for (int i = 0; i < vm_num; i++) {  
	                        int cpu_required = Integer.parseInt(sheet.getCell(4, i).getContents());  
	                        double normalized_cpu_req=Double.parseDouble(sheet.getCell(5, i).getContents());
	                        double normalized_mem_req=Double.parseDouble(sheet.getCell(6, i).getContents());
	                        vmCpu[i]=cpu_required*normalized_cpu_req;
	                        vmMem[i]=normalized_mem_req;
	                        // 将从Excel中读取的数据写入到txt中   
	                }
	                
	            	StringBuffer buffer = new StringBuffer();
					for(int i=0; i<ProgenyCount_num; i++){
	        			for(int j=0; j<vm_num; j++){
							int target = CommonUtil.generateIntNumber(physical_node_num);
	        				buffer.append(target+":"+vmMem[j]+":"+vmCpu[j]+";");
	        			}
	        			buffer.append("\n");
	        		}
	            	writer.write(buffer.toString());
	                System.out.println("finished!!!");
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (BiffException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                // 记得关闭流  
	                writer.flush();  
	            	writer.close();  
	                // 由于此处用到了缓冲流，如果数据量过大，不进行flush操作，某些数据将依旧  
	                // 存在于内从中而不会写入文件，此问题一定要注意  

	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  

}
