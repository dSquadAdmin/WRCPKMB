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
    private NodePane node1;

    NetworkPane(MainLoad mainLoad) {
        setLayout(new GridLayout());
        this.mainLoad = mainLoad;
        this.mainLoad.setTitle("WSN: Network Monitor");

        node1 = new NodePane("Network");
        add(node1);
    }
}
