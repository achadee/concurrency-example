package main;


/**
 * Thread that controls moving tugs from one section to the next
 * @author avinchadee
 *
 */

public class Normal_tug extends Thread {

	private Section s2;
	private Section s;
	private int id;
	

	public Normal_tug(int i, Section section, Section section2) {
		this.s = section;
		this.s2 = section2;
		this.id = i;
	}
	
	
	/**
	 * listen to the section that the tug is assigned to
	 * move vessel if valid
	 */
	public void run() {
		while(true){
			synchronized (s){
				if(s.getCurrent_vessel() != null 
						&& s2.getCurrent_vessel() == null){
					this.s.moveTo(this.s2);
					s.notifyAll();
				}
			}
			
		}
	}

	private boolean vessel_appears_ready_to_move_to_next_section() {
		return s.getCurrent_vessel() != null 
				&& s2.getCurrent_vessel() == null;
	}
}
