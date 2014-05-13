package models.concurrent;

import main.Param;
import models.Lock;
import models.Vessel;
import main.*;

/**
 * Produces ships at random intervals
 * @author avinchadee
 *
 */

public class Producer extends Thread{

	Lock l;
	public Producer(Lock lock) {
		// TODO Auto-generated constructor stub
		this.l = lock;
	}
	
	/**
	 * creates a new ship and loads it into the lock when safe
	 * to do so
	 * @param lock
	 */
	public void create_vessel_on_free_lock(Lock lock) {
			Vessel v = Vessel.getNewVessel();
			synchronized (lock){
				lock.take_in_vessel(v);
				lock.notifyAll();
			}
			
	}
	
	/**
	 * waits for another ship to arrive
	 */
	
	private void wait_for_another_ship(){
		try {
			int a = Param.arrivalLapse();
			Thread.sleep(a);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * loops the producer so it continues to create ships
	 */
	
	@Override
	public void run() {
		while(true){
				wait_for_another_ship();
				create_vessel_on_free_lock(l);
		}
	}
}
