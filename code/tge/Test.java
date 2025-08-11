package tge;

public class Test {

	static class A{
		public A() {}
	}
	
	static class B extends A{

		public B() {super();}
	}
	
	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		char test='U';
		System.out.println(test);
		System.out.println((byte)test);
		System.out.println((char)85);
	}

}
