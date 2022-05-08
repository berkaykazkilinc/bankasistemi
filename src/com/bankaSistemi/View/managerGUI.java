package com.bankaSistemi.View;

import com.bankaSistemi.Helper.Config;
import com.bankaSistemi.Helper.Helper;
import com.bankaSistemi.Model.Credit;
import com.bankaSistemi.Model.Currency;
import com.bankaSistemi.Model.Salary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JButton btn_logout;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTable tbl_doviz;
    private JTextField fld_dovizAd;
    private JTextField fld_kurOrani;
    private JLabel lbl_dovizAd;
    private JLabel lbl_kurOran;
    private JTextField fld_update_dovizAd;
    private JTextField fld_update_yeniKur;
    private JLabel lbl_update_ad;
    private JLabel lbl_update_kur;
    private JLabel lbl_delete_doviz;
    private JButton btn_delete_doviz;
    private JTextField fld_delete_doviz;
    private JTextField fld_maas;
    private JLabel lbl_maas;
    private JTable tbl_maas;
    private JScrollPane scrl_maas;
    private JScrollPane scrl_doviz;
    private JTable tbl_kredi;
    private JTextField fld_kredi_faiz;
    private JButton btn_kredi_faiz;
    private JTextField fld_gecikme_faiz;
    private JLabel lbl_gecikme_faiz;
    private JScrollPane scrl_kredi;
    private JLabel lbl_kredi_faiz;
    private DefaultTableModel mdl_dovizlist;
    private DefaultTableModel mdl_maaslist;
    private DefaultTableModel mdl_kredilist;
    private Object [] row_doviz_list;
    private Object [] row_maas_list;
    private Object [] row_kredi_list;


    public managerGUI () {
        this.add(wrapper);
        setSize(600,550);
        int x = Helper.screenLocationCenter("x",getSize());
        int y = Helper.screenLocationCenter("y",getSize());
        setLocation(x,y);
        setTitle(Config.PROJECT_TITTLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldiniz Müdürüm");

        // doviz tablosu

        mdl_dovizlist = new DefaultTableModel();
        Object[] col_userlist = {"Doviz Adi","Kur Orani"};
        mdl_dovizlist.setColumnIdentifiers(col_userlist);

        row_doviz_list = new Object[col_userlist.length];

        tbl_doviz.setModel(mdl_dovizlist);
        tbl_doviz.getTableHeader().setReorderingAllowed(false);

        // maas tablosu

        mdl_maaslist = new DefaultTableModel();
        Object[] col_maaslist = {"Çalışan Maaşı"};
        mdl_maaslist.setColumnIdentifiers(col_maaslist);

        row_maas_list = new Object[col_maaslist.length];

        tbl_maas.setModel(mdl_maaslist);
        tbl_maas.getTableHeader().setReorderingAllowed(false);

        // kredi tablosu

        mdl_kredilist = new DefaultTableModel();
        Object[] col_kredilist = {"Kredi Faiz Oranı","Gecikme Faiz Oranı","Oran Guncel Mi?","Kredi Oran ID"};
        mdl_kredilist.setColumnIdentifiers(col_kredilist);

        row_kredi_list = new Object[col_kredilist.length];

        tbl_kredi.setModel(mdl_kredilist);
        tbl_kredi.getTableHeader().setReorderingAllowed(false);




        loadDovizModel();
        loadMaasModel();
        loadCreditModel();

        btn_logout.addActionListener(e -> {
            dispose();
            bankaGUI bankGUI = new bankaGUI();
        });
        paraBirimiEkleButton.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_dovizAd) || Helper.isFieldEmpty(fld_kurOrani)){
                Helper.showMessage("fill");
            }
            else {
                String doviz_adi = fld_dovizAd.getText();
                float kur = Float.parseFloat(fld_kurOrani.getText());
                if(Currency.currencyAdd(doviz_adi,kur))
                {
                    Helper.showMessage("done");
                    loadDovizModel();

                    fld_dovizAd.setText(null);
                    fld_kurOrani.setText(null);
                }
                else {
                    Helper.showMessage("error");
                }


            }
        });
        kurOranıGüncelleButton.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_update_dovizAd) || Helper.isFieldEmpty(fld_update_yeniKur)){
                Helper.showMessage("fill");
            }
            else {
                String doviz_adi = fld_update_dovizAd.getText();
                float kur = Float.parseFloat(fld_update_yeniKur.getText());
                if(Currency.currencyUpdate(doviz_adi,kur))
                {
                    Helper.showMessage("done");
                    loadDovizModel();

                    fld_update_dovizAd.setText(null);
                    fld_update_yeniKur.setText(null);
                }
                else {
                    Helper.showMessage("error");
                }


            }
        });
        btn_delete_doviz.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_delete_doviz)){
                Helper.showMessage("fill");
            }
            else {
                String doviz_adi = fld_delete_doviz.getText();
                if(Currency.currencyDelete(doviz_adi))
                {
                    Helper.showMessage("done");
                    loadDovizModel();

                    fld_delete_doviz.setText(null);

                }
                else {
                    Helper.showMessage("error");
                }


            }
        });
        maaşBelirleButton.addActionListener(e -> {
            int maas = Integer.parseInt(fld_maas.getText());
            if(Salary.salaryUpdate(maas))
            {
                Helper.showMessage("done");
                loadMaasModel();

                fld_maas.setText(null);

            }
            else {
                Helper.showMessage("error");
            }

        });
        btn_kredi_faiz.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_kredi_faiz) || Helper.isFieldEmpty(fld_gecikme_faiz)){
                Helper.showMessage("fill");
            }
            else {

                float kredi_faizOrani = Float.parseFloat(fld_kredi_faiz.getText());
                float gecikme_faizOrani = Float.parseFloat(fld_gecikme_faiz.getText());
                if(Credit.creditAdd(kredi_faizOrani,gecikme_faizOrani))
                {
                    Helper.showMessage("done");
                    loadCreditModel();

                    fld_kredi_faiz.setText(null);
                    fld_gecikme_faiz.setText(null);
                }
                else {
                    Helper.showMessage("error");
                }



            }



        });
    }


    public void loadDovizModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_doviz.getModel();
        clearModel.setRowCount(0);

        for(Currency obj : Currency.getCurrencyList())
        {


            row_doviz_list[0] = obj.getDoviz_turu();
            row_doviz_list[1] = obj.getKur_orani();
            mdl_dovizlist.addRow(row_doviz_list);
        }
    }

    public void loadMaasModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_maas.getModel();
        clearModel.setRowCount(0);

        for(Salary obj : Salary.getSalaryList())
        {


            row_maas_list[0] = obj.getMaas();
            mdl_maaslist.addRow(row_maas_list);
        }
    }
    public void loadCreditModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_kredi.getModel();
        clearModel.setRowCount(0);

        for(Credit obj : Credit.getCreditList())
        {


            row_kredi_list[0] = obj.getKredi_faiz_orani();
            row_kredi_list[1] = obj.getGecikme_faiz_oranı();
            row_kredi_list[2] = obj.isGuncellik();
            row_kredi_list[3] = obj.getKredi_oran_id();
            mdl_kredilist.addRow(row_kredi_list);
        }
    }


    public static void main(String[] args) {
        Helper.setLayout();
        managerGUI mGUI = new managerGUI();
    }


}
