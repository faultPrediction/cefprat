package com.neino.manager;

public class Strategy_A1 extends Strategy {

	/**
	 * ����1�������Թ��������ֵ�����ģ��
	 * 
	 * @param index
	 *            ģ���±�
	 * @return
	 */
	@Override
	public double getTestEffort(int index) {
		double efforts=totalEfforts / n;
		//System.out.println("efforts="+efforts);
		return efforts;
	}
	
	

}
