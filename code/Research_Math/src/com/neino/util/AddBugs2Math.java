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
 * ��new��old xls���ݼ��ļ������һ������:bug
 * 
 * @param bugInfofileName
 *            ����bug��Ϣ��txt
 *            
 * @param bugId 
 *            �����ð汾������bug��txt�ļ��еı��
 * @param dataSetfileName
 *            ���ݼ�xls
 * @param destfileName
 *            ���ɵ�Ŀ��xls�ļ�
 */
public class AddBugs2Math {

	public static void getAllModulesFrEXECL(String bugInfofileName,int []bugId,String dataSetfileName, String destfileName) {
		try {
			// �ȱ��������в���������Ϣ�ı�
			File file = new File(bugInfofileName); // �����ļ�����

			BufferedReader reader = null;
			Map<String, Integer> partFileBugs = new HashMap<String, Integer>();// keyΪ�ļ�����valueΪbug��
			try {
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				boolean  List_of_modified_sources=false;//�ǲ����Ѿ�������List_of_modified_sources��һ��
				String identifier="List of modified sources";
				String identifier2="org.apache";
				int i=0;
				int totalmodifiedFiles=0;//��¼�ð汾������bug�漰����file��Ŀ����Ϊ�е�bug���漰���modifide files��
				boolean hasReadidentifier2=false;
				while (((tempString = reader.readLine()) != null) ) {
					if(tempString.indexOf(identifier)!=-1)
					{
						List_of_modified_sources=true;
						i++;//˵���Ѿ�����һ��bug����Ϣ
					}
					if((tempString.indexOf(identifier2)!=-1)&&List_of_modified_sources){
						hasReadidentifier2=true;
						if(isin(i,bugId)){//����ñ�־��bug��bugId��
							totalmodifiedFiles++;
							String className=tempString.substring(tempString.lastIndexOf(".")+1);//�����һ����֮����ļ�����ȡ����
							if (partFileBugs.containsKey(className)) {
								int value = partFileBugs.get(className);
								value++;
								partFileBugs.put(className, value);
							} else
								partFileBugs.put(className, 1);
							System.out.println(className);
						}
						
					}
					else if(hasReadidentifier2){//��Ϊ��ʱ��һ��bug���ܻ��޸Ķ���ļ����ж�hasReadidentifier2��Ҫȷ�����Ѿ����˸�bug��list of modified files��
						List_of_modified_sources=false;//��ʼ����һ��bug����Ϣ
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
			//���ˣ��������ڸð汾��bug���ڵ��ļ��Ѿ����˳���
			
			file = new File(dataSetfileName); // ���ݼ��ļ�
			Workbook wb = Workbook.getWorkbook(file); // ���ļ����л�ȡExcel����������WorkBook��
			Sheet sheet = wb.getSheet(0); // �ӹ�������ȡ��ҳ��Sheet��

			List<String>allFiles=new ArrayList<String>();//��ԭ˳�򱣴����ݼ����ļ���
			List<Integer>correspondBugs=new ArrayList<Integer>();//����ļ���Ӧ��Bug��
			int bugNum=0;
			for (int i = 0; i < sheet.getRows(); i++) { // ѭ����ӡExcel���е����� ���У�
				if (i == 0)
					continue;// �Թ���һ��

				for (int j = 0; j < sheet.getColumns(); j++) { // ��3�У�
					Cell cell = sheet.getCell(j, i);
					String content = cell.getContents().trim();
					switch (j) {
					case 0:// �ļ���
						allFiles.add(content);
						String className=content.substring(content.lastIndexOf(".")+1);//�����һ����֮����ļ�����ȡ����,��Ϊ�ȶ�
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
           
			//���������ļ���bug��Ϣ�Ѿ���allFileBugs��
			
			// ����һ�����ļ���д��bug��Ϣ
			WritableWorkbook book = Workbook.createWorkbook(new File(destfileName));
			WritableSheet sheet3 = book.createSheet("ͳ��", 0);
			// �Լ���Ԫ������Ϊtest
			Label label = new Label(0, 0, "class");
			// ������õĵ�Ԫ����ӵ���������
			sheet3.addCell(label);
			Label label2 = new Label(1, 0, "bug");
			// ������õĵ�Ԫ����ӵ���������
			sheet3.addCell(label2);
				
		  for (int j = 0; j < allFiles.size(); j++) {
			String fileName=allFiles.get(j);
			Integer bug=correspondBugs.get(j);
			Label l1 = new Label(0, j+1, fileName);//key����class
			sheet3.addCell(l1);
			Label l2 = new Label(1, j+1,bug.toString());
			sheet3.addCell(l2);
		  }
			
			// д�����ݲ��ر��ļ�
			book.write();
			book.close(); // �����finally�йرգ��˴�����Ϊʾ����̫�淶
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
		//�ȴ�ӡ��bug��id��
		for (int i = 1; i <= 106; i++) {
			System.out.print(i+",");
		}
		File bugInfo = new File(AddBugs2Math.class.getResource("Mathbuginfo.txt").getPath()); 
		String path=bugInfo.getAbsolutePath();
		path=path.replaceAll("%20", " ");
		
		int[]bugId1={36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70};
		String fileName2 = "C:/Users/Administrator/Desktop/SIR/����/math2.1.xls"; // Excel�ļ�����·��
		String fileName3 = "C:/Users/Administrator/Desktop/SIR/����/math2.1bug.xls"; // Excel�ļ�����·��
		getAllModulesFrEXECL(path,bugId1, fileName2, fileName3);
		
		int[]bugId2={15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
		fileName2 = "C:/Users/Administrator/Desktop/SIR/����/math3.0.xls"; // Excel�ļ�����·��
		fileName3 = "C:/Users/Administrator/Desktop/SIR/����/math3.0bug.xls"; // Excel�ļ�����·��
		getAllModulesFrEXECL(path,bugId2, fileName2, fileName3);
	}

}
