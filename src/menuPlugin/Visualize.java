package menuPlugin;

import WSN.MiningPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ksv on 8/17/16.
 */
public class Visualize extends JMenu implements ActionListener {
    private MiningPane handle;
    private JMenuItem data;
    private JMenuItem clusters;
    public Visualize(JPanel handle){
        super("Visualize");
        this.handle = (MiningPane)handle;

        data = new JMenuItem ( "Data Set" );
        data.setActionCommand ( "dataset" );
        data.addActionListener ( this );
        clusters = new JMenuItem ( "Clusters" );
        clusters.setActionCommand ( "clusters" );
        clusters.addActionListener ( this );

        this.add ( data );
        this.add ( clusters );
    }


    @Override
    public void actionPerformed(ActionEvent event){
        if(event.getActionCommand ().equals ( "dataset" )){
            handle.plot ();
        }

        if(event.getActionCommand ().equals ( "clusters" )){
            handle.plotBarGraphClusters ();
        }
    }


    public void plotDataSet( ){
    }
}
