package tabusearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nqueens.FitnessComputing;

import utilities.Triple;
import JaCoP.core.IntDomain;
import JaCoP.core.ValueEnumeration;

/**
 * This class provides method to determines the set of adjacent solutions
 * that can be reached from the current solution.
 * @author bmael
 *
 */
public class Neighborhood {
	private int[] solution;
	private IntDomain[] domains;
	private List<Move> moves;
	
	/**
	* Constructor
	* @param solution The solution from which a set of adjacent solutions can be reached
	* @param domain The domain of the current solution
	*/
	public Neighborhood(int[] solution, IntDomain[] domain){
		this.solution = solution;
		this.domains = domain;
		this.moves = new ArrayList<Move>();
	}
	
	/**
	* Determines a new Neighborhood
	*/
	public void determineNeighborhood(){
			
		for (int i=0; i<this.solution.length; i++){
			ValueEnumeration values = this.domains[i].valueEnumeration();
			
			for (int j=0; j<this.domains[i].getSize();j++){
				int valueElem = values.nextElement();
				
				//We keep only values which are different to the solution
				if(valueElem != this.solution[i]){
					Move move = new Move(i, valueElem);
					this.moves.add(move);
				}
			}
		}
	}
	
	/**
	* This method allow to reduce the current neighborhood, deleting elements which are 
	* also contained in the TabuList.
	* @param tabuList TabuList which contains wrong/forbidden moves
	*/
	public void reduceNeighborhood(TabuList tabuList){
		Iterator<Move> it = moves.iterator();
		
		while(it.hasNext()){
			Move neighbour = it.next();
			if(tabuList.isTabu(neighbour)){
				it.remove();
			}
		}
	}
	
	/**
	 * Returns a Triple containing {bestMove, bestCandidateSolution, bestCandidateSolutionCost}
	 * @return res a Triple containing {bestMove, bestCandidateSolution, bestCandidateSolutionCost}.
	 */
	public Triple getBestCandidateSolution(){
		Triple res = new Triple();
		Move bestMove = null;
		int[] bestCandidateSolution = null;
		int bestCandidateSolutionCost = Integer.MAX_VALUE;
		
		for(Move m : moves){
			int [] candidateSolution = new int[solution.length];
			System.arraycopy(solution, 0, candidateSolution, 0, solution.length);
			candidateSolution[m.getVariable()] = m.getValue();
			int currentCost = FitnessComputing.fitness(candidateSolution);
			
			if( currentCost < bestCandidateSolutionCost ){
				bestCandidateSolution = candidateSolution;
				bestCandidateSolutionCost = currentCost;
				bestMove = m;
			}
			
		}
		
		res.setFirst(bestMove);
		res.setSecond(bestCandidateSolution);
		res.setThird(bestCandidateSolutionCost);
		
		return res;
	}
	
}
