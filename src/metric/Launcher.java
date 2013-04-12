package metric;

import nqueens.ChessQueens;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import utilities.Pair;
import utilities.Stats;

public class Launcher {

	private static Pair launchNBError(int nMax, int nbTry, int nbTSIt, int tabuSize){
		
		/* Variables for errors */
		int nbErrorTS, nbErrorCS;
		nbErrorTS = nbErrorCS = 0;
		double averageErrorTS, averageErrorCS;
		averageErrorTS = averageErrorCS = 0;
		
		/* Variables for execution time */
		long startCS,startTS, endCS, endTS;
		startCS = startTS = endCS = endTS = 0;
		long timeCS, timeTS , timeAverageCS, timeAverageTS;
		timeCS = timeTS = timeAverageCS = timeAverageTS = 0;
		
		/* Collections for charts */
		final XYSeriesCollection errorCollection = new XYSeriesCollection();
		final XYSeriesCollection timeCollection = new XYSeriesCollection();
		
		/* Series for execution time chart */
		final XYSeries timeSeriesTS = new XYSeries("Execution time TS");
		final XYSeries timeSeriesCS = new XYSeries("Execution time CS");
		
		/* Series for error chart */
		final XYSeries errorSeriesTS = new XYSeries("Error TS");
		final XYSeries errorSeriesCS = new XYSeries("Error CS");
		
		for(int i=1;i<=nMax; i++){
			System.out.println("######### n = " + i + "#########");

			nbErrorTS = nbErrorCS = 0;
			
			ChessQueens model = new ChessQueens(i);
			
			for(int j=0; j<nbTry; j++){
				
				startCS = System.currentTimeMillis();	
				boolean resultCS = model.completeSearch(false); // Stopped when a first solution is found.
				endCS = System.currentTimeMillis();
				
				if(!resultCS) nbErrorCS++;
				
				timeCS += endCS - startCS;
				
				startTS = System.currentTimeMillis();	
				boolean resultTS = model.tabuSearch(nbTSIt, tabuSize);
				endTS = System.currentTimeMillis();

				if(!resultTS) nbErrorTS ++;
				
				timeTS += endTS - startTS;
					
			}

			timeAverageCS = timeCS / nbTry;
			averageErrorCS = nbErrorCS / nbTry;
			System.out.println("--------- CS --------");
			System.out.println("#error = " + nbErrorCS);
			System.out.println("errorAverage = " + averageErrorCS);
			errorSeriesCS.add(i, averageErrorCS);
			timeSeriesCS.add(i, timeAverageCS);

			
			timeAverageTS = timeTS / nbTry;
			averageErrorTS = nbErrorTS / nbTry;
			System.out.println("--------- TS --------");
			System.out.println("#error = " + nbErrorTS);
			System.out.println("errorAverage = " + averageErrorTS);
			System.out.println("#########################");
			errorSeriesTS.add(i, averageErrorTS);
			timeSeriesTS.add(i, timeAverageTS);
		}
		errorCollection.addSeries(errorSeriesTS);
		errorCollection.addSeries(errorSeriesCS);
		
		timeCollection.addSeries(timeSeriesTS);
		timeCollection.addSeries(timeSeriesCS);

		return new Pair(errorCollection, timeCollection);
		
	}
	
	public static void main(String[] args){
		Pair charts = launchNBError(70, 10, 10, 100); //Set up the experimentation
		
		JFreeChart errorChart = Stats.createChart("Error rate", "problem size", "Error", (XYSeriesCollection)charts.getFirst());
		JFreeChart timeChart = Stats.createChart("Execution time", "problem size", "Execution time (ms)", (XYSeriesCollection)charts.getSecond());

		Stats.show(errorChart);
		Stats.show(timeChart);

	}

}
