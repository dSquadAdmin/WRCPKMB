package netcore;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;


/**
 * Created by ksv on 4/24/16.
 */
public class Connection {
    BufferedInputStream input;
    BufferedOutputStream output;
    private String[] fileList;
    private Socket sock;
    private int port;
    private byte[] buff;

    public Connection() {
    }

    public Socket connect(Inet4Address ip, int port) throws Exception {
        this.port = port;
        if ((sock = new Socket(ip, this.port)) != null) {
            return sock;
        } else {
            return null;
        }
    }

    public BufferedInputStream getInputStream() throws IOException {
        InputStream in = sock.getInputStream();
        input = new BufferedInputStream(in);
        return input;
    }

    public BufferedOutputStream getOutputStream() throws IOException {
        OutputStream out = sock.getOutputStream();
        output = new BufferedOutputStream(out);
        return output;
    }

    public String[] getFileList() throws IOException {
        String[] str = null;
        return str;
    }
}
