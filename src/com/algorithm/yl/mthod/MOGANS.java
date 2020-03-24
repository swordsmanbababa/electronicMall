package com.algorithm.yl.mthod;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.algorithm.yl.common.Operator;
import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.pojo.Genents;
import com.algorithm.yl.pojo.PhysicalNode;
import com.algorithm.yl.util.CommonUtil;

/**      
 * @�ļ�����: MOGANS.java   
 * @��·��: com.algorithm.yl.main   
 * @����: TODO   
 * @���ߣ�li
 * @ʱ�䣺2017-9-23 ����7:41:58     
 */
public class MOGANS {
	
	private List<Genents> populations = null;
	
	private static String fileUrl="file/output/MOGA.txt";  //���
	FileWriter writer;
	
	private static NSGA2 nsga2=new NSGA2();
	
	private List<PhysicalNode> phyList = new ArrayList<PhysicalNode>();
	private Genents root = null;
	private String [][]forecast = null;
	
	//��ʼ������
	public void init() throws Exception{
		phyList = Operator.initPhyNode();
		populations = Operator.initGeneration(phyList);
		root = CommonUtil.copy(populations.get(0));
		forecast = Operator.readForecastFile();  //��ȡ�����ļ�
		
		for(int i=0; i<populations.size(); i++){
			if(i==0){
				populations.get(i).setIs_init(1);
			}
			populations.get(i).setPhy_count(Operator.getUnActivePhyCount(populations.get(i)));
			populations.get(i).setShift_count(Operator.getShiftCount(root, populations.get(i)));
			populations.get(i).setStab_time(Operator.getStabTime(populations.get(i), forecast));
		}
		for(Genents tgen:populations){
			 System.out.println(""+tgen.getShift_count()+" "+tgen.getStab_time()+" "+tgen.getPhy_count());}
	
	}
	public void start() throws Exception{
		int gen = 1;
		Genents subgen = null;
		int var_index = 0;
		while(true){
			if(gen >= CommonConstants.GEN_NUM){
				break;
			}
			int sub = 0;  //�����Ӹ���ĸ���
		//	while(sub >= CommonConstants.ProgenyCount_num){
//amend alter one row			
			while(sub <= CommonConstants.ProgenyCount_num){
				int _cross=(int)(Math.random()*100);
//				if(_cross <= CommonConstants.Cross_Probability){
//amend alter one row				
				if(_cross <= CommonConstants.Cross_Probability*100){
					subgen = Operator.cross(populations.get(CommonUtil.generateIntNumber(CommonConstants.ProgenyCount_num)), 
							populations.get(CommonUtil.generateIntNumber(CommonConstants.ProgenyCount_num)));
					subgen.setPhy_count(Operator.getUnActivePhyCount(subgen));
					subgen.setShift_count(Operator.getShiftCount(root, subgen));
					subgen.setStab_time(Operator.getStabTime(subgen, forecast));
					populations.add(subgen);
					sub++;
				}
				int _varation=(int)(Math.random()*100);
				if(_varation>(100-CommonConstants.Variation_Probability*100)){
					var_index = CommonUtil.generateIntNumber(CommonConstants.ProgenyCount_num);
					populations.set(var_index, Operator.variation(populations.get(var_index)));
					populations.get(var_index).setPhy_count(Operator.getUnActivePhyCount(populations.get(var_index)));
					populations.get(var_index).setShift_count(Operator.getShiftCount(root, populations.get(var_index)));
					populations.get(var_index).setStab_time(Operator.getStabTime(populations.get(var_index), forecast));
				}
			}
//			generation=sort_select(generation);  //�����ɸѡ ��������ǰ����Ϊ��һ������
	
			populations = nsga2.sort(populations, CommonConstants.ProgenyCount_num);
			gen++;
		}
		
		System.out.println();
		
		System.out.println("MOGANS���н��(Ǩ�ƴ���,�ȶ�ʱ��,δ���������ڵ����)��");
//		int selectIndex = select_lastOne(populations);
//		System.out.println(Operator.getShiftCount(root, populations.get(selectIndex)) + "\t"
//				+ Operator.getStabTime(populations.get(selectIndex), forecast) + "\t"
//				+ Operator.getUnActivePhyCount(populations.get(selectIndex)));
		Genents lastgen=select_lastOne(populations);
		System.out.println(Operator.getShiftCount(root, lastgen) + "\t"
		+ Operator.getStabTime(lastgen, forecast) + "\t"
		+ Operator.getUnActivePhyCount(lastgen));
		for(Genents tgen:populations){
			 System.out.println(""+tgen.getShift_count()+" "+tgen.getStab_time()+" "+tgen.getPhy_count());}
	}
	//������ѽ��
	public static Genents select_lastOne(List<Genents> generation){
//		int i=0;
//		int index=0;
//		float []fitNess=new float[generation.size()];
//		for(i=0;i<generation.size();i++){
//			fitNess[i]=(float) ((float)generation.get(i).getStab_time()-0.1*generation.get(i).getShift_count());
//		}
//		for(i=1;i<generation.size();i++){
//			if(fitNess[index]<fitNess[i] && generation.get(i).getIs_init()!=1){
//				index=i;
//			}
//		}
//		return index;
		Genents genent=generation.get(0);
		float []fitNess=new float[generation.size()];
		for(int i=0;i<generation.size();i++){
			fitNess[i]=(float) ((float)generation.get(i).getStab_time()-0.1*(float)generation.get(i).getShift_count()+(float)generation.get(i).getPhy_count());
		}
		int index=1;
		for(int i=1;i<generation.size();i++){
			if(fitNess[index]<fitNess[i] && generation.get(i).getIs_init()!=1){
				index=i;
			}
		}
		genent=generation.get(index);
		return genent;
	}
}
