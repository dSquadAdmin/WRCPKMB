package WSN;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


/**
 * Created by ksv on 3/23/16.
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
public class MainLoad extends JFrame implements ChangeListener {
    private JTabbedPane tpane;
    private NetworkPane npane;
    private MiningPane mpane;
    private Developer devpane;
    private JLabel statuslabel;
    private JPanel statuspanel;


    public MainLoad() {
    }

    public MainLoad(String x) {
        setTitle(x);
        setSize(1600, 900);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tpane = new JTabbedPane();
        npane = new NetworkPane(this);
        mpane = new MiningPane(this);
        devpane = new Developer(this);

        tpane.add(npane, "Network");
        tpane.add(mpane, "Knowledge Base");
        tpane.add(devpane, "About");
        tpane.addChangeListener(this);
        add(tpane);

        statuslabel = new JLabel("Welcome!");
        statuslabel.setBackground(Color.WHITE);
        statuslabel.setLayout(new BorderLayout());


        statuspanel = new JPanel();
        statuspanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statuspanel, BorderLayout.SOUTH);
        statuspanel.setPreferredSize(new Dimension(this.getWidth(), 25));
        statuspanel.setLayout(new BoxLayout(statuspanel, BoxLayout.X_AXIS));
        statuspanel.setAlignmentX(SwingConstants.LEFT);
        statuspanel.add(statuslabel);
        setVisible(true);
    }


    public void setStatuslabel(String msg) {
        statuslabel.setText(msg);
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JTabbedPane) {
            int n = tpane.getSelectedIndex();
            if (n == 0) {
                statuslabel.setText("Network");
                setTitle("WSN: Network Monitor");
            }
            if (n == 1) {
                statuslabel.setText("Knowledge Base");
                setTitle("WSN: Knowledge Base");
            }
            if (n == 2) {
                statuslabel.setText("About");
                setTitle("WSN: Developers");
            }
        }
    }

}