package nqueens;

import java.util.Random;

import tabusearch.Move;
import tabusearch.Neighborhood;
import tabusearch.TabuList;
import utilities.Triple;
import JaCoP.constraints.Alldifferent;
import JaCoP.constraints.XplusCeqZ;
import JaCoP.core.IntDomain;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.core.ValueEnumeration;
import JaCoP.search.DepthFirstSearch;
import JaCoP.search.IndomainMedian;
import JaCoP.search.SelectChoicePoint;
import JaCoP.search.SimpleSelect;
import JaCoP.search.SmallestDomain;

public class ChessQueens {
	private Store store;
	private IntVar[] Q;		// main variables: Q[i] represents the column of the queen on the i-th row 
	
	public ChessQueens(int n) {
		store = new Store();
		Q = new IntVar[n];
		IntVar[] y = new IntVar[n];
		IntVar[] z = new IntVar[n];

		for (int i=0; i<n; ++i) {
			Q[i] = new IntVar(store,"Q" + i,0,n-1);
			y[i] = new IntVar(store,"y" + i,-i,n-1-i);
			z[i] = new IntVar(store,"z" + i,i,n-1+i);
			
			store.impose(new XplusCeqZ(Q[i],i,z[i]));
			store.impose(new XplusCeqZ(y[i],i,Q[i]));
		}

		// all different: no attack on columns
		store.impose(new Alldifferent(Q));
		store.impose(new Alldifferent(y));
		store.impose(new Alldifferent(z));
	}
	
	// Get the domains of the main variables
	public IntDomain[] getDomains() {
		IntDomain[] tab = new IntDomain[Q.length];
		for (int i=0; i<Q.length; ++i) {
			tab[i] = Q[i].domain;
		}
		return tab;
	}
	
	// Generate randomly a solution within the domains
	public int[] generateSolution(IntDomain[] domains) {
		Random rand = new Random();
		int[] solution = new int[domains.length];
		
		for (int i=0; i<domains.length; ++i) {
			ValueEnumeration values = domains[i].valueEnumeration();
			int r = rand.nextInt(domains[i].getSize());   // 0 .. getSize()-1

			for (int j=0; j<=r; ++j) {
				solution[i] = values.nextElement();  // only the r-th is relevant
			}
		}
		
		return solution;
	}
	
	// Cost or fitness of an alldifferent constraint
	public int costAllDifferent(int[] sol) {
		int n = 0;
		for (int i=0; i<sol.length; ++i) {
			for (int j=i+1; j<sol.length; ++j) {
				if (sol[i] == sol[j]) ++n;
			}
		}
		return n;
	}
	
	// Fitness of a solution for the n-queens problem
	public int fitness(int[] sol) {
		int n = 0;

		// allDifferent on Q
		n += costAllDifferent(sol);

		// allDifferent on y
		int[] aux = new int[sol.length];
		for (int i=0; i<sol.length; ++i) {
			aux[i] = sol[i] + i;
		}
		n += costAllDifferent(aux);

		// allDifferent on z
		for (int i=0; i<sol.length; ++i) {
			aux[i] = sol[i] - i;
		}
		n += costAllDifferent(aux);
		
		return n;
	}
	
	// Display a solution
	public static void printSolution(int[] sol) {
		System.out.print("{");
		for (int i=0; i<sol.length; ++i) {
			if (i!=0) System.out.print(", ");
			System.out.print(sol[i]);
		}
		System.out.print("}");
	}
	
	/**
	 * Solves the problem. Based on the Tabu Search
	 * @param itMax the number of maximum iterations to do 
	 * @param tabuSize the size of the tabulist.
	 * @return
	 */
	public boolean tabuSearch(int itMax, int tabuSize) {
		
		// Generate a first solution
		IntDomain[] domains = getDomains();
		int[] currentSolution = generateSolution(domains);
        int[] bestSolution = currentSolution; //best known solution
        
		// Calculate the cost of the current solution
		int currentCost = fitness(currentSolution);
        int bestCost = currentCost; 

        TabuList tl = new TabuList(tabuSize);
        System.out.print("Starting solution : ");
		printSolution(currentSolution);
		System.out.println("\nfitness : " + bestCost);
        
        int k = 0; // Iteration number

        while(k < itMax && currentCost > 0 ){
        	k++;
    		
        	Neighborhood n = new Neighborhood(currentSolution, domains); 
//        	try{
        		n.determineNeighborhood();	//computes candidate solution.
//        	}catch(Exception e){
//        		return false;
//        	}
        	n.reduceNeighborhood(tl);
        	
        	// Retrieving the best Neighborhood
        	Triple neighborBestSolution = n.getBestCandidateSolution();
    		currentSolution = (int[])neighborBestSolution.getSecond();
    		currentCost = (int)neighborBestSolution.getThird();
        	tl.add((Move)neighborBestSolution.getFirst());
        	
        	if(currentCost < bestCost){
        		bestSolution = currentSolution;
    			bestCost = currentCost;
        	}
        	  	
        }
        System.out.print("Final solution is ");
		printSolution(bestSolution);
		System.out.println("");
		
		return bestCost == 0;
	}
	
	
	public boolean completeSearch() {
		DepthFirstSearch<IntVar> search = new DepthFirstSearch<IntVar>();

		search.getSolutionListener().searchAll(true);
		search.getSolutionListener().recordSolutions(true);

		SelectChoicePoint<IntVar> select =
				new SimpleSelect<IntVar>(Q,
										 new SmallestDomain<IntVar>(),
										 new IndomainMedian<IntVar>());

		boolean result = search.labeling(store, select);

		for (int i=1; i<=search.getSolutionListener().solutionsNo(); i++){
			System.out.print("Solution " + i + ": [");
			for (int j=0; j<search.getSolution(i).length; j++) {
				if (j!=0) System.out.print(", ");
			    System.out.print(search.getSolution(i)[j]);
			}
			System.out.println("]");
		}
		
		return result;
	}

	public static void main(String[] args) {
		
	}

}
