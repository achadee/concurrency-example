package models.tugs;

import models.Lock;
import models.Section;

/**
 * thread that controls returning a tug and freeing it from memory
 * @author avinchadee
 *
 */

public class Return_tug extends Thread {

	private Section section;
	private Lock l;

	public Return_tug(Section section, Lock lock) {
		// TODO Auto-generated constructor stub
		this.l = lock;
		this.section = section;
	}

	/**
	 * returns a tug to the lock if safe to do so
	 */
	@Override
	public void run() {
		while(true){
			synchronized (section){
				synchronized (l){
					if(l.getState() == 1 && l.getCurrent_vessel() == null 
							&& section.getCurrent_vessel() != null){
						l.return_vessel(this.section.getCurrent_vessel(), section);
						this.section.notifyAll();
					}
				}
			}
		}
	}
}
