package com.algorithm.yl.mthod;

import java.util.ArrayList;
import java.util.List;

import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.pojo.Genents;
import com.algorithm.yl.util.Attribute;

/**      
 * @�ļ�����: NSGA2.java   
 * @��·��: com.algorithm.yl.main   
 * @����: TODO   
 * @���ߣ�li
 * @ʱ�䣺2017-9-23 ����7:51:52     
 */
public class NSGA2 {
	
	public List<Attribute<Integer,Integer,Float>> att;
	
	public List<Genents> noneDominatedSort(List<Genents> genentsList){   //���ٷ�֧������
		List<Genents> sp=null;
		List<Genents> Q=null;
		List<Genents> F=new ArrayList<Genents>();
		for(Genents p:genentsList){
			sp=new ArrayList<Genents>();
			p.setRank(0);
			p.setTemp_rank(0);
			for(Genents q:genentsList){
				if(is_dominated(p, q)){   //p֧��q
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
	
	public boolean is_dominated(Genents p,Genents q){   //֧���ϵ
		if(p.getStab_time()>q.getStab_time() && p.getShift_count()<q.getShift_count()){
			return true;
		}
		return false;
	}
	
	public void crowding_distance(List<Genents> genentsList,int rank,int start_rank,int start_rank_length){  //ӵ������ļ���
		float gen_disatance[][]=new float[genentsList.size()][3];   //���������е�id ����ڵ�ʹ�ø��� Ǩ�ƴ��� ������ӵ���ά�������� �������������� ����ӵ������
		int i=0,j=0,k=0,l=0;
		for(i=0;i<genentsList.size();i++){
			gen_disatance[i][0]=genentsList.get(i).getStab_time();
			gen_disatance[i][1]=CommonConstants.vm_num-genentsList.get(i).getShift_count();
			genentsList.get(i).setDistance(0);
			gen_disatance[i][2]=i;
		}
		
		float max,min;
		//�����ȶ�ʱ������
		//����Ǩ�ƴ�������
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
			//���һ���Ϳ�ʼ�ĵ�һ��ֵ����Ϊ�����
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
	
	public List<Genents> sort(List<Genents> genentsList,int len){   //����Ǩ�ƴ��� ������ڵ��������
		noneDominatedSort(genentsList);
		/*---------------------------------------------���յȼ�����ֲ�ӵ������--------------------------------------------------------*/
		//���յĵȼ�����ֲ�ӵ������
		int rank=0;   //�ȼ���
		int start_rank=0;   //��Ҫ����ӵ������ļ���
		int start_rank_length=0;   //��Ҫ����ӵ������ļ���ĸ�����
		for(Genents p:genentsList){
			if(rank<p.getRank()){
				rank=p.getRank();
			}
		}
		int count_sum=0;  //ͳ��ÿһ���ۼӵĸ�����
		//�ж�����һ������
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
		att=new ArrayList<Attribute<Integer,Integer,Float>>();   //���������е�id ����ڵ�ʹ�ø��� Ǩ�ƴ��� ������ӵ���ά�������� �������������� ����ӵ������
		Attribute attribute=null;
		int i=0,j=0,k=0;
		for(i=0;i<genentsList.size();i++){
			attribute=new Attribute<Integer, Integer, Float>();
			attribute.setKey(i);
			attribute.setValue1(genentsList.get(i).getRank());  //�ȼ�
			attribute.setValue2(genentsList.get(i).getDistance());  //ӵ������
			att.add(attribute);
		}
		//����
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
