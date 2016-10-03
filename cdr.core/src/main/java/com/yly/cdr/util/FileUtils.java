package com.yly.cdr.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.core.Constants;

public class FileUtils
{
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	/**
	 * 以文件形式保存图片
	 * */
	public static boolean saveMedicalImageToFile(byte[] bt, String directoryPath, String fileName) throws IOException{
		if(!directoryPath.endsWith("/")){
			directoryPath += "/"; 
		}
		File directory = new File(directoryPath); 
		if(!directory.exists()){
			directory.mkdirs();
		}
		File file = new File(directoryPath + fileName);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);  
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(bt);
		bos.close();
		fos.close();
		logger.debug("文件保存成功：{}", directoryPath + fileName);
		return true;
	}
	/**
	 * 读取图片文件
	 * */
	public static byte[] readMedicalImageFromFile(String filePath) throws IOException{
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));        
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);        
        byte[] temp = new byte[1024];        
        int size = 0;        
        while ((size = in.read(temp)) != -1) {        
                out.write(temp, 0, size);        
        }        
        in.close(); 
        byte[] content = out.toByteArray(); 
        out.close();
        logger.debug("读取文件成功：{}", filePath);
        return content;
	}
	
	public static boolean isFileExsit(String filePath){
		if(filePath == null) return false;
		File file = new File(filePath);
		return file.exists();
	}
	
	public static void deleteFiles(String... filePaths){
		if(filePaths == null){
			logger.debug("文件路径为空");
			return ;
		}
		for(String filePath : filePaths){
			if(isFileExsit(filePath)){
				File file = new File(filePath);
				boolean flag = file.delete();
				if(flag){
					logger.debug("文件：{} 删除成功", filePath);
				} else {
					logger.debug("文件：{} 删除失败", filePath);
				}
			} else {
				logger.debug("文件：{} 不存在", filePath);
			}
		}
	}
	
}
