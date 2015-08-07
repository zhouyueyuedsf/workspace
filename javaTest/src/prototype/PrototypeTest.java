package prototype;

import java.util.ArrayList;
import java.util.List;
//测试类
public class PrototypeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Prototype prototype = new Prototype();
		List<String> contents = new ArrayList<String>();
		contents.add("我爱你");
		contents.add("你是我的唯一");
		prototype.setContents(contents);
		
		//clone模式,直接克隆对象.
		Prototype prototype2 = prototype.clone();
		System.out.println(prototype2.getContents().get(0));
		//给prototype2添加更多的情话
		contents.add("你是我的真命天女");
		prototype2.setContents(contents);
		System.out.println(prototype2.getContents());
		
	}

}
