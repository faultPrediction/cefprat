package com.neino.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.neino.entity.Module;

/**
 * 给每个模块添加add/modified信息（已废弃）
 * @author Administrator
 *
 */
public class AddAM2V2 {
	//从另一个excel文件中抽象出来的对象
	static class NewMoudleInfo{
		private double newLoc;//记录该模块中新添加的/修改过的代码的行数
		private String name;//模块名称，便于比对
		public double getNewLoc() {
			return newLoc;
		}
		public void setNewLoc(double newLoc) {
			this.newLoc = newLoc;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	
	//从新文件中添加addOtherInfo中各模块的New/Modified Loc 信息以及reused Loc信息
	private static ArrayList<Module> addAMInfo(ArrayList<Module> modules,String newFilePath){
		ArrayList<NewMoudleInfo> modulesInfo=new ArrayList<NewMoudleInfo>();
		try {
			
			File file = new File(newFilePath); // 创建文件对象  
			Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）  
			Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet）  
			  
			for (int i = 0; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容  （行）
				if(i==0)continue;//略过第一行
				
				NewMoudleInfo moduleInfo=new NewMoudleInfo();
				double AM=0;
			    for (int j = 0; j < sheet.getColumns(); j++) {  //（遍历每一列）
			        Cell cell = sheet.getCell(j, i);  
			        String content=cell.getContents();
			        switch(j){
			        /*case 4://A&&M列
			        	AM=Double.valueOf(content);
			        	break;
			        case 6://CMT列
			        	AM-=Double.valueOf(content);
			        	moduleInfo.setNewLoc(AM);
			        	break;*/
			        case 7://NBNC列
			        	AM=Double.valueOf(content);
			        	moduleInfo.setNewLoc(AM);
			        	break;
			        case 10://name列
			        	moduleInfo.setName(content);
			        break;
			        }
			        if((moduleInfo.getName()!=null)&&!moduleInfo.getName().trim().equals("")){//name不为空，因为如果为空，说明新版中，这个文件被删除了
			        	modulesInfo.add(moduleInfo);
			        }
			    }
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//依次将modules与modulesInfo的name属性进行比对
		for (int i = 0; i < modules.size(); i++) {
			Module m=modules.get(i);
			String name1=m.getName().substring(m.getName().lastIndexOf(".")+1);//只留最后一个文件名
			boolean isInAMFILE=false;//表示改类在AMFile文件中是否有记载
			for (int j = 0; j < modulesInfo.size(); j++) {
				NewMoudleInfo info=modulesInfo.get(j);
				String name2=info.getName().substring(0,info.getName().lastIndexOf("."));//"把.java去去掉"
				if((name1!=null)&&(name2!=null)&&(name1.trim().equals(name2.trim()))){//名称相同
					double newLoc=info.getNewLoc();
					m.setNewLoc(newLoc);
					m.setReusedLoc(m.getSLOC()-newLoc);
					isInAMFILE=true;
					break;
				}
			}
			if(!isInAMFILE){//如果执行到这，说明该模块在AM中没有记录，那么说明这个文件和原来的文件是一模一样的
				m.setNewLoc(0);
				m.setReusedLoc(m.getSLOC());
			}
		}
		
		return modules;
	}
	
	
	public static ArrayList<Module> getFinalInfo(String oldSrcFile,String AMFile) {
		ArrayList<Module> modules=ModuleInfoUtil.getAllModulesFrEXECL(oldSrcFile);
		modules=addAMInfo(modules,AMFile);
		return modules;
	}  
	
}
