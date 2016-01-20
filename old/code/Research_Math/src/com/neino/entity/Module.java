package com.neino.entity;

/**
 * 模块类
 * 
 * @author Administrator
 * 
 */
public class Module {

	// 以下这些信息需要从excle文件中获得
	private String version;// 模块所属的版本号
	private String name;// 模块名称
	private double SLOC;//源码行数
	
	private double bug;// 实际cls文件中的缺陷数目
	private double testEffort;// 实际测试工作量(原文中是事先已经知道每个module的tests是多少，但是实际只需要知道总的tests就行，所以这些我们会初始化为0，最后设置一下总的tests就行了)

	private double newLoc;// 该模块新添加/修改了多少行代码
	private double reusedLoc;// 该模块重用了多少行代码
	private double estimateBug;// 由前一个版本经过缺陷预测模型预测得到的缺陷数目


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
