import WSN.MainLoad;

import javax.swing.*;

/**
 * Created by ksv on 8/23/16.
 */
public class Main {
    public static void main(String args[]) {
        SwingUtilities.invokeLater ( new Runnable ( ) {
            @Override
            public void run() {
                new MainLoad ( "WSN MONITOR" );
            }
        } );
    }

}
