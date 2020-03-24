package com.gemptc.entity;

public class Category {
	
	private int c_id;
	private String c_name;
	private int f_id;//ÉÏÒ»²ãÄ¿Â¼id
	
	public int getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		this.f_id = f_id;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	@Override
	public String toString() {
		return "Category [c_id=" + c_id + ", c_name=" + c_name + "]";
	}

}
