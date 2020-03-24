package com.algorithm.yl.mthod;

import java.util.ArrayList;
import java.util.List;

import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.pojo.Genents;
import com.algorithm.yl.util.Attribute;

/**      
 * @文件名称: NSGA2.java   
 * @类路径: com.algorithm.yl.main   
 * @描述: TODO   
 * @作者：li
 * @时间：2017-9-23 下午7:51:52     
 */
public class NSGA2 {
	
	public List<Attribute<Integer,Integer,Float>> att;
	
	public List<Genents> noneDominatedSort(List<Genents> genentsList){   //快速非支配排序
		List<Genents> sp=null;
		List<Genents> Q=null;
		List<Genents> F=new ArrayList<Genents>();
		for(Genents p:genentsList){
			sp=new ArrayList<Genents>();
			p.setRank(0);
			p.setTemp_rank(0);
			for(Genents q:genentsList){
				if(is_dominated(p, q)){   //p支配q
					p.getTemp_genents().add(q);
				}else if(is_dominated(q, p)){
					p.setTemp_rank(p.getTemp_rank()+1);
				}
			}
			if(p.getTemp_rank()==0){
				p.setRank(1);
				F.add(p);
			}
		}
		int i=1;
		while(!F.isEmpty()){
			Q=new ArrayList<Genents>();
			for(Genents p:F){
				for(Genents q:p.getTemp_genents()){
					q.setTemp_rank(q.getTemp_rank()-1);
					if(q.getTemp_rank()==0){
						q.setRank(i+1);
						Q.add(q);
					}
				}
			}
			i++;
			F=Q;
		}
		return genentsList;
	}
	
	public boolean is_dominated(Genents p,Genents q){   //支配关系
		if(p.getStab_time()>q.getStab_time() && p.getShift_count()<q.getShift_count()){
			return true;
		}
		return false;
	}
	
	public void crowding_distance(List<Genents> genentsList,int rank,int start_rank,int start_rank_length){  //拥挤距离的计算
		float gen_disatance[][]=new float[genentsList.size()][3];   //将基因序列的id 物理节点使用个数 迁移次数 参数添加到二维数组里面 方便后面进行排序 计算拥挤距离
		int i=0,j=0,k=0,l=0;
		for(i=0;i<genentsList.size();i++){
			gen_disatance[i][0]=genentsList.get(i).getStab_time();
			gen_disatance[i][1]=CommonConstants.vm_num-genentsList.get(i).getShift_count();
			genentsList.get(i).setDistance(0);
			gen_disatance[i][2]=i;
		}
		
		float max,min;
		//按照稳定时间排序
		//按照迁移次数排序
		for(k=0;k<2;k++){
			for (j = 0; j < gen_disatance.length ; j++) {
				for (i = 0; i < gen_disatance.length - 1; i++) {
					float[] temp;
					if (gen_disatance[i][k]<gen_disatance[i + 1][k]) {
						temp = gen_disatance[i];
						gen_disatance[i] =gen_disatance[i + 1];
						gen_disatance[i + 1] = temp;
					}
				}
			}
			max=gen_disatance[0][k];
			min=gen_disatance[gen_disatance.length-1][k];
			//最后一个和开始的第一个值设置为无穷大
			genentsList.get((int)gen_disatance[0][2]).setDistance(Float.MAX_VALUE);
			genentsList.get((int)gen_disatance[gen_disatance.length-1][2]).setDistance(Float.MAX_VALUE);
			float weight=1.0f;
			if(k==1){
				weight=0.05f;
			}else{
				weight=0.95f;
			}
			for(l=1;l<gen_disatance.length-1;l++){
//				generation.get(gen_disatance[l][2]).setDistance(generation.get(gen_disatance[l][2]).getDistance()+Math.abs(gen_disatance[l+1][k]-gen_disatance[l-1][k])/(max-min));
				genentsList.get((int)gen_disatance[l][2]).setDistance(genentsList.get((int)gen_disatance[l][2]).getDistance()+weight*(gen_disatance[l+1][k]-gen_disatance[l-1][k])/(max-min));
			}
		}
	}
	
	public List<Genents> sort(List<Genents> genentsList,int len){   //按照迁移次数 或物理节点个数排序
		noneDominatedSort(genentsList);
		/*---------------------------------------------按照等级计算局部拥挤距离--------------------------------------------------------*/
		//按照的等级计算局部拥挤距离
		int rank=0;   //等级数
		int start_rank=0;   //需要计算拥挤距离的级别
		int start_rank_length=0;   //需要计算拥挤距离的级别的个体数
		for(Genents p:genentsList){
			if(rank<p.getRank()){
				rank=p.getRank();
			}
		}
		int count_sum=0;  //统计每一级累加的个体数
		//判断在哪一级计算
		for(int id=1;id<=rank;id++){
			int count_id=0;
			for(Genents p:genentsList){
				if(id==p.getRank()){
					count_id++;
				}
			}
			count_sum+=count_id;
			if(count_sum>=CommonConstants.GEN_NUM){
				start_rank_length=count_id;
				start_rank=id;
				break;
			}
		}
		/*---------------------------------------------------------------------------------------------------------------------*/
		crowding_distance(genentsList, rank, start_rank, start_rank_length);
		
		List<Genents> generation_new=new ArrayList<Genents>();
		att=new ArrayList<Attribute<Integer,Integer,Float>>();   //将基因序列的id 物理节点使用个数 迁移次数 参数添加到二维数组里面 方便后面进行排序 计算拥挤距离
		Attribute attribute=null;
		int i=0,j=0,k=0;
		for(i=0;i<genentsList.size();i++){
			attribute=new Attribute<Integer, Integer, Float>();
			attribute.setKey(i);
			attribute.setValue1(genentsList.get(i).getRank());  //等级
			attribute.setValue2(genentsList.get(i).getDistance());  //拥挤距离
			att.add(attribute);
		}
		//排序
		for (j = 0; j < att.size(); j++) {
			for (i = 0; i < att.size() - 1; i++) {
				Attribute temp;
				if (att.get(i).getValue1()>att.get(i+1).getValue1()) {
					temp = att.get(i);
					att.set(i, att.get(i+1));
					att.set(i+1, temp);
				}else if(att.get(i).getValue1()==att.get(i+1).getValue1()){
					if(att.get(i).getValue2()<att.get(i+1).getValue2()){
						temp = att.get(i);
						att.set(i, att.get(i+1));
						att.set(i+1, temp);
					}
				}else{
				}
			}
		}
		for(i=0;i<len;i++){
			generation_new.add(genentsList.get(att.get(i).getKey()));
		}
		return generation_new;
	}
}
