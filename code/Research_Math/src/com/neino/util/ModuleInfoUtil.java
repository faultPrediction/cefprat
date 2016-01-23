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
 * ��cls�ļ��ж������Գ�ʼ��module����
 * @param fileName
 * @return
 */
public class ModuleInfoUtil {
	/**
	 * ��cls�ļ��ж������Գ�ʼ��module����
	 * @param fileName  ���ݼ��ļ�·����
	 * @return
	 */
	public static ArrayList<Module> getAllModulesFrEXECL(String fileName){
		ArrayList<Module>modules=new ArrayList<Module>();
        try {  
            File file = new File(fileName); // �����ļ�����  
            Workbook wb = Workbook.getWorkbook(file); // ���ļ����л�ȡExcel����������WorkBook��  
            Sheet sheet = wb.getSheet(0); // �ӹ�������ȡ��ҳ��Sheet��  
              
            for (int i = 0; i < sheet.getRows(); i++) { // ѭ����ӡExcel���е�����  ���У�
            	if(i==0)continue;//�Թ���һ��
            	
            	Module module=new Module();
            	double newLoc=0;
            	double modLoc=0;
                for (int j = 0; j < sheet.getColumns(); j++) {  //��24�У�
                    Cell cell = sheet.getCell(j, i);  
                    String content=cell.getContents();
                    double estimateBug=0;
                    switch(j){
                    case 0:
                    	module.setName(content);
                    	break;
                    case 16://Դ��������
                    	module.setSLOC(Double.valueOf(content));
                    	break;
                    case 36://add����
                    	newLoc=Double.valueOf(content);
                    	break;
                    case 37://mod����
                    	modLoc=Double.valueOf(content);
                    	module.setNewLoc(newLoc+modLoc);//����ӵ�����
                    	module.setReusedLoc(module.getSLOC()-module.getNewLoc());//���õ�����
                    	break;
                    case 39://bug��
                    	module.setBug(Double.valueOf(content));
                    	module.setTestEffort(0);//��ʼ��Ϊ0
                    	break;
                    case 40://estimateBug�������ɭ��Ԥ��ȱ������
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//����10����������
                    	estimateBug=Double.valueOf(content);
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;
                   /* case 41://estimateBug�����߼��ع�Ԥ��ȱ������
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//����10����������
                    	estimateBug=Double.valueOf(content);
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;*/
                   /* case 42://estimateBug����CARTԤ��ȱ������
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//����10����������
                    	estimateBug=Double.valueOf(content);
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;*/
                   /* case 43://estimateBug�������ɭ��Ԥ��ȱ���ܶȣ�
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//����10����������
                    	estimateBug=Double.valueOf(content)*module.getSLOC();
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;*/
                   /* case 44://estimateBug�����߼��ع�Ԥ��ȱ������
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//����10����������
                    	estimateBug=Double.valueOf(content)*module.getSLOC();;
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;*/
                  /*  case 45://estimateBug����CartԤ��ȱ������
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//����10����������
                    	estimateBug=Double.valueOf(content)*module.getSLOC();
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;*/
              
                    	
                    }
                    
                }  
                modules.add(module);
            }  
        } catch (BiffException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
		return modules;
	}
	
	

}
