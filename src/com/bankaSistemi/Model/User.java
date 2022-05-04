package com.bankaSistemi.Model;

import com.bankaSistemi.Helper.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
        private String fullName;
        private String  telNo;
        private String tcNo;
        private String adress;
        private String email;


                public User(){}


        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getTelNo() {
            return telNo;
        }

        public void setTelNo(String telNo) {
            this.telNo = telNo;
        }

        public String getTcNo() {
            return tcNo;
        }

        public void setTcNo(String tcNo) {
            this.tcNo = tcNo;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


    public static ArrayList<User> getList(){
            ArrayList<User> userList = new ArrayList<>();
            String query = "SELECT * FROM user";
            User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new User();
                obj.setFullName(rs.getString("FullName"));
                obj.setAdress(rs.getNString("Adress"));
                obj.setEmail(rs.getNString("Email"));
                obj.setTcNo(rs.getNString("TcNo"));
                obj.setTelNo(rs.getNString("TelNo"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    }
