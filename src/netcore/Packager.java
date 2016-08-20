package netcore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ksv on 4/24/16.
 */
public class Packager {
    public int CON_REQ = 0x00;
    public int CON_ACK = 0x01;
    public int DEV_REQ = 0x02;
    public int DB_REQ = 0x03;
    public int DENIED = 0x04;
    private byte[] hash = null;
    private MessageDigest digest;


    Packager() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("MD5");
    }

    public byte[] hashDigest(byte[] data) {
        hash = digest.digest(data);
        return hash;
    }
}
