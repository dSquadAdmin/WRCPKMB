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
 *
 * Copyright (c) All right reserved Keshav Bist.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * @Author Keshav Bist <squad.reconn@gmail.com>
 * @URI http://keshavbist.com.np
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
