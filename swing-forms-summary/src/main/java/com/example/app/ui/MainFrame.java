
package com.example.app.ui;

import com.example.app.model.Message;
import com.example.app.service.PreferencesService;
import com.example.app.ui.form.FormPanel;
import com.example.app.ui.summary.SummaryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private final CardLayout cards = new CardLayout();
    private final JPanel content = new JPanel(cards);

    private final FormPanel formPanel = new FormPanel();
    private final SummaryPanel summaryPanel = new SummaryPanel();

    private final PreferencesService prefs = new PreferencesService(MainFrame.class);

    public MainFrame() {
        super("Swing Forms Summary");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);

        content.add(formPanel, "form");
        content.add(summaryPanel, "summary");
        setContentPane(content);

        // carrega valores salvos (se houver)
        formPanel.setValues(prefs.getName(), prefs.getEmail(), prefs.getBody());

        // callback de envio
        formPanel.setOnSubmit(this::onFormSubmit);

        // voltar do summary com ESC
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "goBack");
        getRootPane().getActionMap()
                .put("goBack", new AbstractAction() {
                    @Override public void actionPerformed(ActionEvent e) { showForm(); }
                });

        // botão voltar
        summaryPanel.getBtnBack().addActionListener(e -> showForm());

        showForm();
    }

    private void onFormSubmit(Message m) {
        // salva preferências básicas
        prefs.setName(m.name());
        prefs.setEmail(m.email());
        prefs.setBody(m.body());

        summaryPanel.showMessage(m);
        showSummary();
    }

    public void showForm() {
        cards.show(content, "form");
        SwingUtilities.invokeLater(() -> getRootPane().setDefaultButton(null)); // evita Enter acionar no summary
    }

    public void showSummary() {
        cards.show(content, "summary");
    }
}
