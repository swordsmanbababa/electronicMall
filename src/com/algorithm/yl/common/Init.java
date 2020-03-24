package com.algorithm.yl.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.util.CommonUtil;

/**      
 * @�ļ�����: Init.java   
 * @��·��: com.algorithm.yl.common   
 * @����: TODO   
 * @���ߣ�li
 * @ʱ�䣺2017-9-23 ����9:12:53     
 */
public class Init {
	private static String file = "file/config/init.txt"; //��������ò���
	private static String phy_file = "file/config/phyconfig.txt"; //����ڵ����
	
	public static int time;
	public static int vm_num;
	public static int vm_mem_count;
	public static int vm_cpu_count;
	public static int ProgenyCount_num;  //����ڵ�����
	public static int physical_node_num;
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		config();
		write();
		System.out.println("finish!");
	}
	
	public static void config()throws Exception{
	    physical_node_num = CommonConstants.physical_node_num;  //��������6
	    vm_num = CommonConstants.vm_num;   //��������14
	    ProgenyCount_num = CommonConstants.ProgenyCount_num;  //��ʼ�Ӵ����� ��������5
	    vm_mem_count = CommonConstants.vm_mem_count;   //������ڴ� 2800
//	    vm_cpu_count = CommonConstants.vm_cpu_count;   //�����cpu����4
	    time = CommonConstants.time;     //ʱ��48
	}
	
	//��ʼ��Ⱥ
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
		
		//��ʼ������ڵ����
		for(j=0; j<physical_node_num; j++){
			buffer.append(phyCpu[j] + ":"+ phyMem[j] +";");
		}
		phywriter.write(buffer.toString());
		phywriter.flush();
		phywriter.close();
		
		//��ʼ��������ڵ�
		buffer = new StringBuffer();
//		for(i=1; i<ProgenyCount_num; i++){  //�Ӵ�����
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
