package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;
import com.bankaSistemi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Credit {

        float kredi_faiz_orani;
        float gecikme_faiz_oranı;
        boolean guncellik;
        int kredi_oran_id;

    public float getKredi_faiz_orani() {
        return kredi_faiz_orani;
    }

    public void setKredi_faiz_orani(float kredi_faiz_orani) {
        this.kredi_faiz_orani = kredi_faiz_orani;
    }

    public float getGecikme_faiz_oranı() {
        return gecikme_faiz_oranı;
    }

    public void setGecikme_faiz_oranı(float gecikme_faiz_oranı) {
        this.gecikme_faiz_oranı = gecikme_faiz_oranı;
    }


    public boolean isGuncellik() {
        return guncellik;
    }

    public void setGuncellik(boolean guncellik) {
        this.guncellik = guncellik;
    }

    public int getKredi_oran_id() {
        return kredi_oran_id;
    }

    public void setKredi_oran_id(int kredi_oran_id) {
        this.kredi_oran_id = kredi_oran_id;
    }

    public static ArrayList<Credit> getCreditList() {
        ArrayList<Credit> creditList = new ArrayList<>();
        String query = "SELECT * FROM kredi_oran_tablosu ORDER BY kredi_oran_id DESC ";
        Credit obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Credit();
                obj.setKredi_faiz_orani(rs.getFloat("faiz_orani"));
                obj.setGecikme_faiz_oranı(rs.getFloat("gecikme_faiz_orani"));
                obj.setGuncellik(rs.getBoolean("is_uptodate"));
                obj.setKredi_oran_id(rs.getInt("kredi_oran_id"));

                creditList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creditList;
    }

    public static boolean creditCheck()
    {
        String sql = "SELECT is_uptodate FROM kredi_oran_tablosu WHERE is_uptodate = 1";

        try {
            Statement st = DBConnector.getInstance().prepareStatement(sql);
            //return st.executeUpdate(sql) != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void creditGuncellikSıfırlama()
    {

        if(Credit.creditCheck())
        {
            String sql2 = "UPDATE kredi_oran_tablosu SET is_uptodate = 0 WHERE is_uptodate = 1";
            try {
                Statement st = DBConnector.getInstance().prepareStatement(sql2);
                st.executeUpdate(sql2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static boolean creditAdd(float kredi_faizOrani,float gecikme_faizOrani)
    {

        if(creditCheck())
        {
            Credit.creditGuncellikSıfırlama();
        }
        String query = "INSERT INTO kredi_oran_tablosu (faiz_orani,gecikme_faiz_orani,is_uptodate) VALUES (?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setFloat(1,kredi_faizOrani);
            pr.setFloat(2,gecikme_faizOrani);
            pr.setBoolean(3,true);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }




}
