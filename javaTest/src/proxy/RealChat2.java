package proxy;
//第二个具体角色的业务逻辑代码
public class RealChat2 implements Chat{
	public void reply(String message) {
		// TODO Auto-generated method stub
		if(message.equals("你操蛋")){
			System.out.println("你妹的,弄不死你");
		}else if(message.equals("麻痹")){
			System.out.println("尼玛");
		}else if(message.equals("撒叼")){
			System.out.println("逗比");
		}else if(message.equals("奶奶的")){
			System.out.println("奶奶的,看我不弄你");
		}else{
		System.out.println("需要我亲自回复的内容...");
	
		}
	}
}
