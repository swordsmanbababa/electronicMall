package com.algorithm.yl.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.algorithm.yl.common.NormalizedVMTrendForecast;
import com.algorithm.yl.common.TreandForecast;

/**      
 * @�ļ�����: PhysicalNode.java   
 * @��·��: com.algorithm.yl.pojo   
 * @����: ����ڵ�  
 * @���ߣ�li
 * @ʱ�䣺2017-9-23 ����1:17:43     
 */
public class PhysicalNode implements Serializable{
	private int id;  //����ڵ���
	private int loadSize;   //����ڵ㸺�ش�С
	private List<VM> vmList = new ArrayList<VM>();   //����ڵ�����������
	private double initCpu; //cpu��ʼֵ
	private double initMemory; //�ڴ��ʼֵ
	private double cpu;
	private double memory;
	private int status;   //����ڵ�ʹ��״̬
	

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
		this.getVmList().clear();  //ɾ��
		this.setCpu(getInitCpu()); //�ָ���ʼֵ
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
			return 0; //�½�
		else {
			return 1; //����
		}
	}
}
