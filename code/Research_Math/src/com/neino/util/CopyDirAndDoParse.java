package com.neino.util;

import java.io.*;

/**
 * ����ָ���ļ����е������ļ�����һ���ļ��У�ͬʱȥ��ԭ���е�����import,package,ע�����Լ�����
 */
public class CopyDirAndDoParse {

	/*static String srcUrl = "C:/Users/Administrator/Desktop/SIR/����/math 3.0"; // Դ�ļ���
	static String destUrl = "C:/Users/Administrator/Desktop/SIR/����/math3.0"; // Ŀ���ļ���
*/	
	/*static String srcUrl = "C:/Users/Administrator/Desktop/SIR/����/math 2.1"; // Դ�ļ���
	static String destUrl = "C:/Users/Administrator/Desktop/SIR/����/math2.1"; // Ŀ���ļ���
*/	
	static String srcUrl = "C:/Users/Administrator/Desktop/SIR/����/math 2.0"; // Դ�ļ���
	static String destUrl = "C:/Users/Administrator/Desktop/SIR/����/math2.0"; // Ŀ���ļ���

	public static boolean comment = false;// ��ǰ�Ƿ�������/*...*/֮��

	public static void main(String args[]) throws IOException {
        File objFileDir=new File(destUrl);
        if(objFileDir.exists()){//������ھ���ɾ��
        	deleteAll(objFileDir);
        }
        else objFileDir.mkdirs(); // ����Ŀ���ļ���
		// ��ȡԴ�ļ��е�ǰ�µ��ļ���Ŀ¼
		File[] file = (new File(srcUrl)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// �����ļ�
				copyFile(file[i], new File(destUrl + file[i].getName()));
			}
			if (file[i].isDirectory()) {
				// ����Ŀ¼
				String sourceDir = srcUrl + File.separator + file[i].getName();
				String targetDir = destUrl + File.separator + file[i].getName();
				copyDirectiory(sourceDir, targetDir);
			}
		}

		/*String a="/* aaaaaaaaaaaaaaa";
		System.out.println(isEloc(a));*/
	}

	// �����ļ�
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		// �½��ļ����������������л���
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		// �½��ļ���������������л���
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(output));

		String str = null;
		while ((str = reader.readLine()) != null) {
			if (isEloc(str.trim())) {// ��������Ч��
				out.write(str + "\n");
			}
		}
		// ˢ�´˻���������
		out.flush();

		// �ر���
		reader.close();
		out.close();
		output.close();
		input.close();
	}

	// �����ļ���
	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// �½�Ŀ��Ŀ¼
		(new File(targetDir)).mkdirs();
		// ��ȡԴ�ļ��е�ǰ�µ��ļ���Ŀ¼
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// Դ�ļ�
				File sourceFile = file[i];
				// Ŀ���ļ�
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// ׼�����Ƶ�Դ�ļ���
				String dir1 = sourceDir + "/" + file[i].getName();
				// ׼�����Ƶ�Ŀ���ļ���
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

	/*
	 * eLOC(effective Lines Of Code, ��Ч������)������һ��LOC(Lines Of Code)�ĵط����ڣ�eLOC�������ǿհ��У�ע���У�����ֻ���� "{" ��
	��}" �Ĵ����С�֮���Գ�֮Ϊ��Ч�����У�����Ϊ���ֺ�����ʽ�ܸ��õ�����programmer productivity��code understandability.
	����Ĵ�������������ʽ���жϿհ��У�ע���У���stand-alone�Ĵ������С�������ַ�����һ�д��룬������true��˵�����д�����eLOC��false���ǡ�
	 */
	public static boolean isEloc(String line) {
		// TODO Auto-generated method stub
		String keyWord1="package.*";
		String keyWord2="import.*";
		String regxNodeBegin = "\\s*/\\*.*";
		String regxNodeEnd = ".*\\*/\\s*";
		String regx = "//.*";
		String regxSpace = "\\s*";
		if (line.matches(keyWord1) || line.matches(keyWord2)) {// ��package��importҲ��ȥ��
			return false;
		}
		if (line.matches(regxNodeBegin) && line.matches(regxNodeEnd)) {
			return false;
		}
		if (line.matches(regxNodeBegin)&&line.indexOf("*/")==-1) { //Ҫ�ų�  /*........*/���滹�д���  ����������������ʵ����eloc
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

	// �ݹ�ɾ��ָ��·���µ������ļ�
	public static void deleteAll(File file) {
		if (file.isFile() || file.list().length == 0) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (File f : files) {
				deleteAll(f);// �ݹ�ɾ��ÿһ���ļ�
				f.delete();// ɾ�����ļ���
			}
		}
	}

}