package WSN;

import cluster.clusterer.dbscan.DbScan;
import menuPlugin.MBar;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by ksv on 4/3/16.
 */
public class MiningPane extends JPanel implements TreeSelectionListener{
    public JTextArea disp;
    private JMenuBar mbar;
    private JTree tree;
    private JScrollPane jsp;
    private JScrollPane trs;
    private DefaultMutableTreeNode root;
    private MainLoad handle;
    private DbScan dbScan;

    public ArrayList<String> data = new ArrayList<String>();
    public ArrayList<String> data1 = new ArrayList<>(); //data from database

    MiningPane(MainLoad handle) {
        this.handle = handle;
        this.handle.setTitle("WSN: Knowledge Base");
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        Dimension dim = new Dimension(handle.getWidth(), handle.getHeight());
        setSize(dim);
        mbar = new MBar(this);
        add(mbar, layout.NORTH);

        disp = new JTextArea();
        jsp = new JScrollPane(disp);

        root = new DefaultMutableTreeNode("Analysis Options");
        initNode();
        tree = new JTree(root);
        tree.setPreferredSize(new Dimension(200, this.getHeight()));
        tree.addTreeSelectionListener(this);
        trs = new JScrollPane(tree);

        add(trs, layout.WEST);
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
                    setDbScan ( data );
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

    public void plot(){
        dbScan.plot();
    }

}
