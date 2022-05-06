package com.bankaSistemi.View;

import com.bankaSistemi.Helper.Config;
import com.bankaSistemi.Helper.Helper;
import com.bankaSistemi.Model.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class managerGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane panel;
    private JPanel pnl_ozet;
    private JPanel pnl_doviz;
    private JPanel pnl_maas;
    private JPanel pnl_kredi;
    private JPanel pnl_cus;
    private JPanel pnl_sistem;
    private JButton sistemİlerletButton;
    private JButton müşteriEkleButton;
    private JButton paraBirimiEkleButton;
    private JButton kurOranıGüncelleButton;
    private JButton maaşBelirleButton;
    private JButton gecikmeFaizOranıBelirleButton;
    private JButton krediFaizOranıBelirleButton;
    private JButton btn_logout;
    private JLabel lbl_welcome;
    private JPanel pnl_top;

    public managerGUI () {
        this.add(wrapper);
        setSize(600,400);
        int x = Helper.screenLocationCenter("x",getSize());
        int y = Helper.screenLocationCenter("y",getSize());
        setLocation(x,y);
        setTitle(Config.PROJECT_TITTLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldiniz Müdürüm");
        btn_logout.addActionListener(e -> {
            dispose();
            bankaGUI bankGUI = new bankaGUI();
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        managerGUI mGUI = new managerGUI();
    }
}
