package com.algorithm.yl.util;

import java.util.Arrays;
import java.util.Random;

import com.algorithm.yl.pojo.Genents;
import com.algorithm.yl.pojo.PhysicalNode;
import com.algorithm.yl.pojo.VM;

/**      
 * @文件名称: CommonUtil.java   
 * @类路径: com.algorithm.yl.util   
 * @描述: TODO   
 * @作者：li
 * @时间：2017-9-23 下午4:47:30     
 */
public class CommonUtil {
	
	private static Random random = new Random();
	
	public static int generateIntNumber(int range){
		return random.nextInt(range);
	}
	public static boolean isExistInArray(int [] arr, int id){
//amend add one row
		Arrays.sort(arr);
		int a = Arrays.binarySearch(arr, id);
		if(a < 0){
			return false;
		}
		return true;
	}
	public static Genents copy(Genents a){
		Genents newGen = new Genents();
		newGen.setWeight(a.getWeight());
		PhysicalNode physicalNode = null;
		VM v = null;
		for(PhysicalNode p:a.getPhyList()){
			physicalNode = new PhysicalNode();
			physicalNode.setCpu(p.getCpu());
			physicalNode.setId(p.getId());
			physicalNode.setMemory(p.getMemory());
			physicalNode.setInitCpu(p.getInitCpu());
			physicalNode.setInitMemory(p.getInitMemory());
			physicalNode.setStatus(p.getStatus());
			if(p.getStatus() == 1){
				for(VM v1:p.getVmList()){
					v = new VM();
					v.setCpu(v1.getCpu());
					v.setId(v1.getId());
					v.setMem(v1.getMem());
					physicalNode.getVmList().add(v);
				}
			}
			
			newGen.getPhyList().add(physicalNode);
		}
		return newGen;
	}
	public void output(){
	}
}
