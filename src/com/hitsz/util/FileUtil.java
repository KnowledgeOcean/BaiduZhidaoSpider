package com.hitsz.util;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
	/**
	 * д�ļ��ĺ���
	 * @param fileName �ļ���
	 * @param content д������
	 * @param append �Ƿ�׷��
	 */
	public static void writeFile(String fileName, String content, boolean append){
		try {
			FileWriter fw = new FileWriter(fileName, append);
			fw.write(content);
			fw.write("\r\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
