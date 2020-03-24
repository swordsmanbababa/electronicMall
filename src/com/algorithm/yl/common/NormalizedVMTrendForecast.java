package com.algorithm.yl.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.pojo.NormalizedVM;
import com.algorithm.yl.pojo.VM;
import com.algorithm.yl.util.CommonUtil;

/**      
 * @文件名称: NormalizedVMTrendForecast.java   
 * @类路径: com.algorithm.yl.common   
 * @描述: TODO   
 * @作者：77370
 * @时间：2018年10月24日 下午3:11:23     
 */
public class NormalizedVMTrendForecast {
	private static String file="file/config/forecast.txt";
	public static int time;
	public static int vm_num;
    public static int time_interval;
	public static double [][] vm_type_config;
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
	public static List<NormalizedVM> readInitForOnrLine() throws Exception {
		List<NormalizedVM> vmList=null;
		File init_file=new File("file/config/init.txt");
		FileReader initFile_reader=new FileReader(init_file);
		BufferedReader initFile_bufferreader=new BufferedReader(initFile_reader);
		String string=null;
		String [] s=new String[CommonConstants.vm_num];
		if((string=initFile_bufferreader.readLine())!=null){
	        s=string.split(";");
			vmList=new ArrayList<NormalizedVM>();
			for(int i=0;i<CommonConstants.vm_num;i++){
				NormalizedVM vm=new NormalizedVM();
				vm.setId(i);
				vm.setCpu(Double.parseDouble((s[i].split(":"))[2]));
				vm.setMem(Double.parseDouble((s[i].split(":"))[1]));
				vmList.add(vm);
			}
		}
		else {
			throw new Exception("init.txt is empty");
		}
		
          		return vmList;
	}
	
	public static List<NormalizedVM> readForecastForOnrLine() throws Exception {
		List<NormalizedVM> vmList=null;
		File  forecast_file=new File("file/config/forecast.txt");
		FileReader forecast_reader=new FileReader(forecast_file);
		BufferedReader forecast_bufferreader=new BufferedReader(forecast_reader);
		String string=null;
		String [] s=new String[11276];
		if((string=forecast_bufferreader.readLine())!=null){
	        s=string.split(";");
			vmList=new ArrayList<NormalizedVM>();
			for(int i=0;i<11275;i++){
				NormalizedVM vm=new NormalizedVM();
				vm.setId(i+1);
				vm.setCpu(Double.parseDouble((s[i].split(":"))[1]));
				vm.setMem(Double.parseDouble((s[i].split(":"))[0]));
				vmList.add(vm);
			}
		}
		else {
			throw new Exception("forecast.txt is empty");
		}
		
          		return vmList;
	}
	
	public static void write()throws Exception{
		List<NormalizedVM> vms=readInitForOnrLine();
		File file1=new File(file);
		FileWriter fw = new FileWriter(file1);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write("");
	    bw.flush();
	    bw.close();
	    
		StringBuffer buffer=new StringBuffer();
		FileWriter writer=new FileWriter(file, true);
		double mem=0;
		double cpu=0;
		int time_current=0;
		for(int j=0;j<vm_num;j++){
			time_current=0;
			cpu=vms.get(j).getCpu();
			mem=vms.get(j).getMem();
			for(int i=0;i<time/time_interval;i++){
				int up_or_down=CommonUtil.generateIntNumber(2);
				for(int m=0;m<time_interval;m++){
					if(up_or_down==0){  //下降
						if(mem>100)
							mem=mem*0.8;
		                    cpu=cpu*0.8;   
					}else {
						mem=mem*1.2;
						cpu=cpu*1.2;  
					}
					forecastofAllVms[time_current][j]=""+mem+":"+cpu+";";
					time_current++;
				}
			}
			for(int k=0;k<time%time_interval;k++){
				int up_or_down=CommonUtil.generateIntNumber(2);
				if(up_or_down==0){  //下降
					if(mem>100)
						mem=mem*0.8;
                        cpu=cpu*0.8;   
				}else {
					mem=mem*1.2;
					cpu=cpu*1.2; 
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
