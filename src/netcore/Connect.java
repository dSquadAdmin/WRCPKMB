package netcore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by ksv on 8/27/16.
 */
public class Connect implements Runnable {
    private Thread thread;
    private String name;
    private NodePane handle;


    public  Connect(String name, NodePane handle){
        this.name = name;
        this.handle = handle;
    }
    @Override
    public void run(){
        Socket sock = null;
        try {
            sock = new Socket ( "127.0.0.1", 6666);
            BufferedInputStream in = new BufferedInputStream ( sock.getInputStream () );
            BufferedOutputStream out = new BufferedOutputStream ( sock.getOutputStream () );

            out.write ( "HELLO".getBytes () );
            out.flush (  );
            byte[] data = new byte[5];
            int j = in.read ( data );
            if (j!= -1)
                System.out.print ( new String(data) );
            out.close ();
            in.close ();
            sock.close ();
        }
        catch(IOException excp){
            handle.setMonitor ( excp.getMessage () );
        }
        finally {

        }
        if(sock!=null) {
            handle.setMonitor ( "yahoo connected to network" );
            handle.setIP ("IP: 127.0.0.1");
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
}
