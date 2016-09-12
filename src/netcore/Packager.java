package netcore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ksv on 4/24/16.
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
