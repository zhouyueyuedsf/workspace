package javaReflect;

 class Office {
public static void main(String[] args) {
	  try { 
		Class c=Class.forName("javaReflect.Word");
		Class c1=Class.forName("javaReflect.Word");
		Class c2=Class.forName("javaReflect.Word");
		try {
			OfficeWEP wep=(OfficeWEP)c.newInstance();
			wep.start();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
