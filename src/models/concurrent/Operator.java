package models.concurrent;

import main.Param;
import models.Lock;

public class Operator extends Thread {

	Lock l;
	
	public Operator(Lock lock) {
		this.l = lock;
	}

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
