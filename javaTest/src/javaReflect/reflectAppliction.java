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
		System.out.print(c1 == c2);// c1=c2说明编译之后集合的泛型是去泛型化的，java中的泛型是防止错误输入的，只在编译阶段有效
		//因为反射是在编译之后的操作所以用反射来验证以上结论
		try {
			Method m=c1.getMethod("add", Object.class);
			m.invoke(array1, 20);//反射操作
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
