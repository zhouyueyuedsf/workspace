package proxy;
//第一个具体角色的业务逻辑代码
public class RealChat implements Chat {

	public void reply(String message) {
		// TODO Auto-generated method stub
		if(message.equals("hi,你好")){
			System.out.println("你也好");
		}else if(message.equals("你从哪里来呀")){
			System.out.println("我来自中国");
		}else if(message.equals("你多大了")){
			System.out.println("我今年22岁啦");
		}else if(message.equals("身高多少呀")){
			System.out.println("我身高178cm");
		}else{
		System.out.println("需要我亲自回复的内容...");
	
		}
	}
}
