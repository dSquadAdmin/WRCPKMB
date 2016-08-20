package netcore;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ksv on 4/24/16.
 */
public class NodePane extends JPanel {

    private JLabel status;
    private JLabel IP;
    // Data attributes
    private float rain;
    private float tempEnv;
    private float tempSoil;
    private float ph;
    private float humidity;

    // Network configuration
    private String host = null;
    private int port;

    //Monitor
    private JTextArea monitor;
    private JButton cnct, config, dicnct, infobtn;
    private JLabel icon;


    public NodePane(String msg) {
        super();
        setToolTipText(msg);
        setLayout(new GridLayout(3, 1));
        setPreferredSize(new Dimension(400, this.getHeight()));
        setBorder(BorderFactory.createTitledBorder(msg));

        monitor = new JTextArea();
        monitor.setBackground(Color.BLACK);
        monitor.setForeground(Color.GREEN);
        monitor.setCaretColor(Color.GREEN);
        monitor.setFont(new Font("san-serif", Font.PLAIN, 14));
        JScrollPane msp = new JScrollPane(monitor);
        msp.setBorder(BorderFactory.createTitledBorder("Monitor"));
        add(msp);

        JPanel info = new JPanel();
        info.setLayout(new GridLayout(4, 1));
        info.setBorder(BorderFactory.createTitledBorder("INFO"));
        JLabel status = new JLabel("STATUS : OFF LINE");
        JLabel IP = new JLabel("Address : N/A");
        info.add(status);
        info.add(IP);
        add(info);

        JPanel btnpane = new JPanel();
        btnpane.setLayout(new GridLayout(4, 3));
        btnpane.setBorder(BorderFactory.createTitledBorder("Config Board"));
        cnct = new JButton("Connect");
        config = new JButton("Configure");
        dicnct = new JButton("Terminate");
        infobtn = new JButton("Grab Info");
        btnpane.add(cnct);
        btnpane.add(config);
        btnpane.add(infobtn);
        btnpane.add(dicnct);
        add(btnpane);
    }

}
