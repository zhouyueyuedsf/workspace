package javaIo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.ObjectInputStream.GetField;

public class BufferInputFile {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public  static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
//	File dir =new File("src/javaIo/test.txt");
	//dir.mkdir();
//	String path=dir.getAbsolutePath().toString();//�õ�·��
//	File file = new File(path+"src/"+"/test1");
//	file.mkdir();
//  System.out.print(dir.getCanonicalFile().toString());  
	//dir.mkdir();
		System.out.print(read("src/javaIo/test.txt"));
	}

	private static String read(String string) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader in = new BufferedReader(new FileReader(
				string));
		StringBuilder builder=new StringBuilder();
		String s;
		while((s=in.readLine())!=null)//����Ҫ��������и�ֵ,��Ȼ��һ���������ֵ
		{
//		String	s=in.readLine();
		builder.append(s+"\n");
		}
		return builder.toString();
	}

}
