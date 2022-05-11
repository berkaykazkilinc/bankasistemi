package com.bankaSistemi.View;

import com.bankaSistemi.Helper.*;
import com.bankaSistemi.Model.Customer;
import com.bankaSistemi.Model.CustomerRepresentative;
import com.bankaSistemi.Model.Manager;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class bankaGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_login;
    private JPasswordField fld_password;
    private JTextField fld_username;
    private JLabel text;
    private JLabel text_kullanıcıAd;
    private JLabel text_sifre;
    private JRadioButton radiobut_customer;
    private JRadioButton radiobut_cusRep;
    private JRadioButton radiobut_manager;

    public bankaGUI() {
        this.add(wrapper);
        setSize(600, 400);
        int x = Helper.screenLocationCenter("x", getSize());
        int y = Helper.screenLocationCenter("y", getSize());
        setLocation(x, y);
        setTitle(Config.PROJECT_TITTLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        btn_login.addActionListener(e -> {
            Customer user1 = new Customer();
            CustomerRepresentative user2 = new CustomerRepresentative();

            if (fld_username.getText().isEmpty() || fld_password.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lutfen Tum Alanlari Doldurun!", "Hata", JOptionPane.INFORMATION_MESSAGE);
            } else if (radiobut_customer.isSelected()) {
                user1.setTcNo(fld_username.getText());
                user1.setSifre(fld_password.getText());
                if (Customer.getFetchbyCustomer(user1.getTcNo(),user1.getSifre())) {
                    customerGUI cusGUI = new customerGUI(user1);
                    dispose();
                }

            } else if (radiobut_cusRep.isSelected()) {
                user2.setTcNo(fld_username.getText());
                user2.setSifre(fld_password.getText());
                if (CustomerRepresentative.getFetchbyCusRep(user2.getTcNo(),user2.getSifre())) {
                    cusRepGUI crepGUI = new cusRepGUI(user2);
                    dispose();
                }

            } else if (radiobut_manager.isSelected()) {
                String tc = fld_username.getText();
                String sifre = fld_password.getText();
                if (Manager.getFetchbyManager(tc,sifre)) {
                    managerGUI mGUI = new managerGUI();
                    dispose();
                }


            } else {
                JOptionPane.showMessageDialog(null, "Lutfen Tum Alanlari Doldurun!", "Hata", JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        bankaGUI bank = new bankaGUI();

    }

}
