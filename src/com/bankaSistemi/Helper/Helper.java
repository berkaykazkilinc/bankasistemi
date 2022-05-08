package com.bankaSistemi.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int screenLocationCenter(String eksen, Dimension size) {
        int point;
        switch (eksen) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
        }
        return point;
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String str) {
        String msg;
        String title;
        switch (str) {
            case "fill":
                msg = "Lütfen Tüm Alanları Doldurunuz !";
                title = "Hata !";
                break;
            case "done":
                msg = "İşlem Başarılı ! ";
                title = "Bilgilendirme";
                break;
            case "error":
                msg = "Bir hata oluştu tekrar deneyin !";
                title = "Hata !";
                break;
            default:
                msg = str;
                title = "Mesaj";

        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
