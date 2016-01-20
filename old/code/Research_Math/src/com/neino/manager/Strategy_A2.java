package com.neino.manager;

public class Strategy_A2 extends Strategy {

	/**
	 * 策略2：将测试工作量按模块大小进行分配
	 * 
	 * @param index
	 *            模块下标
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
