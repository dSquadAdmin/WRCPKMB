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

    Connect ct;
    //Monitor
    private JTextArea monitor;
    private JButton cnct, config, dicnct, infobtn;

    public NodePane(String msg){
        super();
        setToolTipText(msg);
        setLayout(new GridLayout(1, 2));
        setPreferredSize(new Dimension(400, this.getHeight()));
        setBorder(BorderFactory.createTitledBorder(msg));
        JPanel pane1 = new JPanel (  );
        pane1.setLayout ( new GridLayout( 3, 1) );

        monitor = new JTextArea();
        monitor.setBackground(Color.BLACK);
        monitor.setForeground(Color.GREEN);
        monitor.setCaretColor(Color.GREEN);
        monitor.setFont(new Font("san-serif", Font.PLAIN, 14));
        JScrollPane msp = new JScrollPane(monitor);
        msp.setBorder(BorderFactory.createTitledBorder("Monitor"));
        pane1.add(msp);

        JPanel info = new JPanel();
        info.setLayout(new GridLayout(4, 1));
        info.setBorder(BorderFactory.createTitledBorder("INFO"));
        status = new JTextField ("STATUS : OFF LINE");
        IP = new JTextField ("Address : N/A");
        info.add(status);
        info.add(IP);
        pane1.add(info);

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
        pane1.add(btnpane);


        add ( pane1);

        JPanel pane2 = new JPanel (  );
        pane2.setBorder ( BorderFactory.createTitledBorder ( "Data Plots" ) );
        add ( pane2 );
    }

    public void setMonitor(String msg){
        monitor.append ( msg +"\n" );
    }
    public void setStatus(String msg){
        this.status.setText ( msg );
    }

    public void setIP(String msg){
        if(msg!=null) {
            this.host = msg;
            IP.setText ( "IP Address: " + msg );
        }
    }




    /**
     * Action Listner code
     * This method is for grabing the informations and network configurations
     */

    @Override
    public void actionPerformed(ActionEvent event){
        String cmd = event.getActionCommand ();

        if(cmd.equals ( "connect")){
            ct = new Connect ( "hello" , this);
            ct.start ();
        }

        if(cmd.equals ( "config" )){
            this.IP.setText ( "IP: 127.0.0.1" );
            this.status.setText ( "Status: Online" );
        }
    }

}
