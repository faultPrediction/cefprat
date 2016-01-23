package com.neino.manager;

public class Strategy_B4 extends Strategy {

	/**
	 * 策略7：将测试工作量按  预测缺陷数*log(模块大小)进行分配
	 * 
	 * @param index
	 *            模块下标
	 * @return
	 */
	@Override
	public double getTestEffort(int index){
		double rate1=modules.get(index).getEstimateBug()/sumEstimateDefects();
		double sumLog=0;
		for (int i = 0; i <n; i++) {
			sumLog+=Math.log(modules.get(i).getSLOC());
		}
		double rate2=Math.log(modules.get(index).getSLOC())/sumLog;
		double efforts=totalEfforts*rate1*rate2;
//		System.out.print("estimateBug:"+modules.get(index).getEstimateBug()+" "+sumEstimateDefects()+" rate1="+rate1+" rate2="+rate2+"  efforts="+efforts);
		return efforts;
		
	}

}
