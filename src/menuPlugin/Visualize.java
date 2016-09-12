package menuPlugin;

import WSN.MiningPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ksv on 8/17/16.
 * ======================================================================================
 * This Class is to implement the data base related tasks
 * ======================================================================================
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
