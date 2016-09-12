package WSN;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;


/**
 * Created by ksv on 4/21/16.
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
