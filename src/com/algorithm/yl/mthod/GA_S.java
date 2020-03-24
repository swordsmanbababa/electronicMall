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
 * @�ļ�����: GA_S.java   
 * @��·��: com.algorithm.yl.main   
 * @����: TODO   �����ȶ�ʱ��
 * @���ߣ�li
 * @ʱ�䣺2017-9-23 ����7:43:05     
 */
public class GA_S {
private List<Genents> populations = null;
	
	private static String fileUrl="file/output/GA_N.txt";  //���
	FileWriter writer;
	
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
			while(sub >= CommonConstants.ProgenyCount_num){
				int _cross=(int)(Math.random()*100);
				if(_cross <= CommonConstants.Cross_Probability){
					subgen = Operator.cross(populations.get(CommonUtil.generateIntNumber(CommonConstants.ProgenyCount_num)), 
							populations.get(CommonUtil.generateIntNumber(CommonConstants.ProgenyCount_num)));
					subgen.setPhy_count(Operator.getUnActivePhyCount(subgen));
					subgen.setShift_count(Operator.getShiftCount(root, subgen));
					subgen.setStab_time(Operator.getStabTime(subgen, forecast));
					sub++;
				}
				int _varation=(int)(Math.random()*100);
				if(_varation>(100-CommonConstants.Variation_Probability*100)){
					var_index = CommonUtil.generateIntNumber(CommonConstants.ProgenyCount_num);
					Operator.variation(populations.get(var_index));
					populations.get(var_index).setPhy_count(Operator.getUnActivePhyCount(populations.get(var_index)));
					populations.get(var_index).setShift_count(Operator.getShiftCount(root, populations.get(var_index)));
					populations.get(var_index).setStab_time(Operator.getStabTime(populations.get(var_index), forecast));
				}
			}
//			generation=sort_select(generation);  //�����ɸѡ ��������ǰ����Ϊ��һ������
			populations = sort(populations);
			gen++;
		}
		
		System.out.println();
		System.out.println("MOGA2���н��(Ǩ�ƴ���,�ȶ�ʱ��,δ���������ڵ����)��");
		int selectIndex = select_lastOne(populations);
		System.out.println(Operator.getShiftCount(root, populations.get(selectIndex)) + "\t"
				+ Operator.getStabTime(populations.get(selectIndex), forecast) + "\t"
				+ Operator.getUnActivePhyCount(populations.get(selectIndex)));
	}
	//����
	public List<Genents> sort(List<Genents> genList){
		// ��������ǰ�ĸ���
		List<Genents> generation_new=new ArrayList<Genents>();
		
		int i,j;
		float gen[][]=new float[genList.size()][2];
		for(i=0;i<gen.length;i++){
			gen[i][0]=i;
			gen[i][1]= Operator.getStabTime(genList.get(i), forecast);
		}
		//����Ǩ�ƴ�������
		for(i=0;i<gen.length;i++){
			for(j=0;j<gen.length-1;j++){
				float []temp;
				if(gen[i][1]>gen[j][1]){
					temp=gen[i];
					gen[i]=gen[j];
					gen[j]=temp;
				}
			}
		}
		for(i=0;i<CommonConstants.ProgenyCount_num;i++){
			generation_new.add(genList.get((int)gen[i][0]));
		}
		return generation_new;
	}
	//������ѽ��
	public static int select_lastOne(List<Genents> generation){
		int i=0;
		int index=0;
		float []fitNess=new float[generation.size()];
		for(i=0;i<generation.size();i++){
			fitNess[i]=(float) ((float)generation.get(i).getStab_time()-0.1*generation.get(i).getShift_count());
		}
		for(i=1;i<generation.size();i++){
			if(fitNess[index]<fitNess[i] && generation.get(i).getIs_init()!=1){
				index=i;
			}
		}
		return index;
	}
}
