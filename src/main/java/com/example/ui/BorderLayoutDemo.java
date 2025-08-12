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

    private static final String[] DIRECTIONS = {"NORTE", "SUL", "OESTE", "LESTE", "CENTRO"};
    private static final String[] ICON_PATHS = {
            "/icons/north.png", "/icons/south.png", "/icons/west.png",
            "/icons/east.png", "/icons/center.png"
    };
    private static final char[] MNEMONICS = {'N', 'S', 'O', 'L', 'C'};
    private static final String[] TOOLTIP_HTML = {
            "<html><b>Ir para o Norte</b><br/>Atalho: Alt+N</html>",
            "<html><b>Ir para o Sul</b><br/>Atalho: Alt+S</html>",
            "<html><b>Ir para o Oeste</b><br/>Atalho: Alt+O</html>",
            "<html><b>Ir para o Leste</b><br/>Atalho: Alt+L</html>",
            "<html><b>Ação do Centro</b><br/>Atalho: Alt+C</html>"
    };

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
        // Criando os botões dinamicamente com base nas arrays definidas
        for (int i = 0; i < DIRECTIONS.length; i++) {
            JButton button = createButton(DIRECTIONS[i], ICON_PATHS[i], TOOLTIP_HTML[i], MNEMONICS[i]);
            String direction = DIRECTIONS[i];
            button.addActionListener(e -> showMessage(direction));
            // Adicionando cada botão nas posições corretas do BorderLayout
            add(button, getBorderLayoutPosition(i));
        }
    }

    /** Cria botão estilizado com ícone (se existir), tooltip, mnemonic e cursor de mão. */
    private JButton createButton(String text, String iconPath, String tooltipHtml, int mnemonic) {
        JButton button = new JButton(text);
        ImageIcon icon = loadIcon(iconPath, 18);
        if (icon != null) {
            button.setIcon(icon);
        }
        button.setToolTipText(tooltipHtml);
        button.setMnemonic(mnemonic);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 12, 8, 12)); // mais área clicável
        return button;
    }

    /** Carrega e escala um PNG do classpath; retorna null se não encontrado. */
    private ImageIcon loadIcon(String path, int size) {
        URL url = getClass().getResource(path);
        if (url == null) return null;
        Image img = new ImageIcon(url).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /** Método de ação genérica para exibir mensagem baseada na direção */
    private void showMessage(String direction) {
        JOptionPane.showMessageDialog(this, "Clique em " + direction + "!", "Ação", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Retorna a posição do BorderLayout com base no índice */
    private String getBorderLayoutPosition(int index) {
        switch (index) {
            case 0: return BorderLayout.NORTH;
            case 1: return BorderLayout.SOUTH;
            case 2: return BorderLayout.WEST;
            case 3: return BorderLayout.EAST;
            case 4: return BorderLayout.CENTER;
            default: return BorderLayout.CENTER;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BorderLayoutDemo frame = new BorderLayoutDemo();
            frame.setVisible(true);
        });
    }
}
