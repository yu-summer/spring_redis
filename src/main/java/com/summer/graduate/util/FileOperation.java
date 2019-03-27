package com.summer.graduate.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName com.summer.graduate.util.FileOperation
 * @Description TODO
 * @Author summer
 * @Date 2019/3/27 10:37
 * @Version 1.0
 **/
@Component
public class FileOperation {
	/**
	 * 读文件
	 * @param filePath
	 * @return
	 */
	private static StringBuffer readFile(String filePath) {
		StringBuffer stringBuffer = new StringBuffer();
		String pathname = filePath;
		try (FileReader reader = new FileReader(pathname);
		     BufferedReader br = new BufferedReader(reader)
		) {
			String line;
			while ((line = br.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}

	/**
	 * 写入文件
	 * @param filePath
	 */
	public static void writeFile(String filePath) {
		StringBuffer content = readFile("E:\\demo\\1.txt");
		try {
			File writeName = new File(filePath);
			try (FileWriter writer = new FileWriter(writeName);
			     BufferedWriter out = new BufferedWriter(writer)
			) {
				out.write(content.toString());
				out.flush(); // 把缓存区内容压入文件
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done....");
	}

	public List<String> readDir(String filePath) {
		File file = new File(filePath);
		File[] files = file.listFiles();

		List<String> fileNames = new ArrayList<>();
		for (File file1 : files) {
			fileNames.add(file1.getName());
		}
		return fileNames;
	}
}
