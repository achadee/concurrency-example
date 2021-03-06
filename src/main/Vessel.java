package main;
/**
 * A class whose instances are vessels, each with its unique Id.
 */

public class Vessel {

  //the Id of this vessel
  protected int id;

  private boolean has_been_through_loop;

  //the next ID to be allocated
  protected static int nextId = 1;

  //create a new vessel with a given Id
  protected Vessel(int id) {
    this.id = id;
  }

  //get a new Vessel instance with a unique Id
  public static Vessel getNewVessel() {
    return new Vessel(nextId++);
  }

  //produce the Id of this vessel
  public int getId() {
    return id;
  }

  //produce an identifying string for the vessel
  public String toString() {
    return "[" + id + "] ";
  }

public boolean isHas_been_through_loop() {
	return has_been_through_loop;
}

public void setHas_been_through_loop(boolean has_been_through_loop) {
	this.has_been_through_loop = has_been_through_loop;
}
}

