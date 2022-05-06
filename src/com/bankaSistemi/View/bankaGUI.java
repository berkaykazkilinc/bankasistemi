package com.bankaSistemi.View;

import com.bankaSistemi.Helper.*;
import com.bankaSistemi.Model.Customer;
import com.bankaSistemi.Model.CustomerRepresentative;
import com.bankaSistemi.Model.User;

import javax.swing.*;

public class bankaGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_login;
    private JPasswordField lbl_password;
    private JTextField lbl_username;
    private JLabel text;
    private JLabel text_kullanıcıAd;
    private JLabel text_sifre;
    private JRadioButton radiobut_customer;
    private JRadioButton radiobut_cusRep;
    private JRadioButton radiobut_manager;

    public bankaGUI(){
        this.add(wrapper);
        setSize(600,400);
        int x = Helper.screenLocationCenter("x",getSize());
        int y = Helper.screenLocationCenter("y",getSize());
        setLocation(x,y);
        setTitle(Config.PROJECT_TITTLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        btn_login.addActionListener(e -> {
            Customer user1 = new Customer();
            CustomerRepresentative user2 = new CustomerRepresentative();
            user1.setFullName("Tefo");
            user2.setFullName("Şebnem");
            if(lbl_username.getText().isEmpty() || lbl_password.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Lutfen Tum Alanlari Doldurun!","Hata",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (radiobut_customer.isSelected())
            {
                customerGUI cusGUI = new customerGUI(user1);
                dispose();
            }
            else if(radiobut_cusRep.isSelected())
            {
                cusRepGUI crepGUI = new cusRepGUI(user2);
                dispose();
            }
            else if (radiobut_manager.isSelected())
            {
                managerGUI mGUI = new managerGUI();
                dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Lutfen Tum Alanlari Doldurun!","Hata",JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        bankaGUI bank = new bankaGUI();

    }

}
