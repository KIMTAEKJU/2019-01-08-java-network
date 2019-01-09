package thread;

public class MultithreadEx01 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Thread digitThread = new DigitThread();
		
		digitThread.start();
		for (char c = 'a'; c <= 'z'; c++)
		{
			//System.out.print("[" + Thread.currentThread().getId() + "]" + c);
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
