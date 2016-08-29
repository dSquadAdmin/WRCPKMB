package cluster.plots;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ksv on 8/17/16.
 */
public class PlotBarGraph extends ApplicationFrame{
    public PlotBarGraph(String applicationTitle, String chartTitle, ArrayList<String> keys, HashMap<String, Integer> values){
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart (chartTitle,
                "Data Class",
                "Instances",
                createDataset(keys, values),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel ( barChart );
        chartPanel.setPreferredSize ( new Dimension ( 560, 367 ) );
        setContentPane ( chartPanel );
    }

    private CategoryDataset createDataset(ArrayList<String> keys, HashMap<String, Integer> values){
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for(String key : keys){
            dataset.addValue(values.get(key), key, key);
        }

        return dataset;
    }
}
