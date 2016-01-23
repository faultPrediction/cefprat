package com.neino.manager;


public class Strategy_A3 extends Strategy {

	/**
	 * 策略3：将测试工作量按"新的/修改过的代码行数+0.1*重用的代码的行数"模块大小进行分配
	 * 
	 * @param index
	 *            模块下标
	 * @return
	 */
	@Override
	public double getTestEffort(int index){
		double newCode=modules.get(index).getNewLoc();
		double reusedCode=modules.get(index).getReusedLoc();
		double allModuleNewCode=sumNewLOCs();
		double allReusedNewCode=sumReusedLOCs();
		double percent=((newCode+0.1*reusedCode)/(allModuleNewCode+0.1*allReusedNewCode));
		double efforts=totalEfforts*percent;
	//	System.out.println("efforts="+efforts);
		return efforts;
	}
	
	

}
