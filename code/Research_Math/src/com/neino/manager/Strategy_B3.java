package com.neino.manager;

public class Strategy_B3 extends Strategy {

	/**
	 * ����6�������Թ������� ģ���Ԥ��ȱ���ܶȵĴ�С���з���
	 * 
	 * @param index
	 *            ģ���±�
	 * @return
	 */
	@Override
	public double getTestEffort(int index) {
		double rate1=modules.get(index).getEstimateBug()/sumEstimateDefects();
		double rate2=modules.get(index).getSLOC()/sumLOCs();
		double efforts=totalEfforts*rate1*rate2;
		//System.out.println("efforts="+efforts);
		return efforts;
	}

}
