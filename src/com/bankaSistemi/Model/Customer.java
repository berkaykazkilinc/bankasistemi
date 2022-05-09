package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;
import com.bankaSistemi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Customer extends User {
    private String temsilci_tc_no;
    private String sifre;

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getTemsilci_tc_no() {
        return temsilci_tc_no;
    }

    public void setTemsilci_tc_no(String temsilci_tc_no) {
        this.temsilci_tc_no = temsilci_tc_no;
    }

    public Customer() {
    }



    /*public static ArrayList<Customer> getCustomerList(){
        ArrayList<Customer> customerList = new ArrayList<>();
        String query = "SELECT * FROM bilgiler_tablosu";
        Customer obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                obj = new Customer();
                obj.setFullName(rs.getString("ad_soyad"));
                obj.setTelNo(rs.getNString("telefon"));
                obj.setTcNo(rs.getNString("tc_no"));
                obj.setAdress(rs.getNString("adres"));
                obj.setEmail(rs.getNString("e_posta"));
                obj.setTemsilci(rs.getNString("temsilci"));
                obj.setType(rs.getNString("kullanici_turu"));
                customerList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }*/


    public static String temsilciBulucu() {
        String query = "aa";
        return query;
    }

    public static boolean customerAdd(String ad_soyad,String telefon,String tc_no,String adres,String e_posta,String temsilci_tc_no,String sifre)
    {
        String query = "INSERT INTO `müşteri_bilgiler_tablosu`(`ad_soyad`, `telefon`, `tc_no`, `adres`, `e_posta`, `temsilci_tc_no`, `sifre`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,ad_soyad);
            pr.setString(2,telefon);
            pr.setString(3,tc_no);
            pr.setString(4,adres);
            pr.setString(5,e_posta);
            pr.setString(6,temsilci_tc_no);
            pr.setString(7,sifre);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
