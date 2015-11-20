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
 * ��ÿ��ģ�����add/modified��Ϣ���ѷ�����
 * @author Administrator
 *
 */
public class AddAM2V2 {
	//����һ��excel�ļ��г�������Ķ���
	static class NewMoudleInfo{
		private double newLoc;//��¼��ģ��������ӵ�/�޸Ĺ��Ĵ��������
		private String name;//ģ�����ƣ����ڱȶ�
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
	
	
	//�����ļ������addOtherInfo�и�ģ���New/Modified Loc ��Ϣ�Լ�reused Loc��Ϣ
	private static ArrayList<Module> addAMInfo(ArrayList<Module> modules,String newFilePath){
		ArrayList<NewMoudleInfo> modulesInfo=new ArrayList<NewMoudleInfo>();
		try {
			
			File file = new File(newFilePath); // �����ļ�����  
			Workbook wb = Workbook.getWorkbook(file); // ���ļ����л�ȡExcel����������WorkBook��  
			Sheet sheet = wb.getSheet(0); // �ӹ�������ȡ��ҳ��Sheet��  
			  
			for (int i = 0; i < sheet.getRows(); i++) { // ѭ����ӡExcel���е�����  ���У�
				if(i==0)continue;//�Թ���һ��
				
				NewMoudleInfo moduleInfo=new NewMoudleInfo();
				double AM=0;
			    for (int j = 0; j < sheet.getColumns(); j++) {  //������ÿһ�У�
			        Cell cell = sheet.getCell(j, i);  
			        String content=cell.getContents();
			        switch(j){
			        /*case 4://A&&M��
			        	AM=Double.valueOf(content);
			        	break;
			        case 6://CMT��
			        	AM-=Double.valueOf(content);
			        	moduleInfo.setNewLoc(AM);
			        	break;*/
			        case 7://NBNC��
			        	AM=Double.valueOf(content);
			        	moduleInfo.setNewLoc(AM);
			        	break;
			        case 10://name��
			        	moduleInfo.setName(content);
			        break;
			        }
			        if((moduleInfo.getName()!=null)&&!moduleInfo.getName().trim().equals("")){//name��Ϊ�գ���Ϊ���Ϊ�գ�˵���°��У�����ļ���ɾ����
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
		
		//���ν�modules��modulesInfo��name���Խ��бȶ�
		for (int i = 0; i < modules.size(); i++) {
			Module m=modules.get(i);
			String name1=m.getName().substring(m.getName().lastIndexOf(".")+1);//ֻ�����һ���ļ���
			boolean isInAMFILE=false;//��ʾ������AMFile�ļ����Ƿ��м���
			for (int j = 0; j < modulesInfo.size(); j++) {
				NewMoudleInfo info=modulesInfo.get(j);
				String name2=info.getName().substring(0,info.getName().lastIndexOf("."));//"��.javaȥȥ��"
				if((name1!=null)&&(name2!=null)&&(name1.trim().equals(name2.trim()))){//������ͬ
					double newLoc=info.getNewLoc();
					m.setNewLoc(newLoc);
					m.setReusedLoc(m.getSLOC()-newLoc);
					isInAMFILE=true;
					break;
				}
			}
			if(!isInAMFILE){//���ִ�е��⣬˵����ģ����AM��û�м�¼����ô˵������ļ���ԭ�����ļ���һģһ����
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
