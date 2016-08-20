package menuPlugin;

import WSN.MiningPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ksv on 4/3/16.
 */
public class MBar extends JMenuBar {
    private static FlowLayout layout = new FlowLayout(FlowLayout.LEFT);

    public MBar(MiningPane handle) {
        super();
        setLayout(layout);
        FileMenu fm = new FileMenu(handle);
        DataBase em = new DataBase(handle);
        Visualize vz = new Visualize ( handle );

        add(fm);
        add(em);
        add ( vz );
    }
}
