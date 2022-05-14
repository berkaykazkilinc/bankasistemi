package com.bankaSistemi.View;

import com.bankaSistemi.Helper.*;
import com.bankaSistemi.Model.Account;
import com.bankaSistemi.Model.Currency;
import com.bankaSistemi.Model.Customer;
import com.bankaSistemi.Model.Transaction;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class customerGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JPanel pnl_hesaplar;
    private Customer customer;
    private JTable tbl_bilgiler;
    private JPanel pnl_kredi;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JScrollPane scrl_bilgiler;
    private JPanel pnl_bilgiler;
    private JTextField fld_hesAc_doviztur;
    private JButton btn_hesapAc;
    private JTable tbl_hesaplar;
    private JTextField fld_kaynakhesap;
    private JTextField fld_hedefhesap;
    private JButton btn_paratransferi;
    private JTextField fld_hesapSil_hesapNo;
    private JButton btn_hesapSil;
    private JScrollPane scrl_hesaplar;
    private JLabel lbl_kaynakhesap;
    private JLabel lbl_hesapsil;
    private JLabel lbl_dovizTur;
    private JLabel lbl_hedefhesap;
    private JTextField fld_miktar;
    private JLabel lbl_miktar;
    private JTextField fld_yatırılacak_tutar;
    private JButton btn_paraYatır;
    private JTextField fld_yatırılacak_hesapNo;
    private JTextField fld_cekilecek_tutar;
    private JTextField fld_cekilecek_hesapNo;
    private JButton btn_paraCek;
    private JLabel lbl_yatırılacak_tutar;
    private JLabel lbl_yatırılacak_hesapNo;
    private JLabel lbl_para_cek;
    private JLabel lbl_cekilecek_hesapNo;
    private JPanel pnl_musteri_islemgecmisi;
    private JScrollPane scrl_musteri_islemgecmisi;
    private JTable tbl_musteri_islemgecmisi;
    private DefaultTableModel mdl_userlist;
    private Object[] row_user_list;
    private DefaultTableModel mdl_musteri_islemgecmisilist;
    private DefaultTableModel mdl_hesaplist;
    private Object[] row_hesap_list;
    private Object[] row_musteri_islemgecmisilist;

    public customerGUI(Customer customer) {

        this.customer = customer;

        this.add(wrapper);
        setSize(600, 550);
        int x = Helper.screenLocationCenter("x", getSize());
        int y = Helper.screenLocationCenter("y", getSize());
        setLocation(x, y);
        setTitle(Config.PROJECT_TITTLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        lbl_welcome.setText("Müşteri Hoşgeldiniz");

        // ModelUserList
        mdl_userlist = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2 || column == 5){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_userlist = {"Ad Soyad", "Telefon", "TC No", "Adres", "E-Posta", "Temsilci TC No", "Şifre"};
        mdl_userlist.setColumnIdentifiers(col_userlist);
        row_user_list = new Object[col_userlist.length];
        tbl_bilgiler.setModel(mdl_userlist);
        tbl_bilgiler.getTableHeader().setReorderingAllowed(false);

        // Hesap Listesi
        mdl_hesaplist = new DefaultTableModel();
        Object[] col_hesaplist = {"Hesap No", "Döviz Türü", "Bakiye", "TC No"};
        mdl_hesaplist.setColumnIdentifiers(col_hesaplist);
        row_hesap_list = new Object[col_hesaplist.length];
        tbl_hesaplar.setModel(mdl_hesaplist);
        tbl_hesaplar.getTableHeader().setReorderingAllowed(false);

        // musteri islem gecmisi tablosu
        mdl_musteri_islemgecmisilist = new DefaultTableModel();
        Object[] col_cusislemgecmislist = {"İşlem No","Kaynak","Hedef","İşlem Türü","Tutar","Kaynak Bakiye","Hedef Bakiye","Tarih"};
        mdl_musteri_islemgecmisilist.setColumnIdentifiers(col_cusislemgecmislist);

        row_musteri_islemgecmisilist = new Object[col_cusislemgecmislist.length];

        tbl_musteri_islemgecmisi.setModel(mdl_musteri_islemgecmisilist);
        tbl_musteri_islemgecmisi.getTableHeader().setReorderingAllowed(false);

        loadCustomerBilgilerModel();
        loadHesaplarModel();
        loadCustomerİslemGecmisi();

        // secerek musteri guncelleme
        tbl_bilgiler.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                String ad_soyad = tbl_bilgiler.getValueAt(tbl_bilgiler.getSelectedRow(), 0).toString();
                String telefon = tbl_bilgiler.getValueAt(tbl_bilgiler.getSelectedRow(), 1).toString();
                String tc_no = tbl_bilgiler.getValueAt(tbl_bilgiler.getSelectedRow(), 2).toString();
                String adres = tbl_bilgiler.getValueAt(tbl_bilgiler.getSelectedRow(), 3).toString();
                String e_posta = tbl_bilgiler.getValueAt(tbl_bilgiler.getSelectedRow(), 4).toString();
                String sifre = tbl_bilgiler.getValueAt(tbl_bilgiler.getSelectedRow(), 6).toString();
                if (Customer.customerUpdate(ad_soyad, telefon, tc_no, adres, e_posta, sifre)) {
                    Helper.showMessage("done");
                    loadCustomerBilgilerModel();
                } else {
                    Helper.showMessage("error");
                }

            }
        });

        btn_logout.addActionListener(e -> {
            dispose();
            bankaGUI bankGUI = new bankaGUI();
        });

        btn_hesapAc.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hesAc_doviztur)) {
                if (Account.accountAdd("Türk Lirası", customer)) {
                    Helper.showMessage("done");
                    loadHesaplarModel();

                    fld_hesAc_doviztur.setText(null);
                } else {
                    Helper.showMessage("error");
                }
            } else {
                String doviz_tur = fld_hesAc_doviztur.getText();
                Currency findCurrency = Currency.getFetchbyDovizAd(doviz_tur);
                if(findCurrency == null){
                    Helper.showMessage("Bu para birimi mevcut değil !");
                }
                else{
                    if (Account.accountAdd(doviz_tur, customer)) {
                        Helper.showMessage("done");
                        loadHesaplarModel();

                        fld_hesAc_doviztur.setText(null);
                    } else {
                        Helper.showMessage("error");
                    }
                }



            }
        });
        btn_hesapSil.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hesapSil_hesapNo)) {
                Helper.showMessage("fill");
            } else {
                int hesap_no = Integer.parseInt(fld_hesapSil_hesapNo.getText());
                if (Account.accountDelete(hesap_no)) {
                    Helper.showMessage("done");
                    loadHesaplarModel();
                    fld_hesapSil_hesapNo.setText(null);

                } else {
                    Helper.showMessage("error");
                }
            }
        });
        btn_paratransferi.addActionListener(e -> {
            int kaynak_hesap;
            int hedef_hesap;
            float miktar;
            if (Helper.isFieldEmpty(fld_kaynakhesap)||Helper.isFieldEmpty(fld_hedefhesap)) {
                Helper.showMessage("fill");
            }
            else{
                kaynak_hesap = Integer.parseInt(fld_kaynakhesap.getText());
                hedef_hesap = Integer.parseInt(fld_hedefhesap.getText());
                miktar = Float.parseFloat(fld_miktar.getText());
                if (Account.paraTransferi(kaynak_hesap, hedef_hesap, miktar)) {
                    Helper.showMessage("done");
                    loadHesaplarModel();
                    loadCustomerİslemGecmisi();

                    fld_kaynakhesap.setText(null);
                    fld_hedefhesap.setText(null);
                    fld_miktar.setText(null);

                }
                else {
                    Helper.showMessage("error");
                }
            }


        });
        btn_paraYatır.addActionListener(e -> {
            float tutar;
            int hesap_no;
            if (Helper.isFieldEmpty(fld_yatırılacak_tutar)||Helper.isFieldEmpty(fld_yatırılacak_hesapNo)) {
                Helper.showMessage("fill");
            }
           else{
                tutar = Float.parseFloat(fld_yatırılacak_tutar.getText());
                hesap_no = Integer.parseInt(fld_yatırılacak_hesapNo.getText());
                if (Account.paraYatırma(tutar, hesap_no, customer)) {
                    Helper.showMessage("done");
                    loadHesaplarModel();
                    loadCustomerİslemGecmisi();
                    fld_yatırılacak_tutar.setText(null);
                    fld_yatırılacak_hesapNo.setText(null);
                }
                else {
                   // Helper.showMessage("error");
                }
            }


        });
        btn_paraCek.addActionListener(e -> {
            float tutar;
            int hesap_no;
            if (Helper.isFieldEmpty(fld_cekilecek_tutar)||Helper.isFieldEmpty(fld_cekilecek_hesapNo)) {
                Helper.showMessage("fill");
            }
            else{
                tutar = Float.parseFloat(fld_cekilecek_tutar.getText());
                hesap_no = Integer.parseInt(fld_cekilecek_hesapNo.getText());
                if (Account.paraCekme(tutar, hesap_no, customer)) {
                    Helper.showMessage("done");
                    loadHesaplarModel();
                    loadCustomerİslemGecmisi();

                    fld_cekilecek_tutar.setText(null);
                    fld_cekilecek_hesapNo.setText(null);

                }
                else {
                    //Helper.showMessage("error");
                }
            }
        });
    }

    public void loadCustomerBilgilerModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_bilgiler.getModel();
        clearModel.setRowCount(0);
        String tc = customer.getTcNo();
        for (Customer obj : Customer.getCustomerList2(tc)) {


            row_user_list[0] = obj.getFullName();
            row_user_list[1] = obj.getTelNo();
            row_user_list[2] = obj.getTcNo();
            row_user_list[3] = obj.getAddress();
            row_user_list[4] = obj.getEmail();
            row_user_list[5] = obj.getTemsilci_tc_no();
            row_user_list[6] = obj.getSifre();
            mdl_userlist.addRow(row_user_list);
        }
    }

    public void loadHesaplarModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hesaplar.getModel();
        clearModel.setRowCount(0);
        String tc = customer.getTcNo();
        for (Account obj : Customer.getHesapList(tc)) {


            row_hesap_list[0] = obj.getHesap_no();
            row_hesap_list[1] = obj.getDoviz_turu();
            row_hesap_list[2] = obj.getBakiye();
            row_hesap_list[3] = obj.getTc_no();
            mdl_hesaplist.addRow(row_hesap_list);
        }
    }
    public void loadCustomerİslemGecmisi() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_musteri_islemgecmisi.getModel();
        clearModel.setRowCount(0);
        String tc = customer.getTcNo();
        for (Transaction obj : Transaction.getMusteriİslemGecmisi(tc)) {


            row_musteri_islemgecmisilist[0] = obj.getIslem_no();
            row_musteri_islemgecmisilist[1] = obj.getKaynak();
            row_musteri_islemgecmisilist[2] = obj.getHedef();
            row_musteri_islemgecmisilist[3] = obj.getIslem_turu();
            row_musteri_islemgecmisilist[4] = obj.getTutar();
            row_musteri_islemgecmisilist[5] = obj.getKaynak_bakiye();
            row_musteri_islemgecmisilist[6] = obj.getHedef_bakiye();
            row_musteri_islemgecmisilist[7] = obj.getTarih();
            mdl_musteri_islemgecmisilist.addRow(row_musteri_islemgecmisilist);
        }
    }


    public static void main(String[] args) {

        Helper.setLayout();
        Customer cus1 = new Customer();
        customerGUI x = new customerGUI(cus1);
    }

}
