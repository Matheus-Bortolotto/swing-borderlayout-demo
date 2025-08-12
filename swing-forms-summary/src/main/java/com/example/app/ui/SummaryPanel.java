
package com.example.app.ui.summary;

import com.example.app.model.Message;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class SummaryPanel extends JPanel {
    private final JTextArea txtSummary = new JTextArea(10, 40);
    private final JButton btnBack = new JButton("Voltar");

    public SummaryPanel() {
        setLayout(new BorderLayout(8, 8));
        txtSummary.setEditable(false);
        txtSummary.setLineWrap(true);
        txtSummary.setWrapStyleWord(true);

        add(new JScrollPane(txtSummary), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(btnBack);
        add(south, BorderLayout.SOUTH);
    }

    public void showMessage(Message m) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String summary = """
                ✅ Enviado com sucesso!

                Nome: %s
                E-mail: %s
                Data/Hora: %s

                Mensagem:
                %s
                """.formatted(m.name(), m.email(), m.createdAt().format(fmt), m.body());
        txtSummary.setText(summary);
        txtSummary.setCaretPosition(0);
    }

    public JButton getBtnBack() { return btnBack; }
}
