/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tabusearchjacop;

import JaCoP.core.IntDomain;
import randomsolution.ChessQueens;

/**
 *
 * @author mael
 */
public class TSAlgorithm {
    
    
    
    public static void basicTSAlgorithm(int nbIteration){
        ChessQueens cq = new ChessQueens(10);
        
        IntDomain[] domains = cq.getDomains();
        
        //Initial solution
        int[] s = cq.generateSolution(domains);
        
        //best known solution
        int[] s2 = s;
        
        // Iteration number
        int k = 0;
        
        while(k != nbIteration){
        	k++;
        	
        	int[][] candidates = null;
        	
        	
        	
        }
            
    }
    
}
