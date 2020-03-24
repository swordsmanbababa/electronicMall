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
 * @�ļ�����: GetExcelInfo.java   
 * @��·��: com.algorithm.yl.util   
 * @����: TODO   
 * @���ߣ�77370
 * @ʱ�䣺2018��10��19�� ����10:29:54     
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

	        // �����ļ������  
			FileWriter writer=null;
	        try {    
	       		writer=new FileWriter(toTxtFile);
	    	    
	            // ��������������ȡExcel  
	            InputStream is = new FileInputStream(file.getAbsolutePath());  
	            // jxl�ṩ��Workbook��  
	            Workbook wb = Workbook.getWorkbook(is);  
	            // Excel��ҳǩ����  
	                // Ϊ��0ҳ����һ��Sheet����  
	                Sheet sheet = wb.getSheet(0);  
	                // sheet.getRows()���ظ�ҳ��������  
	                for (int i = 0; i < vm_num; i++) {  
	                        int cpu_required = Integer.parseInt(sheet.getCell(4, i).getContents());  
	                        double normalized_cpu_req=Double.parseDouble(sheet.getCell(5, i).getContents());
	                        double normalized_mem_req=Double.parseDouble(sheet.getCell(6, i).getContents());
	                        vmCpu[i]=cpu_required*normalized_cpu_req;
	                        vmMem[i]=normalized_mem_req;
	                        // ����Excel�ж�ȡ������д�뵽txt��   
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
	                // �ǵùر���  
	                writer.flush();  
	            	writer.close();  
	                // ���ڴ˴��õ��˻�������������������󣬲�����flush������ĳЩ���ݽ�����  
	                // �������ڴ��ж�����д���ļ���������һ��Ҫע��  

	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  

}
