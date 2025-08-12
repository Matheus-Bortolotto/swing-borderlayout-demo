
package com.example.app;

import com.example.app.ui.MainFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Look & Feel (use Nimbus por padrão; troque por FlatLaf se adicionar a dependência)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            // Se usar FlatLaf:
            // UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
