package models.tugs;


import models.Lock;
import models.Section;

public class Launch_tug extends Thread {
	
	public Lock l;
	private Section section;
	
	public Launch_tug(Lock lock, Section section) {
		this.l = lock;
		this.section = section;
	}
	
	public void run() {
		while(true){
			synchronized (l){
				synchronized (section){
					if(l.getState() == 1 && l.getCurrent_vessel() != null && section.getCurrent_vessel() == null){
						this.l.moveTo(this.section);
						this.section.notifyAll();
					}
				}
			}
		}
	}
}
