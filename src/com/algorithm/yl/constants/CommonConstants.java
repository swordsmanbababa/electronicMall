package com.algorithm.yl.constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;


public class CommonConstants {
//	public static int physical_node_num = 64;   //����ڵ����
//	public static int vm_num = 143;  //���������
//	public static int ProgenyCount_num = 48;  //��ʼ�Ӵ�����(�������и���)
//	public static int GEN_NUM = 60;   //����
	public static double Cross_Probability = 0.7;  //�������
	public static double Variation_Probability = 0.05;  //������� 
	
//			��������
			public static int physical_node_num = 32;   //����ڵ����
			public static int vm_num = 84;  //���������
			public static int ProgenyCount_num = 40;  //��ʼ�Ӵ�����(�������и���)
			public static int GEN_NUM = 32;   //����
			public static int[] phy_cpu_count = {16,32,64};
			public static int [] vm_cpu_count = {4};
//	
	//MOEAD
	public static int neighborhoodSize = ProgenyCount_num; // 8;
	//�칹����
	public static int[] phy_mem_count = {1};  //��λM
//	public static int[] phy_cpu_count = {4,8};
	
	//�������������  �������
	public static int vm_mem_count = 2800;
//	public static int vm_cpu_count = 2;
	
	public static DecimalFormat decimalFormat=new DecimalFormat(".00");//������λС�������С������2λ,����0����
	
	//Ԥ�����ݶ���
	public static int time = 48;   //ʱ��
	
//	public static void config()throws Exception{
//		String file="file/tools.txt";
//		File fore_file=new File(file);
//		FileReader reader=new FileReader(fore_file);
//	    BufferedReader bufreader=new BufferedReader(reader);
//	    String s=null;
//	    s=bufreader.readLine();  //��ȡ������Ϣ
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
