package metric;

import nqueens.ChessQueens;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import utilities.Pair;
import utilities.Stats;

public class Launcher {

	private static Pair launchNBError(int nMax, int nbTry, int nbTSIt, int tabuSize){
		
		int nbError = 0;
		double averageError = 0;
		
		long timeCS, timeTS , timeAverageCS, timeAverageTS;
		timeCS = timeTS = timeAverageCS = timeAverageTS = 0;
		
		final XYSeriesCollection errorCollection = new XYSeriesCollection();
		final XYSeriesCollection timeCollection = new XYSeriesCollection();
		
		final XYSeries timeSeriesTS = new XYSeries("Execution time TS");
		final XYSeries timeSeriesCS = new XYSeries("Execution time CS");
		
		final XYSeries errorSeriesTS = new XYSeries("Error TS");
		final XYSeries errorSeriesCS = new XYSeries("Error CS");
		
		long startCS,startTS, endCS, endTS = 0;
		
		for(int i=1;i<=nMax; i++){
			System.out.println("######### n = " + i + "#########");
			nbError = 0;
			
			ChessQueens model = new ChessQueens(i);
			
			startCS = System.currentTimeMillis();	
//			boolean resultCS = model.completeSearch();
			endCS = System.currentTimeMillis();
			
//			if(!resultCS){
//				errorSeriesCS.add(i, 1);
//			}
			
			timeAverageCS = endCS - startCS;
			timeSeriesCS.add(i, timeAverageCS);
			
			for(int j=0; j<nbTry; j++){
				
				startTS = System.currentTimeMillis();	
				boolean resultTS = model.tabuSearch(nbTSIt, tabuSize);
				endTS = System.currentTimeMillis();

				if(!resultTS) nbError ++;
				
				timeTS += endTS - startTS;
					
			}
			timeAverageTS = timeTS / nbTry;
			averageError = nbError / nbTry;
			System.out.println("#error = " + nbError);
			System.out.println("errorAverage = " + averageError);
			System.out.println("#########################");
			errorSeriesTS.add(i, averageError);
			timeSeriesTS.add(i, timeAverageTS);
		}
		errorCollection.addSeries(errorSeriesTS);
		errorCollection.addSeries(errorSeriesCS);
		
		timeCollection.addSeries(timeSeriesTS);
		timeCollection.addSeries(timeSeriesCS);
//		data.addSeries(seriesTimeTS);
		return new Pair(errorCollection, timeCollection);
		
	}
	
	public static void main(String[] args){
		Pair charts = launchNBError(50, 5, 10, 100);
		
		JFreeChart errorChart = Stats.createChart("Error rate", "problem size", "Error", (XYSeriesCollection)charts.getFirst());
		JFreeChart timeChart = Stats.createChart("Execution time", "problem size", "Execution time (ms)", (XYSeriesCollection)charts.getSecond());

		Stats.show(errorChart);
		Stats.show(timeChart);
		//		JFreeChart chart = Stats.createChart("Error rate", "problem size", "error average",launchNBError(3, 2, 5, 1));
//		Stats.show(chart);		
	}

}
