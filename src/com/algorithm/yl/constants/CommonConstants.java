package com.algorithm.yl.constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;


public class CommonConstants {
//	public static int physical_node_num = 64;   //物理节点个数
//	public static int vm_num = 143;  //虚拟机个数
//	public static int ProgenyCount_num = 48;  //初始子代个数(基因序列个数)
//	public static int GEN_NUM = 60;   //代数
	public static double Cross_Probability = 0.7;  //交叉概率
	public static double Variation_Probability = 0.05;  //变异概率 
	
//			测试数据
			public static int physical_node_num = 32;   //物理节点个数
			public static int vm_num = 84;  //虚拟机个数
			public static int ProgenyCount_num = 40;  //初始子代个数(基因序列个数)
			public static int GEN_NUM = 32;   //代数
			public static int[] phy_cpu_count = {16,32,64};
			public static int [] vm_cpu_count = {4};
//	
	//MOEAD
	public static int neighborhoodSize = ProgenyCount_num; // 8;
	//异构环境
	public static int[] phy_mem_count = {1};  //单位M
//	public static int[] phy_cpu_count = {4,8};
	
	//虚拟机参数配置  随机生成
	public static int vm_mem_count = 2800;
//	public static int vm_cpu_count = 2;
	
	public static DecimalFormat decimalFormat=new DecimalFormat(".00");//保留两位小数，如果小数不足2位,会以0补足
	
	//预测数据定义
	public static int time = 48;   //时间
	
//	public static void config()throws Exception{
//		String file="file/tools.txt";
//		File fore_file=new File(file);
//		FileReader reader=new FileReader(fore_file);
//	    BufferedReader bufreader=new BufferedReader(reader);
//	    String s=null;
//	    s=bufreader.readLine();  //读取配置信息
//	    bufreader.close();
//	    reader.close(); 
//	    String []config=s.split(",");
//	    
//	    physical_node_num=Integer.parseInt(config[0]);
//	    vm_num=Integer.parseInt(config[1]);
//	    ProgenyCount_num=Integer.parseInt(config[2]);
//	    GEN_NUM=Integer.parseInt(config[3]);
//	    Cross_Probability=Double.parseDouble(config[4]);
//	    Variation_Probability=Double.parseDouble(config[5]);
//	    phy_mem_count=Integer.parseInt(config[6]);
//	    phy_cpu_count=Integer.parseInt(config[7]);
//	    vm_mem_count=Integer.parseInt(config[8]);
//	    vm_cpu_count=Integer.parseInt(config[9]);
//	    time=Integer.parseInt(config[10]);
//	}
}
