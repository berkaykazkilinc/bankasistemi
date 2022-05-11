package com.bankaSistemi.Model;

import java.util.ArrayList;

import com.bankaSistemi.Helper.DBConnector;
import com.bankaSistemi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Transaction {
    int islem_no;
    int kaynak;
    int hedef;
    String islem_turu;
    int tutar;
    int kaynak_bakiye;
    int hedef_bakiye;
    String tarih;

    public int getIslem_no() {
        return islem_no;
    }

    public void setIslem_no(int islem_no) {
        this.islem_no = islem_no;
    }

    public int getKaynak() {
        return kaynak;
    }

    public void setKaynak(int kaynak) {
        this.kaynak = kaynak;
    }

    public int getHedef() {
        return hedef;
    }

    public void setHedef(int hedef) {
        this.hedef = hedef;
    }

    public String getIslem_turu() {
        return islem_turu;
    }

    public void setIslem_turu(String islem_turu) {
        this.islem_turu = islem_turu;
    }

    public int getTutar() {
        return tutar;
    }

    public void setTutar(int tutar) {
        this.tutar = tutar;
    }

    public int getKaynak_bakiye() {
        return kaynak_bakiye;
    }

    public void setKaynak_bakiye(int kaynak_bakiye) {
        this.kaynak_bakiye = kaynak_bakiye;
    }

    public int getHedef_bakiye() {
        return hedef_bakiye;
    }

    public void setHedef_bakiye(int hedef_bakiye) {
        this.hedef_bakiye = hedef_bakiye;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

        public static ArrayList<Transaction> getTransactionList(int islem_sayisi){
        ArrayList<Transaction> TransactionList = new ArrayList<>();
        String query = " SELECT * FROM islem_tablosu ORDER BY islem_no DESC LIMIT ? ";
        Transaction obj;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,islem_sayisi);
            ResultSet rs = pr.executeQuery();
            while (rs.next())
            {
                obj = new Transaction();
                obj.setIslem_no(rs.getInt("islem_no"));
                obj.setKaynak(rs.getInt("kaynak"));
                obj.setHedef(rs.getInt("hedef"));
                obj.setIslem_turu(rs.getString("islem_turu"));
                obj.setTutar(rs.getInt("tutar"));
                obj.setKaynak_bakiye(rs.getInt("kaynak_bakiye"));
                obj.setHedef_bakiye(rs.getInt("hedef_bakiye"));
                obj.setTarih(rs.getString("tarih"));

                TransactionList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return TransactionList;
    }

}
