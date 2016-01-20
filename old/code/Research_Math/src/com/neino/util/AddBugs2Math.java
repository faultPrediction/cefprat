package com.neino.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 往new和old xls数据集文件中添加一列数据:bug
 * 
 * @param bugInfofileName
 *            含有bug信息的txt
 *            
 * @param bugId 
 *            标明该版本的所有bug在txt文件中的标号
 * @param dataSetfileName
 *            数据集xls
 * @param destfileName
 *            生成的目标xls文件
 */
public class AddBugs2Math {

	public static void getAllModulesFrEXECL(String bugInfofileName,int []bugId,String dataSetfileName, String destfileName) {
		try {
			// 先遍历含有有测试用例信息的表
			File file = new File(bugInfofileName); // 创建文件对象

			BufferedReader reader = null;
			Map<String, Integer> partFileBugs = new HashMap<String, Integer>();// key为文件名，value为bug数
			try {
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				boolean  List_of_modified_sources=false;//是不是已经遍历过List_of_modified_sources这一行
				String identifier="List of modified sources";
				String identifier2="org.apache";
				int i=0;
				int totalmodifiedFiles=0;//记录该版本的所有bug涉及到的file数目（因为有的bug会涉及多个modifide files）
				boolean hasReadidentifier2=false;
				while (((tempString = reader.readLine()) != null) ) {
					if(tempString.indexOf(identifier)!=-1)
					{
						List_of_modified_sources=true;
						i++;//说明已经读到一个bug的信息
					}
					if((tempString.indexOf(identifier2)!=-1)&&List_of_modified_sources){
						hasReadidentifier2=true;
						if(isin(i,bugId)){//如果该标志的bug在bugId中
							totalmodifiedFiles++;
							String className=tempString.substring(tempString.lastIndexOf(".")+1);//把最后一个点之后的文件名截取下来
							if (partFileBugs.containsKey(className)) {
								int value = partFileBugs.get(className);
								value++;
								partFileBugs.put(className, value);
							} else
								partFileBugs.put(className, 1);
							System.out.println(className);
						}
						
					}
					else if(hasReadidentifier2){//因为有时候一个bug可能会修改多个文件，判断hasReadidentifier2是要确保我已经读了该bug的list of modified files了
						List_of_modified_sources=false;//开始读下一个bug的信息
						hasReadidentifier2=false;
					}
					
				}
				
			//	System.out.println(totalmodifiedFiles);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
			//至此，所有属于该版本的bug所在的文件已经列了出来
			
			file = new File(dataSetfileName); // 数据集文件
			Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
			Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet）

			List<String>allFiles=new ArrayList<String>();//按原顺序保存数据集的文件名
			List<Integer>correspondBugs=new ArrayList<Integer>();//与该文件对应的Bug数
			int bugNum=0;
			for (int i = 0; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容 （行）
				if (i == 0)
					continue;// 略过第一行

				for (int j = 0; j < sheet.getColumns(); j++) { // （3列）
					Cell cell = sheet.getCell(j, i);
					String content = cell.getContents().trim();
					switch (j) {
					case 0:// 文件名
						allFiles.add(content);
						String className=content.substring(content.lastIndexOf(".")+1);//把最后一个点之后的文件名截取下来,作为比对
						if (partFileBugs.containsKey(className)) {
							int value = partFileBugs.get(className);
							bugNum+=value;
							correspondBugs.add(value);
						} else
							correspondBugs.add(0);
						break;

					}
				}
			}
			System.err.println("bugNum="+bugNum);
           
			//至此所有文件的bug信息已经在allFileBugs中
			
			// 创建一个新文件，写入bug信息
			WritableWorkbook book = Workbook.createWorkbook(new File(destfileName));
			WritableSheet sheet3 = book.createSheet("统计", 0);
			// 以及单元格内容为test
			Label label = new Label(0, 0, "class");
			// 将定义好的单元格添加到工作表中
			sheet3.addCell(label);
			Label label2 = new Label(1, 0, "bug");
			// 将定义好的单元格添加到工作表中
			sheet3.addCell(label2);
				
		  for (int j = 0; j < allFiles.size(); j++) {
			String fileName=allFiles.get(j);
			Integer bug=correspondBugs.get(j);
			Label l1 = new Label(0, j+1, fileName);//key就是class
			sheet3.addCell(l1);
			Label l2 = new Label(1, j+1,bug.toString());
			sheet3.addCell(l2);
		  }
			
			// 写入数据并关闭文件
			book.write();
			book.close(); // 最好在finally中关闭，此处仅作为示例不太规范
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static boolean isin(int i,int [] bugId){
		for (int j = 0; j < bugId.length; j++) {
			if(bugId[j]==i){
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		//先打印出bug的id号
		for (int i = 1; i <= 106; i++) {
			System.out.print(i+",");
		}
		File bugInfo = new File(AddBugs2Math.class.getResource("Mathbuginfo.txt").getPath()); 
		String path=bugInfo.getAbsolutePath();
		path=path.replaceAll("%20", " ");
		
		int[]bugId1={36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70};
		String fileName2 = "C:/Users/Administrator/Desktop/SIR/论文/math2.1.xls"; // Excel文件所在路径
		String fileName3 = "C:/Users/Administrator/Desktop/SIR/论文/math2.1bug.xls"; // Excel文件所在路径
		getAllModulesFrEXECL(path,bugId1, fileName2, fileName3);
		
		int[]bugId2={15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
		fileName2 = "C:/Users/Administrator/Desktop/SIR/论文/math3.0.xls"; // Excel文件所在路径
		fileName3 = "C:/Users/Administrator/Desktop/SIR/论文/math3.0bug.xls"; // Excel文件所在路径
		getAllModulesFrEXECL(path,bugId2, fileName2, fileName3);
	}

}
