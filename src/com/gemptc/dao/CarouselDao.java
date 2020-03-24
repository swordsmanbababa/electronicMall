package com.gemptc.dao;



import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.gemptc.entity.Carousel;
import com.gemptc.entity.User;


public interface CarouselDao {
	
	public List<Carousel> searchAllCarouses() throws Exception;
	public boolean deleceCarouselsByCarID(String car_id) throws Exception;
	public boolean updateCarouselByCarID(Carousel carouse) throws Exception;
}
