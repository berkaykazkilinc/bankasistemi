package com.bankaSistemi.View;

import com.bankaSistemi.Helper.*;
import com.bankaSistemi.Model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class customerGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JPanel pnl_hesaplar;
    private DefaultTableModel mdl_userlist;
    private JPanel pnl_bilgiler;
    private JTable tbl_bilgiler;
    private JScrollPane scrl_bilgiler;
    private JPanel pnl_kredi;
    private JPanel pnl_ozet;
    private JButton btn_hesapAc;
    private JButton hesapSilButton;
    private JButton paraTransferiButton;
    private JButton bilgiGüncelleButton;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_logout;

    public customerGUI(Customer customer){
        this.add(wrapper);
        setSize(600,400);
        int x = Helper.screenLocationCenter("x",getSize());
        int y = Helper.screenLocationCenter("y",getSize());
        setLocation(x,y);
        setTitle(Config.PROJECT_TITTLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        lbl_welcome.setText(customer.getFullName());

        // ModelUserList
        mdl_userlist = new DefaultTableModel();
        Object[] col_userlist = {"Ad-Soyad", "TelefonNo", "TcNo", "Adres", "Email","Temsilci","Maaş(TL)","Kullanıcı Türü"};
        mdl_userlist.setColumnIdentifiers(col_userlist);
        Object[] firstRow = {"Mahmut Tuncer", "5057843214", "4403214789", "İstanbul", "mtuncer@hotmail.com","Ali","","Müşteri"};
        Object[] secondRow = {"Kagan Can Baba", "5245289664", "58746512334", "Ankara", "kcbaba@hotmail.com","Ahmet","","Müşteri"};
        Object[] thirdRow = {"Ahmet Aslan", "5315284964", "79465123548", "Kırşehir", "a.aslan@hotmail.com","null","10000","Temsilci"};
        mdl_userlist.addRow(firstRow);
        mdl_userlist.addRow(secondRow);
        mdl_userlist.addRow(thirdRow);

        tbl_bilgiler.setModel(mdl_userlist);
        tbl_bilgiler.getTableHeader().setReorderingAllowed(false);

        btn_logout.addActionListener(e -> {
            dispose();
            bankaGUI bankGUI = new bankaGUI();
        });
    }
    public static void main(String[] args) {

        Helper.setLayout();
        Customer cus1 = new Customer();
        cus1.setFullName("Kerpeten Ali");
        customerGUI x = new customerGUI(cus1);
    }

}
