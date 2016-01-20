package com.neino.util;

import java.io.*;

/**
 * 复制指定文件夹中的所有文件到另一个文件夹，同时去除原文中的所有import,package,注释行以及空行
 */
public class CopyDirAndDoParse {

	/*static String srcUrl = "C:/Users/Administrator/Desktop/SIR/论文/math 3.0"; // 源文件夹
	static String destUrl = "C:/Users/Administrator/Desktop/SIR/论文/math3.0"; // 目标文件夹
*/	
	/*static String srcUrl = "C:/Users/Administrator/Desktop/SIR/论文/math 2.1"; // 源文件夹
	static String destUrl = "C:/Users/Administrator/Desktop/SIR/论文/math2.1"; // 目标文件夹
*/	
	static String srcUrl = "C:/Users/Administrator/Desktop/SIR/论文/math 2.0"; // 源文件夹
	static String destUrl = "C:/Users/Administrator/Desktop/SIR/论文/math2.0"; // 目标文件夹

	public static boolean comment = false;// 当前是否正处于/*...*/之中

	public static void main(String args[]) throws IOException {
        File objFileDir=new File(destUrl);
        if(objFileDir.exists()){//如果存在就先删除
        	deleteAll(objFileDir);
        }
        else objFileDir.mkdirs(); // 创建目标文件夹
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(srcUrl)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 复制文件
				copyFile(file[i], new File(destUrl + file[i].getName()));
			}
			if (file[i].isDirectory()) {
				// 复制目录
				String sourceDir = srcUrl + File.separator + file[i].getName();
				String targetDir = destUrl + File.separator + file[i].getName();
				copyDirectiory(sourceDir, targetDir);
			}
		}

		/*String a="/* aaaaaaaaaaaaaaa";
		System.out.println(isEloc(a));*/
	}

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(output));

		String str = null;
		while ((str = reader.readLine()) != null) {
			if (isEloc(str.trim())) {// 该行是有效行
				out.write(str + "\n");
			}
		}
		// 刷新此缓冲的输出流
		out.flush();

		// 关闭流
		reader.close();
		out.close();
		output.close();
		input.close();
	}

	// 复制文件夹
	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

	/*
	 * eLOC(effective Lines Of Code, 有效代码行)区别于一般LOC(Lines Of Code)的地方在于，eLOC并不考虑空白行，注释行，还有只包含 "{" 或
	“}" 的代码行。之所以称之为有效代码行，是因为这种衡量方式能更好地描述programmer productivity和code understandability.
	下面的代码利用正则表达式来判断空白行，注释行，和stand-alone的大括号行。输入的字符串是一行代码，若返回true则说明此行代码是eLOC，false则不是。
	 */
	public static boolean isEloc(String line) {
		// TODO Auto-generated method stub
		String keyWord1="package.*";
		String keyWord2="import.*";
		String regxNodeBegin = "\\s*/\\*.*";
		String regxNodeEnd = ".*\\*/\\s*";
		String regx = "//.*";
		String regxSpace = "\\s*";
		if (line.matches(keyWord1) || line.matches(keyWord2)) {// 把package和import也给去掉
			return false;
		}
		if (line.matches(regxNodeBegin) && line.matches(regxNodeEnd)) {
			return false;
		}
		if (line.matches(regxNodeBegin)&&line.indexOf("*/")==-1) { //要排除  /*........*/后面还有代码  这种情况，这种情况实际是eloc
			comment = true;
			return false;
		} else if (comment&&line.matches(regxNodeEnd)) {
			comment = false;
			return false;
		} else if (line.matches(regxSpace)) {
			return false;
		}
		else if (line.matches(regx)) {
			return false;
		} else if (comment) {
			return false;
		}

		return true;
	}

	// 递归删除指定路径下的所有文件
	public static void deleteAll(File file) {
		if (file.isFile() || file.list().length == 0) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (File f : files) {
				deleteAll(f);// 递归删除每一个文件
				f.delete();// 删除该文件夹
			}
		}
	}

}