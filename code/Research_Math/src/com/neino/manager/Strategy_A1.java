package com.neino.manager;

public class Strategy_A1 extends Strategy {

	/**
	 * 策略1：将测试工作量均分到所有模块
	 * 
	 * @param index
	 *            模块下标
	 * @return
	 */
	@Override
	public double getTestEffort(int index) {
		double efforts=totalEfforts / n;
		//System.out.println("efforts="+efforts);
		return efforts;
	}
	
	

}
