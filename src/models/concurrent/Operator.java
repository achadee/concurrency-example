package models.concurrent;

import main.Param;
import models.Lock;

/**
 * Operates the level of the lock, all logic is maintained in the lock
 * so that the operator is limited in what it can do
 * @author avinchadee
 *
 */

public class Operator extends Thread {

	Lock l;
	
	public Operator(Lock lock) {
		this.l = lock;
	}

	/**
	 * trigger that runs the operator forever
	 */
	@Override
	public void run() {
		while(true){
			l.try_to_change_state();
			try {
				Thread.sleep(Param.operateLapse());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
}
