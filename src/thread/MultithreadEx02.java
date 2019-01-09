package thread;

public class MultithreadEx02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread th1 = new DigitThread();
		Thread th2 = new AlphabeticThread();
		Thread th3 = new DigitThread();

		th1.start();
		th2.start();
		th3.start();
		
	}

}
