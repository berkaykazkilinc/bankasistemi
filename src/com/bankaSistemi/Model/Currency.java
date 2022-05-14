package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;
import com.bankaSistemi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Currency {
    String doviz_turu;
    float kur_orani;

    public String getDoviz_turu() {
        return doviz_turu;
    }

    public void setDoviz_turu(String doviz_turu) {
        this.doviz_turu = doviz_turu;
    }

    public float getKur_orani() {
        return kur_orani;
    }

    public void setKur_orani(float kur_orani) {
        this.kur_orani = kur_orani;
    }


    public static ArrayList<Currency> getCurrencyList() {
        ArrayList<Currency> currencyList = new ArrayList<>();
        String query = "SELECT * FROM doviz_tablosu";
        Currency obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Currency();
                obj.setDoviz_turu(rs.getString("doviz_turu"));
                obj.setKur_orani(rs.getFloat("kur_orani"));
                currencyList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencyList;
    }

    public static boolean currencyAdd(String doviz_adi,float kur)
    {
        String query = "INSERT INTO doviz_tablosu (doviz_turu,kur_orani) VALUES (?,?)";
        Currency findCurrency = Currency.getFetchbyDovizAd(doviz_adi);
        if(findCurrency != null){
            Helper.showMessage("Bu para birimi daha önceden mevcut !");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,doviz_adi);
            pr.setFloat(2,kur);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Currency getFetchbyDovizAd(String doviz_adi)
    {
        Currency obj = null;
        String query = "SELECT * FROM doviz_tablosu WHERE doviz_turu = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,doviz_adi);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = new Currency();
                obj.setDoviz_turu(rs.getString("doviz_turu"));
                obj.setKur_orani(rs.getFloat("kur_orani"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static boolean currencyUpdate(String doviz_adi,float kur)
    {
        String query = "UPDATE doviz_tablosu set kur_orani = ? WHERE doviz_turu = ? ";
        Currency findCurrency = Currency.getFetchbyDovizAd(doviz_adi);
        if(findCurrency == null){
            Helper.showMessage("Bu para birimi mevcut değil !");
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);

            pr.setFloat(1,kur);
            pr.setString(2,doviz_adi);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean currencyDelete(String doviz_adi)
    {
        String query = "DELETE FROM doviz_tablosu WHERE doviz_turu = ? ";
        Currency findCurrency = Currency.getFetchbyDovizAd(doviz_adi);
        if(findCurrency == null){
            Helper.showMessage("Bu para birimi mevcut değil !");
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);

           pr.setString(1,doviz_adi);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
   /* public static boolean currencyVarMi(String doviz_adi)
    {
        String query = "SELECT doviz_turu FROM doviz_tablosu WHERE doviz_turu = ?";
        Currency findCurrency = Currency.getFetchbyDovizAd(doviz_adi);
        if(findCurrency == null){
            Helper.showMessage("Bu para birimi mevcut değil !");
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);

            pr.setFloat(1,kur);
            pr.setString(2,doviz_adi);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }*/
}
