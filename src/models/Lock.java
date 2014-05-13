package models;

import main.Param;

public class Lock extends Component {
	
	private int state;
	
	public Lock(int state){
		super(null, -1);
		this.setState(state);
	}

	public int getState() {
		return state;
	}

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

	public synchronized void take_in_vessel(Vessel v) {
		// while the lock is full or there is a current vessel in the lock - wait
		while(this.getState() == 1 || this.getCurrent_vessel() != null){
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
	
	public synchronized void try_to_change_state(){
		if(this.getCurrent_vessel() == null && this.getState() == 0){
			this.try_to_fill();
		}
		else if(this.getCurrent_vessel() == null && this.getState() == 1){
			this.try_to_empty();
		}
		notifyAll();
	}
	
	
	public void try_to_fill() {
		if(this.getState() == 1){
			System.out.println("Tried to fill lock: Already full!");
		}
		this.setState(1);

	}
	
	public void try_to_empty() {
		if(this.getState() == 0){
			System.out.println("Tried to empty lock: Already empty!");
		}
		this.setState(0);
	}
	

	
}
