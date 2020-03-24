package com.algorithm.yl.pojo;

import java.io.Serializable;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.INEG;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

/**      
 * @�ļ�����: VM.java   
 * @��·��: com.algorithm.yl.pojo   
 * @����: �����   
 * @���ߣ�li
 * @ʱ�䣺2017-9-23 ����1:16:21     
 */
public class VM implements Serializable{
	private int id;
	private double cpu;
	private double mem;
	private int targetId; //����ڵ�id
	private int type;
	public int getId() {
		return id;
	}
	public double getCpu() {
		return cpu;
	}
	public double getMem() {
		return mem;
	}
	public int getTargetId() {
		return targetId;
	}
	public int getType() {
		return type;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCpu(double cpu) {
		this.cpu = cpu;
	}
	public void setMem(double mem) {
		this.mem = mem;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public static int[] getVmsId(List<VM> vms) {
		int []vmsid=new int[vms.size()];
		for(int i=0;i<vms.size();i++)
			vmsid[i]=vms.get(i).getId();
		return vmsid;
		
	}
	
	public void printVM(){
		System.out.println("vm_id="+this.id);
		System.out.println("mem="+this.mem);
		System.out.println("cpu="+this.cpu);
	}

	

}
