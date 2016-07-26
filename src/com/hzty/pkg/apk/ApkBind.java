package com.hzty.pkg.apk;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ApkBind {

	// 执行的apktool的文件目录
	public static String apktoolPath = new File("").getAbsolutePath() + "\\lib";

	/***
	 * 
	 * @param sourceFile
	 *            要打包的apk源文件
	 * @param newApkPath
	 *            新的未签名的apk所放路径
	 * @param newApkName
	 *            新的未签名的apk的名称
	 * @return
	 */
	public static String bindPack(String sourceFile, String unSignApkPath,
			String unSignApk) {
		// 要打包的成的apk
		String apkPath = unSignApkPath + "\\" + unSignApk;
		String cmd = "cmd.exe /c apktool b " + sourceFile + " -o " + apkPath;
		System.out.println("打包:"+cmd);
		Process process = null;
		try {
			File file = new File(apktoolPath);
			process = Runtime.getRuntime().exec(cmd, null, file);
			print(process.getInputStream());
			int code=process.waitFor();
			if (code!= 0) {
				System.out.println("打包失败。。。");
				return "";
			}
			return apkPath;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * apk重新签名
	 * 
	 * @param keystore
	 * @param password
	 * @param unSignatureApk
	 *            未签名的apk
	 * @param newApkPath
	 *            如：F:\stateApk\Hospital\apk\newApp.apk
	 */
	public static void signApk(String keystore, String password,
			String unSignApk, String signApk) {
		String cmd = "cmd.exe /c jarsigner -digestalg SHA1 -sigalg MD5withRSA -verbose -keystore "
				+ keystore
				+ " -storepass "
				+ password				 
				+ "  -keypass "
				+ password
				+ " -signedjar "
				+ unSignApk
				+ " "
				+ signApk+ " android_hztywl";
		System.out.println("文件签名:"+cmd);
		Process process = null;
		try {
			File file = new File(apktoolPath);
			process = Runtime.getRuntime().exec(cmd, null, file);
			print(process.getInputStream());
			if (process.waitFor() != 0) {
				System.out.println("文件签名失败！！！");
			} else {
				System.out.println("文件签名成功！！！");
				// File unsignedApk = new File(unSignatureApk);
				// unsignedApk.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件签名失败！！！"+"\n"+e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("文件签名失败！！！"+"\n"+e.getMessage());
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
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
