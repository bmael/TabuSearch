package utilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;







/**
 * Static class provides methods to compute statistic graph with JFreeChart
 * @author bmael
 */
public class Stats {
    
  public static JFreeChart createChart(String title, String xName, String yName, XYSeriesCollection data){
	  final JFreeChart chart = ChartFactory.createXYLineChart(title,
																xName, 
																yName, 
													            data,
													            PlotOrientation.VERTICAL,
													            true,
													            true,
													            false
													        );
	  return chart;
  }
  
  public static void show(JFreeChart chart){
	  ChartFrame frame=new ChartFrame("Chart",chart);
      frame.pack();
      frame.setVisible(true);
  }
  public static void main(String[] args){
	//create and display a frame...
      ChartFrame frame=new ChartFrame("First",createChart("Testing class Stats", "X axis", "Y axis", null));
      frame.pack();
      frame.setVisible(true);
  }
  
}
