package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;
import com.bankaSistemi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Salary {
    public int getMaas() {
        return maas;
    }

    public void setMaas(int maas) {
        this.maas = maas;
    }

    int maas;

    public static ArrayList<Salary> getSalaryList() {
        ArrayList<Salary> salaryList = new ArrayList<>();
        String query = "SELECT maas FROM temsilci_bilgiler_tablosu";
        Salary obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Salary();
                obj.setMaas(rs.getInt("maas"));

                salaryList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salaryList;
    }

    public static boolean salaryUpdate(int maas)
    {
        String query = "UPDATE temsilci_bilgiler_tablosu SET maas= (?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,maas);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
