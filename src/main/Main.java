package main;
import models.Lock;
import models.Section;
import models.concurrent.Consumer;
import models.concurrent.Operator;
import models.concurrent.Producer;
import models.tugs.Launch_tug;
import models.tugs.Return_tug;
import models.tugs.Normal_tug;

/**
 * The top-level component of the canal system simulator.
 */ 

public class Main {

  /**
   * The driver of the lock/canal system:
   *  - create the components for the system; 
   *  - start all of the processes; 
   *  - supervise processes regularly to check all are alive.
   */

  public static void main(String [] args) {
    int n = Param.SECTIONS;

    //generate the lock
    Lock lock = new Lock(0);

    //create an array sec to hold the canal sections
    Section[] sec = new Section[n];

    //generate the individual sections
    for (int i = 0; i < n; i++) {
      sec[i] = new Section(i);
    }

    //generate the producer, the consumer and the operator
    Producer producer = new Producer(lock);
    Consumer consumer = new Consumer(lock);
    Operator operator = new Operator(lock);

    ////create an array sec to hold the tugboats
    Normal_tug[] tug = new Normal_tug[n-1];

    //generate the individual tugboats
    for (int i = 0; i < n-1; i++) {
      tug[i] = new Normal_tug(i,sec[i],sec[i+1]);
      tug[i].start();
    }

    //generate special tugboats that have access to the lock
    Launch_tug launch_tug = new Launch_tug(lock,sec[0]);
    Return_tug return_tug = new Return_tug(sec[n-1],lock);

    //start up all the components
    launch_tug.start();
    return_tug.start();
    producer.start();
    consumer.start();
    operator.start();
    
    //regularly check on the status of threads
    boolean tugs_alive = true;
    for (int i = 0; i < n-1; i++) {
      tugs_alive = tugs_alive && tug[i].isAlive();
    }
    
    
    while (producer.isAlive() && consumer.isAlive()
           && operator.isAlive() && tugs_alive) {
      try {
        Thread.sleep(Param.MAIN_INTERVAL);
      }
      catch (InterruptedException e) {
        System.out.println("Main was interrupted");
        break;
      }
      for (int i = 0; i < n-1; i++) {
        tugs_alive = tugs_alive && tug[i].isAlive();
      }
    }

    //if some thread died, interrupt all other threads and halt
    producer.interrupt();    
    consumer.interrupt();
    operator.interrupt();

    for (int i = 0; i < n-1; i++) {
      tug[i].interrupt();
    }
    launch_tug.interrupt();
    return_tug.interrupt();

    System.out.println("Main terminates, all threads terminated");
    System.exit(0);
  }
}
