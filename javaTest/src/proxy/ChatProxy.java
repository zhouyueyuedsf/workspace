package proxy;
//具体的代理类
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ChatProxy implements InvocationHandler {
	private Object sub;
	public ChatProxy(Object obj){
		this.sub = obj;
	}
	//
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("请稍候...");
	    method.invoke(sub, args);//这个方法采用给了反射的机制,相当于sub.method(args)
	 
		return null;
	}

}
