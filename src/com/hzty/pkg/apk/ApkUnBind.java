package com.hzty.pkg.apk;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ApkUnBind {
	
	// 执行的apktool的文件目录
	public static String apktoolPath = new File("").getAbsolutePath() + "\\lib";
	public static boolean unApk(String apkPath,String resultPath) {
		File resultFile=new File(resultPath);
		boolean exists=resultFile.exists();
		if(!exists){
			resultFile.mkdirs();
		}
		try {
			String cmd = "cmd.exe /c apktool d [-s] -f " + apkPath + " -o "
					+ resultPath;
			 // cmd = "cmd.exe /c apktool d " + apkPath + " "
			//		+ resultPath;
			//cmd = "cmd.exe /c apktool d -r " + apkPath + " "
			//		 		+ resultPath;
			File file = new File(apktoolPath);
			System.out.println(cmd);
			Process process = Runtime.getRuntime().exec(cmd, null, file);
			print(process.getInputStream());
			int code = process.waitFor();
			if (code != 0) {
				System.out.println("解压失败。。。");
				return false;
			} 
			System.out.println("解压成功。。。");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
 	}
	public static void print(InputStream in) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				System.out.println(temp);
			}
			System.out.println("解压："+temp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解压失败。。。"+"\n"+e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("解压失败。。。"+"\n"+e.getMessage());
				}
			}
		}
	}
}
