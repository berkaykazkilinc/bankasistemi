package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Customer extends User {
    private String temsilci;

    public Customer() {
    }

    public String getTemsilci() {
        return temsilci;
    }

    public void setTemsilci(String temsilci) {
        this.temsilci = temsilci;
    }

    public static ArrayList<Customer> getCustomerList(){
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
    }
}
