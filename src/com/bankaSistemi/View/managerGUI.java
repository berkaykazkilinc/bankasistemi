package com.bankaSistemi.View;

import com.bankaSistemi.Helper.Config;
import com.bankaSistemi.Helper.Helper;
import com.bankaSistemi.Model.*;

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
    private JButton btn_customer_add;
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
    private JTextField fld_customer_name;
    private JTextField fld_customer_telno;
    private JTextField fld_customer_tcno;
    private JTextField fld_customer_adress;
    private JTextField fld_customer_password;
    private JLabel lbl_musteri_ad;
    private JLabel lbl_musteri_tel;
    private JLabel lbl_musteri_tc;
    private JLabel lbl_musteri_adres;
    private JLabel lbl_musteri_sifre;
    private JTextField fld_customer_eposta;
    private JLabel lbl_customer_eposta;
    private JPanel pnl_islemtab_ozet;
    private JTextField fld_islem_listele;
    private JButton btn_islem_listele;
    private JTable tbl_islemtab_ozet;
    private JLabel txt_islem_miktari;
    private JScrollPane scrl_islemtab_ozet;
    private DefaultTableModel mdl_dovizlist;
    private DefaultTableModel mdl_maaslist;
    private DefaultTableModel mdl_kredilist;
    private DefaultTableModel mdl_islemlist;
    private Object[] row_doviz_list;
    private Object[] row_maas_list;
    private Object[] row_kredi_list;
    private Object[] row_islem_list;


    public managerGUI() {
        this.add(wrapper);
        setSize(600, 550);
        int x = Helper.screenLocationCenter("x", getSize());
        int y = Helper.screenLocationCenter("y", getSize());
        setLocation(x, y);
        setTitle(Config.PROJECT_TITTLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldiniz Müdürüm");

        // doviz tablosu

        mdl_dovizlist = new DefaultTableModel();
        Object[] col_userlist = {"Doviz Adi", "Kur Orani"};
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
        Object[] col_kredilist = {"Kredi Faiz Oranı", "Gecikme Faiz Oranı", "Oran Guncel Mi?", "Kredi Oran ID"};
        mdl_kredilist.setColumnIdentifiers(col_kredilist);

        row_kredi_list = new Object[col_kredilist.length];

        tbl_kredi.setModel(mdl_kredilist);
        tbl_kredi.getTableHeader().setReorderingAllowed(false);

        // islem tablosu
        mdl_islemlist = new DefaultTableModel();
        Object[] col_islemlist = {"İşlem No","Kaynak","Hedef","İşlem Türü","Tutar","Kaynak Bakiye","Hedef Bakiye","Tarih"};
        mdl_islemlist.setColumnIdentifiers(col_islemlist);

        row_islem_list = new Object[col_islemlist.length];

        tbl_islemtab_ozet.setModel(mdl_islemlist);
        tbl_islemtab_ozet.getTableHeader().setReorderingAllowed(false);

        loadDovizModel();
        loadMaasModel();
        loadCreditModel();
        CustomerRepresentative.temsilciMusteriSayisiDuzenleyici();

        btn_logout.addActionListener(e -> {
            dispose();
            bankaGUI bankGUI = new bankaGUI();
        });
        paraBirimiEkleButton.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_dovizAd) || Helper.isFieldEmpty(fld_kurOrani)) {
                Helper.showMessage("fill");
            } else {
                String doviz_adi = fld_dovizAd.getText();
                float kur = Float.parseFloat(fld_kurOrani.getText());
                if (Currency.currencyAdd(doviz_adi, kur)) {
                    Helper.showMessage("done");
                    loadDovizModel();

                    fld_dovizAd.setText(null);
                    fld_kurOrani.setText(null);
                } else {
                    Helper.showMessage("error");
                }


            }
        });
        kurOranıGüncelleButton.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_update_dovizAd) || Helper.isFieldEmpty(fld_update_yeniKur)) {
                Helper.showMessage("fill");
            } else {
                String doviz_adi = fld_update_dovizAd.getText();
                float kur = Float.parseFloat(fld_update_yeniKur.getText());
                if (Currency.currencyUpdate(doviz_adi, kur)) {
                    Helper.showMessage("done");
                    loadDovizModel();

                    fld_update_dovizAd.setText(null);
                    fld_update_yeniKur.setText(null);
                } else {
                    Helper.showMessage("error");
                }


            }
        });
        btn_delete_doviz.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_delete_doviz)) {
                Helper.showMessage("fill");
            } else {
                String doviz_adi = fld_delete_doviz.getText();
                if (Currency.currencyDelete(doviz_adi)) {
                    Helper.showMessage("done");
                    loadDovizModel();

                    fld_delete_doviz.setText(null);

                } else {
                    Helper.showMessage("error");
                }


            }
        });
        maaşBelirleButton.addActionListener(e -> {
            int maas = Integer.parseInt(fld_maas.getText());
            if (Salary.salaryUpdate(maas)) {
                Helper.showMessage("done");
                loadMaasModel();

                fld_maas.setText(null);

            } else {
                Helper.showMessage("error");
            }

        });
        btn_kredi_faiz.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_kredi_faiz) || Helper.isFieldEmpty(fld_gecikme_faiz)) {
                Helper.showMessage("fill");
            } else {

                float kredi_faizOrani = Float.parseFloat(fld_kredi_faiz.getText());
                float gecikme_faizOrani = Float.parseFloat(fld_gecikme_faiz.getText());
                if (Credit.creditAdd(kredi_faizOrani, gecikme_faizOrani)) {
                    Helper.showMessage("done");
                    loadCreditModel();

                    fld_kredi_faiz.setText(null);
                    fld_gecikme_faiz.setText(null);
                } else {
                    Helper.showMessage("error");
                }


            }


        });

        sistemİlerletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        // gerçek müşteri ekleme
        btn_customer_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_customer_name) || Helper.isFieldEmpty(fld_customer_adress)|| Helper.isFieldEmpty(fld_customer_tcno)|| Helper.isFieldEmpty(fld_customer_telno)|| Helper.isFieldEmpty(fld_customer_eposta)|| Helper.isFieldEmpty(fld_customer_password)) {
                Helper.showMessage("fill");
            } else {
                CustomerRepresentative.temsilciMusteriSayisiDuzenleyici();

                String temsilci_tc;

                temsilci_tc = CustomerRepresentative.enAzMusteriliTemsilciBul();

                String ad_soyad = fld_customer_name.getText();
                String telefon = fld_customer_telno.getText();
                String tc = fld_customer_tcno.getText();
                String adres = fld_customer_adress.getText();
                String e_posta = fld_customer_eposta.getText();
                String sifre = fld_customer_password.getText();

                if (Customer.customerAdd(ad_soyad, telefon, tc, adres, e_posta, temsilci_tc, sifre)) {
                    Helper.showMessage("done");
                    CustomerRepresentative.temsilciMusteriSayisiDuzenleyici();

                    fld_customer_password.setText(null);
                    fld_customer_adress.setText(null);
                } else {
                    Helper.showMessage("error");
                }




            }
        });
        btn_islem_listele.addActionListener(e -> {
            DefaultTableModel clearModel = (DefaultTableModel) tbl_islemtab_ozet.getModel();
            clearModel.setRowCount(0);
            if (Helper.isFieldEmpty(fld_islem_listele)) {
                Helper.showMessage("fill");
            }
            else{
                int islem_sayisi = Integer.parseInt(fld_islem_listele.getText());
                for (Transaction obj : Transaction.getTransactionList(islem_sayisi)) {


                    row_islem_list[0] = obj.getIslem_no();
                    row_islem_list[1] = obj.getKaynak();
                    row_islem_list[2] = obj.getHedef();
                    row_islem_list[3] = obj.getIslem_turu();
                    row_islem_list[4] = obj.getTutar();
                    row_islem_list[5] = obj.getKaynak_bakiye();
                    row_islem_list[6] = obj.getHedef_bakiye();
                    row_islem_list[7] = obj.getTarih();
                    mdl_islemlist.addRow(row_islem_list);
                }
            }

        });
    }


    public void loadDovizModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_doviz.getModel();
        clearModel.setRowCount(0);

        for (Currency obj : Currency.getCurrencyList()) {


            row_doviz_list[0] = obj.getDoviz_turu();
            row_doviz_list[1] = obj.getKur_orani();
            mdl_dovizlist.addRow(row_doviz_list);
        }
    }

    public void loadMaasModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_maas.getModel();
        clearModel.setRowCount(0);

        for (Salary obj : Salary.getSalaryList()) {


            row_maas_list[0] = obj.getMaas();
            mdl_maaslist.addRow(row_maas_list);
        }
    }

    public void loadCreditModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_kredi.getModel();
        clearModel.setRowCount(0);

        for (Credit obj : Credit.getCreditList()) {


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
