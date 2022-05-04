package com.bankaSistemi.View;

import com.bankaSistemi.Helper.*;

import javax.swing.*;

public class bankaGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_login;
    private JPasswordField passwordField1;
    private JTextField textField1;
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
    }

    public static void main(String[] args) {
        Helper.setLayout();
        bankaGUI bank = new bankaGUI();

    }

}
