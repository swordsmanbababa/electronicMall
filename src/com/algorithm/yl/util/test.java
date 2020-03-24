package com.algorithm.yl.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.algorithm.yl.common.Forecast;
import com.algorithm.yl.common.Operator;
import com.algorithm.yl.common.TreandForecast;
import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.pojo.Genents;
import com.algorithm.yl.pojo.PhysicalNode;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

import sun.misc.VM;

/**      
 * @文件名称: test.java   
 * @类路径: com.algorithm.yl.util   
 * @描述: TODO   
 * @作者：77370
 * @时间：2018年10月10日 下午6:30:34     
 */
public class test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		Random random = new Random();
//		while(true)
//		System.out.println(CommonUtil.generateIntNumber(2));
//		
//		System.out.println(random.nextInt(2));
//		System.out.println(CommonConstants.phy_cpu_count[CommonUtil.generateIntNumber(CommonConstants.phy_cpu_count.length)]);
//		RandomGenerator randomGenerator = new RandomGenerator(3, CommonConstants.ProgenyCount_num);
//		List<double[]> weights = randomGenerator.generate();	
//		for(double[] wei : weights)
//		{   for(double d: wei)
//		   { System.out.print(d+"  ");}
//		System.out.println();
//		}
//		int []arr={1,4,1,9,-1};
//		System.out.println(CommonUtil.isExistInArray(arr,2));
		List<Genents> populations = null;
		List<PhysicalNode> phyList = new ArrayList<PhysicalNode>();
		phyList = Operator.initPhyNode();
		populations = Operator.initGeneration(phyList);
		Genents gen_a=populations.get(0);
		Genents gen_b=populations.get(1);
//		gen_a.printGen();
//		gen_b.printGen();
		Genents gen_c=Operator.cross(gen_a, gen_b);
//		gen_c.printGen();
		String [][] forecast = Operator.readForecastFile();  //读取配置文件
//		 System.out.println(Operator.getStabTimeOri(gen_a, forecast));
//		 for(Genents gen:populations){
//			 gen.printGen();
//		     for(PhysicalNode p:gen_a.getPhyList()){
//		    	 for(com.algorithm.yl.pojo.VM vm:p.getVmList())
//		    	 {
//		    		 if(vm.getMem()>1||vm.getCpu()>1)
//		    		 {
//		    			 vm.printVM();
//		    		 }
//		    	 }
//		     }
//			 System.out.println(Operator.getStabTime(gen_a, forecast));
//		 }
//		int i=1;
//		while(i<6){
//			gen_a.printGen();
//			gen_c.printGen();
//	        gen_c=Operator.cross(gen_c, gen_b);
//			gen_c.printGen();
//            System.out.println(Operator.getStabTime(gen_c, forecast));
//			i=Operator.getStabTime(gen_c, forecast);
//		}
////	 
//	   	RandomGenerator randomGenerator = new RandomGenerator(3, CommonConstants.ProgenyCount_num);
//		
//			List<double[]> weights = randomGenerator.generate();
//					
//			for(int i=0; i<populations.size(); i++){
//				
//				populations.get(i).setWeight(weights.get(i));
//			}
//			Collections.sort(populations, new WeightSorter(gen_a));  
		
//		   List<com.algorithm.yl.pojo.VM> nms=TreandForecast.readForecastForOnrLine();
//		   for(com.algorithm.yl.pojo.VM v:nms){
//			  System.out.println(v.getCpu());
//		   }
//		List<Genents> sortedPopulation = new ArrayList<Genents>(  );
//		sortedPopulation.addAll(populations);
//		System.out.println(populations.get(0).getId());
//		System.out.println(sortedPopulation .get(0).getId());
//		populations.get(0).setId(12);
//		System.out.println(populations.get(0).getId());
//		System.out.println(sortedPopulation .get(0).getId());
	}

}
