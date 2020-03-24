package com.gemptc.entity;

public class Product {
	private int pro_id;
	private String pro_name;
	private double pro_price;
	private String pro_desc;
	//˼�� ������� Date ��ôʵ��
	private String pro_create;
	
	private String pro_image;
	//������Ķ��� ��Ϊ Product������     ����  ����ĸ���
	// SSH   SpringMVC  Spring      Hibernate (ʹ�ñȽ���)
	// SSH   Struts2    Spring      Hibernate
	          
	//Service(Servlet)  �������еĶ���Ĵ���     dao��  �������ݿ���в�����
	
	// SSM   SpringMVC  Spring      Mybatis  (ʹ�ñȽ϶�)
	// SSM   Struts2    Spring      Mybatis
	private Category cate;
	
	public int getPro_id() {
		return pro_id;
	}
	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public double getPro_price() {
		return pro_price;
	}
	public void setPro_price(double pro_price) {
		this.pro_price = pro_price;
	}
	public String getPro_desc() {
		return pro_desc;
	}
	public void setPro_desc(String pro_desc) {
		this.pro_desc = pro_desc;
	}
	public String getPro_create() {
		return pro_create;
	}
	public void setPro_create(String pro_create) {
		this.pro_create = pro_create;
	}
	public Category getCate() {
		return cate;
	}
	public void setCate(Category cate) {
		this.cate = cate;
	}
	public String getPro_image() {
		return pro_image;
	}
	public void setPro_image(String pro_image) {
		this.pro_image = pro_image;
	}
	
	
	
}
