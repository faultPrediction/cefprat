package com.neino.manager;

public class Strategy_B1 extends Strategy {

	/**
	 * 策略4：将测试工作量按 模块的预测缺陷数的大小进行分配
	 * 
	 * @param index
	 *            模块下标
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
