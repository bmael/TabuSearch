package utilities;

/**
 * This class provides method to manage a triple.
 * @author bmael
 *
 */
public class Triple {
	  private Object o1;
	  private Object o2;
	  private Object o3;
	  
	  public Triple(){};
	  public Triple(Object o1, Object o2, Object o3) { this.o1 = o1; this.o2 = o2; this.o3 = o3; }
	 
	  public Object getFirst() { return o1; }
	  public Object getSecond() { return o2; }
	  public Object getThird() { return o3; }

	  public void setFirst(Object o) { o1 = o; }
	  public void setSecond(Object o) { o2 = o; }
	  public void setThird(Object o) { o3 = o; }
	  
	  public String toString() {
	    return "Triple{" + o1 + ", " + o2 + ", " + o3 + "}";
	  }
}
