package WSN;

import cluster.clusterer.data.Clusters;
import cluster.clusterer.dbscan.DbScan;
import cluster.plots.CountMap;
import cluster.plots.PlotBarGraph;
import menuPlugin.MBar;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ksv on 4/3/16.
 */
public class MiningPane extends JPanel implements TreeSelectionListener{
    public JTextArea disp;
    public ArrayList<String> data = new ArrayList<> ( );
    public ArrayList<String> data1 = new ArrayList<> ( ); //data from database
    private JMenuBar mbar;
    private JTree tree;
    private JScrollPane jsp;
    private JScrollPane trs;
    private DefaultMutableTreeNode root;
    private MainLoad handle;
    private DbScan dbScan;
    private JPanel layoutpane;

    MiningPane(MainLoad handle) {
        this.handle = handle;
        this.handle.setTitle("WSN: Knowledge Base");
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        Dimension dim = new Dimension(handle.getWidth(), handle.getHeight());
        setSize(dim);
        mbar = new MBar(this);
        add(mbar, layout.NORTH);
        layoutpane = new JPanel ( );
        layoutpane.setLayout ( new GridLayout ( 1, 5 ) );

        disp = new JTextArea();
        jsp = new JScrollPane(disp);
        root = new DefaultMutableTreeNode("Analysis Options");
        initNode();
        tree = new JTree(root);
        tree.setPreferredSize(new Dimension(200, this.getHeight()));
        tree.addTreeSelectionListener(this);
        trs = new JScrollPane(tree);

        add ( trs, BorderLayout.EAST );
        add(jsp, BorderLayout.CENTER);

    }


    void initNode() {
        DefaultMutableTreeNode patternMinig = new DefaultMutableTreeNode("Pattern");
        DefaultMutableTreeNode clustering = new DefaultMutableTreeNode("Clustering");
        root.add(clustering);
        root.add(patternMinig);

        DefaultMutableTreeNode apriori = new DefaultMutableTreeNode("Apriori");
        DefaultMutableTreeNode dbscan = new DefaultMutableTreeNode("DBSCAN");
        patternMinig.add(apriori);
        clustering.add(dbscan);
    }

    public void sendStatus(String msg) {
        handle.setStatuslabel(msg);
    }

    /**
     * Tree expansion and click
     * @param e
     */
    public void valueChanged(TreeSelectionEvent e) {
        TreePath text = e.getPath();
        String name = text.getLastPathComponent().toString();
        handle.setStatuslabel(name);
        switch(name){
            case "DBSCAN":
                if(!data.isEmpty()){
                    setDbScan (  data  );
                }
                else if(!data1.isEmpty ()){
                    setDbScan ( data1 );
                }
                else{
                    JOptionPane.showMessageDialog(this, "Select a data set", "Error", JOptionPane.ERROR_MESSAGE);
                }
            default:
                ;
        }
    }

    /**
     * Methods to initialize Data analysis algorithm
     */
    private void setDbScan(ArrayList<String> set){
        /**
         * call DBSCAN algorithm
         */
        JTextField minRadVal = new JTextField();
        JTextField minPtsVal = new JTextField();
        Object[] message = {
                "Set Minimum Radius (eps):", minRadVal,
                "Minimum Neighbour Points", minPtsVal,
        };
        double rad;
        int npts;
        int option = JOptionPane.showConfirmDialog(this, message, "INSERT DBSCAN PARAMETERS", JOptionPane.OK_CANCEL_OPTION);
        if(option == JOptionPane.OK_OPTION){
            try {
                rad = Double.parseDouble(minRadVal.getText());
                npts = Integer.parseInt(minPtsVal.getText());
                dbScan = new DbScan(set, rad, npts);
            }
            catch(NumberFormatException e){
                System.out.print("\n\n Error in the inputs");
            }
        }
        else{
            dbScan = new DbScan(set, 0.1, 5);
        }
    }

    //Normalize
    public ArrayList<String> normaLize(ArrayList<String> strings){
        ArrayList<String> temp = new ArrayList<> ();
        double[] squaresum = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double[] sum = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0,};
        double[] mean = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0,};
        double[] sd = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0,};
        Integer N = strings.size ();
        System.out.print(N);
        for(String string: strings){
            String[] str = string.split ( "," );
            for(int i= 0; i<6; i++){
                double tmp = Double.parseDouble ( str[i] );
                sum[i] = sum[i] + tmp;
                squaresum[i] = tmp*tmp;
            }
        }

        for(int i= 0; i<6; i++){
            mean[i] = sum[i]/N;
        }

        for (int i= 0; i<6; i++){
           sd[i] = Math.sqrt ( (squaresum[i]/N) - (mean[i]*mean[i]) );
        }

        for(String string: strings){
            double[] tmp = new double[6];
            String[] arr = string.split ( "," );
            for(int i =0; i < 6; i++){
                if(sd[i] != 0){
                    tmp[i] = (Double.parseDouble ( arr[i])/sd[i]);
                }
                else{
                    tmp[i] =0;
                }
            }
            String str = tmp[0]+","+tmp[1]+","+tmp[2]+","+tmp[3]+","+tmp[4]+","+tmp[5];
            if(arr.length > 6){
                str = str +", "+ arr[6];
            }

            temp.add ( str );
        }

        return temp;
    }  // Normalize the data end




    // plots
    public void plot(){
        //dbScan.plot();
        CountMap countMap = new CountMap ( data );
        ArrayList<String> keys = countMap.getKeys ();
        HashMap<String, Integer> values = (HashMap<String, Integer>) countMap.getCountMaps ();
        PlotBarGraph pg = new PlotBarGraph ( "WRCKB", "Dataset Plot", keys, values );
        pg.pack ();
        RefineryUtilities.centerFrameOnScreen ( pg );
        pg.setVisible ( true );
    }

    // cluster plots
    public void plotBarGraphClusters(){
        if(dbScan!=null) {
            ArrayList<Clusters> clusters = dbScan.getClusters ( );
            ArrayList<String> keys = new ArrayList<> ( );
            HashMap<String, Integer> map = new HashMap<> ( );
            int total = 0;
            for (Clusters c : clusters) { // generate key and map
                total += c.getSize ( );
                if (!(c.isNoise ( ))) {
                    String k = Integer.toString ( c.getId ( ) );
                    keys.add ( k );
                    map.put ( k, c.getSize ( ) );
                }
            }
            keys.add ( "Total" );
            map.put ( "Total", total );

            PlotBarGraph clustergraph = new PlotBarGraph ( "WRCKB", "Cluster Strength", keys, map );
            clustergraph.pack ( );
            RefineryUtilities.centerFrameOnScreen ( clustergraph );
            clustergraph.setVisible ( true );
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Run the DBSCAN ALGORITHM FIRST", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
