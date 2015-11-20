package com.neino.manager;

public class Strategy_B3 extends Strategy {

	/**
	 * 策略6：将测试工作量按 模块的预测缺陷密度的大小进行分配
	 * 
	 * @param index
	 *            模块下标
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
