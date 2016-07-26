package com.hzty.pkg;

import java.io.File;

import org.junit.Test;

import com.hzty.pkg.apk.ApkBind;
import com.hzty.pkg.apk.ApkUnBind;
import com.hzty.pkg.data.ReplaceDataImage;
import com.hzty.pkg.data.ReplaceDataXML;

//海棠在线动态打包
public class Htzx {
	// 根目录
	public static String root = new File("").getAbsolutePath();

	private final String rootPath = root+"\\stateApk\\prepare_htzx\\";
	@Test
	public void test() {
		boolean isOk = unApk();
		if (!isOk) {
			return;
		}
		 
		replaceData();
		release();
	}

	// apk源
	private String apkPath = root+"\\stateApk\\apk\\Hospital-ddys_htzx-release.apk";
	// apk解压路径
	private String unApkPath = rootPath+"unPak";

	// 解压apk
	public boolean unApk() {
		boolean isOk = ApkUnBind.unApk(apkPath, unApkPath);
		return isOk;
	}

	// 替换数据
	public void replaceData() {
		replaceLogo();
		replaceImage();
		replaceXML();
	}

	private String sourcePng =rootPath+ "prepare\\logo.png";
	private String replaceRootPath = rootPath+"unPak";
	private String iconName = "icon_launcher.png";

	// 替换logo 
	public void replaceLogo() {
		ReplaceDataImage.replaceLogo(sourcePng, replaceRootPath, iconName);
	}

	private String imageSourcePng = rootPath+"prepare\\push.png";
	private String imageReplacePng = rootPath+"unPak\\res\\drawable-ldpi\\push.png";
	//启动图
		private String stateSourcePng = rootPath+"prepare\\welcome.png";
		private String stateReplacePng = rootPath+"unPak\\res\\drawable-xhdpi\\welcome.png";

	// 替换推送指定图片
	 
	public void replaceImage() {
		ReplaceDataImage.replaceImage(imageSourcePng, imageReplacePng, 36, 36);
		ReplaceDataImage.replaceImage(stateSourcePng, stateReplacePng, 0, 0);
	}

	private String manifest = rootPath+"unPak\\AndroidManifest.xml";
	private String[] mNames = new String[] { "BMOB_CHANNEL" };
	private String[] mValues = new String[] { "ddys_krzx" };
	
	private String[] authorities=new String[]{"downloads.cn.hztywl.ddysys"};
	private String[] authoritiesReplace=new String[]{"downloads.cn.hztywl.ddysys.htzx"};
	
	private String stringXml =rootPath+ "unPak\\res\\values\\strings.xml";
	private String[] sNames = new String[] { "app_name","app_type" };
	private String[] sValues = new String[] { "海堂在线" ,"ddys_htzx"};
	
	@Test
	public void replaceXML() {
		// 修改Manifest.xml
		ReplaceDataXML.replaceManifest(manifest, mNames, mValues);
		//Provider 
		ReplaceDataXML.replaceManifestProvider(manifest, authorities, authoritiesReplace);
		// 修改string.xml
		ReplaceDataXML.replaceString(stringXml, sNames, sValues);
	}

	// 要打包成apk的源文件
	private String sourceFile = rootPath+"unPak";
	// 新的apk路径
	private String apksPath = rootPath+"apks";
	private String unSignApk = "unSignApk.apk";
	private String releaseApk = "release.apk";
	private String keystore = root+"\\stateApk\\apk\\key";
	private String password = "hztywl";

	// 打包和签名
	private void release() {
		String signApk = ApkBind.bindPack(sourceFile, apksPath, unSignApk);
		if (signApk.equals("")) {
			return;
		}
		System.out.println("签名：" + signApk);
		ApkBind.signApk(keystore, password, apksPath + "\\" + releaseApk,
				signApk);
	}
}
