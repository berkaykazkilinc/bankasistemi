package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;
import com.bankaSistemi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {


    int hesap_no;
    String tc_no;
    String istek_turu;
    String doviz_turu;
    String onay_durumu;
    float bakiye;
    float kur_orani;


    public float getKur_orani() {
        return kur_orani;
    }

    public void setKur_orani(float kur_orani) {
        this.kur_orani = kur_orani;
    }


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

    public float getBakiye() {
        return bakiye;
    }

    public void setBakiye(float bakiye) {
        this.bakiye = bakiye;
    }

    public String getTc_no() {
        return tc_no;
    }

    public void setTc_no(String tc_no) {
        this.tc_no = tc_no;
    }

    public static boolean accountAdd2(String doviz_turu,Customer customer)
    {
        String query = "INSERT INTO istek_tablosu (tc_no,istek_turu,doviz_turu,onay_durumu) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,customer.getTcNo());
            pr.setString(2,"Hesap Açma");
            pr.setString(3,doviz_turu);
            pr.setString(4,"Onay Bekliyor");
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
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
        float bakiye;
        if(findCurrency == null){
            Helper.showMessage("Bu hesap mevcut değil !");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query2);
            pr.setInt(1,hesap_no);
            ResultSet rs = pr.executeQuery();
            rs.next();
            bakiye = rs.getFloat("bakiye");
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

    public static boolean paraTransferi(int kaynak_hesap_no,int hedef_hesap_no,float tutar){
        Account findCurrency = Account.getFetchbyHesapNo(kaynak_hesap_no);
        Account findCurrency2 = Account.getFetchbyHesapNo(hedef_hesap_no);
        Account kaynak_hesap = new Account();
        Account hedef_hesap = new Account();
        kaynak_hesap.setHesap_no(kaynak_hesap_no);
        hedef_hesap.setHesap_no(hedef_hesap_no);
        if(findCurrency == null || findCurrency2 == null){
            Helper.showMessage("Bu hesap mevcut değil !");
            return false;
        }
        String query = "SELECT hesap_no,doviz_turu,bakiye FROM hesap_tablosu WHERE hesap_no = ?";//kaynak sorgusu
        String query2= "SELECT hesap_no,doviz_turu,bakiye FROM hesap_tablosu WHERE hesap_no = ?";//hedef bakiye bul
        String query3 = "UPDATE hesap_tablosu SET bakiye = ? WHERE hesap_no = ?";
        String query4 = "UPDATE hesap_tablosu SET bakiye = ? WHERE hesap_no = ?";
        String query5 = "SELECT kur_orani FROM doviz_tablosu WHERE doviz_turu = ?"; // kaynak kur orani
        String query6 = "SELECT kur_orani FROM doviz_tablosu WHERE doviz_turu = ?"; // hedef kur orani
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,kaynak_hesap_no);
            ResultSet rs = pr.executeQuery();
            rs.next();
            kaynak_hesap.setDoviz_turu(rs.getString("doviz_turu"));
            kaynak_hesap.setBakiye(rs.getFloat("bakiye"));
            if(tutar>(kaynak_hesap.getBakiye())){
                Helper.showMessage("Bakiyeniz Yeterli Değil !");
                return false;
            }
            else {
                try {
                    PreparedStatement pr2 = DBConnector.getInstance().prepareStatement(query2);
                    pr2.setInt(1,hedef_hesap_no);
                    ResultSet rs2 = pr2.executeQuery();
                    rs2.next();
                    hedef_hesap.setDoviz_turu(rs2.getString("doviz_turu"));
                    hedef_hesap.setBakiye(rs2.getFloat("bakiye"));
                    if(kaynak_hesap.getDoviz_turu().equals(hedef_hesap.getDoviz_turu()))
                    {
                        PreparedStatement pr3 = DBConnector.getInstance().prepareStatement(query3);
                        pr3.setFloat(1,(tutar+hedef_hesap.getBakiye()));
                        pr3.setInt(2,hedef_hesap_no);
                        PreparedStatement pr4 = DBConnector.getInstance().prepareStatement(query4);
                        pr4.setFloat(1,(kaynak_hesap.bakiye-tutar));
                        pr4.setInt(2,kaynak_hesap_no);
                        pr4.executeUpdate();
                        return pr3.executeUpdate() != -1;


                    }
                    else{
                        try {
                            PreparedStatement pr5 = DBConnector.getInstance().prepareStatement(query5);
                            pr5.setString(1,kaynak_hesap.getDoviz_turu());
                            ResultSet rs5 = pr5.executeQuery();
                            rs5.next();
                            kaynak_hesap.setKur_orani(rs5.getFloat("kur_orani"));


                            PreparedStatement pr6 = DBConnector.getInstance().prepareStatement(query6);
                            pr6.setString(1,hedef_hesap.getDoviz_turu());
                            ResultSet rs6 = pr6.executeQuery();
                            rs6.next();
                            hedef_hesap.setKur_orani(rs6.getFloat("kur_orani"));

                            PreparedStatement pr4 = DBConnector.getInstance().prepareStatement(query4);
                            pr4.setFloat(1,(kaynak_hesap.bakiye-tutar));
                            pr4.setInt(2,kaynak_hesap_no);
                            pr4.executeUpdate();
                            tutar = tutar * (kaynak_hesap.getKur_orani()/hedef_hesap.getKur_orani());
                            PreparedStatement pr3 = DBConnector.getInstance().prepareStatement(query3);
                            pr3.setFloat(1,(tutar+hedef_hesap.getBakiye()));
                            pr3.setInt(2,hedef_hesap_no);
                            return pr3.executeUpdate() != -1;
                        }
                        catch (SQLException e){
                            e.printStackTrace();
                        }




                    }
                  //  return pr2.executeUpdate() != -1;
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
