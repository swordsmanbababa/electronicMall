package com.algorithm.yl.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.algorithm.yl.constants.CommonConstants;
import com.algorithm.yl.pojo.Genents;
import com.algorithm.yl.pojo.NormalizedVM;
import com.algorithm.yl.pojo.PhysicalNode;
import com.algorithm.yl.pojo.VM;
import com.algorithm.yl.util.CommonUtil;


public class Operator {
	private static String file = "file/config/phyconfig.txt";
	private static String init_file = "file/config/init.txt";
	private static String forecast_file = "file/config/forecast.txt";
	private static String trend_forecast_file = "file/config/TrendForecast.txt";
	
	public static String[][] readForecastFile() throws Exception{
		String result[][] = new String[CommonConstants.time][CommonConstants.vm_num];
		//��ȡԤ��ֵ
	    File fore_file=new File(forecast_file);
	    FileReader reader=new FileReader(fore_file);
	    BufferedReader bufreader=new BufferedReader(reader);
	    String s=null;
	    int i=0;
	    while((s=bufreader.readLine())!=null){
	    	result[i]=s.split(";");
	    	i++;
	    }
	    bufreader.close();
	    reader.close();
	    return result;
	}
	public static String[][] readTrendForecastFile() throws Exception{
		String result[][] = new String[CommonConstants.time][CommonConstants.vm_num];
		//��ȡԤ��ֵ
	    File fore_file=new File(trend_forecast_file);
	    FileReader reader=new FileReader(fore_file);
	    BufferedReader bufreader=new BufferedReader(reader);
	    String s=null;
	    int i=0;
	    while((s=bufreader.readLine())!=null){
	    	result[i]=s.split(";");
	    	i++;
	    }
	    bufreader.close();
	    reader.close();
	    return result;
	}
	
	public static String read()throws Exception{
		//
	    File fore_file=new File(file);
	    FileReader reader=new FileReader(fore_file);
	    BufferedReader bufreader=new BufferedReader(reader);
	    String s=null;
	    s=bufreader.readLine();
	    bufreader.close();
	    reader.close();
	    return s;
	}
	
	//��ʼ������ڵ�
	public static List<PhysicalNode> initPhyNode() throws Exception  //��ʼ������ڵ�
	{
		String s = read();
//		8:4000;  cpu���� �ڴ��СM
		String result[] = s.split(";");
		int []mem = new int[CommonConstants.physical_node_num];
		int []cpu = new int[CommonConstants.physical_node_num];
		
		for(int i=0; i<CommonConstants.physical_node_num; i++){
			String arr[] = result[i].split(":");
			cpu[i] = Integer.parseInt(arr[0]);
			mem[i] = Integer.parseInt(arr[1]);
		}
		int i=0;
		List<PhysicalNode> phyList=new ArrayList<PhysicalNode>();
		PhysicalNode physicalNode = null;
		for(i=0;i<CommonConstants.physical_node_num;i++){
			physicalNode = new PhysicalNode();
			physicalNode.setId(i);
			physicalNode.setStatus(0);
			physicalNode.setMemory(mem[i]);
			physicalNode.setCpu(cpu[i]);
			physicalNode.setInitCpu(cpu[i]);
			physicalNode.setInitMemory(mem[i]);
			
			phyList.add(physicalNode);
		}
		return phyList;
	}
	//��ʼ��������Ⱥ
	public static List<Genents> initGeneration(List<PhysicalNode> phyList) throws Exception{
		List<Genents> populations = new ArrayList<Genents>();
		
		Genents gen=null;  //һ����ʼ����
		
		File fore_file=new File(init_file);
	    FileReader reader = null;
	    BufferedReader bufreader=null;
	    String s=null;
	    int i = 0;
	    //15:1021:2����ʾ��  ��һ�����������id �ڶ����ڴ������С  ��������Ҫcpu����
	    try {
	    	reader = new FileReader(fore_file);
	    	bufreader=new BufferedReader(reader);
			while((s=bufreader.readLine())!=null){
				gen=new Genents();
				gen.setId(i);
				
				String []str=new String[CommonConstants.vm_num];
				str=s.split(";");
//amend add one row
				phyList=Operator.initPhyNode();
				rule(gen, phyList, str);//����һ�����壬��Ԥ���ļ��е���������õ����������ڵ���
				populations.add(gen);
				i++;
			}
			bufreader.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return populations;
	}
	//�����������ڵ���ù���
//amend rewrite method	
	public static void rule(Genents gen,List<PhysicalNode> phyList,String []str) throws Exception{
		//vm_info[0]target_id  vm_info[1] mem  vm_info[2] cpu
		VM vm=null;
		int targetID = 0;
		boolean flag = false;
		for(int i=0;i<str.length;i++){
			flag = false;
			String []vm_info=new String[3];
			vm_info=str[i].split(":");
			vm=new VM();
			vm.setId(i);
			vm.setMem(Double.parseDouble(vm_info[1]));
			vm.setCpu(Double.parseDouble(vm_info[2]));
			
			targetID = Integer.parseInt(vm_info[0]);
			if((phyList.get(targetID).getCpu() >= vm.getCpu()) && (phyList.get(targetID).getMemory() >= vm.getMem())){
				flag = true;
			}else{
				for(int k=0; k<phyList.size(); k++){
					if((phyList.get(k).getCpu() >= vm.getCpu()) && (phyList.get(k).getMemory() >= vm.getMem())){
						targetID = k;
						flag = true;
						break;
					}
				}
			}
			if(flag){
				phyList.get(targetID).setCpu(phyList.get(targetID).getCpu() - vm.getCpu());
				phyList.get(targetID).setMemory(phyList.get(targetID).getMemory()- vm.getMem());
				phyList.get(targetID).setStatus(1);
				phyList.get(targetID).getVmList().add(vm);
			}else{
				throw new Exception();
			}
		}
		gen.setPhyList(phyList);
	}
//	public static void rule(Genents gen,List<PhysicalNode> phyList,String []str) throws Exception{
//	//vm_info[0]target_id  vm_info[1] mem  vm_info[2] cpu
//	VM vm=null;
//	int targetID = 0;
//	boolean flag = false;
//	for(int i=0;i<str.length;i++){
//		flag = false;
//		String []vm_info=new String[3];
//		vm_info=str[i].split(":");
//		vm=new VM();
//		vm.setId(i);
//		vm.setMem(Integer.parseInt(vm_info[1]));
//		vm.setCpu(Integer.parseInt(vm_info[2]));
//		
//		targetID = Integer.parseInt(vm_info[0]);
//		if((phyList.get(targetID).getCpu() >= vm.getCpu()) && (phyList.get(targetID).getMemory() >= vm.getMem())){
//			flag = true;
//		}else{
//			for(int k=0; k<phyList.size(); k++){
//				if((phyList.get(k).getCpu() >= vm.getCpu()) && (phyList.get(k).getMemory() >= vm.getMem())){
//					targetID = k;
//					flag = true;
//					break;
//				}
//			}
//		}
//		if(flag){
//			phyList.get(targetID).setCpu(phyList.get(targetID).getCpu() - vm.getCpu());
//			phyList.get(targetID).setMemory(phyList.get(targetID).getMemory()- vm.getMem());
//			phyList.get(targetID).setStatus(1);
//		}else{
//			throw new Exception();
//		}
//	}
//	gen.setPhyList(phyList);
//}
	/*---------------------------------------------------------------------------------------------------------*/
	//��������Ǩ�ƴ���
	public static int getShiftCount(Genents root, Genents node){
//		node.printGen();
		int count = 0;
		int index=0;
		int arr[] = null;
		int i = 0;
		for(PhysicalNode p:root.getPhyList()){
			
			if(p.getVmList().size() == 0){
				count += node.getPhyList().get(index).getVmList().size();
			}else{
				arr = new int[p.getVmList().size()];
				i = 0;
				for(VM v:p.getVmList()){
					arr[i] = v.getId();
					i++;
				}
				if(node.getPhyList().size()==0){
					System.out.println(node.getPhyList().size() +" "+index+";");
					node.printGen();}
				for(VM v:node.getPhyList().get(index).getVmList()){
					if(!CommonUtil.isExistInArray(arr, v.getId())){
						count++;
					}
				}
			}
			index++;
		}
		return count;
	}
	//��������δʹ�ýڵ����
	public static int getUnActivePhyCount(Genents genents){
		int count = 0;
		for(PhysicalNode p:genents.getPhyList()){
			if(p.getStatus() == 0){
				count++;
			}
		}
		return count;
	}
	//���������ȶ�ʱ��
	public static int getStabTime(Genents genents, String [][]result){
		int time = 1;
		double sumMem;
		double sumCpu;
		boolean flag = false;
		for(int i=0;i<result.length;i++){
			if(flag){
				break;
			}
			for(PhysicalNode p:genents.getPhyList()){
				sumMem = 0;
				sumCpu = 0;
				for(VM v:p.getVmList()){
					String []arr = result[i][v.getId()].split(":");
					sumMem += Double.parseDouble(arr[0]);
					sumCpu += Double.parseDouble(arr[1]);
				
				}
				if(sumCpu > p.getInitCpu() || sumMem > p.getInitMemory()){
					flag = true;
					break;
				}
			}
			
			if(!flag){
				time++;
			}
		}
		return time;
	}
//amend rewrite getStableTime
	public static int getStabTimePro(Genents genents, String [][]result){
		int time = 1;
		int alterPhyNodeNum=0;
		int sumMem;
		int sumCpu;

		for(int i=0;i<result.length;i++){
			alterPhyNodeNum=0;
			for(PhysicalNode p:genents.getPhyList()){
				
				sumMem = 0;
				sumCpu = 0;
				for(VM v:p.getVmList()){
					String []arr = result[i][v.getId()].split(":");
					sumMem += Integer.parseInt(arr[0]);
					sumCpu += Integer.parseInt(arr[1]);
				    
				}
				if(sumCpu > p.getInitCpu() || sumMem > p.getInitMemory()){
				    alterPhyNodeNum++;
				}
			}
//			System.out.println("alter "+alterPhyNodeNum);
			if(alterPhyNodeNum<CommonConstants.physical_node_num*0.15){
				time++;
			}
		}
		return time;
	}
//amend rewrite cross
	/*---------------------------------------------------------------------------------------------------------*/
	/*
	 * ����
	 */
	public static Genents cross(Genents a, Genents b) throws Exception{
		int a_index = CommonUtil.generateIntNumber(a.getPhyList().size());  //a�����λ��
		int b_index = CommonUtil.generateIntNumber(b.getPhyList().size());  //b�����λ��
//		int a_index = 0;  //a�����λ��
//		int b_index = 1;//b�����λ��
//		
		b=CommonUtil.copy(b);
		a=CommonUtil.copy(a);

		while(true){
			if(a.getPhyList().get(a_index).getStatus() == 1){
				break;
			}else{
				a_index = CommonUtil.generateIntNumber(a.getPhyList().size());
			}
		}
		while(true){   //��֤������϶��������
			if(b.getPhyList().get(b_index).getStatus() == 1){
				break;
			}else{
				b_index = CommonUtil.generateIntNumber(a.getPhyList().size());
			}
		}
		PhysicalNode cross_a=a.getPhyList().get(a_index);
		PhysicalNode cross_b=b.getPhyList().get(b_index);
		
		List<VM> listVmsForDel = new ArrayList<VM>(); //������Ҫ���·ֲ�������Ľڵ��ϵ����������
        int[] listVmsForDel_id=null;
        List<PhysicalNode> pNodeForDel=new ArrayList<PhysicalNode>();
        int []vmIds_of_crossb = cross_b.getVmsId();  //b���沿��id
        
        for(PhysicalNode pnode:a.getPhyList()){
        	for(VM vm:pnode.getVmList()){
        		if(CommonUtil.isExistInArray(vmIds_of_crossb, vm.getId())){
        			pNodeForDel.add(pnode);
        		}
        	}
        }
        
        for(PhysicalNode pnode:pNodeForDel){
        	for(VM vm_for_del:pnode.getVmList())
    		{
    			if(!CommonUtil.isExistInArray(vmIds_of_crossb, vm_for_del.getId())){
    				listVmsForDel.add(vm_for_del);
    			}
    		}
    		pnode.reSet();
        }
      
      listVmsForDel_id=VM.getVmsId(listVmsForDel);
      if(cross_a.getStatus()!=0){
          for(VM vm:cross_a.getVmList()){
        	  if(!CommonUtil.isExistInArray(vmIds_of_crossb, vm.getId())){
        		  listVmsForDel.add(vm);
        	  }
          }
          cross_a.reSet();
      }
      listVmsForDel_id=VM.getVmsId(listVmsForDel);
     
      for(VM vm:cross_b.getVmList()){
    	  if(cross_a.ifResEnough(vm)){
    		  cross_a.addVmToList(vm);
    	  }else {
    	     listVmsForDel.add(vm);
		}
      }
        
//      ɾ������������·ֲ�
      forecastvmsDistribution(listVmsForDel,a);
        

		return a;
		
	}	
	public static void forecastvmsDistribution(List<VM> listVmsForDel,Genents a) throws Exception{
		List<PhysicalNode> upPhysicalNodes=new ArrayList<PhysicalNode>();
		List<PhysicalNode> downPhysicalNodes=new ArrayList<PhysicalNode>();
		List<NormalizedVM> vmsNext=NormalizedVMTrendForecast.readForecastForOnrLine();
		
		for(PhysicalNode pNode:a.getPhyList()){
				if(pNode.getForecastTrend()==0){
					downPhysicalNodes.add(pNode);
			    }else {
				upPhysicalNodes.add(pNode);
	     	}
	    }
		
		for(VM vm:listVmsForDel){
			if(vm.getCpu()<vmsNext.get(vm.getId()).getCpu()){  //CPU�����½�
				 boolean flag=true;
				 int initPnodeNum=CommonUtil.generateIntNumber(upPhysicalNodes.size());
				 int i=initPnodeNum;
				 for(int k=0; k <upPhysicalNodes.size(); k++,i=(i+1)%upPhysicalNodes.size()){
							if(upPhysicalNodes.get(i).ifResEnough(vm)){
								upPhysicalNodes.get(i).addVmToList(vm);
								flag = false;
								break;
							}	 
				 }
				 
						if(flag == true){
							 initPnodeNum=CommonUtil.generateIntNumber(downPhysicalNodes.size());
							 i=initPnodeNum;
							 for(int k=0; k <downPhysicalNodes.size(); k++,i=(i+1)%downPhysicalNodes.size()){
										if(downPhysicalNodes.get(i).ifResEnough(vm)){
											downPhysicalNodes.get(i).addVmToList(vm);
											flag = false;
											break;
										}	 
							 }
						}
						
			}else {
				boolean flag=true;
				 int initPnodeNum=CommonUtil.generateIntNumber(downPhysicalNodes.size());
				 int i=initPnodeNum;
				 for(int k=0; k <downPhysicalNodes.size(); k++,i=(i+1)%downPhysicalNodes.size()){
							if(downPhysicalNodes.get(i).ifResEnough(vm)){
								downPhysicalNodes.get(i).addVmToList(vm);
								flag = false;
								break;
							}	 
				 }
						if(flag == true){
							initPnodeNum=CommonUtil.generateIntNumber(upPhysicalNodes.size());
							i=initPnodeNum;
							 for(int k=0; k <upPhysicalNodes.size(); k++,i=(i+1)%upPhysicalNodes.size()){
										if(upPhysicalNodes.get(i).ifResEnough(vm)){
											upPhysicalNodes.get(i).addVmToList(vm);
											flag = false;
											break;
										}	 
							 }
						}
						
			}
		}
		
	}
	 public static void vmsCircleRedistribution(List<VM> listVmsForDel,Genents a) throws Exception{
		 int initPnodeNum=0;
		 for(VM v:listVmsForDel){
			 boolean flag=true;
			 int i=initPnodeNum;
			 for(int k=0; k < a.getPhyList().size(); k++,i=(i+1)%a.getPhyList().size()){
				 if(a.getPhyList().get(i).getStatus() == 1){
						if(a.getPhyList().get(i).ifResEnough(v)){
							a.getPhyList().get(i).addVmToList(v);
							flag = false;
							break;
						}
					}	 
			 }
			 if(flag){
					int res = -1;
					 i=initPnodeNum;
					for(int k=0;k < a.getPhyList().size(); k++,i=(i+1)%a.getPhyList().size()){
						if(a.getPhyList().get(i).getStatus() == 0){
							if(a.getPhyList().get(i).ifResEnough(v)){
								res = k;
								a.getPhyList().get(i).addVmToList(v);
								break;
							}					
						}
					}
					if(res == -1){
						System.out.println("��������");
						a.printGen();
						throw new Exception("��������: ");
					}
		      }
			 initPnodeNum=(initPnodeNum+1)%a.getPhyList().size();
	     }
	 }
	 public static void vmsCircleRedistributionWithoutState(List<VM> listVmsForDel,Genents a) throws Exception{
		 int initPnodeNum=CommonUtil.generateIntNumber(a.getPhyList().size());
		 for(VM v:listVmsForDel){
			 boolean flag=true;
			 int i=initPnodeNum;
			 for(int k=0; k < a.getPhyList().size(); k++,i=(i+1)%a.getPhyList().size()){
						if(a.getPhyList().get(i).ifResEnough(v)){
							a.getPhyList().get(i).addVmToList(v);
							flag = false;
							break;
						}	 
			 }
			 
					if(flag == true){
						System.out.println("��������");
						a.printGen();
						throw new Exception("��������: ");
					}
					 initPnodeNum=CommonUtil.generateIntNumber(a.getPhyList().size());
		      }
			
	     }
	
    public static void vmsRedistribution(List<VM> listVmsForDel,Genents a) throws Exception {
    	
    	for(VM v:listVmsForDel){
			boolean flag = true;
			for(int k=0; k < a.getPhyList().size(); k++){
				if(a.getPhyList().get(k).getStatus() == 1){
					if(a.getPhyList().get(k).ifResEnough(v)){
						a.getPhyList().get(k).addVmToList(v);
						flag = false;
						break;
					}
				}
			}
			if(flag){
				int i = -1;
				for(int k=0; k < a.getPhyList().size(); k++){
					if(a.getPhyList().get(k).getStatus() == 0){
						if(a.getPhyList().get(k).ifResEnough(v)){
							i = k;
							a.getPhyList().get(k).addVmToList(v);
							break;
						}					
					}
				}
				if(i == -1){
					System.out.println("��������");
					a.printGen();
					throw new Exception("��������: ");
				}
			}
		}
	}
//	/*---------------------------------------------------------------------------------------------------------*/
//	/*
//	 * ����
//	 */
//	public static Genents cross(Genents a, Genents b) throws Exception{
//		int a_index = CommonUtil.generateIntNumber(a.getPhyList().size());  //a�����λ��
//		int b_index = CommonUtil.generateIntNumber(b.getPhyList().size());  //b�����λ��
//		while(true){
//			if(a.getPhyList().get(a_index).getStatus() == 1){
//				break;
//			}else{
//				a_index = CommonUtil.generateIntNumber(a.getPhyList().size());
//			}
//		}
//		while(true){   //��֤������϶��������
//			if(b.getPhyList().get(b_index).getStatus() == 1){
//				break;
//			}else{
//				b_index = CommonUtil.generateIntNumber(a.getPhyList().size());
//			}
//		}
//		
//		List<VM> listVmsForDel = new ArrayList<VM>();
////		int []vmIds = new int[a.getPhyList().get(a_index).getVmList().size()];
////amend alter one row 	
//		int []vmIds = new int[b.getPhyList().get(b_index).getVmList().size()];
//		int i=0;
//		listVmsForDel.addAll(a.getPhyList().get(a_index).getVmList());
//		
//		a.getPhyList().get(a_index).getVmList().clear();  //ɾ��
//		a.getPhyList().get(a_index).setCpu(a.getPhyList().get(a_index).getInitCpu()); //�ָ���ʼֵ
//		a.getPhyList().get(a_index).setMemory(a.getPhyList().get(a_index).getInitMemory());
//		
//		//b�еĸ����е���������Ƶ�a�����Ӧ������ڵ���
//		for(VM v:b.getPhyList().get(b_index).getVmList()){
//			if((a.getPhyList().get(a_index).getCpu() >= v.getCpu()) && 
//					(a.getPhyList().get(a_index).getMemory() >= v.getMem())){
//				a.getPhyList().get(a_index).setCpu(a.getPhyList().get(a_index).getCpu() - v.getCpu());
//				a.getPhyList().get(a_index).setMemory(a.getPhyList().get(a_index).getMemory() - v.getMem());
//				
//				vmIds[i] = v.getId();  //������a�ϵĽ�������������
//				i++;
//			}else{
//				listVmsForDel.add(v);
//			}
//		}
//		a.getPhyList().get(a_index).setVmList(b.getPhyList().get(b_index).getVmList());
//		
//		i=0;
//		boolean flag = false;
//		List<Integer> delPhyNodeIds = new ArrayList<Integer>();  //������Ҫ���·ֲ�������Ľڵ�ID
////		List<VM> delPhyNodeAllVms = new ArrayList<VM>();  //������Ҫ���·ֲ�������Ľڵ��ϵ����������
//		
//		//��������ڵ�
//		for(PhysicalNode physicalNode:a.getPhyList()){
//			flag = false;
//			if(physicalNode.getId() != a_index){   //��������ڵ�
//				for(VM v:physicalNode.getVmList()){
//					if(CommonUtil.isExistInArray(vmIds, v.getId())){  //�����ظ�
//						flag = true;
//					}
//				}
//			}
//			if(flag){
//				delPhyNodeIds.add(physicalNode.getId());
//			}
//			i++;
//		}
//		for(Integer keys:delPhyNodeIds){
//			for(VM v:a.getPhyList().get(keys).getVmList()){
//				if(!CommonUtil.isExistInArray(vmIds, v.getId())){
//					listVmsForDel.add(v);
//				}
//			}
//			a.getPhyList().get(keys).getVmList().clear();
//			a.getPhyList().get(keys).setStatus(0);  //���δʹ��
//		}
//		
//		//ɾ������������·ֲ�
//		for(VM v:listVmsForDel){
//			flag = true;
//			for(int k=0; k < a.getPhyList().size(); k++){
//				if(a.getPhyList().get(k).getStatus() == 1){
//					if((a.getPhyList().get(k).getMemory() >= v.getMem()) && (a.getPhyList().get(k).getCpu() >= v.getCpu())){
//						a.getPhyList().get(k).getVmList().add(v);
//						a.getPhyList().get(k).setCpu(a.getPhyList().get(k).getCpu() - v.getCpu());
//						a.getPhyList().get(k).setMemory(a.getPhyList().get(k).getMemory() - v.getMem());
//						flag = false;
//						break;
//					}
//				}
//			}
//			if(flag){
//				i = -1;
//				for(int k=0; k < a.getPhyList().size(); k++){
//					if(a.getPhyList().get(k).getStatus() == 0){
//						if((a.getPhyList().get(k).getCpu() >= v.getCpu()) && (a.getPhyList().get(k).getMemory() >= v.getMem())){
//							i = k;
//						}
//						break;
//					}
//				}
//				if(i == -1){
//					throw new Exception("��������");
//				}
//				a.getPhyList().get(i).getVmList().add(v);
//				a.getPhyList().get(i).setCpu(a.getPhyList().get(i).getCpu() - v.getCpu());
//				a.getPhyList().get(i).setMemory(a.getPhyList().get(i).getMemory() - v.getMem());
//				a.getPhyList().get(i).setStatus(1);
//			}
//		}
//		return a;
//	}
	/*
	 * ����
	 */
	public static Genents variation(Genents a) throws Exception{
//		a.printGen();
		a=CommonUtil.copy(a);
		int index_a = CommonUtil.generateIntNumber(a.getPhyList().size());
		while(true){
			if(a.getPhyList().get(index_a).getStatus() == 1){
				break;
			}else{
				index_a = CommonUtil.generateIntNumber(a.getPhyList().size());
			}
		}
		List<VM> delVms = new ArrayList<VM>();
		delVms.addAll(a.getPhyList().get(index_a).getVmList());
		a.getPhyList().get(index_a).reSet();
	
	
		//���·���
		for(VM v:delVms){
			boolean flag = true;
			for(int k=0; k < a.getPhyList().size(); k++){
				if(a.getPhyList().get(k).getStatus() == 1){
					if(a.getPhyList().get(k).ifResEnough(v)){
						a.getPhyList().get(k).addVmToList(v);
						flag = false;
						break;
					}
				}
			}
			if(flag){
				int i = -1;
				for(int k=0; k < a.getPhyList().size(); k++){
					if(a.getPhyList().get(k).getStatus() == 0){
						if(a.getPhyList().get(k).ifResEnough(v)){
							i = k;
							a.getPhyList().get(k).addVmToList(v);
							break;
						}					
					}
				}
				if(i == -1){
					System.out.println("��������");
					a.printGen();
					throw new Exception("��������: ");
				}
			}
	 }
		return a;
}
	/*---------------------------------------------------------------------------------------------------------*/
	public static boolean dominate(Genents a, Genents b){  //֧��
		if((a.getShift_count() < b.getShift_count()) && (a.getPhy_count() > b.getPhy_count()) && 
				(a.getStab_time() > b.getStab_time())){
			return true;
		}
		return false;
	}
}
