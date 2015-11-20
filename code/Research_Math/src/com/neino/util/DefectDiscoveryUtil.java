package com.neino.util;

import java.util.ArrayList;
import java.util.List;

import com.neino.entity.Module;
import com.neino.manager.Strategy;

/**
 * ȱ�ݷ���ģ��(Hi(ti)=ai[1-exp(-bi*ti)]),bi=b0/si
 * @author Administrator
 *
 */
public class DefectDiscoveryUtil {
	
	private Strategy strategy;//ʹ�ú��ֲ���
	ParameterEstimateUtil paraUtil;//�������ƹ���
	private List<Module>modules=new ArrayList<Module>();
	
	public DefectDiscoveryUtil(Strategy strategy) {
		this.strategy = strategy;
		paraUtil=new ParameterEstimateUtil(strategy.getModules());
		modules=strategy.getModules();
	}
	
	/**
	 * ��֪ĳһ��ģ��Ĳ��Թ����������ģ����Է��ֵ�ȱ����
	 * @param index
	 */
	public double discoveryDefect(int index){
		//�ȵõ�Ԥ��Ĳ��Թ�����
		double ti=strategy.getTestEffort(index);
		double ai=paraUtil.estimateAi(index);
		double b0=paraUtil.estimateB0();
		//double bi=b0/strategy.sumLOCs();    //����ط�д����						//��ģ�����ط�д���ˣ����ǿӵ�
		double bi=b0/modules.get(index).getSLOC();
		double HHi=ai*(1-Math.exp(-bi*ti));//���վ���ȱ�ݷ���ģ�͵õ���^Hi
		return HHi;
	}
	
	/**
	 * ����ģ�龭��ĳһ�ֲ��Կ������շ��ֵ�ȱ����
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
