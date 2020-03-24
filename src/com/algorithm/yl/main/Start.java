package com.algorithm.yl.main;

import com.algorithm.yl.mthod.MOEAD;
import com.algorithm.yl.mthod.MOGANS;
import com.algorithm.yl.mthod.NSGA2;


public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
//		MOGANS mogans = new MOGANS();
//		mogans.init();  //初始化
//		mogans.start();
	
		
		MOEAD moead=new MOEAD();	
		moead.init();
		moead.start();
		System.out.print("执行时间:" + (System.currentTimeMillis() - start)/1000);
//	
	
	}

}
