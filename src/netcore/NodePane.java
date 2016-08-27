package netcore;

import WSN.NetworkPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by ksv on 4/24/16.
 */
public class NodePane extends JPanel implements ActionListener{

    private JTextField status;
    private JTextField IP;
    // Data attributes
    private float rain;
    private float tempEnv;
    private float tempSoil;
    private float ph;
    private float humidity;

    // Network configuration
    private String host = null;
    private int port;
    private Socket socket = null;

    //Monitor
    private JTextArea monitor;
    private JButton cnct, config, dicnct, infobtn;
    private JLabel icon;

    public NodePane(String msg){
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
        status = new JTextField ("STATUS : OFF LINE");
        IP = new JTextField ("Address : N/A");
        info.add(status);
        info.add(IP);
        add(info);

        JPanel btnpane = new JPanel();
        btnpane.setLayout(new GridLayout(4, 3));
        btnpane.setBorder(BorderFactory.createTitledBorder("Config Board"));

        // connect button
        cnct = new JButton("Connect");
        cnct.setActionCommand ( "connect" );
        cnct.addActionListener ( this );

        // configuratiions button
        config = new JButton("Configure");
        config.setActionCommand ( "config" );
        config.addActionListener ( this );

        // Remote network shutdown
        dicnct = new JButton("Terminate");
        dicnct.setActionCommand ( "shutdown" );
        dicnct.addActionListener ( this );

        // infromation about data
        infobtn = new JButton("Grab Info");
        infobtn.setActionCommand ( "Net Info" );
        infobtn.addActionListener ( this );

        btnpane.add(cnct);
        btnpane.add(config);
        btnpane.add(infobtn);
        btnpane.add(dicnct);
        add(btnpane);
    }

    public void setMonitor(String msg){
        monitor.append ( msg +"\n" );
    }

    public void setIP(String msg){
        IP.setText ( "127.0.0.1" );
        status.setText ( "Online" );
    }




    /**
     * Action Listner code
     * This method is for grabing the informations and network configurations
     */

    @Override
    public void actionPerformed(ActionEvent event){
        String cmd = event.getActionCommand ();

        if(cmd.equals ( "connect")){
            Connect ct = new Connect ( "hello" , this);
            ct.start ();
        }

        if(cmd.equals ( "config" )){
            this.IP.setText ( "IP: 127.0.0.1" );
            this.status.setText ( "Status: Online" );
        }
    }

}
