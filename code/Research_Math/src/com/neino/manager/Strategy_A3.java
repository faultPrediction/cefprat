package com.neino.manager;


public class Strategy_A3 extends Strategy {

	/**
	 * ����3�������Թ�������"�µ�/�޸Ĺ��Ĵ�������+0.1*���õĴ��������"ģ���С���з���
	 * 
	 * @param index
	 *            ģ���±�
	 * @return
	 */
	@Override
	public double getTestEffort(int index){
		double newCode=modules.get(index).getNewLoc();
		double reusedCode=modules.get(index).getReusedLoc();
		double allModuleNewCode=sumNewLOCs();
		double allReusedNewCode=sumReusedLOCs();
		double percent=((newCode+0.1*reusedCode)/(allModuleNewCode+0.1*allReusedNewCode));
		double efforts=totalEfforts*percent;
	//	System.out.println("efforts="+efforts);
		return efforts;
	}
	
	

}
