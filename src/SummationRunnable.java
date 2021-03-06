import java.util.concurrent.locks.ReentrantLock;

public class SummationRunnable implements Runnable{
	private int upperBound;
	private boolean done = false;
	private ReentrantLock alock;
	
	public SummationRunnable(int n){
		upperBound = n;
		alock = new ReentrantLock();
	}
	
	public void setDone(){
		done = true;
	}
	
	public void run(){
		alock.lock();
		try{
			while( !done ){
				System.out.println(upperBound);
				upperBound--;
				Thread.sleep(1000);
				if( upperBound<0 ){
					System.out.println("print done");
					return;
				}
			}
			System.out.println("stopped by main()!");
			done = false;
			alock.unlock();
		} catch (InterruptedException e){}
	}
	
	public static void main(String[] args){
		SummationRunnable sRunnable = new SummationRunnable(10);
		Thread thread = new Thread(sRunnable);
		thread.start();
		
		for(int i=0; i<5; i++){
			System.out.println("#");
		}
		sRunnable.setDone();
		try{
			thread.join();
			new Thread(sRunnable).start();
		} catch (InterruptedException e){
		}
	}
}
