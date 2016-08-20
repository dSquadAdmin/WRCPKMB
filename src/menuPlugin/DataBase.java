package menuPlugin;

import WSN.MiningPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ksv on 4/3/16.
 */
public class DataBase extends JMenu implements ActionListener {
    public MiningPane handle;
    private JMenuItem open;
    private JMenuItem update;
    private JMenuItem connect;
    private JMenuItem close;
    private Connection con;
    private Statement stmt;


    public DataBase(MiningPane handle) {
        super();
        con = null;
        stmt = null;
        setText("Database");
        setMnemonic(KeyEvent.VK_B);
        this.handle = handle;

        connect = new JMenuItem("Connect");
        update = new JMenuItem("Update");
        open = new JMenuItem("Browse");
        close = new JMenuItem("Close");

        //set action commands
        connect.setActionCommand("connect");
        update.setActionCommand("update");
        open.setActionCommand("browse");
        close.setActionCommand("close");

        //menu listner;
        connect.addActionListener(this);
        update.addActionListener(this);
        open.addActionListener(this);
        close.addActionListener(this);

        //Add menus
        add(connect);
        add(open);
        add(update);
        add(close);
    }

    public static int update(Connection connection, String sql, List<Object> parameters) throws SQLException {
        int numRowsUpdated = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement ( sql );

            int i = 0;
            for (Object parameter : parameters) {
                ps.setObject ( ++i, parameter );
            }
            numRowsUpdated = ps.executeUpdate ( );
        } finally {
            ps.close ( );
        }
        return numRowsUpdated;
    } /** End of the connect data base*/

    /**
     * Method to connect to the database
     */
    public void connectDatabase() {

        JTextField addr = new JTextField();
        JTextField user = new JTextField();
        JTextField database = new JTextField();
        JPasswordField pass = new JPasswordField();
        Object[] message = {
                "jdbc://", addr,
                "Database:", database,
                "USER ID: ", user,
                "PASSWORD: ", pass,
        };

        int option = JOptionPane.showConfirmDialog(this,
                message,
                "Enter database Informations",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {

            String ADDR = addr.getText();
            String DB = database.getText();
            String USER = user.getText();
            char[] pswd = pass.getPassword();
            String PASS = new String(pswd);

            String fulladdr = "jdbc:postgresql://" + ADDR + "/" + DB;
            try {
                this.con = DriverManager.getConnection(fulladdr, USER, PASS);
                handle.sendStatus ( "Connected to database ....." );
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(this,
                        "Sorry! Connection cannot be established",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /**
     *  Method to browse database
     */
    public void browseDatabase(){

        try  {
            if(!handle.data1.isEmpty ())
                handle.data1.clear ();
            ArrayList<String> tableList = new ArrayList<>();
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "%", new String[] { "TABLE" });
            while (tables.next()) {
                String S = tables.getString("TABLE_NAME");
                tableList.add(S);
            }

            Object[] o = tableList.toArray();
            //Object[] possibilities = {"ham", "spam", "yam"};
            String s = (String)JOptionPane.showInputDialog(
                    this,
                    "Dataset",
                    "Select Data",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    o,
                    o[0].toString()
            );
            if(s!=null && s.length()>0){
                handle.sendStatus ( s );
                stmt = con.createStatement();
                String query = "SELECT * FROM "+s;
                handle.disp.setText ( "" );
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next ()){
                    String instance = rs.getString ( 1 )+","+rs.getArray ( 2 )+","+rs.getArray ( 3 )+","+rs.getArray ( 4 )+","+rs.getArray ( 5 )+","+rs.getArray ( 6 )+","+rs.getArray ( 7 )+","+rs.getArray ( 8 );
                    handle.disp.append ( instance +"\n" );
                    handle.data1.add ( instance );
                }
            }
        }
        catch(Exception e){
            showError ( );
        }
    }/*End of browse database*/

    /**
     * Insert data in the data base
     */
    public void updateDatabase(){
        if(!(handle.data.isEmpty())){
            try {
                Statement st = con.createStatement ( );
                if (con.getAutoCommit ( )) {
                    con.setAutoCommit ( false );
                }

                ArrayList<String> tableList = new ArrayList<> ( );
                DatabaseMetaData dbmd = con.getMetaData ( );
                ResultSet tables = dbmd.getTables ( null, null, "%", new String[]{"TABLE"} );
                while (tables.next ( )) {
                    String S = tables.getString ( "TABLE_NAME" );
                    tableList.add ( S );
                }

                Object[] o = tableList.toArray ( );
                //Object[] possibilities = {"ham", "spam", "yam"};
                String s = (String) JOptionPane.showInputDialog (
                        this,
                        "Dataset",
                        "Select Data",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        o,
                        o[0].toString ( )
                );

                String db = (s != null && s.length ( ) > 0) ? s : "test_db";
                handle.sendStatus ( db );


                String sqlUpdate = "INSERT INTO " + db + "(c1, c2, c3, c4, c5, c6, c7) VALUES(?,?,?,?,?,?,?)";
                for (String data : handle.data) {
                    String[] dataset = data.split ( "," );
                    List params = Arrays.asList ( Double.parseDouble ( dataset[0] ),
                            Double.parseDouble ( dataset[1] ),
                            Double.parseDouble ( dataset[2] ),
                            Double.parseDouble ( dataset[3] ),
                            Double.parseDouble ( dataset[4] ),
                            Double.parseDouble ( dataset[5] ),
                            dataset[6] );
                    int numRowsUpdated = update ( con, sqlUpdate, params );
                    con.commit ( );
                    /*
                    String query_st = "INSERT INTO test_db(c1, c2, c3, c4, c5, c6, c7) VALUES ('"+Double.parseDouble ( dataset[0] )+ "', '"+
                            Double.parseDouble ( dataset[1] )+
                            "', "+dataset[2]+
                            "', "+dataset[3]+
                            "', "+dataset[4]+
                            "', "+dataset[5]+
                            "', "+dataset[6]+");";
                    st.execute ( query_st );
                    */
                }
                st.close ( );
            } catch (SQLException e) {
                System.out.print ( e.getMessage ( ) );
                showError ( );
            }
        }
    } /* End of Database syncing*/

    public void closeDataBase(){
        try{
            this.con.close();
        }
        catch(Exception e)
        {
            showError ( );
        }
    }

    public void showError() {
        JOptionPane.showMessageDialog ( this,
                "The connection is either closed or is null!\nCannot retreive any data",
                "Error!",
                JOptionPane.ERROR_MESSAGE );
        ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JComboBox){
            System.out.print(((JComboBox) e.getSource()).getSelectedItem());
        }
        else {
            String p = e.getActionCommand();

            if (p.equals("connect")) {
                connectDatabase();
            }

            if (p.equals("update")) {
                updateDatabase();
            }

            if (p.equals("browse")) {
                browseDatabase();
            }

            if (p.equals("close")) {
                closeDataBase();
            }
        }

    }

}
