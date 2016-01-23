package com.neino.util;

import java.util.ArrayList;
import java.util.List;

import com.neino.entity.Module;

/**
 * 对缺陷发现模型中的参数ai,bo进行估计(与具体的策略无关,与预测的缺陷数，预测的分配测试工作量了无关，之和数据集中的真实值有关)
 * @author Administrator
 *
 */
public class ParameterEstimateUtil {
	private List<Module>modules=new ArrayList<Module>();
	
    
	public ParameterEstimateUtil(List<Module>modules) {
		this.modules=modules;
	}


	/**
	 * 预测ai的值
	 * @param index
	 * @return
	 */
	double estimateAi(int index){
		double Hi=modules.get(index).getBug();//得到实际缺陷数
		double r1=RemainingRateFault.R1*modules.get(index).getNewLoc()/1000;
		double r2=RemainingRateFault.R2*(modules.get(index).getReusedLoc())/1000;
		return Hi+r1+r2;
	}
	
	/**
	 * 预测b0的值
	 * @param index
	 * @return
	 */
	double estimateB0(){
		//计算Htotal和Atotal
		double Htotal=0;//真实的缺陷数总和
		double Atotal=0;//ai的总和
		double Ttotal=0;//真实的测试工作量总和
		double Stotal=0;//代码行数总和
		for (int i = 0; i < modules.size(); i++) {
			Stotal+=modules.get(i).getSLOC();//真实的代码行数
			Ttotal+=modules.get(i).getTestEffort();//真实的测试工作量
			Htotal+=modules.get(i).getBug();//真实的缺陷数;
			Atotal+=estimateAi(i);
		}
		Ttotal=1500;//调整Ttotal的值（实际的tests总量，事先已经获知，用来进行参数估计）
		double b0=-Stotal/Ttotal*Math.log(1-Htotal/Atotal);
	//	System.out.println("Stotal="+Stotal);
//		System.out.println("Htotal="+Htotal);
//		System.out.println("Atotal="+Atotal);
//		System.out.println("Math.log(1-Htotal/Atotal)="+Math.log(1-Htotal/Atotal));
	//	System.out.println("b0="+b0);
		return b0;
	}


}
