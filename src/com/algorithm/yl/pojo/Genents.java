package com.algorithm.yl.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Genents implements Serializable{
	private int id;
	private List<PhysicalNode> phyList = new ArrayList<PhysicalNode>();
	private int rank;
	private int phy_count;  //δ��������ڵ�
	private int shift_count;   //Ǩ�ƴ���
	private float distance;   //ӵ������
	private float stab_time;   //�ȶ�ʱ��
	private int temp_rank;   //�洢���䵱ǰ����ĸ���
	private int is_init;   //�Ƿ��ǳ�ʼ����
	private List<Genents> temp_genents=new ArrayList<Genents>();
	
	private double[] weight;  //MOEAD�㷨��Ȩ������
	private List<Genents> neighbor = new ArrayList<Genents>();  //MOEAD ����
	
	public List<Genents> getNeighbor() {
		return neighbor;
	}
	public void setNeighbor(List<Genents> neighbor) {
		this.neighbor = neighbor;
	}
	public double[] getWeight() {
		return weight;
	}
	public void setWeight(double[] weight) {
		this.weight = weight;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<PhysicalNode> getPhyList() {
		return phyList;
	}
	public void setPhyList(List<PhysicalNode> phyList) {
		this.phyList = phyList;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getPhy_count() {
		return phy_count;
	}
	public void setPhy_count(int phy_count) {
		this.phy_count = phy_count;
	}
	public int getShift_count() {
		return shift_count;
	}
	public void setShift_count(int shift_count) {
		this.shift_count = shift_count;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public float getStab_time() {
		return stab_time;
	}
	public void setStab_time(float stab_time) {
		this.stab_time = stab_time;
	}
	public int getTemp_rank() {
		return temp_rank;
	}
	public void setTemp_rank(int temp_rank) {
		this.temp_rank = temp_rank;
	}
	public int getIs_init() {
		return is_init;
	}
	public void setIs_init(int is_init) {
		this.is_init = is_init;
	}
	public List<Genents> getTemp_genents() {
		return temp_genents;
	}
	public void setTemp_genents(List<Genents> temp_genents) {
		this.temp_genents = temp_genents;
	}
	public void printGen() {
		int vmNum=0;
		System.out.println("Gen"+this.getId()+":");
		for(PhysicalNode pNode:this.getPhyList()){
			vmNum+=pNode.getVmList().size();
			System.out.print("phyNode"+pNode.getId()+"����");
			pNode.printPhyNode();
			System.out.print("  ");
		}
		System.out.println("vmnum:"+vmNum);
	}
}
