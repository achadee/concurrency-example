package models;

import main.Param;

/**
 * 
 * @author avinchadee
 * extended class for sections and locks (because they 
 * contain a lot of similar methods	
 *
 */

public class Component {
	public Vessel current_vessel;
	boolean isLock;
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Component(Vessel v, int i) {
		this.id = i;
		this.setCurrent_vessel(v);
	}
	
	/**
	 * checks if the component contains a vessel
	 * @return
	 */
	public boolean does_not_contain_ship() {
		if(getCurrent_vessel() == null){
			return true;
		}
		return false;	
	}

	public Vessel getCurrent_vessel() {
		return current_vessel;
	}

	/**
	 * setter that also logs every time something is set
	 * @param current_vessel
	 */
	public void setCurrent_vessel(Vessel current_vessel) {
		if(current_vessel != null){
			System.out.print("[" + current_vessel.getId() + "] ");
			if(this.getId() == -1){
				System.out.print("enters lock to go " );
				if(current_vessel.isHas_been_through_loop()){
					System.out.println("down");
				}
				else{
					System.out.println("up");
				}
				
			}
			else{
				System.out.println("enters section " + this.getId());
			}
		}
		this.current_vessel = current_vessel;
	}
	
	/**
	 * abstracted 'move to' moves the vessel from the current state
	 * 'this' to the next state
	 * @param next
	 */
	
	public synchronized void moveTo(Component next) {

		// while there is no ship to transport or the next section is full - wait
		while(this.getCurrent_vessel() == null || next.getCurrent_vessel() != null){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			current_vessel.setHas_been_through_loop(true);
			System.out.print("[" + current_vessel.getId() + "] leaves ");
			if(this.id == -1){
				System.out.println("the lock");
			}
			else{
				System.out.println("section " + this.getId());
			}
			Vessel temp = this.getCurrent_vessel();
			this.setCurrent_vessel(null);
			Thread.sleep(Param.TOWING_TIME);
			next.setCurrent_vessel(temp);

			notifyAll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
