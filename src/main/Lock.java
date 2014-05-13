package main;


/**
 * 
 * @author avinchadee
 *	lock class, Monitor that mantains the state of the lock
 */

public class Lock extends Component {
	
	private int state;
	
	public Lock(int state){
		super(null, -1);
		this.setState(state);
	}

	public int getState() {
		return state;
	}

	/**
	 * Sets the state of the lock and logs it out to console
	 * @param state
	 */
	public void setState(int state) {
		if(state == 1){
			System.out.println("Chamber Fills");
		}
		else if(state == 0){
			System.out.println("Chamber drains");
		}
		try {
			Thread.sleep(Param.OPERATE_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.state = state;
	}

	/**
	 * Takes in a vessel from the producer
	 * @param v
	 */
	
	public synchronized void take_in_vessel(Vessel v) {
		// while the lock is full with water or there is a current vessel in 
		// the lock - wait
		// OR 
		// while the lock is empty and there is no vessel in the lock
		while(this.getState() == 1 || this.getCurrent_vessel() != null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.setState(1);
		this.setCurrent_vessel(v);
		notifyAll();
	}
	
	/**
	 * Returns a vessel from section[n-1]
	 * @param v
	 */
	
	public synchronized void return_vessel(Vessel v, Component prev) {
		// while the lock is full with water or there is a current vessel in 
		//the lock - wait
		// OR 
		// while the lock is empty and there is no vessel in the lock
		System.out.println("State is: " + this.getState() + " " + this.getCurrent_vessel() + " " + v.isHas_been_through_loop());
		while(this.getState() == 0 && this.getCurrent_vessel() != null
				&& v.isHas_been_through_loop()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(v.isHas_been_through_loop()){
			prev.setCurrent_vessel(null);
			this.setState(0);
			this.setCurrent_vessel(v);
		}
		notifyAll();
	}
	
	/**
	 * tries to change the state of the lock if the pre conditions are met
	 */
	public synchronized void try_to_change_state(){
		if(this.getCurrent_vessel() == null && this.getState() == 0){
			this.try_to_fill();
		}
		else if(this.getCurrent_vessel() == null && this.getState() == 1){
			this.try_to_empty();
		}
		notifyAll();
	}
	
	/**
	 * tries to fill
	 */
	public void try_to_fill() {
		if(this.getState() == 1){
			System.out.println("Tried to fill lock: Already full!");
		}
		this.setState(1);

	}
	
	/**
	 * tries to empty
	 */
	public void try_to_empty() {
		if(this.getState() == 0){
			System.out.println("Tried to empty lock: Already empty!");
		}
		this.setState(0);
	}

	public void setVesseltohasvisited() {
		this.current_vessel.setHas_been_through_loop(true);
		
	}

	public synchronized void removeVessel() {
		
			while(this.getCurrent_vessel() == null 
					|| !this.getCurrent_vessel().isHas_been_through_loop()){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.setCurrent_vessel(null);
	}
	

	
}
