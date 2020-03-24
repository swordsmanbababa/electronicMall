package com.algorithm.yl.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.algorithm.yl.common.NormalizedVMTrendForecast;
import com.algorithm.yl.common.TreandForecast;

/**      
 * @文件名称: PhysicalNode.java   
 * @类路径: com.algorithm.yl.pojo   
 * @描述: 物理节点  
 * @作者：li
 * @时间：2017-9-23 下午1:17:43     
 */
public class PhysicalNode implements Serializable{
	private int id;  //物理节点编号
	private int loadSize;   //物理节点负载大小
	private List<VM> vmList = new ArrayList<VM>();   //物理节点上虚拟机编号
	private double initCpu; //cpu初始值
	private double initMemory; //内存初始值
	private double cpu;
	private double memory;
	private int status;   //物理节点使用状态
	

	public int getId() {
		return id;
	}

	public int getLoadSize() {
		return loadSize;
	}

	public List<VM> getVmList() {
		return vmList;
	}

	public double getInitCpu() {
		return initCpu;
	}

	public double getInitMemory() {
		return initMemory;
	}

	public double getCpu() {
		return cpu;
	}

	public double getMemory() {
		return memory;
	}

	public int getStatus() {
		return status;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLoadSize(int loadSize) {
		this.loadSize = loadSize;
	}

	public void setVmList(List<VM> vmList) {
		this.vmList = vmList;
	}

	public void setInitCpu(double initCpu) {
		this.initCpu = initCpu;
	}

	public void setInitMemory(double initMemory) {
		this.initMemory = initMemory;
	}

	public void setCpu(double cpu) {
		this.cpu = cpu;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int[] getVmsId() {
		int []vmsid=new int[this.getVmList().size()];
		for(int i=0;i<this.getVmList().size();i++)
			vmsid[i]=this.getVmList().get(i).getId();
		return vmsid;
		
	}
	
	public void reSet() {
		this.getVmList().clear();  //删除
		this.setCpu(getInitCpu()); //恢复初始值
		this.setMemory(getInitMemory());
		this.setStatus(0);
	}
	
	public boolean ifResEnough(VM vm) {
		 if(this.getCpu()>vm.getCpu()&&this.getMemory()>vm.getMem())
			 return true;
		 return false;
	}
	
	public void addVmToList(VM vm) {
		this.setCpu(getCpu()-vm.getCpu());
        this.setMemory(getMemory()-vm.getMem());	
        this.getVmList().add(vm);
        if(this.status==0)
        	this.status=1;
	}
	
	public void printPhyNode() {
	
	
		for (VM vm:this.getVmList()){
//			System.out.print(vm.getId()+":"+vm.getCpu()+":"+vm.getMem()+"; ");
			System.out.print(vm.getId()+"; ");

		}
		
	
	}
	
	public int getForecastTrend() throws Exception {
		if(this.getVmList()==null)
			return 0;
		double memCurrent=0;
		double memnext=0;
		for(VM v:this.getVmList()){
			memCurrent=memCurrent+v.getMem();
		}
		List<NormalizedVM> vmsNext=NormalizedVMTrendForecast.readForecastForOnrLine();
		for(NormalizedVM v:vmsNext){
			memnext=+v.getMem();
		}
		if(memCurrent>memnext)
			return 0; //下降
		else {
			return 1; //上升
		}
	}
}
