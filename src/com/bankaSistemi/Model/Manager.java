package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;
import com.bankaSistemi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Manager {
    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    String tc;
    String sifre;

    public static boolean getFetchbyManager(String tc,String sifre)
    {
        Manager obj = null;
        String query = "SELECT banka_muduru_tc,banka_muduru_sifre FROM banka_bilgileri  WHERE banka_muduru_tc = (?) AND banka_muduru_sifre = (?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,tc);
            pr.setString(2,sifre);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = new Manager();
                obj.setTc(rs.getString("banka_muduru_tc"));
                obj.setSifre(rs.getString("banka_muduru_sifre"));

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
