package com.gemptc.dao;

import java.util.ArrayList;

import com.gemptc.entity.Category;

public interface CategoryDao {
	
	public ArrayList<Category>  getAllCategory() throws Exception;
	public ArrayList<Category>  getSecondCategory(int f_id) throws Exception;

}
