package za.ac.cput;

/* CompanyView.java
 * View company data using JTable
 * Author: Nolusindiso Makosa (219023557)
 * Date: 29 May 2021
 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Company extends JFrame {
    private JPanel panelNorth;
    private JPanel panelSouth;
    private JLabel lblHeading;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private DefaultTableCellRenderer cellRenderer;
    private static Connection connect = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static String url = "jdbc:mysql://localhost:3306/connect_mysql_database";
    private static String user = "root", pass = "";

    public Company() {
        panelNorth = new JPanel();
        panelSouth = new JPanel();
        lblHeading = new JLabel("Job Opportunities");
        setTitle("Recruitment Application");
        panelNorth.setLayout(new FlowLayout());
        panelSouth.setLayout(new GridLayout());
        panelNorth.add(lblHeading);
        lblHeading.setFont(new Font( "Calibri" , Font.BOLD, 30 ));

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.setSize(600,500);
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 350));
        JTable table = new JTable();
        scrollPane.setViewportView(table);
        model = (DefaultTableModel)table.getModel();

        model.addColumn("Name");
        model.addColumn("Location");
        model.addColumn("Job Description");
        model.addColumn("Salary");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pass);
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select * from company_table");

            while(resultSet.next()){
                model.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4)});
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        table.setRowHeight(40);
        table.getTableHeader().setFont( new Font( "Calibri" , Font.BOLD, 17 ));
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        add(scrollPane);
        setSize(600, 350);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Company();

    }

}
