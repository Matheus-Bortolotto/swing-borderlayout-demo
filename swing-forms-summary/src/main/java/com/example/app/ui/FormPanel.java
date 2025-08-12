
package com.example.app.ui.form;

import com.example.app.model.Message;
import com.example.app.util.IconLoader;
import com.example.app.util.Validation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class FormPanel extends JPanel {
    private final JTextField txtName = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextArea  txtBody = new JTextArea(6, 20);
    private final JButton btnSend  = new JButton("Enviar");
    private final JButton btnClear = new JButton("Limpar");

    private Consumer<Message> onSubmit;

    private static final Border ERROR_BORDER = BorderFactory.createLineBorder(new Color(0xD32F2F), 2);
    private static final Border DEFAULT_BORDER = new JTextField().getBorder();

    public FormPanel() {
        setOpaque(true);
        initUI();
        initValidation();
        initActions();
    }

    private void initUI() {
        JLabel lblName  = new JLabel("Nome");
        JLabel lblEmail = new JLabel("E-mail");
        JLabel lblBody  = new JLabel("Mensagem");

        lblName.setDisplayedMnemonic('N');
        lblEmail.setDisplayedMnemonic('E');
        lblBody.setDisplayedMnemonic('M');

        lblName.setLabelFor(txtName);
        lblEmail.setLabelFor(txtEmail);
        lblBody.setLabelFor(txtBody);

        txtName.getAccessibleContext().setAccessibleName("Nome");
        txtEmail.getAccessibleContext().setAccessibleName("E-mail");
        txtBody.getAccessibleContext().setAccessibleName("Mensagem");

        txtBody.setLineWrap(true);
        txtBody.setWrapStyleWord(true);

        JScrollPane spBody = new JScrollPane(txtBody);

        btnSend.setIcon(IconLoader.load("/icons/send.png", 16));

        GroupLayout gl = new GroupLayout(this);
        setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createParallelGroup()
                .addGroup(gl.createSequentialGroup()
                        .addGroup(gl.createParallelGroup()
                                .addComponent(lblName)
                                .addComponent(lblEmail)
                                .addComponent(lblBody))
                        .addGroup(gl.createParallelGroup()
                                .addComponent(txtName)
                                .addComponent(txtEmail)
                                .addComponent(spBody)))
                .addGroup(gl.createSequentialGroup()
                        .addComponent(btnClear)
                        .addGap(0, Short.MAX_VALUE)
                        .addComponent(btnSend))
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblName).addComponent(txtName))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEmail).addComponent(txtEmail))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblBody).addComponent(spBody))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnClear).addComponent(btnSend))
        );

        // botao padrão
        SwingUtilities.getRootPane(this).setDefaultButton(btnSend);

        // foco inicial
        SwingUtilities.invokeLater(() -> txtName.requestFocusInWindow());
    }

    private void initValidation() {
        txtName.setInputVerifier(new NotBlankVerifier("Nome"));
        txtEmail.setInputVerifier(new EmailVerifier());
        txtBody.setInputVerifier(new NotBlankVerifier("Mensagem"));

        ((AbstractDocument) txtName.getDocument()).setDocumentFilter(new MaxLenFilter(80));
        ((AbstractDocument) txtEmail.getDocument()).setDocumentFilter(new MaxLenFilter(120));
        ((AbstractDocument) txtBody.getDocument()).setDocumentFilter(new MaxLenFilter(2000));
    }

    private void initActions() {
        btnSend.addActionListener(e -> submit());
        btnClear.addActionListener(e -> {
            txtName.setText("");
            txtEmail.setText("");
            txtBody.setText("");
            clearError(txtName);
            clearError(txtEmail);
            clearError(txtBody);
            txtName.requestFocusInWindow();
        });

        // Ctrl+Enter para enviar
        txtBody.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke("ctrl ENTER"), "send");
        txtBody.getActionMap().put("send", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { submit(); }
        });
    }

    private void submit() {
        String name  = Validation.normalize(txtName.getText());
        String email = Validation.normalize(txtEmail.getText());
        String body  = Validation.normalize(txtBody.getText());

        boolean ok = true;
        if (!Validation.notBlank(name))  { markError(txtName, "Informe seu nome"); ok = false; }
        if (!Validation.isEmail(email))  { markError(txtEmail, "E-mail inválido"); ok = false; }
        if (!Validation.notBlank(body))  { markError(txtBody, "Escreva a mensagem"); ok = false; }

        if (!ok) {
            Toolkit.getDefaultToolkit().beep();
            (txtName.isBorderPainted() ? txtName : txtEmail).requestFocusInWindow();
            return;
        }

        clearError(txtName); clearError(txtEmail); clearError(txtBody);

        if (onSubmit != null) {
            onSubmit.accept(new Message(name, email, body));
        }
    }

    public void setOnSubmit(Consumer<Message> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public void setValues(String name, String email, String body) {
        txtName.setText(name == null ? "" : name);
        txtEmail.setText(email == null ? "" : email);
        txtBody.setText(body == null ? "" : body);
    }

    private void markError(JTextComponent c, String tip) {
        c.setBorder(ERROR_BORDER);
        c.setToolTipText(tip);
    }

    private void clearError(JTextComponent c) {
        c.setBorder(DEFAULT_BORDER);
        c.setToolTipText(null);
    }

    // ===== Verifiers & Filters =====
    private static class NotBlankVerifier extends InputVerifier {
        private final String fieldName;
        NotBlankVerifier(String fieldName) { this.fieldName = fieldName; }
        @Override public boolean verify(JComponent c) {
            JTextComponent tc = (JTextComponent) c;
            boolean ok = Validation.notBlank(tc.getText());
            tc.setBorder(ok ? DEFAULT_BORDER : ERROR_BORDER);
            c.setToolTipText(ok ? null : fieldName + " é obrigatório");
            return ok;
        }
    }

    private static class EmailVerifier extends InputVerifier {
        @Override public boolean verify(JComponent c) {
            JTextComponent tc = (JTextComponent) c;
            boolean ok = Validation.isEmail(tc.getText());
            tc.setBorder(ok ? DEFAULT_BORDER : ERROR_BORDER);
            c.setToolTipText(ok ? null : "E-mail inválido");
            return ok;
        }
    }

    private static class MaxLenFilter extends DocumentFilter {
        private final int max;
        MaxLenFilter(int max) { this.max = max; }

        @Override
        public void insertString(FilterBypass fb, int off, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            if (fb.getDocument().getLength() + str.length() <= max) {
                super.insertString(fb, off, str, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        @Override
        public void replace(FilterBypass fb, int off, int len, String str, AttributeSet a) throws BadLocationException {
            if (str == null) str = "";
            if (fb.getDocument().getLength() - len + str.length() <= max) {
                super.replace(fb, off, len, str, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}
