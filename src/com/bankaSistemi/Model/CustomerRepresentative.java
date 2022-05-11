package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;
import com.bankaSistemi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerRepresentative extends User {
    private int maas;
    private String sifre;

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public CustomerRepresentative() {
    }

    public int getMaas() {
        return maas;
    }

    public void setMaas(int maas) {
        this.maas = maas;
    }


    public static void temsilciMusteriSayisiDuzenleyici(){
        String tc;

        String query = "SELECT tc_no FROM temsilci_bilgiler_tablosu";

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                tc= rs.getString("tc_no");
                temsilciMusteriSayisiGuncelle(tc);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static boolean temsilciMusteriSayisiGuncelle(String temsilci_tc) {
        String query = "SELECT COUNT(*) FROM müşteri_bilgiler_tablosu WHERE müşteri_bilgiler_tablosu.temsilci_tc_no = (?) ";
        String query2 = "UPDATE temsilci_bilgiler_tablosu SET musteri_sayisi = ? WHERE tc_no = (?) ";
        int  musteri_sayisi;

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,temsilci_tc);
            ResultSet rs = pr.executeQuery();
            rs.next();
            musteri_sayisi = rs.getInt(1);
           // System.out.println(musteri_sayisi);

            PreparedStatement pr1 = DBConnector.getInstance().prepareStatement(query2);

            pr1.setInt(1,musteri_sayisi);
            pr1.setString(2,temsilci_tc);
            return pr1.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
    public static String enAzMusteriliTemsilciBul(){
        String query = "SELECT * FROM temsilci_bilgiler_tablosu ORDER BY musteri_sayisi LIMIT 1";
        String temsilci_tc= null;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            temsilci_tc = rs.getString("tc_no");
            return temsilci_tc ;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temsilci_tc;
    }
    public static boolean getFetchbyCusRep(String tc,String sifre)
    {
        CustomerRepresentative obj = null;
        String query = "SELECT tc_no,sifre FROM temsilci_bilgiler_tablosu  WHERE tc_no = (?) AND sifre = (?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,tc);
            pr.setString(2,sifre);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = new CustomerRepresentative();
                obj.setTcNo(rs.getString("tc_no"));
                obj.setSifre(rs.getString("sifre"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(obj==null){
            Helper.showMessage("error");
            return false;
        }
        else {
            return true;
        }


    }
}

