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
 * 将AM文件里的信息按照数据集文件里的顺序提取出来，生成到一个新的文件中去 思路：先读AM文件，然后将所有文件的信息放到一个Map中 再读数据集文件，对每一个文件从Map中找信息，如果有，则设置ADD/MOD/DEL信息，否则说明这个文件和原来的文件完全一样，那么ADD/MOD/DEL皆为0
 * 
 */
public class AddAMs2Math {

	// 从AM excel文件中抽象出来的对象
	static class NewMoudleInfo {
		private int add;// add
		private int mod;// mod
		private int del;// del
		private int a_m;// a&m
		private int nbnc;// not blank not comment
		private String name;// 模块名称，便于比对

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
	 *            :由diffCount生成的文件
	 * @param dataSetfileName
	 *            ：mathnew.xls
	 * @param AMNewFileName
	 *            :最终要生成的新文件
	 */
	public static void getAMInOrder(String AMOldFileName, String dataSetfileName, String AMNewFileName) {
		try {
			// 先遍历AMOldFileName，把结果放到Map中
			File amOldFile = new File(AMOldFileName); // 创建文件对象

			Workbook amOldWb = Workbook.getWorkbook(amOldFile); // 从文件流中获取Excel工作区对象（WorkBook）
			Sheet amOldSheet = amOldWb.getSheet(0); // 从工作区中取得页（Sheet）

			Map<String, NewMoudleInfo> AMInfo = new HashMap<String, NewMoudleInfo>();// 保存每个文件的相关信息
			for (int i = 0; i < amOldSheet.getRows(); i++) { // 循环打印Excel表中的内容 （行）
				if (i == 0)
					continue;// 略过第一行(标题)

				int add = 0;// 增加的行
				int mod = 0;// 修改的行
				int del = 0;// 删除的行
				int a_m = 0;// 增加的行和修改的行总和
				int nbnc = 0;
				String name = "";
				for (int j = 0; j < amOldSheet.getColumns(); j++) { // 依次遍历列
					Cell cell = amOldSheet.getCell(j, i);
					String content = cell.getContents().trim();
					switch (j) {
					case 1:// ADD列
						add = Integer.valueOf(content);
						break;
					case 2:// MOD列
						mod = Integer.valueOf(content);
						break;
					case 3:// DEL列
						del = Integer.valueOf(content);
						break;
					case 4:// A&M列
						a_m = Integer.valueOf(content);
						break;
					case 7:// NBNC列
						nbnc = Integer.valueOf(content);
						break;
					case 10:// Target file列
						name = content;
						if (name != null && !name.trim().equals("")) {// 如果name为空，则不记录，说明新文件中已经没有这个文件了
							name = name.substring(0, name.indexOf(".java"));// 截取.java之前的名称
							NewMoudleInfo moduleInfo = new NewMoudleInfo(add, mod, del, a_m, nbnc, name);
							AMInfo.put(name, moduleInfo);
						}

						break;

					}
				}
			}

			// 至此所有文件的AM信息已经在AMInfo中

			// 再遍历dataSetfileName
			File dataSetFile = new File(dataSetfileName); // 创建文件对象

			Workbook dataSetWb = Workbook.getWorkbook(dataSetFile); // 从文件流中获取Excel工作区对象（WorkBook）
			Sheet dataSetSheet = dataSetWb.getSheet(0); // 从工作区中取得页（Sheet）

			List<NewMoudleInfo> dataSetInfo = new ArrayList<NewMoudleInfo>();// 保存每个文件的相关信息
			for (int i = 0; i < dataSetSheet.getRows(); i++) { // 循环打印Excel表中的内容 （行）
				if (i == 0)
					continue;// 略过第一行(标题)

				int add = 0;// 增加的行
				int mod = 0;// 修改的行
				int del = 0;// 删除的行
				int a_m = 0;// 增加的行和修改的行总和
				int nbnc = 0;
				String name = "";
				for (int j = 0; j < dataSetSheet.getColumns(); j++) { // 依次遍历列
					Cell cell = dataSetSheet.getCell(j, i);
					String content = cell.getContents().trim();
					switch (j) {
					case 0:// class name列
						name = content;
						String fileName = name.substring(name.lastIndexOf(".") + 1);// 截取最后一个.之后的文件名
						boolean hasFound = false;
						for (String key : AMInfo.keySet()) { // 遍历AMInfo
							if (key.equals(fileName)) {// 找到了
								NewMoudleInfo m = AMInfo.get(key);
								m.setName(name);// 更新一下name为全路径名
								dataSetInfo.add(m);
								hasFound = true;
							}

						}
						if (!hasFound) {// 如果没找到
							NewMoudleInfo m = new NewMoudleInfo(add, mod, del, a_m, nbnc, name);
							dataSetInfo.add(m);
						}
						break;

					}
				}
			}

			// 至此 ，所有文件的AM信息已经按照dataSetFile中的顺序记录在dataSetInfo中

			// 创建一个新文件，写入对应信息
			WritableWorkbook amNewFile = Workbook.createWorkbook(new File(AMNewFileName));
			WritableSheet amNewSheet = amNewFile.createSheet("统计", 0);
			Label label = new Label(0, 0, "CLASS");
			// 将定义好的单元格添加到工作表中
			amNewSheet.addCell(label);
			Label label2 = new Label(1, 0, "ADD");
			// 将定义好的单元格添加到工作表中
			amNewSheet.addCell(label2);
			Label label3 = new Label(2, 0, "MOD");
			// 将定义好的单元格添加到工作表中
			amNewSheet.addCell(label3);
			Label label4 = new Label(3, 0, "DEL");
			// 将定义好的单元格添加到工作表中
			amNewSheet.addCell(label4);
			Label label5 = new Label(4, 0, "A&M");
			// 将定义好的单元格添加到工作表中
			amNewSheet.addCell(label5);
			Label label6 = new Label(5, 0, "NBNC");
			// 将定义好的单元格添加到工作表中
			amNewSheet.addCell(label6);

			for (int j = 0; j < dataSetInfo.size(); j++) {
				NewMoudleInfo m = dataSetInfo.get(j);
				String name = m.getName();
				int add = m.getAdd();// 增加的行
				int mod = m.getMod();// 修改的行
				int del = m.getDel();// 删除的行
				int a_m = m.getA_m();// 增加的行和修改的行总和
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

			// 写入数据并关闭文件
			amNewFile.write();
			amNewFile.close(); // 最好在finally中关闭，此处仅作为示例不太规范
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

		String oldAMFileName = "C:/Users/Administrator/Desktop/SIR/论文/math3.0to2.1.xls"; // Excel文件所在路径
		String dataSetFileName = "C:/Users/Administrator/Desktop/SIR/论文/math3.0.xls"; // Excel文件所在路径
		String newAMFileName = "C:/Users/Administrator/Desktop/SIR/论文/mathAM3.0.xls"; // Excel文件所在路径
		getAMInOrder(oldAMFileName, dataSetFileName, newAMFileName);

		
		oldAMFileName = "C:/Users/Administrator/Desktop/SIR/论文/math2.1to2.0.xls"; // Excel文件所在路径
		dataSetFileName = "C:/Users/Administrator/Desktop/SIR/论文/math2.1.xls"; // Excel文件所在路径
		newAMFileName = "C:/Users/Administrator/Desktop/SIR/论文/mathAM2.1.xls"; // Excel文件所在路径
		getAMInOrder(oldAMFileName, dataSetFileName, newAMFileName);
	}

}
