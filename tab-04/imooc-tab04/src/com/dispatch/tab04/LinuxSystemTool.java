package com.dispatch.tab04;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.StringTokenizer;

public final class LinuxSystemTool
{
    private final static float TotalBandwidth = 1000; 
   public static void setIOWay(final String way){
	   new Thread(){
		   public void run() {	   
			   Runtime r = Runtime.getRuntime();
			   //给scheduler赋777权值,原始为644,导致了adb能访问,而用户程序不能访问,太蛋疼了,777,644跟访问矩阵有关
			   
			   String[] command ={"sh","-c", "echo "+way+" > /sys/block/mmcblk0/queue/scheduler"};
			   try {
				   Process p = r.exec("su");	
				   r.exec("chmod 777 /sys/block/mmcblk0/queue/scheduler");
				   //r.exec("mount -o remount,rw -t yaffs2 /dev/block/mtdblock0 /system");
				   Process pro = r.exec(command);
				   if(pro.waitFor()!=0){
					   
				   }
				  p.destroy();
				  pro.destroy();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   } catch (InterruptedException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
		   };
	   }.start();
   }
   public static float getIOofDisk(){
       float ioUsage = 0.0f;  
       Process pro = null;  
       Runtime r = Runtime.getRuntime();  
       try {  
           String command = "busybox iostat -d";  
           pro = r.exec(command);  
           BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));  
           String line = null;  
           int count =  0;  
          while(count<3){
        	 in.readLine();
        	 count++;
          }
           if((line=in.readLine()) != null){          
                   String[] temp = line.split("\\s+");  
                   if(temp.length > 1){  
                       float util =  Float.parseFloat(temp[2])+Float.parseFloat(temp[3]) * 100;
                       ioUsage = util;  
                   }  
           }               
           in.close();  
           pro.destroy();  
       } catch (IOException e) {  
           StringWriter sw = new StringWriter();  
           e.printStackTrace(new PrintWriter(sw));  
       }     
       return ioUsage;  
   }
   
}