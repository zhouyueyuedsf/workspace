package singleInstance;

public class SingleInstance {
	private String name;
//	private static SingleInstance instance = new SingleInstance();//这样浪费资源
	private static SingleInstance instance;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//构造函数私有化,限制访问权限
	private SingleInstance (){
		
	}
	//提供一个获取实例的方法,这样写即不浪费资源也没有多线程问题?
	public static SingleInstance getSingleInstance(){
		if(instance == null){
			//这样设计的核心是如果Instance不为null就不用调用synchronized,避免了资源浪费,又维护了线程安全
			synchronized (SingleInstance.class) {
				if(instance == null){
					instance = new SingleInstance();
				}
			}
		}
		return instance;
	}
}
					
		
