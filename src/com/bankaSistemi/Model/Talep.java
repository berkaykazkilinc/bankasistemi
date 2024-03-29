package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;
import com.bankaSistemi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Talep {

    int istek_id;
    int hesap_no;
    String tc_no;
    String istek_turu;
    String doviz_turu;
    String onay_durumu;

    public int getIstek_id() {
        return istek_id;
    }

    public void setIstek_id(int istek_id) {
        this.istek_id = istek_id;
    }

    public int getHesap_no() {
        return hesap_no;
    }

    public void setHesap_no(int hesap_no) {
        this.hesap_no = hesap_no;
    }

    public String getTc_no() {
        return tc_no;
    }

    public void setTc_no(String tc_no) {
        this.tc_no = tc_no;
    }

    public String getIstek_turu() {
        return istek_turu;
    }

    public void setIstek_turu(String istek_turu) {
        this.istek_turu = istek_turu;
    }

    public String getDoviz_turu() {
        return doviz_turu;
    }

    public void setDoviz_turu(String doviz_turu) {
        this.doviz_turu = doviz_turu;
    }

    public String getOnay_durumu() {
        return onay_durumu;
    }

    public void setOnay_durumu(String onay_durumu) {
        this.onay_durumu = onay_durumu;
    }

    public static ArrayList<Talep> getHesapTalepList(String tc) {
        ArrayList<Talep> hesaptalepList = new ArrayList<>();
        String query = "SELECT istek_tablosu.istek_id,istek_tablosu.hesap_no,istek_tablosu.tc_no,istek_tablosu.istek_turu,istek_tablosu.doviz_turu,istek_tablosu.onay_durumu, müşteri_bilgiler_tablosu.temsilci_tc_no FROM `istek_tablosu` INNER JOIN müşteri_bilgiler_tablosu ON istek_tablosu.tc_no = müşteri_bilgiler_tablosu.tc_no WHERE müşteri_bilgiler_tablosu.temsilci_tc_no = ?";
        Talep obj;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, tc);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                obj = new Talep();
                obj.setIstek_id(rs.getInt("istek_id"));
                obj.setHesap_no(rs.getInt("hesap_no"));
                obj.setTc_no(rs.getString("tc_no"));
                obj.setIstek_turu(rs.getString("istek_turu"));
                obj.setDoviz_turu(rs.getString("doviz_turu"));
                obj.setOnay_durumu(rs.getString("onay_durumu"));
                hesaptalepList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hesaptalepList;
    }

    public static boolean talepDelete(int istek_id) {
        String query = "UPDATE istek_tablosu SET onay_durumu = ? WHERE istek_id = ? ";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,"Reddedildi");
            pr.setInt(2, istek_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean talepOnaylaVeSil(int istek_id) {
        String query = "UPDATE istek_tablosu SET onay_durumu = ? WHERE istek_id = ? ";
        String query2 = "SELECT hesap_no FROM istek_tablosu WHERE istek_id = ?";
        int hesap_no;

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,"Onaylandı");
            pr.setInt(2, istek_id);
            try {
                PreparedStatement pr2 = DBConnector.getInstance().prepareStatement(query2);
                pr2.setInt(1,istek_id);
                ResultSet rs = pr2.executeQuery();
                rs.next();
                hesap_no = rs.getInt("hesap_no");
                Account.accountDelete(hesap_no);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
    public static boolean talepOnaylaVeAc(int istek_id) {
        String query = "UPDATE istek_tablosu SET onay_durumu = ? WHERE istek_id = ? ";
        String query2 = "SELECT tc_no,doviz_turu FROM istek_tablosu WHERE istek_id = ?";
        String tc;
        String dovizturu;

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,"Onaylandı");
            pr.setInt(2, istek_id);
            try {
                PreparedStatement pr2 = DBConnector.getInstance().prepareStatement(query2);
                pr2.setInt(1,istek_id);
                ResultSet rs = pr2.executeQuery();
                rs.next();
                tc = rs.getString("tc_no");
                dovizturu= rs.getString("doviz_turu");
                Account.accountAdd2(dovizturu,tc);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean silmeTalebiOlustur(int hesap_no,String tc_no,String doviz_turu) {
        String query = "INSERT INTO istek_tablosu(hesap_no,tc_no,istek_turu,doviz_turu) VALUES (?,?,?,?) ";
        String query2= "SELECT bakiye FROM hesap_tablosu WHERE hesap_no = ?";
        Account findCurrency = Account.getFetchbyHesapNo(hesap_no);
        float bakiye;
        if(findCurrency == null){
            Helper.showMessage("Bu hesap mevcut değil !");
            return false;
        }
        try {

            PreparedStatement pr2 = DBConnector.getInstance().prepareStatement(query2);
            pr2.setInt(1,hesap_no);
            ResultSet rs = pr2.executeQuery();
            rs.next();
            bakiye = rs.getFloat("bakiye");
            if(bakiye>0){
                Helper.showMessage("Bakiyeniz 0 Değil !");
                return false;
            }
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,hesap_no);
            pr.setString(2, tc_no);
            pr.setString(3, "Hesap Silme");
            pr.setString(4, doviz_turu);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean acmaTalebiOlustur(String tc_no,String doviz_turu) {
        String query = "INSERT INTO istek_tablosu(tc_no,istek_turu,doviz_turu) VALUES (?,?,?) ";
        try {

            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, tc_no);
            pr.setString(2, "Hesap Açma");
            pr.setString(3, doviz_turu);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static String dovizTuruBul(int hesap_no){
        String query = "SELECT doviz_turu FROM hesap_tablosu WHERE hesap_no = ? ";
        String doviz;

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,hesap_no);
            ResultSet rs = pr.executeQuery();
            rs.next();
            doviz = rs.getString("doviz_turu");
            return doviz;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String TalepTuruBul(int istek_id){
        String query = "SELECT istek_turu FROM istek_tablosu WHERE istek_id = ? ";
        String talep;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,istek_id);
            ResultSet rs = pr.executeQuery();
            rs.next();
            talep = rs.getString("istek_turu");
            return talep;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
