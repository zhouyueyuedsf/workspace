package com.example.birthdaytree.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Environment;

public class FileUtil {
	private static String getSDCardPath() {
		File sdcardDir = null;
		// 获取sdcard路径
		boolean sdcardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		return sdcardDir.toString();
	}
	
	public static File getSaveFile(String projectName,String fileName){
		String fileStr = getSDCardPath()+projectName+"/images/"+fileName;
		File file = new File(fileStr);
		if(file.exists()){
			file.delete();
		}
		try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return file;			
	}
	//得到输出文件的URI
	public static Uri getOutFileUri(int fileType) {
		return Uri.fromFile(getOutFile(fileType));
	}
	
	//生成输出文件
	private static File getOutFile(int fileType) {
		
		String storageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_REMOVED.equals(storageState)){
			return null;
		}
		
		File mediaStorageDir = new File (Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
				,"MyPictures");
		if (!mediaStorageDir.exists()){
			if (!mediaStorageDir.mkdirs()){
				return null;
			}
		}
		
		File file = new File(getFilePath(mediaStorageDir,fileType));
		
		return file;
	}
	//生成输出文件路径
	private static String getFilePath(File mediaStorageDir, int fileType){
		String timeStamp =new SimpleDateFormat("yyyyMMdd_HHmmss")
							.format(new Date());
		String filePath = mediaStorageDir.getPath() + File.separator;
		if (fileType == 1){
			filePath += ("IMG_" + timeStamp + ".jpg");
		}else if (fileType == 2){
			filePath += ("VIDEO_" + timeStamp + ".mp4");
		}else{
			return null;
		}
		return filePath;
	}
}
