package com.neino.entity;

/**
 * ģ����
 * 
 * @author Administrator
 * 
 */
public class Module {

	// ������Щ��Ϣ��Ҫ��excle�ļ��л��
	private String version;// ģ�������İ汾��
	private String name;// ģ������
	private double SLOC;//Դ������
	
	private double bug;// ʵ��cls�ļ��е�ȱ����Ŀ
	private double testEffort;// ʵ�ʲ��Թ�����(ԭ�����������Ѿ�֪��ÿ��module��tests�Ƕ��٣�����ʵ��ֻ��Ҫ֪���ܵ�tests���У�������Щ���ǻ��ʼ��Ϊ0���������һ���ܵ�tests������)

	private double newLoc;// ��ģ�������/�޸��˶����д���
	private double reusedLoc;// ��ģ�������˶����д���
	private double estimateBug;// ��ǰһ���汾����ȱ��Ԥ��ģ��Ԥ��õ���ȱ����Ŀ


	public Module() {}

	

	public Module(String version, String name, double sLOC, double bug, double testEffort, double newLoc,double reusedLoc,
			double estimateBug) {
		super();
		this.version = version;
		this.name = name;
		SLOC = sLOC;
		this.bug = bug;
		this.testEffort = testEffort;
		this.newLoc = newLoc;
		this.reusedLoc = reusedLoc;
		this.estimateBug = estimateBug;
	}




	@Override
	public String toString() {
		return version + " " + name + " " + SLOC + " "+ bug+" "+estimateBug;
	}



	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public double getSLOC() {
		return SLOC;
	}



	public void setSLOC(double sLOC) {
		SLOC = sLOC;
	}



	public double getBug() {
		return bug;
	}



	public void setBug(double bug) {
		this.bug = bug;
	}



	public double getTestEffort() {
		return testEffort;
	}



	public void setTestEffort(double testEffort) {
		this.testEffort = testEffort;
	}



	public double getNewLoc() {
		return newLoc;
	}



	public void setNewLoc(double newLoc) {
		this.newLoc = newLoc;
	}



	public double getReusedLoc() {
		return reusedLoc;
	}



	public void setReusedLoc(double reusedLoc) {
		this.reusedLoc = reusedLoc;
	}



	public double getEstimateBug() {
		return estimateBug;
	}



	public void setEstimateBug(double estimateBug) {
		this.estimateBug = estimateBug;
	}

	

}
