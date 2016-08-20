package WSN;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;


/**
 * Created by ksv on 4/21/16.
 */
public class Developer extends JPanel implements TreeSelectionListener {
    private JTree dev;
    private DefaultMutableTreeNode developers;
    private MainLoad handle;
    private JPanel devPane;

    Developer(MainLoad handle) {
        super();
        this.handle = handle;
        this.handle.setTitle("WSN: Developers");
        setLayout(new BorderLayout());
        developers = new DefaultMutableTreeNode("About");
        dev = new JTree(developers);
        dev.setPreferredSize(new Dimension(200, this.getHeight()));
        dev.addTreeSelectionListener(this);
        developTree();
        add(dev, BorderLayout.WEST);
    }

    private void developTree() {
        DefaultMutableTreeNode deepak = new DefaultMutableTreeNode("069/BEX/411");
        DefaultMutableTreeNode kamal = new DefaultMutableTreeNode("069/BEX/416");
        DefaultMutableTreeNode keshav = new DefaultMutableTreeNode("069/BEX/417");
        DefaultMutableTreeNode krishna = new DefaultMutableTreeNode("069/BEX/418");

        this.developers.add(deepak);
        this.developers.add(kamal);
        this.developers.add(keshav);
        this.developers.add(krishna);
    }

    public void valueChanged(TreeSelectionEvent e) {
        TreePath text = e.getPath();
        String name = text.getLastPathComponent().toString();
        handle.setStatuslabel(name);
    }

    private void ksvInfo() {
        ImageIcon im = new ImageIcon("ksv.jpg");
    }
}
