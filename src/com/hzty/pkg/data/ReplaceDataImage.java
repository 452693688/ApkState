package com.hzty.pkg.data;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hzty.pkg.utile.Log;

public class ReplaceDataImage {
	// 替换logo
	private static String[] logos = new String[] { "drawable-ldpi",
			"drawable-mdpi", "drawable-hdpi", "drawable-xhdpi",
			"drawable-xxhdpi" };
	private static int[] size = new int[] { 36, 48, 72, 96, 144 };

	/**
	 * 
	 * @param replacePath
	 *            替换目录
	 * @param sourcePng
	 *            源目录
	 * @param iconName
	 *            替换目标的图片名称
	 * @throws IOException
	 */
	public static void replaceLogo(String sourcePng, String replaceRootPath,
			String iconName) {
		for (int i = 0; i < logos.length; i++) {
			String rootPath = replaceRootPath + "\\res\\" + logos[i] + "\\"
					+ iconName;
			replaceImage(sourcePng, rootPath, size[i], size[i]);
		}

	}

	// 替换指定图片
	public static void replaceImage(String sourcePng, String replacetPng,
			int width, int height) {
		try {
			writeImage(sourcePng, replacetPng, width, height);
			Log.e("图片源:" + sourcePng + " \n替换：" + replacetPng);
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("修改图片失败：" + e.getMessage());
		}
	}

	// 从本地读取
	private static void writeImage(String sourcePng, String replacePath,
			int width, int height) throws IOException {
		File sourceFile = new File(sourcePng);
		if (!sourceFile.exists()) {
			Log.e("修改图片失败：源图不存在");
			return;
		}
		BufferedImage bufferedImage = javax.imageio.ImageIO.read(sourceFile);
		double widthRatio = 0.0;
		double heightRatio = 0.0;
		File file = new File(replacePath);
		if (width == 0) {
			// 不裁剪
			ImageIO.write(bufferedImage, "png", file);
			Log.e("修改图片成功");
			return;
		}
		Image Itemp = bufferedImage.getScaledInstance(width, height,
				bufferedImage.SCALE_SMOOTH);
		widthRatio = (double) height / bufferedImage.getWidth();
		heightRatio = (double) width / bufferedImage.getHeight();
		AffineTransformOp op = new AffineTransformOp(
				AffineTransform.getScaleInstance(widthRatio, heightRatio), null);
		Itemp = op.filter(bufferedImage, null);
		ImageIO.write((BufferedImage) Itemp, "png", file);
		Log.e("修改图片成功");
	}
}
