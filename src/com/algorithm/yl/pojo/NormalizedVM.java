package com.algorithm.yl.pojo;

/**      
 * @�ļ�����: Normalized_VM.java   
 * @��·��: com.algorithm.yl.pojo   
 * @����: TODO   
 * @���ߣ�77370
 * @ʱ�䣺2018��10��24�� ����3:09:15     
 */
public class NormalizedVM {
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
	
	
}
