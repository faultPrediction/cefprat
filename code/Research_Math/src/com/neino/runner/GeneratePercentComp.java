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
 * ����һ��excel�ļ�����¼ÿ�����ԵĲ��Թ�������ռԭ���Թ�������0%��200%ʱ��Ӧ�ɷ��ֵ�ȱ������ԭȱ�����İٷֱ�
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

		String oldSrcFile = "C:/Users/Administrator/Desktop/SIR/����/math3.0.xls"; // oldSrcFile���Ѿ������bug���Լ�estimateBug��Ϣ������xls�ļ�
		String destFile = "C:/Users/Administrator/Desktop/SIR/����/math3.0Percent_Random_1.xls"; // oldSrcFile���Ѿ������bug���Լ�estimateBug��Ϣ������xls�ļ�
		

		A1.initModules(oldSrcFile);
		A2.initModules(oldSrcFile);
		A3.initModules(oldSrcFile);
		B1.initModules(oldSrcFile);
		B2.initModules(oldSrcFile);
		B3.initModules(oldSrcFile);
		B4.initModules(oldSrcFile);
		// �������ֲ������ǵ�ģ�鶼����ͬ��

		DefectDiscoveryUtil defectDiscoveryUtil = null;
		List<Module> modules = null;
		double sumDefects = 0;
		double realTests=1500;//��ʵ��tests��Ŀ
		double effortStart=0;
		double effortEnd=realTests*2;
		double effortStep=realTests*0.05;
		double orignBug=23.0;//ԭʼbug��Ŀ

		ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();// ��¼����Ҫ���������

		defectDiscoveryUtil = new DefectDiscoveryUtil(A1);
		modules = A1.getModules();
		sumDefects = 0;
		ArrayList<String> listA1 = new ArrayList<String>();// ��¼����A1���е�����
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// ÿ������ԭ���Թ�������5%��һֱ���ӵ�ԭ��������200%����3000
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
		ArrayList<String> listA2 = new ArrayList<String>();// ��¼����A2���е�����
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// ÿ������ԭ���Թ�������5%��һֱ���ӵ�ԭ��������200%����3000
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
		ArrayList<String> listA3 = new ArrayList<String>();// ��¼����A3���е�����
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// ÿ������ԭ���Թ�������5%��һֱ���ӵ�ԭ��������200%����3000
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
		ArrayList<String> listB1 = new ArrayList<String>();// ��¼����B1���е�����
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// ÿ������ԭ���Թ�������5%��һֱ���ӵ�ԭ��������200%����3000
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
		ArrayList<String> listB2 = new ArrayList<String>();// ��¼����B2���е�����
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// ÿ������ԭ���Թ�������5%��һֱ���ӵ�ԭ��������200%����3000
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
		ArrayList<String> listB3 = new ArrayList<String>();// ��¼����B3���е�����
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// ÿ������ԭ���Թ�������5%��һֱ���ӵ�ԭ��������200%����3000
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
		ArrayList<String> listB4 = new ArrayList<String>();// ��¼����B4���е�����
		for (double totaleffort = effortStart; totaleffort <= effortEnd; effortStep += effortStep) {// ÿ������ԭ���Թ�������5%��һֱ���ӵ�ԭ��������200%����3000
			B4.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			listB4.add(FormatToPercent(sumDefects/orignBug));
		}
		lists.add(listB4);

		// ���ˣ�����Ҫд��excel�����ݶ���lists����

		try {
			// ����һ�����ļ���д��bug��Ϣ
			File destF=new File(destFile);
			if(destF.exists()){
				System.out.println("�ļ��Ѿ�����");
				return;
			}
			WritableWorkbook book = Workbook.createWorkbook(destF);
			WritableSheet sheet = book.createSheet("ͳ��", 0);
			//���ٷֱȱ�־д���һ��
			for (int i = 0; i <= 200; i+=5) {
				Label label = new Label(i/5+1, 0, i+"%");
				sheet.addCell(label);
			}
			//����д��ÿһ�����Ե�����
			for (int i = 0; i < lists.size(); i++) {
				Label label = new Label(0, i+1, i+1+"");
				sheet.addCell(label);
				ArrayList<String> list = lists.get(i);
				for (int j = 0; j < list.size(); j++) {
					label = new Label(j+1,i+1,list.get(j)+"");
					sheet.addCell(label);
				}
			}
			// д�����ݲ��ر��ļ�
			book.write();
			book.close(); // �����finally�йرգ��˴�����Ϊʾ����̫�淶
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String FormatToPercent(double value){//��valueת����%������ʽ
		NumberFormat num = NumberFormat.getPercentInstance(); 
		num.setMaximumIntegerDigits(3); 
		num.setMaximumFractionDigits(2); 
		return num.format(value);
	}

}
