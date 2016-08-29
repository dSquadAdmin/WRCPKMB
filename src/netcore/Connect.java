package netcore;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ksv on 8/27/16.
 */
public class Connect implements Runnable {
    private Thread thread;
    private String name;
    private NodePane handle;

    private Socket sock = null;
    private InputStream in;
    private OutputStream out;
    private byte[] databytes;


    private static final int HEADERLEN = 4;
    private static final int DATA = 0x01;
    private static final int ENERGY = 0x02;
    private static final int ROUTE = 0x03;
    private static final String dburi = "jdbc:postgresql://localhost:5432/dbase";
    private static final String user = "adminksv";
    private static final String password = "adminroot";

    // database information
    private Connection connection = null;
    private Statement statement = null;
    public  Connect(String name, NodePane handle){
        this.name = name;
        this.handle = handle;
    }


    public void dbaseConnect() throws SQLException, ClassNotFoundException{
        connection = DriverManager.getConnection ( dburi, user, password );
        statement = connection.createStatement ();
    }
    private void connect() throws  IOException{
        sock = new Socket ( "10.42.0.126", 6660 );
        in = sock.getInputStream ();
        out =  sock.getOutputStream();
    }

    private void send(byte[] packet) throws IOException{
        out.write ( packet);
        out.flush ();
    }

    private void receive() throws IOException{
        byte[] header = new byte[1024];
        int numrec = in.read(header);
        handle.setMonitor ( new String ( header ) );
        /*
        if(numrec<HEADERLEN){
            handle.setMonitor ( "Brocken Packet ............. \n Discarding packet.........." );
            return;
        }
        int type = new Byte(header[0]).intValue ();

        int length = (short) (((header[2] & 0xFF) << 8) | (header[3] & 0xFF));
        this.databytes = new byte[length-HEADERLEN];
        int rec = HEADERLEN;
        while (rec<length-HEADERLEN){
            numrec = in.read ( databytes, rec, length-rec );
            if((numrec!= -1))
                return;
        }

        if(type==DATA){

        }
    */
    }



    @Override
    public void run(){
        Socket sock = null;
        try {
            connect ();
            handle.setMonitor ( "Connected ...." );
            handle.setIP ( "192.168.0.1" );
            send ("Hello".getBytes ());
            receive ();
            sock.close ();

        }
        catch(IOException excp){
            handle.setMonitor ( excp.getMessage () );
            this.stop ();
        }
        try{
            dbaseConnect ();
            handle.setMonitor ( "Connected ...." );
        }
        catch(SQLException sqlerror){
            handle.setMonitor ( sqlerror.getMessage () );
        }
        catch(ClassNotFoundException cnf){
            handle.setMonitor ( cnf.getMessage () );
        }
        finally {
            this.stop ();
        }

        if(sock!=null) {
            handle.setMonitor ( "connected to network" );
            handle.setIP (sock.getInetAddress ().toString ());
            handle.setStatus ( "Online" );
        }

    }

    public void start ()
    {

        if (thread == null)
        {
            thread = new Thread (this, name);
            thread.start ();
        }
    }

    public void stop(){
        try{
            in.close ();
            out.flush ();
            out.close ();
            sock.close ();

            statement.close ();
            connection.close ();
        }
        catch (IOException e){
            handle.setMonitor ( "Connection Terminated ............." );
            handle.setIP ( "N/A" );
            handle.setStatus("Offline");
        }
        catch (SQLException ex){
            handle.setMonitor ( "SQL Error .............. " );
        }
        this.start ();
    }
}
