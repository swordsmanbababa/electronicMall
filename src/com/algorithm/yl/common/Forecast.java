package com.algorithm.yl.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.util.CommonUtil;

/**      
 * @文件名称: Forecast.java   
 * @类路径: com.algorithm.yl.common   
 * @描述: TODO   
 * @作者：li
 * @时间：2017-9-23 下午9:10:33     
 */
public class Forecast {
	private static String file="file/config/forecast.txt";
	public static int time;
	public static int vm_num;
	public static int vm_mem_count;
	public static int vm_cpu_count;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		config();
		write();
		System.out.println("finish!");
	}
	
	public static void config()throws Exception{
	    vm_num = CommonConstants.vm_num;
	    vm_mem_count = CommonConstants.vm_mem_count;
//	    vm_cpu_count = CommonConstants.vm_cpu_count;
	    
	    time = CommonConstants.time;
	}
	
	public static void write()throws Exception{
		File file1=new File(file);
		FileWriter fw = new FileWriter(file1);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write("");
	    bw.flush();
	    bw.close();
	    
		StringBuffer buffer=new StringBuffer();
		FileWriter writer=new FileWriter(file, true);
		for(int i=0;i<time;i++){   //时刻
			for(int j=0;j<vm_num;j++){    //虚拟机个数
//				int mem=(int)(Math.random()*vm_mem_count);
//				int cpu=(int)(Math.random()*vm_cpu_count+1);
				int mem=CommonUtil.generateIntNumber(CommonConstants.vm_mem_count);
//				int cpu=CommonUtil.generateIntNumber(CommonConstants.vm_cpu_count) + 1;
				int cpu=CommonConstants.vm_cpu_count[CommonUtil.generateIntNumber(CommonConstants.vm_cpu_count.length)];
//				int mem=400;
//				int cpu=1;
				if(cpu==0){
					cpu=1;
				}
				buffer.append(mem+":"+cpu+";");
			}
			buffer.append("\n");
		}
		writer.write(buffer.toString());
		writer.close();
	}
	
	public static void read()throws Exception{
		//读取预测值
	    File fore_file=new File(file);
	    FileReader reader=new FileReader(fore_file);
	    BufferedReader bufreader=new BufferedReader(reader);
	    String s=null;
	    while((s=bufreader.readLine())!=null){
	    	String []str=new String[time];
	    	str=s.split(";");
	    	for(int i=0;i<str.length;i++){
	    		System.out.print(str[i]+"\t");
	    	}
	    	System.out.println();
	    }
	    bufreader.close();
	    reader.close();
	}
}
