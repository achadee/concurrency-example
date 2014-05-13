package models.concurrent;

import main.Param;
import models.Lock;
import models.Vessel;
import main.*;

public class Producer extends Thread{

	Lock l;
	public Producer(Lock lock) {
		// TODO Auto-generated constructor stub
		this.l = lock;
	}
	
	public void create_vessel_on_free_lock(Lock lock) {
			Vessel v = Vessel.getNewVessel();
			synchronized (lock){
				lock.take_in_vessel(v);
				lock.notifyAll();
			}
			
	}
	
	
	private void wait_for_another_ship(){
		try {
			int a = Param.arrivalLapse();
			Thread.sleep(a);
			//System.out.println("Another ship has arrived! creating ship! took: " + a);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void run() {
		while(true){
				wait_for_another_ship();
				create_vessel_on_free_lock(l);
		}
	}
}
