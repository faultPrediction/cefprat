package com.neino.manager;

public class Strategy_B1 extends Strategy {

	/**
	 * ����4�������Թ������� ģ���Ԥ��ȱ�����Ĵ�С���з���
	 * 
	 * @param index
	 *            ģ���±�
	 * @return
	 */
	@Override
	public double getTestEffort(int index) {
		double percent=modules.get(index).getEstimateBug()/sumEstimateDefects();
		double efforts=totalEfforts*percent;
//		System.out.println("efforts="+efforts);
		return efforts;
	}
	

}
