package com.neino.runner;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

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
 * 生成一个excel文件，记录每个策略的测试工作量从占原测试工作量的0%到200%时对应可发现的缺陷数与原缺陷数的百分比
 * 
 * @author Administrator
 * 
 */
public class GeneratePercentComp {

	public static void main(String[] args) {
		Strategy A1 = new Strategy_A1();
		Strategy A2 = new Strategy_A2();
		Strategy_A3 A3 = new Strategy_A3();
		Strategy B1 = new Strategy_B1();
		Strategy B2 = new Strategy_B2();
		Strategy B3 = new Strategy_B3();
		Strategy B4 = new Strategy_B4();

		String oldSrcFile = "C:/Users/Administrator/Desktop/SIR/论文/math3.0.xls"; // oldSrcFile是已经添加了bug，以及estimateBug信息的最终xls文件
		String destFile = "C:/Users/Administrator/Desktop/SIR/论文/math3.0Percent_Random_1.xls"; // oldSrcFile是已经添加了bug，以及estimateBug信息的最终xls文件
		

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
		double realTests=1500;//真实的tests数目
		double effortStart=0;
		double effortEnd=realTests*2;
		double effortStep=realTests*0.05;
		double orignBug=23.0;//原始bug数目

		ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();// 记录所有要保存的数据

		defectDiscoveryUtil = new DefectDiscoveryUtil(A1);
		modules = A1.getModules();
		sumDefects = 0;
		ArrayList<String> listA1 = new ArrayList<String>();// 记录策略A1所有的数据
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// 每次增加原测试工作量的5%，一直增加到原测试量的200%，即3000
			A1.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			listA1.add(FormatToPercent(sumDefects/orignBug));
		}
		lists.add(listA1);

		defectDiscoveryUtil = new DefectDiscoveryUtil(A2);
		modules = A2.getModules();
		sumDefects = 0;
		ArrayList<String> listA2 = new ArrayList<String>();// 记录策略A2所有的数据
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// 每次增加原测试工作量的5%，一直增加到原测试量的200%，即3000
			A2.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				if (!Double.valueOf(def).isNaN())
					sumDefects += def;
			}
			listA2.add(FormatToPercent(sumDefects/orignBug));
		}
		lists.add(listA2);

		defectDiscoveryUtil = new DefectDiscoveryUtil(A3);
		modules = A3.getModules();
		sumDefects = 0;
		ArrayList<String> listA3 = new ArrayList<String>();// 记录策略A3所有的数据
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// 每次增加原测试工作量的5%，一直增加到原测试量的200%，即3000
			A3.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			listA3.add(FormatToPercent(sumDefects/orignBug));
		}
		lists.add(listA3);

		defectDiscoveryUtil = new DefectDiscoveryUtil(B1);
		modules = B1.getModules();
		sumDefects = 0;
		ArrayList<String> listB1 = new ArrayList<String>();// 记录策略B1所有的数据
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// 每次增加原测试工作量的5%，一直增加到原测试量的200%，即3000
			B1.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			listB1.add(FormatToPercent(sumDefects/orignBug));
		}
		lists.add(listB1);

		defectDiscoveryUtil = new DefectDiscoveryUtil(B2);
		modules = B2.getModules();
		sumDefects = 0;
		ArrayList<String> listB2 = new ArrayList<String>();// 记录策略B2所有的数据
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// 每次增加原测试工作量的5%，一直增加到原测试量的200%，即3000
			B2.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			listB2.add(FormatToPercent(sumDefects/orignBug));
		}
		lists.add(listB2);

		defectDiscoveryUtil = new DefectDiscoveryUtil(B3);
		modules = B3.getModules();
		sumDefects = 0;
		ArrayList<String> listB3 = new ArrayList<String>();// 记录策略B3所有的数据
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// 每次增加原测试工作量的5%，一直增加到原测试量的200%，即3000
			B3.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			listB3.add(FormatToPercent(sumDefects/orignBug));
		}
		lists.add(listB3);

		defectDiscoveryUtil = new DefectDiscoveryUtil(B4);
		modules = B4.getModules();
		sumDefects = 0;
		ArrayList<String> listB4 = new ArrayList<String>();// 记录策略B4所有的数据
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// 每次增加原测试工作量的5%，一直增加到原测试量的200%，即3000
			B4.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			listB4.add(FormatToPercent(sumDefects/orignBug));
		}
		lists.add(listB4);

		// 至此，所有要写入excel的数据都在lists里面

		try {
			// 创建一个新文件，写入bug信息
			File destF=new File(destFile);
			if(destF.exists()){
				System.out.println("文件已经存在");
				return;
			}
			WritableWorkbook book = Workbook.createWorkbook(destF);
			WritableSheet sheet = book.createSheet("统计", 0);
			//将百分比标志写入第一行
			for (int i = 0; i <= 200; i+=5) {
				Label label = new Label(i/5+1, 0, i+"%");
				sheet.addCell(label);
			}
			//按行写入每一个策略的数据
			for (int i = 0; i < lists.size(); i++) {
				Label label = new Label(0, i+1, i+1+"");
				sheet.addCell(label);
				ArrayList<String> list = lists.get(i);
				for (int j = 0; j < list.size(); j++) {
					label = new Label(j+1,i+1,list.get(j)+"");
					sheet.addCell(label);
				}
			}
			// 写入数据并关闭文件
			book.write();
			book.close(); // 最好在finally中关闭，此处仅作为示例不太规范
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String FormatToPercent(double value){//将value转换成%计数格式
		NumberFormat num = NumberFormat.getPercentInstance(); 
		num.setMaximumIntegerDigits(3); 
		num.setMaximumFractionDigits(2); 
		return num.format(value);
	}

}
