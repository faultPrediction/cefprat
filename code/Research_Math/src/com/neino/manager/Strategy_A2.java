package com.neino.manager;

public class Strategy_A2 extends Strategy {

	/**
	 * ����2�������Թ�������ģ���С���з���
	 * 
	 * @param index
	 *            ģ���±�
	 * @return
	 */
	@Override
	public double getTestEffort(int index) {
		double sumLoc=sumLOCs();
		double percent=modules.get(index).getSLOC()/sumLoc;
		double efforts=totalEfforts*percent;
		//System.out.println("efforts="+efforts);
		return efforts;
	}
	
	

}
