package com.neino.util;

import java.util.ArrayList;
import java.util.List;

import com.neino.entity.Module;
import com.neino.manager.Strategy;

/**
 * 缺陷发现模型(Hi(ti)=ai[1-exp(-bi*ti)]),bi=b0/si
 * @author Administrator
 *
 */
public class DefectDiscoveryUtil {
	
	private Strategy strategy;//使用何种策略
	ParameterEstimateUtil paraUtil;//参数估计工具
	private List<Module>modules=new ArrayList<Module>();
	
	public DefectDiscoveryUtil(Strategy strategy) {
		this.strategy = strategy;
		paraUtil=new ParameterEstimateUtil(strategy.getModules());
		modules=strategy.getModules();
	}
	
	/**
	 * 已知某一个模块的测试工作量，求该模块可以发现的缺陷数
	 * @param index
	 */
	public double discoveryDefect(int index){
		//先得到预测的测试工作量
		double ti=strategy.getTestEffort(index);
		double ai=paraUtil.estimateAi(index);
		double b0=paraUtil.estimateB0();
		//double bi=b0/strategy.sumLOCs();    //这个地方写错了						//妈的，这个地方写错了，真是坑爹
		double bi=b0/modules.get(index).getSLOC();
		double HHi=ai*(1-Math.exp(-bi*ti));//最终经过缺陷发现模型得到的^Hi
		return HHi;
	}
	
	/**
	 * 所有模块经由某一种策略可以最终发现的缺陷数
	 * @return
	 */
	public double sumDefects(){
		int n=modules.size();
		double sumdefects=0;
		for (int i = 0; i < n; i++) {
			sumdefects+=discoveryDefect(i);
		}
		return sumdefects;
	}
	
	
	public Strategy getStrategy() {
		return strategy;
	}
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
}
