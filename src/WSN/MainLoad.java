package WSN;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


/**
 * Created by ksv on 3/23/16.
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