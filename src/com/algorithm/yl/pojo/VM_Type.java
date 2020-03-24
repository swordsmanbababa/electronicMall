package com.algorithm.yl.pojo;

/**      
 * @�ļ�����: VM_Type.java   
 * @��·��: com.algorithm.yl.pojo   
 * @����: TODO   
 * @���ߣ�77370
 * @ʱ�䣺2018��10��15�� ����10:39:41     
 */
public enum VM_Type {
	 NorVm(0,4,8,1400,2000),
	 CupVm(1,8,16,1400,2000),
	 MemVm(2,4,8,2000,2800),
	 RamVm(3,0,16,0,2800);
  
	private int type;
	private int mimCpu;
	private int maxCpu;
	private int mimMem;
	private int maxMem;
	private  VM_Type(int type,int mimCpu,int maxCpu,int mimMem,int maxMem){
		this.type=type;
		this.maxCpu=maxCpu;
		this.mimCpu=mimCpu;
		this.maxMem=maxMem;
		this.mimMem=mimMem;
	}
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMimCpu() {
		return mimCpu;
	}
	public int getMaxCpu() {
		return maxCpu;
	}
	public int getMimMem() {
		return mimMem;
	}
	public int getMaxMem() {
		return maxMem;
	}
	public void setMimCpu(int mimCpu) {
		this.mimCpu = mimCpu;
	}
	public void setMaxCpu(int maxCpu) {
		this.maxCpu = maxCpu;
	}
	public void setMimMem(int mimMem) {
		this.mimMem = mimMem;
	}
	public void setMaxMem(int maxMem) {
		this.maxMem = maxMem;
	}
	
	
}