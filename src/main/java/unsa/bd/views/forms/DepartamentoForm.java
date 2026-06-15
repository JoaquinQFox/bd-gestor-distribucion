package unsa.bd.views.forms;

import unsa.bd.dao.DepartamentoDAO;
import unsa.bd.model.Departamento;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class DepartamentoForm extends JInternalFrame {
    // Colores
    private static final Color FONDO = new Color(248, 250, 252);
    private static final Color BORDER = new Color(47, 52, 60);
    private static final Color TABLE_HEADER = new Color(51, 136, 224);
    private static final Color TABLE_SELECTION = new Color(126, 179, 245);
    private static final Color DISABLED = new Color(0xE5E5E5);
    private static final Color BUTTON_SELECTED = new Color(255, 31, 31);

    // Fuentes
    private static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 25);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 14);
    private static final Font SECTION_FONT = new Font("SansSerif", Font.BOLD, 18);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 15);
    private static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 15);
    private static final Font TABLE_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font TABLEH_FONT = new Font("SansSerif", Font.BOLD, 15);

    // Campos de texto
    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    // Botones
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JButton inactiveButton;
    private JButton reactiveButton;
    private JButton updateButton;
    private JButton exitButton;

    // Tabla
    private DefaultTableModel model;
    private JTable table;

    // Logica
    private int carFlaAct = 0;
    private String modo = "MODO";

    public DepartamentoForm(String title) {
        this.setTitle(title);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(600, 800);
        try {
            this.setMaximum(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }

        this.generateContent();
        this.generateButtonsAction();

        this.setVisible(true);
    }

    private void generateContent() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(FONDO);
        mainPanel.add(buildHeader(), BorderLayout.NORTH);
        mainPanel.add(buildCenterContent(), BorderLayout.CENTER);

        this.add(mainPanel);
    }

    private JPanel buildHeader() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(titlePanel.getWidth(), 40));
        titlePanel.setBackground(TABLE_HEADER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("DEPARTAMENTO");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        return titlePanel;
    }

    private JPanel buildCenterContent() {
        JPanel centerPanel = new JPanel(new BorderLayout(10, 5));
        centerPanel.setOpaque(false);
        centerPanel.add(this.getRegisterSectionPanel(), BorderLayout.NORTH);
        centerPanel.add(this.getTablePanel(), BorderLayout.CENTER);
        centerPanel.add(this.getButtonsPanel(), BorderLayout.SOUTH);
        return centerPanel;
    }

    private JPanel getRegisterSectionPanel() {
        JPanel registerPanel = getStyledSectionPanel();
        registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        registerPanel.setBackground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(" Formulario de datos");
        subtitleLabel.setFont(SECTION_FONT);
        subtitleLabel.setPreferredSize(new Dimension(0, 28));
        subtitleLabel.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, TABLE_HEADER));
        registerPanel.add(subtitleLabel, BorderLayout.NORTH);

        JPanel registerFormPanel = new JPanel(new GridBagLayout());
        registerFormPanel.setBorder(BorderFactory.createEmptyBorder(12, 5, 10, 5));
        registerFormPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 20, 10, 20);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; c.weightx = 0.0; c.fill = GridBagConstraints.NONE;
        registerFormPanel.add(makeStyledLabel("Código:"), c);

        codField = makeStyledField(10);
        setFieldEditable(codField, false);
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0; c.fill = GridBagConstraints.NONE;
        registerFormPanel.add(codField, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0.0; c.fill = GridBagConstraints.NONE;
        registerFormPanel.add(makeStyledLabel("Nombre:"), c);

        nomField = makeStyledField(0);
        setFieldEditable(nomField, false);
        c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL;
        registerFormPanel.add(nomField, c);

        c.gridx = 0; c.gridy = 2; c.weightx = 0.0; c.fill = GridBagConstraints.NONE;
        registerFormPanel.add(makeStyledLabel("Est. Reg.:"), c);

        estRegField = makeStyledField(5);
        setFieldEditable(estRegField, false);
        c.gridx = 1; c.gridy = 2; c.weightx = 1.0; c.fill = GridBagConstraints.NONE;
        registerFormPanel.add(estRegField, c);

        registerPanel.add(registerFormPanel, BorderLayout.CENTER);
        return registerPanel;
    }

    private JLabel makeStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        return label;
    }

    private JTextField makeStyledField(int cols) {
        JTextField field = cols > 0 ? new JTextField(cols) : new JTextField();
        field.setFont(FIELD_FONT);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 32));
        return field;
    }

    private void setFieldEditable(JTextField field, boolean editable) {
        if (editable) {
            field.setEditable(true);
            field.setFocusable(true);
            field.setBackground(Color.WHITE);
        } else {
            field.setEditable(false);
            field.setFocusable(false);
            field.setBackground(DISABLED);
        }
    }

    private JPanel getTablePanel() {
        JPanel tablePanel = getStyledSectionPanel();
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablePanel.setOpaque(false);

        JLabel subtitleLabel = new JLabel(" Tabla de datos");
        subtitleLabel.setFont(SECTION_FONT);
        subtitleLabel.setPreferredSize(new Dimension(0, 28));
        subtitleLabel.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, TABLE_HEADER));
        tablePanel.add(subtitleLabel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("Est. Reg.");

        getTableData(model);
        table = getJTable(model);

        JTableHeader tableHeader = table.getTableHeader();
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setFont(TABLEH_FONT);
                label.setForeground(Color.WHITE);
                label.setBackground(TABLE_HEADER);
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 1, BORDER),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                return label;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        tableHeader.setPreferredSize(new Dimension(0, 32));
        tableHeader.setBackground(TABLE_HEADER);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(true);
        tableHeader.setBorder(BorderFactory.createEmptyBorder());

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                if (c instanceof JLabel label) {
                    label.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                }
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(340);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER, 1));

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private static JTable getJTable(DefaultTableModel model) {
        JTable tableDep = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDep.setFont(TABLE_FONT);
        tableDep.setRowHeight(28);
        tableDep.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableDep.setGridColor(BORDER);
        tableDep.setSelectionBackground(TABLE_SELECTION);
        tableDep.setForeground(Color.BLACK);
        tableDep.setFillsViewportHeight(true);
        return tableDep;
    }

    private static void getTableData(DefaultTableModel model) {
        DepartamentoDAO dao = new DepartamentoDAO();
        try {
            List<Departamento> departamentos = dao.listarTodo();
            for (Departamento d : departamentos) {
                model.addRow(new Object[]{
                        d.getDepCod(),
                        d.getDepNom(),
                        d.getDepEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al cargar los datos");
            System.exit(1);
        }
    }

    private static JPanel getStyledSectionPanel() {
        return new JPanel(new BorderLayout(0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(15, 23, 42, 15));
                g2.fillRoundRect(3, 4, getWidth() - 3, getHeight() - 4, 12, 12);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 4, 12, 12);
                g2.setColor(BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 4, getHeight() - 5, 12, 12);
                g2.dispose();
            }
        };
    }

    private JPanel getButtonsPanel() {
        JPanel outerPanel = getStyledSectionPanel();
        outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        outerPanel.setOpaque(false);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 4, 8, 8));
        buttonsPanel.setOpaque(false);

        addButton = makeButton("Adicionar"); buttonsPanel.add(addButton);
        modifyButton = makeButton("Modificar"); buttonsPanel.add(modifyButton);
        deleteButton = makeButton("Eliminar"); buttonsPanel.add(deleteButton);
        cancelButton = makeButton("Cancelar"); buttonsPanel.add(cancelButton);
        inactiveButton = makeButton("Inactivar"); buttonsPanel.add(inactiveButton);
        reactiveButton = makeButton("Reactivar"); buttonsPanel.add(reactiveButton);
        updateButton = makeButton("Actualizar"); buttonsPanel.add(updateButton);
        exitButton = makeButton("Salir"); buttonsPanel.add(exitButton);

        outerPanel.add(buttonsPanel, BorderLayout.CENTER);
        return outerPanel;
    }

    private JButton makeButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color baseColor = getBackground();
                Color currentColor = getModel().isPressed() ? baseColor.darker() : getModel().isRollover() ? baseColor.brighter() : baseColor;
                g2.setColor(currentColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2.setColor(currentColor.darker());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(BUTTON_FONT);
        btn.setBackground(TABLE_HEADER);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 38));
        return btn;
    }

    private void generateButtonsAction() {
        addButton.addActionListener(e -> {
            if (carFlaAct == 0) {
                setFieldEditable(nomField, true);
                estRegField.setText("A");
                carFlaAct = 1;
                modo = "ADD";
                addButton.setBackground(BUTTON_SELECTED);
            } else {
                JOptionPane.showMessageDialog(null, "Cancele la operación actual para cambiar a otra");
            }
        });

        modifyButton.addActionListener(e -> {
            if (carFlaAct == 0) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione en la tabla la fila a modificar");
                    return;
                }
                codField.setText(table.getValueAt(row, 0).toString());
                nomField.setText(table.getValueAt(row, 1).toString());
                estRegField.setText(table.getValueAt(row, 2).toString());
                setFieldEditable(nomField, true);
                carFlaAct = 1;
                modo = "MODIFY";
                modifyButton.setBackground(BUTTON_SELECTED);
            } else {
                JOptionPane.showMessageDialog(null, "Cancele la operación actual para cambiar a otra");
            }
        });

        deleteButton.addActionListener(e -> {
            if (carFlaAct == 0) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione en la tabla la fila a eliminar");
                    return;
                }
                codField.setText(table.getValueAt(row, 0).toString());
                nomField.setText(table.getValueAt(row, 1).toString());
                estRegField.setText(table.getValueAt(row, 2).toString());
                carFlaAct = 1;
                modo = "DELETE";
                deleteButton.setBackground(BUTTON_SELECTED);
            } else {
                JOptionPane.showMessageDialog(null, "Cancele la operación actual para cambiar a otra");
            }
        });

        inactiveButton.addActionListener(e -> {
            if (carFlaAct == 0) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione en la tabla la fila a inactivar");
                    return;
                }
                codField.setText(table.getValueAt(row, 0).toString());
                nomField.setText(table.getValueAt(row, 1).toString());
                estRegField.setText(table.getValueAt(row, 2).toString());
                carFlaAct = 1;
                modo = "INACTIVATE";
                inactiveButton.setBackground(BUTTON_SELECTED);
            } else {
                JOptionPane.showMessageDialog(null, "Cancele la operación actual para cambiar a otra");
            }
        });

        reactiveButton.addActionListener(e -> {
            if (carFlaAct == 0) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione en la tabla la fila a reactivar");
                    return;
                }
                codField.setText(table.getValueAt(row, 0).toString());
                nomField.setText(table.getValueAt(row, 1).toString());
                estRegField.setText(table.getValueAt(row, 2).toString());
                carFlaAct = 1;
                modo = "REACTIVATE";
                reactiveButton.setBackground(BUTTON_SELECTED);
            } else {
                JOptionPane.showMessageDialog(null, "Cancele la operación actual para cambiar a otra");
            }
        });

        cancelButton.addActionListener(e -> {
            switch (modo) {
                case "ADD" -> addButton.setBackground(TABLE_HEADER);
                case "MODIFY" -> modifyButton.setBackground(TABLE_HEADER);
                case "DELETE" -> deleteButton.setBackground(TABLE_HEADER);
                case "INACTIVATE" -> inactiveButton.setBackground(TABLE_HEADER);
                case "REACTIVATE" -> reactiveButton.setBackground(TABLE_HEADER);
            }
            carFlaAct = 0;
            clearFields();
            setFieldsNotEditable();
        });

        updateButton.addActionListener(e -> {
            if (carFlaAct == 0) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún comando");
                return;
            }
            int cod = codField.getText().isEmpty() ? 0 : Integer.parseInt(codField.getText());
            String nom = nomField.getText();
            String estReg = estRegField.getText();

            Departamento dep = new Departamento(cod, nom, estReg);
            try {
                DepartamentoDAO dao = new DepartamentoDAO();
                switch (modo) {
                    case "ADD" -> {
                        dao.agregar(dep);
                        JOptionPane.showMessageDialog(null, "Registro agregado correctamente", "Agregación de Registro", JOptionPane.INFORMATION_MESSAGE);
                        addButton.setBackground(TABLE_HEADER);
                        refreshTable(true);
                        clearFields();
                    }
                    case "MODIFY" -> {
                        dao.modificar(dep);
                        JOptionPane.showMessageDialog(null, "El registro fue modificado correctamente", "Modificación de Registro", JOptionPane.INFORMATION_MESSAGE);
                        modifyButton.setBackground(TABLE_HEADER);
                        refreshTable(false);
                        clearFields();
                    }
                    case "DELETE" -> {
                        dao.eliminar(dep.getDepCod());
                        JOptionPane.showMessageDialog(null, "El registro fue marcado como eliminado correctamente", "Eliminación de Registro", JOptionPane.INFORMATION_MESSAGE);
                        deleteButton.setBackground(TABLE_HEADER);
                        refreshTable(false);
                        clearFields();
                    }
                    case "INACTIVATE" -> {
                        dao.inactivar(dep.getDepCod());
                        JOptionPane.showMessageDialog(null, "El registro fue marcado como inactivado correctamente", "Inactivación de Registro", JOptionPane.INFORMATION_MESSAGE);
                        inactiveButton.setBackground(TABLE_HEADER);
                        refreshTable(false);
                        clearFields();
                    }
                    case "REACTIVATE" -> {
                        dao.reactivar(dep.getDepCod());
                        JOptionPane.showMessageDialog(null, "El registro fue marcado como activado correctamente", "Reactivación de Registro", JOptionPane.INFORMATION_MESSAGE);
                        reactiveButton.setBackground(TABLE_HEADER);
                        refreshTable(false);
                        clearFields();
                    }
                }
                carFlaAct = 0;
                setFieldsNotEditable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al actualizar el registro", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        exitButton.addActionListener(e -> dispose());
    }

    private void clearFields() {
        nomField.setText("");
        codField.setText("");
        estRegField.setText("");
        table.clearSelection();
    }

    private void setFieldsNotEditable() {
        setFieldEditable(nomField, false);
        setFieldEditable(codField, false);
        setFieldEditable(estRegField, false);
    }

    private void refreshTable(boolean toFinal) {
        model.setRowCount(0);
        getTableData(model);
        if (toFinal) {
            int lastRow = model.getRowCount() - 1;
            if (lastRow >= 0) {
                table.scrollRectToVisible(table.getCellRect(lastRow, 0, true));
            }
        }
    }
}