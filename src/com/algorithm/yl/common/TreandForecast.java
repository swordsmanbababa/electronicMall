package com.algorithm.yl.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.pojo.VM;
import com.algorithm.yl.util.CommonUtil;

/**      
 * @�ļ�����: TreandForecast.java   
 * @��·��: com.algorithm.yl.common   
 * @����: TODO   
 * @���ߣ�77370
 * @ʱ�䣺2018��10��14�� ����11:45:40     
 */
public class TreandForecast {
	private static String file="file/config/TrendForecast.txt";
	public static int time;
	public static int vm_num;
    public static int time_interval;
	public static int [][] vm_type_config;
	public static String[][] forecastofAllVms;
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
	    time = CommonConstants.time;
	    time_interval=3;
	    forecastofAllVms=new String[CommonConstants.time][CommonConstants.vm_num];
	    
	}
	public static List<VM> readInitForOnrLine() throws Exception {
		List<VM> vmList=null;
		File init_file=new File("file/config/init.txt");
		FileReader initFile_reader=new FileReader(init_file);
		BufferedReader initFile_bufferreader=new BufferedReader(initFile_reader);
		String string=null;
		String [] s=new String[CommonConstants.vm_num];
		if((string=initFile_bufferreader.readLine())!=null){
	        s=string.split(";");
			vmList=new ArrayList<VM>();
			for(int i=0;i<CommonConstants.vm_num;i++){
				VM vm=new VM();
				vm.setId(i);
				vm.setCpu(Integer.parseInt((s[i].split(":"))[2]));
				vm.setMem(Integer.parseInt((s[i].split(":"))[1]));
				vmList.add(vm);
			}
		}
		else {
			throw new Exception("init.txt is empty");
		}
		
          		return vmList;
	}
	
	public static List<VM> readForecastForOnrLine() throws Exception {
		List<VM> vmList=null;
		File  forecast_file=new File("file/config/forecast.txt");
		FileReader forecast_reader=new FileReader(forecast_file);
		BufferedReader forecast_bufferreader=new BufferedReader(forecast_reader);
		String string=null;
		String [] s=new String[CommonConstants.vm_num];
		if((string=forecast_bufferreader.readLine())!=null){
	        s=string.split(";");
			vmList=new ArrayList<VM>();
			for(int i=0;i<CommonConstants.vm_num;i++){
				VM vm=new VM();
				vm.setId(i);
				vm.setCpu(Integer.parseInt((s[i].split(":"))[1]));
				vm.setMem(Integer.parseInt((s[i].split(":"))[0]));
				vmList.add(vm);
			}
		}
		else {
			throw new Exception("forecast.txt is empty");
		}
		
          		return vmList;
	}
	
	public static void write()throws Exception{
		List<VM> vms=readInitForOnrLine();
		File file1=new File(file);
		FileWriter fw = new FileWriter(file1);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write("");
	    bw.flush();
	    bw.close();
	    
		StringBuffer buffer=new StringBuffer();
		FileWriter writer=new FileWriter(file, true);
		int mem=0;
		int cpu=0;
		int time_current=0;
		for(int j=0;j<vm_num;j++){
			time_current=0;
			cpu=(int) vms.get(j).getCpu();
			mem=(int) vms.get(j).getMem();
			for(int i=0;i<time/time_interval;i++){
				int up_or_down=CommonUtil.generateIntNumber(2);
				for(int m=0;m<time_interval;m++){
					if(up_or_down==0){  //�½�
						if(mem>100)
							mem-=100;
						if(cpu>1)
							cpu-=1;
					}else {
						mem+=100;
						cpu+=1;
					}
					forecastofAllVms[time_current][j]=""+mem+":"+cpu+";";
					time_current++;
				}
			}
			for(int k=0;k<time%time_interval;k++){
				int up_or_down=CommonUtil.generateIntNumber(2);
				if(up_or_down==0){  //�½�
					if(mem>100)
						mem-=100;
					if(cpu>1)
						cpu-=1;
				}else {
					mem+=100;
					cpu+=1;
				}
				forecastofAllVms[time_current][j]=""+mem+":"+cpu+";";
				time_current++;
			}
		}
		for(int i=0;i<time;i++){
			for(int j=0;j<CommonConstants.vm_num;j++)
			{
	            buffer.append(forecastofAllVms[i][j]);
			}
			buffer.append("\n");
		}
			
		writer.write(buffer.toString());
		writer.close();
	}
	
	public static void read()throws Exception{
		//��ȡԤ��ֵ
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
