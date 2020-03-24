package com.algorithm.yl.pojo;

/**      
 * @文件名称: Normalized_VM.java   
 * @类路径: com.algorithm.yl.pojo   
 * @描述: TODO   
 * @作者：77370
 * @时间：2018年10月24日 下午3:09:15     
 */
public class NormalizedVM {
	private int id;
	private double cpu;
	private double mem;
	private int targetId; //物理节点id
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
	
	
}
