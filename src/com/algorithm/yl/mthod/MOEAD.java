package com.algorithm.yl.mthod;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.genetics.Population;

import com.algorithm.yl.common.Operator;
import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.pojo.Genents;
import com.algorithm.yl.pojo.PhysicalNode;
import com.algorithm.yl.util.CommonUtil;
import com.algorithm.yl.util.RandomGenerator;
import com.algorithm.yl.util.WeightSorter;

/**      
 * @�ļ�����: MOEAD.java   
 * @��·��: com.algorithm.yl.main   
 * @����: TODO   
 * @���ߣ�li
 * @ʱ�䣺2017-9-23 ����7:41:28     
 */
public class MOEAD {
private List<Genents> populations = null;
	
	private static String fileUrl="file/output/MOGA.txt";  //���
	FileWriter writer;
	
	private static NSGA2 nsga2=new NSGA2();
	
	private List<PhysicalNode> phyList = new ArrayList<PhysicalNode>();
	private static Genents root = null;
	private static String [][]forecast = null;
	
	private static int [] ideapoint = new int[3];  //Ǩ�ƴ���,�ȶ�ʱ��,δ���������ڵ����
	
	//��ʼ������
	public void init() throws Exception{
		phyList = Operator.initPhyNode();
		populations = Operator.initGeneration(phyList);
		root = CommonUtil.copy(populations.get(0));
		forecast = Operator.readForecastFile();  //��ȡ�����ļ�
		
		//����Ȩ������
    	RandomGenerator randomGenerator = new RandomGenerator(3, CommonConstants.ProgenyCount_num);
	
		List<double[]> weights = randomGenerator.generate();
				
		for(int i=0; i<populations.size(); i++){
			if(i==0){
				populations.get(i).setIs_init(1);
			}
			populations.get(i).setPhy_count(Operator.getUnActivePhyCount(populations.get(i)));
			populations.get(i).setShift_count(Operator.getShiftCount(root, populations.get(i)));
			populations.get(i).setStab_time(Operator.getStabTime(populations.get(i), forecast));
			
			populations.get(i).setWeight(weights.get(i));
		}
		for(Genents tgen:populations){
			 System.out.println(""+tgen.getShift_count()+" "+tgen.getStab_time()+" "+tgen.getPhy_count());
			 }
		initializeNeighborhoods();
		initIdeaPoint();
	}
//	private void initializeNeighborhoods() {   //Ȩ������֮��ľ���
//		
//
//		for (Genents genents : populations) {
//			List<Genents> sortedPopulation = new ArrayList<Genents>( );
//			
//			for(Genents gen : populations)
//				sortedPopulation.add(CommonUtil.copy(gen));
//			
//			Collections.sort(sortedPopulation, new WeightSorter(genents));   //������Ȩ������֮����������
//
//			for (int i = 0; i < CommonConstants.neighborhoodSize; i++) {     //Ϊÿһ��individual�ҵ��������neighborhoodSize������
//				genents.getNeighbor().add(sortedPopulation.get(i));
//			}
//		}
//	}
	private void initializeNeighborhoods() {   //Ȩ������֮��ľ���
		List<Genents> sortedPopulation = new ArrayList<Genents>( );
		sortedPopulation.addAll(populations);
		
		for (Genents genents : populations) {
			Collections.sort(sortedPopulation, new WeightSorter(genents));   //������Ȩ������֮����������
			for (int i = 0; i < CommonConstants.neighborhoodSize; i++) {     //Ϊÿһ��individual�ҵ��������neighborhoodSize������
				genents.getNeighbor().add(sortedPopulation.get(i));
			}
		}
	}
	public void start() throws Exception{
		int gen = 1;
		Genents subgen = null;
		while(true){
			if(gen >= CommonConstants.GEN_NUM){
				break;
			}
			
			for(int k=0; k<populations.size(); k++){
				double _cross=Math.random();
				if(_cross <= CommonConstants.Cross_Probability){
					subgen = Operator.cross(populations.get(k), 
							populations.get(CommonUtil.generateIntNumber(CommonConstants.ProgenyCount_num)));
				    
					subgen.setPhy_count(Operator.getUnActivePhyCount(subgen));
					subgen.setShift_count(Operator.getShiftCount(root, subgen));
					subgen.setStab_time(Operator.getStabTime(subgen, forecast));
					
				}
				int _varation=(int)(Math.random()*100);
				if(_varation<CommonConstants.Variation_Probability*100){
					Genents subgen_v=Operator.variation(populations.get(k));
					if(Operator.dominate(subgen_v, subgen)){
						subgen=subgen_v;
						subgen.setPhy_count(Operator.getUnActivePhyCount(subgen));
						subgen.setShift_count(Operator.getShiftCount(root, subgen));
						subgen.setStab_time(Operator.getStabTime(subgen, forecast));
					 updateIdeaPoint(populations.get(k));
					}
				}
				if(subgen==null)
				{
					continue;
				}
					
					boolean flag = false;
					//��������� todo
					for(Genents g:populations.get(k).getNeighbor()){
						if(Operator.dominate(subgen, g)){  //���ɵ��Ӵ�����֧��������壬����
							flag = true;
						}else{
							if(fitness(g, g.getWeight()) > fitness(subgen, g.getWeight())){
								flag = true;
							}
						}
						if(flag){
							populations.get(k).getPhyList().clear();
							populations.get(k).getPhyList().addAll(subgen.getPhyList());
							populations.get(k).setShift_count(Operator.getShiftCount(root, subgen));
							populations.get(k).setPhy_count(Operator.getUnActivePhyCount(subgen));
							populations.get(k).setStab_time(Operator.getStabTime(subgen, forecast));
//							populations.get(g.getId()).getPhyList().clear();
//							populations.get(g.getId()).getPhyList().addAll(subgen.getPhyList());
//							populations.get(g.getId()).setShift_count(Operator.getShiftCount(root, subgen));
//							populations.get(g.getId()).setPhy_count(Operator.getUnActivePhyCount(subgen));
//							populations.get(g.getId()).setStab_time(Operator.getStabTime(subgen, forecast));
//							g.getPhyList().clear();
//							g.getPhyList().addAll(subgen.getPhyList());
//							g.setShift_count(Operator.getShiftCount(root, subgen));
//							g.setPhy_count(Operator.getUnActivePhyCount(subgen));
//							g.setStab_time(Operator.getStabTime(subgen, forecast));
							
							 updateIdeaPoint(subgen);
						}
					}
			
				
			
			}
			gen++;
		}
		
		System.out.println();
		System.out.println("MOGAD���н��(Ǩ�ƴ���,�ȶ�ʱ��,δ���������ڵ����)��");
//		int selectIndex = select_lastOne(populations);
//		System.out.println(Operator.getShiftCount(root, populations.get(selectIndex)) + "\t"
//				+ Operator.getStabTime(populations.get(selectIndex), forecast) + "\t"
//				+ Operator.getUnActivePhyCount(populations.get(selectIndex)));
		Genents lastgen=select_lastOne(populations);
		System.out.println(Operator.getShiftCount(root, lastgen) + "\t"
		+ Operator.getStabTime(lastgen, forecast) + "\t"
		+ Operator.getUnActivePhyCount(lastgen));
		
		for(Genents tgen:populations){
		 System.out.println(""+tgen.getShift_count()+" "+tgen.getStab_time()+" "+tgen.getPhy_count());
		 tgen.printGen();
		}
	}
	//������ѽ��
	public static Genents select_lastOne(List<Genents> generation){
//		int i=0;
//		int index=0;
		Genents genent=generation.get(0);
//	    
//		for(Genents gen:generation){
//			 if(fitness(genent, genent.getWeight()) > fitness(gen, gen.getWeight())){
//				genent=gen;
//			}
//		}
		float []fitNess=new float[generation.size()];
		for(int i=0;i<generation.size();i++){
			fitNess[i]=(float) ((float)generation.get(i).getStab_time()-(float)generation.get(i).getShift_count()+(float)generation.get(i).getPhy_count());
		}
		int index=0;
		for(int i=1;i<generation.size();i++){
			if(fitNess[index]<fitNess[i] && generation.get(i).getIs_init()!=1){
				index=i;
			}
		}
		genent=generation.get(index);
		return genent;
	}
	//����Ŀ������
	public void updateIdeaPoint(Genents g){
		int shiftCount = Operator.getShiftCount(root, g);
		int stabTime = Operator.getStabTime(g, forecast);
		int unActivePhyNode = Operator.getUnActivePhyCount(g);
		if(ideapoint[0] > shiftCount){
			ideapoint[0] = shiftCount;
		}
		if(ideapoint[1] < stabTime){
			ideapoint[1] = stabTime;
		}
		if(ideapoint[2] < unActivePhyNode){
			ideapoint[2] = unActivePhyNode;
		}
	}
	//��ʼ���ο�����
	public void initIdeaPoint(){
		int shiftCount = CommonConstants.vm_num;
		int stabTime = 0;
		int unActivePhyNode = 0;
		ideapoint[0]=CommonConstants.vm_num;
		for(Genents genents:populations){
//			System.out.println(""+genents.getId()+"  "+genents.getShift_count());
			shiftCount = Operator.getShiftCount(root, genents);
			stabTime = Operator.getStabTime(genents, forecast);
			unActivePhyNode = Operator.getUnActivePhyCount(genents);
			if((ideapoint[0] > shiftCount) && (genents.getIs_init() != 1)){
				ideapoint[0] = shiftCount;
			}
			if(ideapoint[1] < stabTime){
				ideapoint[1] = stabTime;
			}
			if(ideapoint[2] < unActivePhyNode){
				ideapoint[2] = unActivePhyNode;
			}
		}
	}
	private static double fitness(Genents genents, double[] weights) {  //��Ӧ��---�Ƚϵ��ǵ�ǰ������ο���֮���ֵ
		double max = Double.NEGATIVE_INFINITY;
		
		int target = 0;
		for (int i = 0; i < 3; i++) {
			
			if(i == 0){
				target = Operator.getShiftCount(root, genents);
			}
			if(i == 1){
				target = Operator.getStabTime(genents, forecast);
			}
			if(i == 2){
				target = Operator.getUnActivePhyCount(genents);
			}
			max = Math.max(max, Math.max(weights[i], 0.0001)
					*(((double) Math.abs(target - ideapoint[i]))/ideapoint[i]));
		}

		return max;
	}
	
	
}
