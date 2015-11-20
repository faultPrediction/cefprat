package com.neino.manager;

public class Strategy_B2 extends Strategy {

	/**
	 * ����5�������Թ������� ģ���Ԥ��ȱ���ܶȵĴ�С���з���
	 * 
	 * @param index
	 *            ģ���±�
	 * @return
	 */
	@Override
	public double getTestEffort(int index) {
		double rate1=modules.get(index).getEstimateBug()/modules.get(index).getSLOC();
		double rate2=sumEstimateDefects()/sumLOCs();
		double efforts=totalEfforts*rate1/rate2;
		//System.out.println("efforts="+efforts);
		return efforts;
	}

}
