package com.neino.util;

import java.util.ArrayList;
import java.util.List;

import com.neino.entity.Module;

/**
 * ��ȱ�ݷ���ģ���еĲ���ai,bo���й���(�����Ĳ����޹�,��Ԥ���ȱ������Ԥ��ķ�����Թ��������޹أ�֮�����ݼ��е���ʵֵ�й�)
 * @author Administrator
 *
 */
public class ParameterEstimateUtil {
	private List<Module>modules=new ArrayList<Module>();
	
    
	public ParameterEstimateUtil(List<Module>modules) {
		this.modules=modules;
	}


	/**
	 * Ԥ��ai��ֵ
	 * @param index
	 * @return
	 */
	double estimateAi(int index){
		double Hi=modules.get(index).getBug();//�õ�ʵ��ȱ����
		double r1=RemainingRateFault.R1*modules.get(index).getNewLoc()/1000;
		double r2=RemainingRateFault.R2*(modules.get(index).getReusedLoc())/1000;
		return Hi+r1+r2;
	}
	
	/**
	 * Ԥ��b0��ֵ
	 * @param index
	 * @return
	 */
	double estimateB0(){
		//����Htotal��Atotal
		double Htotal=0;//��ʵ��ȱ�����ܺ�
		double Atotal=0;//ai���ܺ�
		double Ttotal=0;//��ʵ�Ĳ��Թ������ܺ�
		double Stotal=0;//���������ܺ�
		for (int i = 0; i < modules.size(); i++) {
			Stotal+=modules.get(i).getSLOC();//��ʵ�Ĵ�������
			Ttotal+=modules.get(i).getTestEffort();//��ʵ�Ĳ��Թ�����
			Htotal+=modules.get(i).getBug();//��ʵ��ȱ����;
			Atotal+=estimateAi(i);
		}
		Ttotal=1500;//����Ttotal��ֵ��ʵ�ʵ�tests�����������Ѿ���֪���������в������ƣ�
		double b0=-Stotal/Ttotal*Math.log(1-Htotal/Atotal);
	//	System.out.println("Stotal="+Stotal);
//		System.out.println("Htotal="+Htotal);
//		System.out.println("Atotal="+Atotal);
//		System.out.println("Math.log(1-Htotal/Atotal)="+Math.log(1-Htotal/Atotal));
	//	System.out.println("b0="+b0);
		return b0;
	}


}
