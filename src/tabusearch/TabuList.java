package tabusearch;

import java.util.ArrayList;
import java.util.List;

/**
 * A TabuList records forbidden moves. This list have a maximum size, which is an important
 *  parameter of Tabu Search.
 * @author bmael
 *
 */
public class TabuList {
	private int maxSize;
	private List<Move> moves;

	/**
	* Tabulist constructor. Initialize maxsize attribute and the empty Set of Move.
	* @param maxsize The size max of the TabuList
	*/
	public TabuList(int maxsize){
		this.maxSize = maxsize;
		this.moves = new ArrayList<Move>();
	}
	
	/**
	* Adding an element to the TabuList
	* @param move the Move to add to the tabulist
	*/
	public void add(Move move){
		if(!moves.contains(move)){
			if(moves.size() >= maxSize){
				moves.remove(0);
			}
			
			moves.add(move);
		}
	}
	
	/**
	* Returns true if this tabulist contains the specified element.
	*  More formally, returns true if and only if this tabulist contains 
	*  at least one element e such that (o==null ? e==null : o.equals(e)) 
	* @param move The move to identify
	* @return True if the move is contained in the tabulist
	*/
	public boolean isTabu(Move move){
		return this.moves.contains(move);
	}
	
	/**
	 * Tabulist maximum size getter.
	* @return the maxSize
	*/
	public int getMaxSize() {
		return maxSize;
	}
	
	/**
	 * Tabulist maximum size setter.
	* @param maxSize the maxSize to set
	*/
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
