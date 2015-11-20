package com.neino.manager;

import java.util.ArrayList;
import java.util.List;

import com.neino.entity.Module;
import com.neino.util.AddAM2V2;
import com.neino.util.ModuleInfoUtil;

public abstract class Strategy {
	static int n = 0;// ģ����
	List<Module> modules = new ArrayList<Module>();// ���ĳһ���汾�����е�module����
	/**
	 * ���弸��������modules�е����ݽ��չ����������
	 */
	double[] estimateBugs;// �������ģ���Ԥ��ȱ����
	double[] locs;// �������ģ��Ĵ�������
	double[] newLocs;// �������ģ�������ӵĴ�������
	double[] reusedLocs;// �������ģ�����ù��Ĵ�������
	
	double totalEfforts = 1500;// ����ȱ�ݷ���ģ��ʱ���ܵĲ��Թ�����(�ܲ��Թ�����δ֪����ʱ��д��ô��)

	/**
	 * ��ʼ�����е�ģ��
	 */
	public void initModules(String srcFile) {
		// ���ù��ߴ�excel�ļ��л�ȡ����ÿ��ģ��������Ϣ
		modules = ModuleInfoUtil.getAllModulesFrEXECL(srcFile);
		n = modules.size();
		if (n <= 0) {
			System.out.println("error:��ʼ������ģ��ʧ��");
			return;
		}
		estimateBugs = new double[n];// �������ģ���Ԥ��ȱ����
		locs = new double[n];// �������ģ��Ĵ�������
		newLocs = new double[n];// �������ģ�������ӵĴ�������
		reusedLocs = new double[n];// �������ģ�����ù��Ĵ�������
		for (int i = 0; i < n; i++) {
			Module m=modules.get(i);
			estimateBugs[i]=m.getEstimateBug();
			locs[i] = m.getSLOC();
			newLocs[i]=m.getNewLoc();
			reusedLocs[i]=m.getReusedLoc();
		}
		
		
	}
	
	/**
	 * ��ʼ�����е�ģ�飨�ѷ�����
	 */
	public void initModules(String srcFile,String AMFile) {
		// ���ù��ߴ�excel�ļ��л�ȡ����ÿ��ģ��������Ϣ
		modules = AddAM2V2.getFinalInfo(srcFile, AMFile);
		n = modules.size();
		if (n <= 0) {
			System.out.println("error:��ʼ������ģ��ʧ��");
			return;
		}
		estimateBugs = new double[n];// �������ģ���Ԥ��ȱ����
		locs = new double[n];// �������ģ��Ĵ�������
		newLocs = new double[n];// �������ģ�������ӵĴ�������
		reusedLocs = new double[n];// �������ģ�����ù��Ĵ�������
		for (int i = 0; i < n; i++) {
			Module m=modules.get(i);
			estimateBugs[i]=m.getEstimateBug();
			locs[i] = m.getSLOC();
			newLocs[i]=m.getNewLoc();
			reusedLocs[i]=m.getReusedLoc();
		}
		
		
	}
	
	
	/**
	 * ��������ģ���ȱ�ݵ�����
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
	 * ��������ģ��Ĵ�������
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
	 * ��������ģ�������ӵĴ����������ܺ�
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
	 * ��������ģ����޸Ĺ��Ĵ����������ܺ�
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
	 * �ӿڣ��õ�ĳһ��ģ��Ӧ�÷���Ĳ��Թ�����
	 * 
	 * @param index
	 *            ģ���±�
	 * @return
	 */
	public abstract double getTestEffort(int index);

}
