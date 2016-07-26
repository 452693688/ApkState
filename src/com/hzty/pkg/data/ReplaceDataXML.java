package com.hzty.pkg.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.hzty.pkg.utile.Log;

public class ReplaceDataXML {
	// 修改Manifest文件 只能修改provider标签
	public static void replaceManifestProvider(String xml, String[] authorities,
			String[] authoritiesReplace) {
	
		File file = new File(xml);
		if (!file.exists()) {
			Log.e("修改Manifest失败：Manifest文件不存在" + xml);
			return;
		}
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(file);
			Element root = document.getRootElement();
			Element element = root.element("application");
			List nodes = element.elements("provider");
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String authoritie = elm.attributeValue("authorities");
				for (int i = 0; i < authorities.length; i++) {
					if (authorities[i].equals(authoritie)) {
						elm.setAttributeValue("authorities", authoritiesReplace[i]);
					}
				}
			}
			XMLWriter(xml, document);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.e("修改Manifest失败：" + e.getMessage());
		} catch (DocumentException e) {
			e.printStackTrace();
			Log.e("修改Manifest失败：" + e.getMessage());
		} 
	}
	// 修改Manifest文件 只能修改meta-data标签
	public static void replaceManifest(String xml, String[] names,
			String[] values) {
		if (names == null || values == null) {
			Log.e("修改Manifest失败：names？values？");
			return;
		}
		if (names.length == 0) {
			Log.e("修改Manifest失败：names==0");
			return;
		}
		if (names.length != values.length) {
			Log.e("修改Manifest失败：names!=values");
			return;
		}
		File file = new File(xml);
		if (!file.exists()) {
			Log.e("修改Manifest失败：Manifest文件不存在" + xml);
			return;
		}
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(file);
			Element root = document.getRootElement();
			Element element = root.element("application");
			List nodes = element.elements("meta-data");
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String name = elm.attributeValue("name");
				for (int i = 0; i < names.length; i++) {
					if (names[i].equals(name)) {
						elm.setAttributeValue("value", values[i]);
					}
				}
			}
			XMLWriter(xml, document);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.e("修改Manifest失败：" + e.getMessage());
		} catch (DocumentException e) {
			e.printStackTrace();
			Log.e("修改Manifest失败：" + e.getMessage());
		} 
	}

	// 修改string文件
	public static void replaceString(String xml, String[] names, String[] values) {
		if (names == null || values == null) {
			Log.e("修改string失败：names？values？");
			return;
		}
		if (names.length == 0) {
			Log.e("修改string失败：names==0");
			return;
		}
		if (names.length != values.length) {
			Log.e("修改string失败：names!=values");
			return;
		}
		File file = new File(xml);
		if (!file.exists()) {
			Log.e("修改string失败：string文件不存在" + xml);
			return;
		}
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(file);
			Element root = document.getRootElement();
			List nodes = root.elements("string");
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String name = elm.attributeValue("name");
				for (int i = 0; i < names.length; i++) {
					if (names[i].equals(name)) {
						elm.setText(values[i]);
					}
				}
			}
			XMLWriter(xml,document);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.e("修改string失败:" + e.getMessage());
		} catch (DocumentException e) {
			e.printStackTrace();
			Log.e("修改string失败:" + e.getMessage());
		} 
	}

	private static void XMLWriter(String xml, Document document) {
		try {
			XMLWriter output = new XMLWriter(new FileWriter(new File(xml)));
			output.write(document);
			output.close();
			Log.e("修改" + xml + "文件成功");
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("修改" + xml + "失败：" + e.getMessage());
		}

	}
}
