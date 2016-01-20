package com.neino.runner;

import java.util.List;

import com.neino.entity.Module;
import com.neino.manager.Strategy;
import com.neino.manager.Strategy_A1;
import com.neino.manager.Strategy_A2;
import com.neino.manager.Strategy_A3;
import com.neino.manager.Strategy_B1;
import com.neino.manager.Strategy_B2;
import com.neino.manager.Strategy_B3;
import com.neino.manager.Strategy_B4;
import com.neino.util.DefectDiscoveryUtil;

/**
 * 主测试类（根据缺陷发现模型来获得每种策略可以获得缺陷数） 运行本类时需要确保以下步骤已经完成： （1）运行AddAMs2Math得到一个xls新文件:mathAM.xls，该文件包含每个文件的AM信息，将其手动添加到mathnew.xls中
 * （2）运行AddBugs2Math得到2个xls新文件:destmathold.xls和destmathnew.xls，两个文件中包含每个文件的bug数,手动将其添加到mathold.xls和mathnew.xls中
 * （3）在RStudio中执行mathpred.R,可以得到一个新的mathpred.csv,手动添加x列到mathnew.xls中，并将列名该为estimateBug （4）运行本程序
 * 
 * @author Administrator
 * 
 */
public class MainRunner {

	public static void main(String[] args) {
		Strategy A1 = new Strategy_A1();
		Strategy A2 = new Strategy_A2();
		Strategy_A3 A3 = new Strategy_A3();
		Strategy B1 = new Strategy_B1();
		Strategy B2 = new Strategy_B2();
		Strategy B3 = new Strategy_B3();
		Strategy B4 = new Strategy_B4();

		/*String oldSrcFile = "C:/Users/Administrator/Desktop/SIR/论文/mathnew.xls"; // oldSrcFile是已经添加了bug，test cases以及estimateBug信息的xls文件
		String AMFile = "C:/Users/Administrator/Desktop/SIR/论文/mathnew2old2.xls"; // AMFile是含有AM信息的xls文件，由工具diffcount生成  
		
		A1.initModules(oldSrcFile,AMFile);
		A2.initModules(oldSrcFile,AMFile);
		A3.initModules(oldSrcFile,AMFile);
		B1.initModules(oldSrcFile,AMFile);
		B2.initModules(oldSrcFile,AMFile);
		B3.initModules(oldSrcFile,AMFile);
		B4.initModules(oldSrcFile,AMFile);*/

		String oldSrcFile = "C:/Users/Administrator/Desktop/SIR/论文/math3.0.xls"; // oldSrcFile是已经添加了bug，以及estimateBug信息的最终xls文件

		A1.initModules(oldSrcFile);
		A2.initModules(oldSrcFile);
		A3.initModules(oldSrcFile);
		B1.initModules(oldSrcFile);
		B2.initModules(oldSrcFile);
		B3.initModules(oldSrcFile);
		B4.initModules(oldSrcFile);
		// 以上七种策略它们的模块都是相同的

		DefectDiscoveryUtil defectDiscoveryUtil = null;
		List<Module> modules = null;
		double sumDefects = 0;
		double effortStart=500;
		double effortEnd=10000;
		double effortStep=10;
		double orignTests=1500;//真实tests数目
		double orignBug=23;//原始bug数

		defectDiscoveryUtil = new DefectDiscoveryUtil(A1);
		modules = A1.getModules();
		sumDefects = 0;
		System.out.println("在策略A1中，所有模块可发现的缺陷数分别如下：");

		for (double totaleffort = effortStart; totaleffort < effortEnd; totaleffort += effortStep) {
			A1.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			if (sumDefects >= orignBug) {
				System.out.println("当发现与原bug数目相等的bug时，所需的tests=" + totaleffort + ",占比是" + totaleffort / orignTests);
				break;
			}
			// System.out.println("基于策略A1,所有模块的总的缺陷预测数目是:"+sumDefects);
		}

		defectDiscoveryUtil = new DefectDiscoveryUtil(A2);
		System.out.println("在策略A2中，所有模块可发现的缺陷数分别如下：");
		modules = A2.getModules();
		sumDefects = 0;
		for (double totaleffort = effortStart; totaleffort < effortEnd; totaleffort += effortStep) {
			A2.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				if (!Double.valueOf(def).isNaN())
					sumDefects += def;
			}
			if (sumDefects >= orignBug) {
				System.out.println("当发现与原bug数目相等的bug时，所需的tests=" + totaleffort + ",占比是" + totaleffort / orignTests);
				break;
			}
			// System.out.println("基于策略A2,所有模块的总的缺陷预测数目是:"+sumDefects);
		}

		defectDiscoveryUtil = new DefectDiscoveryUtil(A3);
		System.out.println("在策略A3中，所有模块可发现的缺陷数分别如下：");
		modules = A3.getModules();
		sumDefects = 0;
		for (double totaleffort = effortStart; totaleffort < effortEnd; totaleffort += effortStep) {
			A3.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			if (sumDefects >= orignBug) {
				System.out.println("当发现与原bug数目相等的bug时，所需的tests=" + totaleffort + ",占比是" + totaleffort / orignTests);
				break;
			}
			// System.out.println("基于策略A3,所有模块的总的缺陷预测数目是:"+sumDefects);
		}

		defectDiscoveryUtil = new DefectDiscoveryUtil(B1);
		System.out.println("在策略B1中，所有模块可发现的缺陷数分别如下：");
		modules = B1.getModules();
		sumDefects = 0;
		for (double totaleffort = effortStart; totaleffort < effortEnd; totaleffort += effortStep) {
			B1.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			if (sumDefects >= orignBug) {
				System.out.println("当发现与原bug数目相等的bug时，所需的tests=" + totaleffort + ",占比是" + totaleffort / orignTests);
				break;
			}
			// System.out.println("基于策略B1,所有模块的总的缺陷预测数目是:"+sumDefects);
		}

		defectDiscoveryUtil = new DefectDiscoveryUtil(B2);
		System.out.println("在策略B2中，所有模块可发现的缺陷数分别如下：");
		modules = B2.getModules();
		sumDefects = 0;
		for (double totaleffort = 1; totaleffort < 100000; totaleffort += 1) {//这个比较特殊
			B2.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			if (sumDefects >= orignBug) {
				System.out.println("当发现与原bug数目相等的bug时，所需的tests=" + totaleffort + ",占比是" + totaleffort / orignTests);
				break;
			}
			// System.out.println("基于策略B2,所有模块的总的缺陷预测数目是:"+sumDefects);
		}

	/*	defectDiscoveryUtil = new DefectDiscoveryUtil(B3);
		System.out.println("在策B3中，所有模块可发现的缺陷数分别如下：");
		modules = B3.getModules();
		sumDefects = 0;
		for (double totaleffort = effortStart; totaleffort < effortEnd; totaleffort += effortStep) {
			B3.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				Module m = modules.get(i);
				double def = defectDiscoveryUtil.discoveryDefect(i);
				// System.out.println(m.getName()+": "+def);
				sumDefects += def;
			}
			if (sumDefects >= orignBug) {
				System.out.println("当发现与原bug数目相等的bug时，所需的tests=" + totaleffort + ",占比是" + totaleffort / orignTests);
				break;
			}
			// System.out.println("基于策略B3,所有模块的总的缺陷预测数目是:"+sumDefects);
		}

		defectDiscoveryUtil = new DefectDiscoveryUtil(B4);
		System.out.println("在策略B4中，所有模块可发现的缺陷数分别如下：");
		modules = B4.getModules();
		sumDefects = 0;
		for (double totaleffort = effortStart; totaleffort < effortEnd; totaleffort += effortStep) {
			B4.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				Module m = modules.get(i);
				double def = defectDiscoveryUtil.discoveryDefect(i);
				// System.out.println(m.getName()+": "+def);
				sumDefects += def;
			}
			if (sumDefects >= orignBug) {
				System.out.println("当发现与原bug数目相等的bug时，所需的tests=" + totaleffort + ",占比是" + totaleffort / orignTests);
				break;
			}
			// System.out.println("基于策略B4,所有模块的总的缺陷预测数目是:"+sumDefects);
		}*/

		/*//下面的结果与策略无关
		System.out.println("根据缺陷发现模型，已知所有模块的预测缺陷数，反过来求要发现这么多缺陷数所需要的测试工作量，各个模块所需的测试工作量如下");
		modules=B4.getModules();
		double sumefforts=0;
		for (int i = 0; i < modules.size(); i++) {
			Module m=modules.get(i);
			double effort=defectDiscoveryUtil.EffortNeeded(i);
			System.out.println(m.getName()+": "+effort);
			sumefforts+=effort;
		}
		System.out.println("各个模块所需的测试工作量的总和是:"+sumefforts);*/

	}

}
