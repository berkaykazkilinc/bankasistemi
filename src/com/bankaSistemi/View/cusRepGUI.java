package com.bankaSistemi.View;

import com.bankaSistemi.Helper.Config;
import com.bankaSistemi.Helper.Helper;
import com.bankaSistemi.Model.CustomerRepresentative;
import com.bankaSistemi.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class cusRepGUI extends JFrame {
    private JPanel wrapper;

    private CustomerRepresentative customerRep;
    private JTabbedPane tab_customerRep;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_userList;
    private JScrollPane scrl_userlist;
    private JTable tbl_userlist;
    private DefaultTableModel mdl_userlist;
    private Object[] row_user_list;

    public cusRepGUI(CustomerRepresentative customerRep) {
        this.customerRep = customerRep;

        this.add(wrapper);
        setSize(600, 400);
        int x = Helper.screenLocationCenter("x", getSize());
        int y = Helper.screenLocationCenter("y", getSize());
        setLocation(x, y);
        setTitle(Config.PROJECT_TITTLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        lbl_welcome.setText(customerRep.getFullName());

        // ModelUserList
        mdl_userlist = new DefaultTableModel();
        Object[] col_userlist = {"FullName", "TelNo", "TcNo", "Adress", "Email"};
        mdl_userlist.setColumnIdentifiers(col_userlist);
        Object[] firstRow = {"mahmut tuncer", "5057843214", "4403214789", "İstanbul", "mtuncer@hotmail.com"};
        mdl_userlist.addRow(firstRow);

        /*for (User obj : User.getList()) {
            Object[] row = new Object[col_userlist.length];
            row[0] = obj.getFullName();
            row[1] = obj.getTelNo();
            row[2] = obj.getTcNo();
            row[3] = obj.getAdress();
            row[4] = obj.getEmail();
            mdl_userlist.addRow(row);
        }
*/
        tbl_userlist.setModel(mdl_userlist);
        tbl_userlist.getTableHeader().setReorderingAllowed(false);

    }

    public static void main(String[] args) {
        Helper.setLayout();
        CustomerRepresentative cusrep1 = new CustomerRepresentative();
        cusrep1.setFullName("Berkay Kazkılınç");
        cusrep1.setAdress("İstanbul");
        cusrep1.setEmail("berkaykazkilinc@gmail.com");
        cusrep1.setTcNo("12345678999");
        cusrep1.setTelNo("5059300071");

        cusRepGUI cRepGUI = new cusRepGUI(cusrep1);

    }
}

