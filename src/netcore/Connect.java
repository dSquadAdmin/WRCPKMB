package netcore;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        System.out.print("\n connecting database");
        connection = DriverManager.getConnection ( dburi, user, password );
        statement = connection.createStatement ();
    }
    public void connect() throws  IOException{
        handle.setMonitor ( "\n starting configurations" );
        sock = new Socket ( "192.168.0.108", 6660 );
        in = sock.getInputStream ();
        out = sock.getOutputStream ();
    }

    public void send(byte[] packet) throws IOException{
        System.out.print ( "\n ssending data " );
        out.write (packet);
        out.flush ();
    }

    private byte[] receive() throws IOException{
        handle.setMonitor ( "\n listening for receiving" );
        final int bufSize = 1024;
        byte [] returnBuf;
        byte buf[] = new byte[bufSize];
        int nBytes = 0, n;
        short lenght = -1;
        try {
            while ((n = in.read(buf, nBytes, bufSize - nBytes)) > 0) {
                nBytes += n;
                if (lenght == -1 && nBytes >= 4) {
                    lenght = (short) (((buf[2] & 0xFF) << 8) | (buf[3] & 0xFF));
                }
                if (nBytes >= lenght && lenght!=-1) {
                    returnBuf = new byte[nBytes];
                    for (int i = 0; i < nBytes; i++)
                        returnBuf[i] = buf[i];
                    return returnBuf;
                }
            }
        } catch (IOException e) {
        }

        return null;
    }


    public void writeT0DataBase(byte[] data ){
        try {
            Statement st = connection.createStatement ( );
            if (connection.getAutoCommit ( )) {
                connection.setAutoCommit ( false );
            }

            byte[] dataset = new byte[data.length - HEADERLEN];

            System.arraycopy(data, HEADERLEN, dataset, 0, data.length-HEADERLEN);
            String string = new String ( dataset );
            System.out.print ( string );

            String sqlUpdate = "INSERT INTO finaldb(tl, ir, rh, temp, moisture) VALUES(?,?,?,?,?)";
            String[] str = string.split ( "," );
            List params = Arrays.asList (
                    Double.parseDouble ( str[0] ),
                    Double.parseDouble ( str[1] ),
                    Double.parseDouble ( str[2] ),
                    Double.parseDouble ( str[3] ),
                    0
                );
            PreparedStatement ps = connection.prepareStatement ( sqlUpdate );
            ps.setObject (1, (Object)params.get ( 0 ) );
            ps.executeUpdate ();
            ps.close ();
            //int numRowsUpdated = updatedb( connection, sqlUpdate, params );
            connection.commit ( );
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }// update data base


    @Override
    public void run(){
        try{
            handle.setMonitor ( "Connecting to database...." );
            dbaseConnect ();
            handle.setMonitor ( "Database Connected ...." );
        }
        catch(SQLException sqlerror){
            handle.setMonitor ( sqlerror.getMessage () );
        }
        catch(ClassNotFoundException cnf){
            handle.setMonitor ( cnf.getMessage () );
        }

        try {
            handle.setMonitor ( "Connecting to Network...." );
            connect ();
            handle.setMonitor ( "Connected ...." );
            handle.setIP ( "192.168.0.1" );
            send ("Hello".getBytes ());
            handle.setMonitor ( "Sending: hello" );
            while(true) {
                byte [] buffdata = receive ();
                if(buffdata!=null){
                    handle.setMonitor ( new String ( buffdata ) );
                    writeT0DataBase ( buffdata );
                }
            }
        }
        catch(IOException excp){
            handle.setMonitor ( "Error! in connection" );
        }
        if(sock!=null) {
            handle.setMonitor ( "connected to network" );
            handle.setIP (sock.getInetAddress ().toString ());
            handle.setStatus ( "Online" );
        }

    } // run

    public void start ()
    {

        if (thread == null)
        {
            System.out.print ( "Starting thread\n" );
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
