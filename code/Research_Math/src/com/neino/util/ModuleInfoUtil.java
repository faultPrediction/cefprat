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
 * 从cls文件中读数据以初始化module对象
 * @param fileName
 * @return
 */
public class ModuleInfoUtil {
	/**
	 * 从cls文件中读数据以初始化module对象
	 * @param fileName  数据集文件路径名
	 * @return
	 */
	public static ArrayList<Module> getAllModulesFrEXECL(String fileName){
		ArrayList<Module>modules=new ArrayList<Module>();
        try {  
            File file = new File(fileName); // 创建文件对象  
            Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）  
            Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet）  
              
            for (int i = 0; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容  （行）
            	if(i==0)continue;//略过第一行
            	
            	Module module=new Module();
            	double newLoc=0;
            	double modLoc=0;
                for (int j = 0; j < sheet.getColumns(); j++) {  //（24列）
                    Cell cell = sheet.getCell(j, i);  
                    String content=cell.getContents();
                    double estimateBug=0;
                    switch(j){
                    case 0:
                    	module.setName(content);
                    	break;
                    case 16://源代码行数
                    	module.setSLOC(Double.valueOf(content));
                    	break;
                    case 36://add行数
                    	newLoc=Double.valueOf(content);
                    	break;
                    case 37://mod行数
                    	modLoc=Double.valueOf(content);
                    	module.setNewLoc(newLoc+modLoc);//新添加的行数
                    	module.setReusedLoc(module.getSLOC()-module.getNewLoc());//复用的行数
                    	break;
                    case 39://bug数
                    	module.setBug(Double.valueOf(content));
                    	module.setTestEffort(0);//初始化为0
                    	break;
                    case 40://estimateBug数（随机森林预测缺陷数）
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//乘以10后四舍五入
                    	estimateBug=Double.valueOf(content);
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;
                   /* case 41://estimateBug数（逻辑回归预测缺陷数）
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//乘以10后四舍五入
                    	estimateBug=Double.valueOf(content);
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;*/
                   /* case 42://estimateBug数（CART预测缺陷数）
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//乘以10后四舍五入
                    	estimateBug=Double.valueOf(content);
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;*/
                   /* case 43://estimateBug数（随机森林预测缺陷密度）
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//乘以10后四舍五入
                    	estimateBug=Double.valueOf(content)*module.getSLOC();
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;*/
                   /* case 44://estimateBug数（逻辑回归预测缺陷数）
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//乘以10后四舍五入
                    	estimateBug=Double.valueOf(content)*module.getSLOC();;
                    	if(estimateBug<0)estimateBug=0;
                    	module.setEstimateBug(estimateBug);
                    	break;*/
                  /*  case 45://estimateBug数（Cart预测缺陷数）
                    	//estimateBug=Math.round(Double.valueOf(content)*10);//乘以10后四舍五入
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
