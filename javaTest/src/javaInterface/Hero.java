package javaInterface;

class Hero extends action implements canFight,canSwim,canFly{

	public void fly() {
		// TODO Auto-generated method stub
		System.out.println("we can fly");
	}

	public void swim() {
		// TODO Auto-generated method stub
		System.out.println("we can swim");
	}
     public static void main(String[] args) throws Exception {
		Hero hero = new Hero();
		Class c = Class.forName("javaInterface.Hero");
		canFight cf=(canFight)c.newInstance();
		cf.fight();
		canFly cf2=(canFly)c.newInstance();
		cf2.fly();
		canSwim cs=(canSwim)c.newInstance();
		cs.swim();
	}	
}
