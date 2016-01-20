package com.neino.manager;

import java.util.ArrayList;
import java.util.List;

import com.neino.entity.Module;
import com.neino.util.AddAM2V2;
import com.neino.util.ModuleInfoUtil;

public abstract class Strategy {
	static int n = 0;// 模块数
	List<Module> modules = new ArrayList<Module>();// 存放某一个版本的所有的module对象
	/**
	 * 定义几个变量把modules中的数据接收过来方便操作
	 */
	double[] estimateBugs;// 存放所有模块的预测缺陷数
	double[] locs;// 存放所有模块的代码行数
	double[] newLocs;// 存放所有模块的新添加的代码行数
	double[] reusedLocs;// 存放所有模块重用过的代码行数
	
	double totalEfforts = 1500;// 带入缺陷发现模型时的总的测试工作量(总测试工作量未知，暂时先写这么多)

	/**
	 * 初始化所有的模块
	 */
	public void initModules(String srcFile) {
		// 利用工具从excel文件中获取关于每个模块的相关信息
		modules = ModuleInfoUtil.getAllModulesFrEXECL(srcFile);
		n = modules.size();
		if (n <= 0) {
			System.out.println("error:初始化个各模块失败");
			return;
		}
		estimateBugs = new double[n];// 存放所有模块的预测缺陷数
		locs = new double[n];// 存放所有模块的代码行数
		newLocs = new double[n];// 存放所有模块的新添加的代码行数
		reusedLocs = new double[n];// 存放所有模块重用过的代码行数
		for (int i = 0; i < n; i++) {
			Module m=modules.get(i);
			estimateBugs[i]=m.getEstimateBug();
			locs[i] = m.getSLOC();
			newLocs[i]=m.getNewLoc();
			reusedLocs[i]=m.getReusedLoc();
		}
		
		
	}
	
	/**
	 * 初始化所有的模块（已废弃）
	 */
	public void initModules(String srcFile,String AMFile) {
		// 利用工具从excel文件中获取关于每个模块的相关信息
		modules = AddAM2V2.getFinalInfo(srcFile, AMFile);
		n = modules.size();
		if (n <= 0) {
			System.out.println("error:初始化个各模块失败");
			return;
		}
		estimateBugs = new double[n];// 存放所有模块的预测缺陷数
		locs = new double[n];// 存放所有模块的代码行数
		newLocs = new double[n];// 存放所有模块的新添加的代码行数
		reusedLocs = new double[n];// 存放所有模块重用过的代码行数
		for (int i = 0; i < n; i++) {
			Module m=modules.get(i);
			estimateBugs[i]=m.getEstimateBug();
			locs[i] = m.getSLOC();
			newLocs[i]=m.getNewLoc();
			reusedLocs[i]=m.getReusedLoc();
		}
		
		
	}
	
	
	/**
	 * 计算所有模块的缺陷的总数
	 * 
	 * @return
	 */
	public double sumEstimateDefects() {
		double sum = 0;
		for (int i = 0; i < n; i++) {
			sum += estimateBugs[i];
		}
		return sum;
	}

	/**
	 * 计算所有模块的代码行数
	 * 
	 * @return
	 */
	public double sumLOCs() {
		double sum = 0;
		for (int i = 0; i < n; i++) {
			sum += locs[i];
		}
		return sum;
	}
	
	/**
	 * 计算所有模块的新添加的代码行数的总和
	 * 
	 * @return
	 */
	public double sumNewLOCs() {
		double sum = 0;
		for (int i = 0; i < n; i++) {
			sum += newLocs[i];
		}
		return sum;
	}


	/**
	 * 计算所有模块的修改过的代码行数的总和
	 * 
	 * @return
	 */
	public double sumReusedLOCs() {
		double sum = 0;
		for (int i = 0; i < n; i++) {
			sum += reusedLocs[i];
		}
		return sum;
	}

	

	public List<Module> getModules() {
		return modules;
	}

	public double getTotalEfforts() {
		return totalEfforts;
	}

	public void setTotalEfforts(double totalEfforts) {
		this.totalEfforts = totalEfforts;
	}

	/**
	 * 接口：得到某一个模块应该分配的测试工作量
	 * 
	 * @param index
	 *            模块下标
	 * @return
	 */
	public abstract double getTestEffort(int index);

}
