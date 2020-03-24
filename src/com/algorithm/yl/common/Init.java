package com.algorithm.yl.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.util.CommonUtil;

/**      
 * @文件名称: Init.java   
 * @类路径: com.algorithm.yl.common   
 * @描述: TODO   
 * @作者：li
 * @时间：2017-9-23 下午9:12:53     
 */
public class Init {
	private static String file = "file/config/init.txt"; //虚拟机配置参数
	private static String phy_file = "file/config/phyconfig.txt"; //物理节点参数
	
	public static int time;
	public static int vm_num;
	public static int vm_mem_count;
	public static int vm_cpu_count;
	public static int ProgenyCount_num;  //子孙节点数量
	public static int physical_node_num;
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		config();
		write();
		System.out.println("finish!");
	}
	
	public static void config()throws Exception{
	    physical_node_num = CommonConstants.physical_node_num;  //测试数据6
	    vm_num = CommonConstants.vm_num;   //测试数据14
	    ProgenyCount_num = CommonConstants.ProgenyCount_num;  //初始子代个数 测试数据5
	    vm_mem_count = CommonConstants.vm_mem_count;   //虚拟机内存 2800
//	    vm_cpu_count = CommonConstants.vm_cpu_count;   //虚拟机cpu个数4
	    time = CommonConstants.time;     //时间48
	}
	
	//初始种群
	public static void write()throws Exception{
		
		File file1=new File(file);
		FileWriter fw = new FileWriter(file1);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write("");
	    bw.flush();
	    bw.close();
	    
	    File file2=new File(phy_file);
		FileWriter fw2 = new FileWriter(file2);
	    BufferedWriter bw2 = new BufferedWriter(fw2);
	    bw2.write("");
	    bw2.flush();
	    bw2.close();
	    
		StringBuffer buffer=new StringBuffer();
		FileWriter writer=new FileWriter(file, true);
		
		FileWriter phywriter=new FileWriter(phy_file, true);
		
		int []vmMem = new int[vm_num];
		int []vmCpu = new int[vm_num];
		
		int []phyMem = new int[physical_node_num];
		int []phyCpu = new int[physical_node_num];
		int i,j;
		
		for(i=0;i<vm_num;i++){
			vmMem[i] = CommonUtil.generateIntNumber(CommonConstants.vm_mem_count);
//			vmCpu[i] = CommonUtil.generateIntNumber(CommonConstants.vm_cpu_count) + 1;
			vmCpu[i]=CommonConstants.vm_cpu_count[CommonUtil.generateIntNumber(CommonConstants.vm_cpu_count.length)];
		}
		for(i=0; i<physical_node_num; i++){
			phyCpu[i] = CommonConstants.phy_cpu_count[CommonUtil.generateIntNumber(CommonConstants.phy_cpu_count.length)];
			phyMem[i] = CommonConstants.phy_mem_count[CommonUtil.generateIntNumber(CommonConstants.phy_mem_count.length)];
		}
		
		//初始化物理节点参数
		for(j=0; j<physical_node_num; j++){
			buffer.append(phyCpu[j] + ":"+ phyMem[j] +";");
		}
		phywriter.write(buffer.toString());
		phywriter.flush();
		phywriter.close();
		
		//初始化虚拟机节点
		buffer = new StringBuffer();
//		for(i=1; i<ProgenyCount_num; i++){  //子代个数
//amend alter one row
		for(i=0; i<ProgenyCount_num; i++){
			for(j=0; j<vm_num; j++){
				int target = CommonUtil.generateIntNumber(physical_node_num);
				buffer.append(target+":"+vmMem[j]+":"+vmCpu[j]+";");
			}
			buffer.append("\n");
		}
		writer.write(buffer.toString());
		writer.close();
	}
	
	public static String read()throws Exception{
		//
	    File fore_file=new File(file);
	    FileReader reader=new FileReader(fore_file);
	    BufferedReader bufreader=new BufferedReader(reader);
	    String s=null;
	    s=bufreader.readLine();
	    bufreader.close();
	    reader.close();
	    return s;
	}
}
