package utilities;

/**
 * This class provides method to manage a triple.
 * @author bmael
 *
 */
public class Pair {
	  private Object o1;
	  private Object o2;
	  
	  public Pair(){};
	  public Pair(Object o1, Object o2) { this.o1 = o1; this.o2 = o2; }
	 
	  public Object getFirst() { return o1; }
	  public Object getSecond() { return o2; }

	  public void setFirst(Object o) { o1 = o; }
	  public void setSecond(Object o) { o2 = o; }
	  
	  public String toString() {
	    return "Pair{" + o1 + ", " + o2 + "}";
	  }
}
