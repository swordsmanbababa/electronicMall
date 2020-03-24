package com.algorithm.yl.util;

import org.apache.commons.math3.util.MathArrays;
import java.util.Comparator;

import com.algorithm.yl.pojo.Genents;

/**      
 * @�ļ�����: WeightSorter.java   
 * @��·��: com.algorithm.yl.util   
 * @����: TODO   
 * @���ߣ�li
 * @ʱ�䣺2017-9-24 ����2:00:58     
 */
public class WeightSorter implements Comparator<Genents>{
	
	private Genents genents;
	
	public WeightSorter(Genents genents){
		this.genents = genents;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Genents o1, Genents o2) {
		// TODO Auto-generated method stub
		double d1 = MathArrays.distance(genents.getWeight(), o1.getWeight());
		double d2 = MathArrays.distance(genents.getWeight(), o2.getWeight());
		
		return Double.compare(d1, d2);
	}
}
