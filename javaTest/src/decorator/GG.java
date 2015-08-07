	package decorator;
	//创建一个具体的人类
	public class GG implements Person{
	
		public void show() {
			// TODO Auto-generated method stub
			this.noDecorator();
		}
	
		public void noDecorator() {
			// TODO Auto-generated method stub
			System.out.println("我是没有经过打扮的GG");
		}
	
	}
