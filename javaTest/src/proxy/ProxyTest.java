package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {
	public static void main(String[] args) throws Throwable{
		//第一个客户端的代理
		RealChat realChat = new RealChat();
		Chat chat= newProxy(realChat);
		chat.reply("hi,你好");	//执行invoke()函数
		 RealChat2 realChat2 = new RealChat2();
		Chat chat2 = newProxy(realChat2);
		chat2.reply("撒叼");
	}

	private static Chat newProxy(Chat realChat) {
		// TODO Auto-generated method stub
		
		InvocationHandler ih = new ChatProxy(realChat);//两个角色共用一个代理
		Class cls = realChat.getClass();
		Chat chat = (Chat) Proxy.newProxyInstance(cls.getClassLoader(),
				cls.getInterfaces(), ih);
		return chat;
	}

	
}
