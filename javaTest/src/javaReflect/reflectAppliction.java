package javaReflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class reflectAppliction {
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ArrayList<String> array1 = new ArrayList<String>();
		ArrayList array2 = new ArrayList();
		array1.add("hello");
		Class c1 = array1.getClass();
		Class c2 = array2.getClass();
		System.out.print(c1 == c2);// c1=c2˵������֮�󼯺ϵķ�����ȥ���ͻ��ģ�java�еķ����Ƿ�ֹ��������ģ�ֻ�ڱ���׶���Ч
		//��Ϊ�������ڱ���֮��Ĳ��������÷�������֤���Ͻ���
		try {
			Method m=c1.getMethod("add", Object.class);
			m.invoke(array1, 20);//�������
			System.out.print(array1);
			for (int i = 0; i < array1.size(); i++) {
	               	
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
