package netcore;


import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Arrays;


/**
 * Created by ksv on 8/27/16.
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
public class Connect implements Runnable {
    private Thread thread;
    private String name;
    volatile boolean shut = false;
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
        sock = new Socket ( "127.0.0.1", 6660 );
        in = sock.getInputStream ();
        out = sock.getOutputStream ();
    }

    public void send(byte[] packet) throws IOException{
        System.out.print ( "\n sending " );
        out.write (packet);
        out.flush ();
    }

    private byte[] receive() throws IOException{
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

            String string = new String ( data );
            String[] str = string.split ( "," );

            double val1 = Double.parseDouble ( str[0] );
            double val2 = Double.parseDouble ( str[1] );
            double val3 = Double.parseDouble ( str[2] );
            double val4 = Double.parseDouble ( str[3] );
            System.out.print ( val1 );

            String sqlUpdate = "INSERT INTO testlog(tl, ir, rh, temp)"
                    +"VALUES ('"+val1+"','"+val2+"', '"+val3+"','"+val4+"');";
            st.executeUpdate ( sqlUpdate );
            connection.commit ( );
        } catch (SQLException e) {
            System.out.print ( e.getMessage () );
        }
    }// update data base


    @Override
    public void run(){
        try{
            handle.setMonitor ( "Connecting to database" );
            dbaseConnect ();
            handle.setMonitor ( "Ok!\nConnected" );
        }
        catch(SQLException sqlerror){
            handle.setMonitor ( sqlerror.getMessage () );
        }
        catch(ClassNotFoundException cnf){
            handle.setMonitor ( cnf.getMessage () );
        }

        try {
            handle.setMonitor ( "Connecting to Network" );
            connect ();
            handle.setMonitor ( "Ok!\nConnected to network" );
            handle.setIP (sock.getInetAddress ().toString ());
            handle.setStatus ( "Online" );
            send ("Hello".getBytes ());
            handle.setMonitor ( "Sending: hello" );
            while(true) {
                try {
                    byte[] buffdata = receive ( );
                    if(buffdata==null) {
                        break;
                    }
                    int i = (int)buffdata[0];
                    if (i==1) {
                        byte[] datatrunc = Arrays.copyOfRange ( buffdata, 4, buffdata.length );
                        System.out.print ( new String ( datatrunc ) + "\n" );
                        handle.setMonitor ( new String ( datatrunc ) + "\n" );
                        writeT0DataBase ( datatrunc );
                    }
                }
                catch (IOException exp){
                    handle.setMonitor ( "\n"+ exp.getMessage ( ) );
                }
            }
        }
        catch(IOException excp){
            handle.setMonitor ( "Error! in connection" );
        }
        this.stop ();
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
            handle.setMonitor ( "Connection Terminated ............." );
            handle.setIP ( "N/A" );
            handle.setStatus("Offline");
        }
        catch (IOException e){
            handle.setMonitor ( e.getMessage () );
        }
        catch (SQLException ex){
            handle.setMonitor ( "SQL Error .............. " );
        }
        this.start ();
    }
}
