package models.concurrent;

import models.Lock;
import models.Vessel;

/**
 * Thread that consumes vessels once they have completed a cycle
 * @author avinchadee
 *
 */

public class Consumer extends Thread {

	Lock l;
	
	public Consumer(Lock lock) {
		// TODO Auto-generated constructor stub
		this.l = lock;
	}

	@Override
	public void run() {
		while(true){
			l.removeVessel();
			
		}
	}

}
