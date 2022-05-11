package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;
import com.bankaSistemi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {


    int hesap_no;
    String doviz_turu;
    int bakiye;
    String tc_no;


    public int getHesap_no() {
        return hesap_no;
    }

    public void setHesap_no(int hesap_no) {
        this.hesap_no = hesap_no;
    }

    public String getDoviz_turu() {
        return doviz_turu;
    }

    public void setDoviz_turu(String doviz_turu) {
        this.doviz_turu = doviz_turu;
    }

    public int getBakiye() {
        return bakiye;
    }

    public void setBakiye(int bakiye) {
        this.bakiye = bakiye;
    }

    public String getTc_no() {
        return tc_no;
    }

    public void setTc_no(String tc_no) {
        this.tc_no = tc_no;
    }

    public static boolean accountAdd(String doviz_turu,Customer customer)
    {
        String query = "INSERT INTO hesap_tablosu (doviz_turu,bakiye,tc_no,sifre,kullanici_turu) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,doviz_turu);
            pr.setInt(2,0);
            pr.setString(3,customer.getTcNo());
            pr.setString(4, customer.getSifre());
            pr.setString(5,"Müşteri");
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static Account getFetchbyHesapNo(int hesap_no)
    {
        Account obj = null;
        String query = "SELECT * FROM hesap_tablosu WHERE hesap_no = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,hesap_no);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = new Account();
                obj.setHesap_no(rs.getInt("hesap_no"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean accountDelete(int hesap_no)
    {
        String query = "DELETE FROM hesap_tablosu WHERE hesap_no = ? ";
        String query2= "SELECT bakiye FROM hesap_tablosu WHERE hesap_no = ?";
        Account findCurrency = Account.getFetchbyHesapNo(hesap_no);
        int bakiye;
        if(findCurrency == null){
            Helper.showMessage("Bu hesap mevcut değil !");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query2);
            pr.setInt(1,hesap_no);
            ResultSet rs = pr.executeQuery();
            rs.next();
            bakiye = rs.getInt("bakiye");
            if(bakiye>0){
                Helper.showMessage("Bakiyeniz 0 Değil !");
                return false;
            }
            else {
                try {
                    PreparedStatement pr1 = DBConnector.getInstance().prepareStatement(query);
                    pr1.setInt(1,hesap_no);
                    return pr1.executeUpdate() != -1;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    }

}
