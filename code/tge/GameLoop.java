package tge;

public class GameLoop extends Thread {
	long timeNano=System.nanoTime();
	int FrameLimiterLogic=60;
	Sim parent;
	public void run() {
	    while(true) {
	    	long time=System.currentTimeMillis();
			loop();
			try {
				long waitTime=(long) (1000d/(double)FrameLimiterLogic-System.currentTimeMillis()+time);
				if(waitTime>0)
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	public int loop() {
		//System.out.println("okokok");
		//return (parent.LogicLoop());
		return (1);
	}
}