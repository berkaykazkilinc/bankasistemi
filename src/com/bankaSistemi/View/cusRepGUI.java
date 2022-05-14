package com.bankaSistemi.View;

import com.bankaSistemi.Helper.Config;
import com.bankaSistemi.Helper.Helper;
import com.bankaSistemi.Model.Customer;
import com.bankaSistemi.Model.CustomerRepresentative;
import com.bankaSistemi.Model.Talep;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cusRepGUI extends JFrame {
    private JPanel wrapper;

    private CustomerRepresentative customerRep;
    private JTabbedPane tab_customerRep;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JTable tbl_musteri_ekle;
    private JPanel pnl_cus;
    private JButton btn_customer_add;
    private JTextField fld_customer_name;
    private JTextField fld_customer_telno;
    private JTextField fld_customer_tcno;
    private JTextField fld_customer_adress;
    private JTextField fld_customer_password;
    private JLabel lbl_musteri_tel;
    private JLabel lbl_musteri_tc;
    private JLabel lbl_musteri_adres;
    private JLabel lbl_musteri_sifre;
    private JLabel lbl_musteri_ad;
    private JTextField fld_customer_eposta;
    private JLabel lbl_customer_eposta;
    private JPanel pnl_musterilerim;
    private JScrollPane scrl_musterilerim;
    private JTable tbl_musterilerim;
    private JTable tbl_hesap_talep;
    private JPanel pnl_hesap_talep;
    private JScrollPane scrl_hesap_talep;
    private JTable tbl_kredi_talep;
    private JPanel pnl_ozet;
    private JPanel pnl_kredi_talep;
    private JScrollPane scrl_kredi_talep;
    private JPanel pnl_musteri_islemgecmisi;
    private JTable tbl_musteri_islemgecmisi;
    private JScrollPane scrl_musteri_islemgecmisi;
    private JButton btn_reddet;
    private JButton btn_onayla;
    private JTextField fld_islemid;
    private JLabel lbl_onay_islemid;
    private DefaultTableModel mdl_userlist;
    private DefaultTableModel mdl_hesap_taleplist;
    private DefaultTableModel mdl_kredi_taleplist;
    private DefaultTableModel mdl_musteri_islemgecmisilist;
    private Object[] row_user_list;
    private Object[] row_hesap_taleplist;
    private Object[] row_kredi_taleplist;
    private Object[] row_musteri_islemgecmisilist;

    public cusRepGUI(CustomerRepresentative customerRep) {
        this.customerRep = customerRep;

        this.add(wrapper);
        setSize(600, 550);
        int x = Helper.screenLocationCenter("x", getSize());
        int y = Helper.screenLocationCenter("y", getSize());
        setLocation(x, y);
        setTitle(Config.PROJECT_TITTLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        lbl_welcome.setText("Müşteri Temsilcisi Hoşgeldiniz");

        // musteri tablosu
        mdl_userlist = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 5 || column == 2)
                {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_musterilist = {"Ad Soyad","Telefon","TcNO","Adres","E-Posta","Temsilci TcNo","Şifre"};
        mdl_userlist.setColumnIdentifiers(col_musterilist);

        row_user_list = new Object[col_musterilist.length];

        tbl_musterilerim.setModel(mdl_userlist);
        tbl_musterilerim.getTableHeader().setReorderingAllowed(false);

        // hesap talep tablosu
        mdl_hesap_taleplist = new DefaultTableModel();
        Object[] col_hesaptaleplist = {"İstek ID","Hesap No","TC No","İstek Türü","Döviz Türü","Onay Durumu"};
        mdl_hesap_taleplist.setColumnIdentifiers(col_hesaptaleplist);

        row_hesap_taleplist = new Object[col_hesaptaleplist.length];

        tbl_hesap_talep.setModel(mdl_hesap_taleplist);
        tbl_hesap_talep.getTableHeader().setReorderingAllowed(false);

        // kredi talep tablosu
        mdl_kredi_taleplist = new DefaultTableModel();
        Object[] col_kreditaleplist = {"Tc No","Kredi Miktarı","Kredi İşlem No","İşlem Tarihi","Onay Durumu","Kredi Oran ID"};
        mdl_kredi_taleplist.setColumnIdentifiers(col_kreditaleplist);

        row_kredi_taleplist = new Object[col_kreditaleplist.length];

        tbl_kredi_talep.setModel(mdl_kredi_taleplist);
        tbl_kredi_talep.getTableHeader().setReorderingAllowed(false);

        // musteri islem gecmisi tablosu
        mdl_musteri_islemgecmisilist = new DefaultTableModel();
        Object[] col_cusislemgecmislist = {"İşlem No","Kaynak","Hedef","İşlem Türü","Tutar","Kaynak Bakiye","Hedef Bakiye","Tarih"};
        mdl_musteri_islemgecmisilist.setColumnIdentifiers(col_cusislemgecmislist);

        row_musteri_islemgecmisilist = new Object[col_cusislemgecmislist.length];

        tbl_musteri_islemgecmisi.setModel(mdl_musteri_islemgecmisilist);
        tbl_musteri_islemgecmisi.getTableHeader().setReorderingAllowed(false);

        loadCustomerModel();
        loadHesapTalepModel();

        // secerek musteri guncelleme
          tbl_musterilerim.getModel().addTableModelListener(e -> {
            if (e.getType()==TableModelEvent.UPDATE){
                String ad_soyad = tbl_musterilerim.getValueAt(tbl_musterilerim.getSelectedRow(),0).toString();
                String telefon = tbl_musterilerim.getValueAt(tbl_musterilerim.getSelectedRow(),1).toString();
                String musteri_tc = tbl_musterilerim.getValueAt(tbl_musterilerim.getSelectedRow(),2).toString();
                String adres = tbl_musterilerim.getValueAt(tbl_musterilerim.getSelectedRow(),3).toString();
                String e_posta = tbl_musterilerim.getValueAt(tbl_musterilerim.getSelectedRow(),4).toString();
                String sifre = tbl_musterilerim.getValueAt(tbl_musterilerim.getSelectedRow(),6).toString();
                if (Customer.customerUpdate(ad_soyad,telefon,musteri_tc,adres,e_posta,sifre)){
                    Helper.showMessage("done");
                    loadCustomerModel();
                }
                else{
                    Helper.showMessage("error");
                }

            }
        });

        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                bankaGUI bankGUI = new bankaGUI();
            }
        });
        btn_customer_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_customer_name) || Helper.isFieldEmpty(fld_customer_adress) || Helper.isFieldEmpty(fld_customer_tcno) || Helper.isFieldEmpty(fld_customer_telno) || Helper.isFieldEmpty(fld_customer_eposta) || Helper.isFieldEmpty(fld_customer_password)) {
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

                    fld_customer_name.setText(null);
                    fld_customer_telno.setText(null);
                    fld_customer_tcno.setText(null);
                    fld_customer_eposta.setText(null);
                    fld_customer_password.setText(null);
                    fld_customer_adress.setText(null);
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        tbl_hesap_talep.getSelectionModel().addListSelectionListener(e -> {

            try{
                String select_islem_id = tbl_hesap_talep.getValueAt(tbl_hesap_talep.getSelectedRow(),0).toString();
                //System.out.println(select_islem_id);
                fld_islemid.setText(select_islem_id);
            }
            catch (Exception exception){

            }

        });
        btn_reddet.addActionListener(e -> {
            int islem_id = Integer.parseInt(fld_islemid.getText());
            Talep.talepDelete(islem_id);
            loadHesapTalepModel();
        });

        btn_onayla.addActionListener(e -> {
            int islem_id = Integer.parseInt(fld_islemid.getText());

        });
    }

    public void loadCustomerModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_musterilerim.getModel();
        clearModel.setRowCount(0);
        String tc = customerRep.getTcNo();
        for (Customer obj : Customer.getCustomerList(tc)) {


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

    public void loadHesapTalepModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hesap_talep.getModel();
        clearModel.setRowCount(0);
        String tc = customerRep.getTcNo();
        for (Talep obj : Talep.getHesapTalepList(tc)) {


            row_hesap_taleplist[0] = obj.getIstek_id();
            row_hesap_taleplist[1] = obj.getHesap_no();
            row_hesap_taleplist[2] = obj.getTc_no();
            row_hesap_taleplist[3] = obj.getIstek_turu();
            row_hesap_taleplist[4] = obj.getDoviz_turu();
            row_hesap_taleplist[5] = obj.getOnay_durumu();
            mdl_hesap_taleplist.addRow(row_hesap_taleplist);
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        CustomerRepresentative cusrep1 = new CustomerRepresentative();
        cusrep1.setFullName("Berkay Kazkılınç");
        cusrep1.setAddress("İstanbul");
        cusrep1.setEmail("berkaykazkilinc@gmail.com");
        cusrep1.setTcNo("12345678999");
        cusrep1.setTelNo("5059300071");

        cusRepGUI cRepGUI = new cusRepGUI(cusrep1);

    }
}

