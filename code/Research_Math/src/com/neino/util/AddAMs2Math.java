package com.neino.util;

import java.io.File;
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
 * ��AM�ļ������Ϣ�������ݼ��ļ����˳����ȡ���������ɵ�һ���µ��ļ���ȥ ˼·���ȶ�AM�ļ���Ȼ�������ļ�����Ϣ�ŵ�һ��Map�� �ٶ����ݼ��ļ�����ÿһ���ļ���Map������Ϣ������У�������ADD/MOD/DEL��Ϣ������˵������ļ���ԭ�����ļ���ȫһ������ôADD/MOD/DEL��Ϊ0
 * 
 */
public class AddAMs2Math {

	// ��AM excel�ļ��г�������Ķ���
	static class NewMoudleInfo {
		private int add;// add
		private int mod;// mod
		private int del;// del
		private int a_m;// a&m
		private int nbnc;// not blank not comment
		private String name;// ģ�����ƣ����ڱȶ�

		public int getAdd() {
			return add;
		}

		public NewMoudleInfo(int add, int mod, int del, int a_m, int nbnc, String name) {
			super();
			this.add = add;
			this.mod = mod;
			this.del = del;
			this.a_m = a_m;
			this.nbnc = nbnc;
			this.name = name;
		}

		public int getNbnc() {
			return nbnc;
		}

		public void setNbnc(int nbnc) {
			this.nbnc = nbnc;
		}

		public void setAdd(int add) {
			this.add = add;
		}

		public int getMod() {
			return mod;
		}

		public void setMod(int mod) {
			this.mod = mod;
		}

		public int getDel() {
			return del;
		}

		public void setDel(int del) {
			this.del = del;
		}

		public int getA_m() {
			return a_m;
		}

		public void setA_m(int a_m) {
			this.a_m = a_m;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	/**
	 * 
	 * @param AMOldFileName
	 *            :��diffCount���ɵ��ļ�
	 * @param dataSetfileName
	 *            ��mathnew.xls
	 * @param AMNewFileName
	 *            :����Ҫ���ɵ����ļ�
	 */
	public static void getAMInOrder(String AMOldFileName, String dataSetfileName, String AMNewFileName) {
		try {
			// �ȱ���AMOldFileName���ѽ���ŵ�Map��
			File amOldFile = new File(AMOldFileName); // �����ļ�����

			Workbook amOldWb = Workbook.getWorkbook(amOldFile); // ���ļ����л�ȡExcel����������WorkBook��
			Sheet amOldSheet = amOldWb.getSheet(0); // �ӹ�������ȡ��ҳ��Sheet��

			Map<String, NewMoudleInfo> AMInfo = new HashMap<String, NewMoudleInfo>();// ����ÿ���ļ��������Ϣ
			for (int i = 0; i < amOldSheet.getRows(); i++) { // ѭ����ӡExcel���е����� ���У�
				if (i == 0)
					continue;// �Թ���һ��(����)

				int add = 0;// ���ӵ���
				int mod = 0;// �޸ĵ���
				int del = 0;// ɾ������
				int a_m = 0;// ���ӵ��к��޸ĵ����ܺ�
				int nbnc = 0;
				String name = "";
				for (int j = 0; j < amOldSheet.getColumns(); j++) { // ���α�����
					Cell cell = amOldSheet.getCell(j, i);
					String content = cell.getContents().trim();
					switch (j) {
					case 1:// ADD��
						add = Integer.valueOf(content);
						break;
					case 2:// MOD��
						mod = Integer.valueOf(content);
						break;
					case 3:// DEL��
						del = Integer.valueOf(content);
						break;
					case 4:// A&M��
						a_m = Integer.valueOf(content);
						break;
					case 7:// NBNC��
						nbnc = Integer.valueOf(content);
						break;
					case 10:// Target file��
						name = content;
						if (name != null && !name.trim().equals("")) {// ���nameΪ�գ��򲻼�¼��˵�����ļ����Ѿ�û������ļ���
							name = name.substring(0, name.indexOf(".java"));// ��ȡ.java֮ǰ������
							NewMoudleInfo moduleInfo = new NewMoudleInfo(add, mod, del, a_m, nbnc, name);
							AMInfo.put(name, moduleInfo);
						}

						break;

					}
				}
			}

			// ���������ļ���AM��Ϣ�Ѿ���AMInfo��

			// �ٱ���dataSetfileName
			File dataSetFile = new File(dataSetfileName); // �����ļ�����

			Workbook dataSetWb = Workbook.getWorkbook(dataSetFile); // ���ļ����л�ȡExcel����������WorkBook��
			Sheet dataSetSheet = dataSetWb.getSheet(0); // �ӹ�������ȡ��ҳ��Sheet��

			List<NewMoudleInfo> dataSetInfo = new ArrayList<NewMoudleInfo>();// ����ÿ���ļ��������Ϣ
			for (int i = 0; i < dataSetSheet.getRows(); i++) { // ѭ����ӡExcel���е����� ���У�
				if (i == 0)
					continue;// �Թ���һ��(����)

				int add = 0;// ���ӵ���
				int mod = 0;// �޸ĵ���
				int del = 0;// ɾ������
				int a_m = 0;// ���ӵ��к��޸ĵ����ܺ�
				int nbnc = 0;
				String name = "";
				for (int j = 0; j < dataSetSheet.getColumns(); j++) { // ���α�����
					Cell cell = dataSetSheet.getCell(j, i);
					String content = cell.getContents().trim();
					switch (j) {
					case 0:// class name��
						name = content;
						String fileName = name.substring(name.lastIndexOf(".") + 1);// ��ȡ���һ��.֮����ļ���
						boolean hasFound = false;
						for (String key : AMInfo.keySet()) { // ����AMInfo
							if (key.equals(fileName)) {// �ҵ���
								NewMoudleInfo m = AMInfo.get(key);
								m.setName(name);// ����һ��nameΪȫ·����
								dataSetInfo.add(m);
								hasFound = true;
							}

						}
						if (!hasFound) {// ���û�ҵ�
							NewMoudleInfo m = new NewMoudleInfo(add, mod, del, a_m, nbnc, name);
							dataSetInfo.add(m);
						}
						break;

					}
				}
			}

			// ���� �������ļ���AM��Ϣ�Ѿ�����dataSetFile�е�˳���¼��dataSetInfo��

			// ����һ�����ļ���д���Ӧ��Ϣ
			WritableWorkbook amNewFile = Workbook.createWorkbook(new File(AMNewFileName));
			WritableSheet amNewSheet = amNewFile.createSheet("ͳ��", 0);
			Label label = new Label(0, 0, "CLASS");
			// ������õĵ�Ԫ����ӵ���������
			amNewSheet.addCell(label);
			Label label2 = new Label(1, 0, "ADD");
			// ������õĵ�Ԫ����ӵ���������
			amNewSheet.addCell(label2);
			Label label3 = new Label(2, 0, "MOD");
			// ������õĵ�Ԫ����ӵ���������
			amNewSheet.addCell(label3);
			Label label4 = new Label(3, 0, "DEL");
			// ������õĵ�Ԫ����ӵ���������
			amNewSheet.addCell(label4);
			Label label5 = new Label(4, 0, "A&M");
			// ������õĵ�Ԫ����ӵ���������
			amNewSheet.addCell(label5);
			Label label6 = new Label(5, 0, "NBNC");
			// ������õĵ�Ԫ����ӵ���������
			amNewSheet.addCell(label6);

			for (int j = 0; j < dataSetInfo.size(); j++) {
				NewMoudleInfo m = dataSetInfo.get(j);
				String name = m.getName();
				int add = m.getAdd();// ���ӵ���
				int mod = m.getMod();// �޸ĵ���
				int del = m.getDel();// ɾ������
				int a_m = m.getA_m();// ���ӵ��к��޸ĵ����ܺ�
				int nbnc = m.getNbnc();
				Label l1 = new Label(0, j + 1, name);
				amNewSheet.addCell(l1);
				Label l2 = new Label(1, j + 1, String.valueOf(add));
				amNewSheet.addCell(l2);
				Label l3 = new Label(2, j + 1, String.valueOf(mod));
				amNewSheet.addCell(l3);
				Label l4 = new Label(3, j + 1, String.valueOf(del));
				amNewSheet.addCell(l4);
				Label l5 = new Label(4, j + 1, String.valueOf(a_m));
				amNewSheet.addCell(l5);
				Label l6 = new Label(5, j + 1, String.valueOf(nbnc));
				amNewSheet.addCell(l6);
			}

			// д�����ݲ��ر��ļ�
			amNewFile.write();
			amNewFile.close(); // �����finally�йرգ��˴�����Ϊʾ����̫�淶
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

	public static void main(String[] args) {

		String oldAMFileName = "C:/Users/Administrator/Desktop/SIR/����/math3.0to2.1.xls"; // Excel�ļ�����·��
		String dataSetFileName = "C:/Users/Administrator/Desktop/SIR/����/math3.0.xls"; // Excel�ļ�����·��
		String newAMFileName = "C:/Users/Administrator/Desktop/SIR/����/mathAM3.0.xls"; // Excel�ļ�����·��
		getAMInOrder(oldAMFileName, dataSetFileName, newAMFileName);

		
		oldAMFileName = "C:/Users/Administrator/Desktop/SIR/����/math2.1to2.0.xls"; // Excel�ļ�����·��
		dataSetFileName = "C:/Users/Administrator/Desktop/SIR/����/math2.1.xls"; // Excel�ļ�����·��
		newAMFileName = "C:/Users/Administrator/Desktop/SIR/����/mathAM2.1.xls"; // Excel�ļ�����·��
		getAMInOrder(oldAMFileName, dataSetFileName, newAMFileName);
	}

}
