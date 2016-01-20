package com.neino.manager;

public class Strategy_B2 extends Strategy {

	/**
	 * 策略5：将测试工作量按 模块的预测缺陷密度的大小进行分配
	 * 
	 * @param index
	 *            模块下标
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
