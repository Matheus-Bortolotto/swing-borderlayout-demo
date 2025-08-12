package com.example.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

/**
 * BorderLayout com botões que possuem ícones, tooltips e mnemonics.
 * Ícones esperados no classpath: /icons/north.png, /icons/south.png, /icons/west.png,
 * /icons/east.png, /icons/center.png, /icons/app.png
 */
public class BorderLayoutDemo extends JFrame {

    public BorderLayoutDemo() {
        super("BorderLayout Demo");
        initialize();
    }

    private void initialize() {
        configureLookAndFeel();
        configureFrame();
        addComponents();
    }

    private void configureLookAndFeel() {
        // Tenta ativar o tema Nimbus para um visual mais moderno (opcional)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) { /* Fallback para o L&F padrão */ }
    }

    private void configureFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        setSize(420, 300);
        setLocationRelativeTo(null); // centraliza

        // Ícone da janela (opcional, se existir)
        ImageIcon appIcon = loadIcon("/icons/app.png", 32);
        if (appIcon != null) {
            setIconImage(appIcon.getImage());
        }
    }

    private void addComponents() {
        JButton btnNorte  = createButton(
                "NORTE", "/icons/north.png",
                "<html><b>Ir para o Norte</b><br/>Atalho: Alt+N</html>", 'N');

        JButton btnSul    = createButton(
                "SUL", "/icons/south.png",
                "<html><b>Ir para o Sul</b><br/>Atalho: Alt+S</html>", 'S');

        JButton btnOeste  = createButton(
                "OESTE", "/icons/west.png",
                "<html><b>Ir para o Oeste</b><br/>Atalho: Alt+O</html>", 'O');

        JButton btnLeste  = createButton(
                "LESTE", "/icons/east.png",
                "<html><b>Ir para o Leste</b><br/>Atalho: Alt+L</html>", 'L');

        JButton btnCentro = createButton(
                "CENTRO", "/icons/center.png",
                "<html><b>Ação do Centro</b><br/>Atalho: Alt+C</html>", 'C');

        // Ações simples de demonstração
        btnNorte.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Clique em NORTE!", "Ação", JOptionPane.INFORMATION_MESSAGE));
        btnSul.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Clique em SUL!", "Ação", JOptionPane.INFORMATION_MESSAGE));
        btnOeste.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Clique em OESTE!", "Ação", JOptionPane.INFORMATION_MESSAGE));
        btnLeste.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Clique em LESTE!", "Ação", JOptionPane.INFORMATION_MESSAGE));
        btnCentro.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Clique em CENTRO!", "Ação", JOptionPane.INFORMATION_MESSAGE));

        add(btnNorte,  BorderLayout.NORTH);
        add(btnSul,    BorderLayout.SOUTH);
        add(btnOeste,  BorderLayout.WEST);
        add(btnLeste,  BorderLayout.EAST);
        add(btnCentro, BorderLayout.CENTER);
    }

    /** Cria botão estilizado com ícone (se existir), tooltip, mnemonic e cursor de mão. */
    private JButton createButton(String text, String iconPath, String tooltipHtml, int mnemonic) {
        JButton b = new JButton(text);
        ImageIcon icon = loadIcon(iconPath, 18);
        if (icon != null) {
            b.setIcon(icon);
        }
        b.setToolTipText(tooltipHtml);
        b.setMnemonic(mnemonic);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFocusPainted(false);
        b.setBorder(new javax.swing.border.EmptyBorder(8, 12, 8, 12)); // mais área clicável
        return b;
    }

    /** Carrega e escala um PNG do classpath; retorna null se não encontrado. */
    private ImageIcon loadIcon(String path, int size) {
        URL url = getClass().getResource(path);
        if (url == null) return null;
        Image img = new ImageIcon(url).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BorderLayoutDemo frame = new BorderLayoutDemo();
            frame.setVisible(true);
        });
    }
}
