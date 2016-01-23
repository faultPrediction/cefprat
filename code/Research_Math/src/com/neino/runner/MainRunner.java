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
 * �������ࣨ����ȱ�ݷ���ģ�������ÿ�ֲ��Կ��Ի��ȱ������ ���б���ʱ��Ҫȷ�����²����Ѿ���ɣ� ��1������AddAMs2Math�õ�һ��xls���ļ�:mathAM.xls�����ļ�����ÿ���ļ���AM��Ϣ�������ֶ���ӵ�mathnew.xls��
 * ��2������AddBugs2Math�õ�2��xls���ļ�:destmathold.xls��destmathnew.xls�������ļ��а���ÿ���ļ���bug��,�ֶ�������ӵ�mathold.xls��mathnew.xls��
 * ��3����RStudio��ִ��mathpred.R,���Եõ�һ���µ�mathpred.csv,�ֶ����x�е�mathnew.xls�У�����������ΪestimateBug ��4�����б�����
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

		/*String oldSrcFile = "C:/Users/Administrator/Desktop/SIR/����/mathnew.xls"; // oldSrcFile���Ѿ������bug��test cases�Լ�estimateBug��Ϣ��xls�ļ�
		String AMFile = "C:/Users/Administrator/Desktop/SIR/����/mathnew2old2.xls"; // AMFile�Ǻ���AM��Ϣ��xls�ļ����ɹ���diffcount����  
		
		A1.initModules(oldSrcFile,AMFile);
		A2.initModules(oldSrcFile,AMFile);
		A3.initModules(oldSrcFile,AMFile);
		B1.initModules(oldSrcFile,AMFile);
		B2.initModules(oldSrcFile,AMFile);
		B3.initModules(oldSrcFile,AMFile);
		B4.initModules(oldSrcFile,AMFile);*/

		String oldSrcFile = "C:/Users/Administrator/Desktop/SIR/����/math3.0.xls"; // oldSrcFile���Ѿ������bug���Լ�estimateBug��Ϣ������xls�ļ�

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
		double effortStart=500;
		double effortEnd=10000;
		double effortStep=10;
		double orignTests=1500;//��ʵtests��Ŀ
		double orignBug=23;//ԭʼbug��

		defectDiscoveryUtil = new DefectDiscoveryUtil(A1);
		modules = A1.getModules();
		sumDefects = 0;
		System.out.println("�ڲ���A1�У�����ģ��ɷ��ֵ�ȱ�����ֱ����£�");

		for (double totaleffort = effortStart; totaleffort < effortEnd; totaleffort += effortStep) {
			A1.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			if (sumDefects >= orignBug) {
				System.out.println("��������ԭbug��Ŀ��ȵ�bugʱ�������tests=" + totaleffort + ",ռ����" + totaleffort / orignTests);
				break;
			}
			// System.out.println("���ڲ���A1,����ģ����ܵ�ȱ��Ԥ����Ŀ��:"+sumDefects);
		}

		defectDiscoveryUtil = new DefectDiscoveryUtil(A2);
		System.out.println("�ڲ���A2�У�����ģ��ɷ��ֵ�ȱ�����ֱ����£�");
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
				System.out.println("��������ԭbug��Ŀ��ȵ�bugʱ�������tests=" + totaleffort + ",ռ����" + totaleffort / orignTests);
				break;
			}
			// System.out.println("���ڲ���A2,����ģ����ܵ�ȱ��Ԥ����Ŀ��:"+sumDefects);
		}

		defectDiscoveryUtil = new DefectDiscoveryUtil(A3);
		System.out.println("�ڲ���A3�У�����ģ��ɷ��ֵ�ȱ�����ֱ����£�");
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
				System.out.println("��������ԭbug��Ŀ��ȵ�bugʱ�������tests=" + totaleffort + ",ռ����" + totaleffort / orignTests);
				break;
			}
			// System.out.println("���ڲ���A3,����ģ����ܵ�ȱ��Ԥ����Ŀ��:"+sumDefects);
		}

		defectDiscoveryUtil = new DefectDiscoveryUtil(B1);
		System.out.println("�ڲ���B1�У�����ģ��ɷ��ֵ�ȱ�����ֱ����£�");
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
				System.out.println("��������ԭbug��Ŀ��ȵ�bugʱ�������tests=" + totaleffort + ",ռ����" + totaleffort / orignTests);
				break;
			}
			// System.out.println("���ڲ���B1,����ģ����ܵ�ȱ��Ԥ����Ŀ��:"+sumDefects);
		}

		defectDiscoveryUtil = new DefectDiscoveryUtil(B2);
		System.out.println("�ڲ���B2�У�����ģ��ɷ��ֵ�ȱ�����ֱ����£�");
		modules = B2.getModules();
		sumDefects = 0;
		for (double totaleffort = 1; totaleffort < 100000; totaleffort += 1) {//����Ƚ�����
			B2.setTotalEfforts(totaleffort);
			sumDefects = 0;
			for (int i = 0; i < modules.size(); i++) {
				double def = defectDiscoveryUtil.discoveryDefect(i);
				sumDefects += def;
			}
			if (sumDefects >= orignBug) {
				System.out.println("��������ԭbug��Ŀ��ȵ�bugʱ�������tests=" + totaleffort + ",ռ����" + totaleffort / orignTests);
				break;
			}
			// System.out.println("���ڲ���B2,����ģ����ܵ�ȱ��Ԥ����Ŀ��:"+sumDefects);
		}

	/*	defectDiscoveryUtil = new DefectDiscoveryUtil(B3);
		System.out.println("�ڲ�B3�У�����ģ��ɷ��ֵ�ȱ�����ֱ����£�");
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
				System.out.println("��������ԭbug��Ŀ��ȵ�bugʱ�������tests=" + totaleffort + ",ռ����" + totaleffort / orignTests);
				break;
			}
			// System.out.println("���ڲ���B3,����ģ����ܵ�ȱ��Ԥ����Ŀ��:"+sumDefects);
		}

		defectDiscoveryUtil = new DefectDiscoveryUtil(B4);
		System.out.println("�ڲ���B4�У�����ģ��ɷ��ֵ�ȱ�����ֱ����£�");
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
				System.out.println("��������ԭbug��Ŀ��ȵ�bugʱ�������tests=" + totaleffort + ",ռ����" + totaleffort / orignTests);
				break;
			}
			// System.out.println("���ڲ���B4,����ģ����ܵ�ȱ��Ԥ����Ŀ��:"+sumDefects);
		}*/

		/*//����Ľ��������޹�
		System.out.println("����ȱ�ݷ���ģ�ͣ���֪����ģ���Ԥ��ȱ��������������Ҫ������ô��ȱ��������Ҫ�Ĳ��Թ�����������ģ������Ĳ��Թ���������");
		modules=B4.getModules();
		double sumefforts=0;
		for (int i = 0; i < modules.size(); i++) {
			Module m=modules.get(i);
			double effort=defectDiscoveryUtil.EffortNeeded(i);
			System.out.println(m.getName()+": "+effort);
			sumefforts+=effort;
		}
		System.out.println("����ģ������Ĳ��Թ��������ܺ���:"+sumefforts);*/

	}

}
