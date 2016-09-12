package menuPlugin;

import WSN.MiningPane;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by ksv on 3/23/16.
 *
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

public class FileMenu extends JMenu implements ActionListener {
    private JMenuItem create;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem saveAs;
    private JMenuItem close;
    private JMenuItem exit;
    private MiningPane handle;
    private JFileChooser fileChooser;
    private File file;
    private BufferedReader in;

    //public static final  ArrayList<String> data = new ArrayList<String>();

    public FileMenu(MiningPane handle) {
        super();
        setText("File");
        setMnemonic(KeyEvent.VK_F);
        this.handle = handle;


        create = new JMenuItem("New", KeyEvent.VK_N);
        create.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

        open = new JMenuItem("Open", KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

        save = new JMenuItem("Save", KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        saveAs = new JMenuItem("Save As", KeyEvent.VK_A);
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        close = new JMenuItem("Close", KeyEvent.VK_W);
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));

        exit = new JMenuItem("Quit", KeyEvent.VK_Q);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        // set  Action commands
        create.setActionCommand("New");
        open.setActionCommand("Open");
        save.setActionCommand("Save");
        saveAs.setActionCommand("SaveAs");
        close.setActionCommand("Close");
        exit.setActionCommand("exit");

        // add action listner
        create.addActionListener(this);
        open.addActionListener(this);
        saveAs.addActionListener(this);
        save.addActionListener(this);
        close.addActionListener(this);
        exit.addActionListener(this);
        // add components to the file menu
        add(create);
        add(open);
        add(save);
        add(saveAs);
        add(close);
        add(exit);
    }

    public void openFile() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv", "xls");
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.showOpenDialog(this.handle);
        file = fileChooser.getSelectedFile();

        if (file != null) {
            close();
            try (FileReader fis = new FileReader(file)) {
                handle.sendStatus(file.getName());
                in = new BufferedReader(fis);
                String line;
                while ((line = in.readLine()) != null) {
                    handle.disp.append(line + "\n");
                    handle.data.add(line);
                }
                if(!(handle.data1.isEmpty()))
                    handle.data1.clear();
            } catch (IOException e) {
                handle.disp.setText("Error!");
                handle.disp.setBackground(Color.RED);
            }
        }
    }

    private void saveFile(File file) {

    }

    private void saveAsFile(File file) {
    }

    private void createFile() {
        handle.sendStatus("create file loaded");
    }

    private void close() {
        handle.disp.setText(new String());
        handle.data.clear();
    }

    public void actionPerformed(ActionEvent e) {
        String param = e.getActionCommand();
        if (param.equals("New"))
            createFile();
        if (param.equals("Open"))
            openFile();
        if (param.equals("Close"))
            close();
        if (param.equals("exit"))
            System.exit(0);
    }
}