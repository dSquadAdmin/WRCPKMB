package WSN;

import netcore.NodePane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ksv on 4/3/16.
 */

public class NetworkPane extends JPanel{

    private MainLoad mainLoad;
    private NodePane node1, node2, node3;

    NetworkPane(MainLoad mainLoad) {
        setLayout(new GridLayout(1, 3));
        this.mainLoad = mainLoad;
        this.mainLoad.setTitle("WSN: Network Monitor");

        node1 = new NodePane("Node 1");
        node2 = new NodePane("Node 2");
        node3 = new NodePane("Node 3");

        add(node1);
        add(node2);
        add(node3);
    }
}
