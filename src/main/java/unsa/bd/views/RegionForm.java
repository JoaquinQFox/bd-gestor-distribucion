package unsa.bd.views;

import unsa.bd.dao.RegionDAO;
import unsa.bd.model.Region;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class RegionForm extends JFrame {
    // Colores
    private static final Color FONDO = new Color(248, 250, 252);
    private static final Color BORDER = new Color(47, 52, 60);
    private static final Color TABLE_HEADER = new Color(51, 136, 224);
    private static final Color TABLE_SELECTION = new Color(126, 179, 245);
    private static final Color DISABLED = new Color(0xE5E5E5);

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
    private JComboBox<String> estRegFieldBox;

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
    private JTable tableRegion;

    private int carFlaAct = 0;

    public RegionForm() {
        this.setSize(600, 800);
        this.setTitle("Region - Formulario");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.generateContent();
        this.generateButtonsAction();
        this.generateTableAction();
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

        JLabel titleLabel = new JLabel("REGIÓN");
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

    // Sección de campos de arriba
    private JPanel getRegisterSectionPanel() {
        JPanel registerPanel = getStyledSectionPanel();
        registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        registerPanel.setBackground(Color.WHITE);

        // Titulo de seccion
        JLabel subtitleLabel = new JLabel(" Formulario de datos");
        subtitleLabel.setFont(SECTION_FONT);
        subtitleLabel.setPreferredSize(new Dimension(0, 28));
        subtitleLabel.setBorder(BorderFactory.createMatteBorder(0,3, 0, 0, TABLE_HEADER));
        registerPanel.add(subtitleLabel, BorderLayout.NORTH);

        // Formulario
        JPanel registerFormPanel = new JPanel(new GridBagLayout());
        registerFormPanel.setBorder(BorderFactory.createEmptyBorder(12, 5, 10, 5));
        registerFormPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 20, 10, 20);
        c.anchor = GridBagConstraints.WEST;

        // Codigo
        c.gridx = 0; c.gridy = 0; c.weightx = 0.0; c.fill = GridBagConstraints.NONE;
        registerFormPanel.add(makeStyledLabel("Código:"), c);

        codField = makeStyledField(10);
        setFieldEditable(codField, false);
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0; c.fill = GridBagConstraints.NONE;
        registerFormPanel.add(codField, c);

        // Nombre
        c.gridx = 0; c.gridy = 1; c.weightx = 0.0; c.fill = GridBagConstraints.NONE;
        registerFormPanel.add(makeStyledLabel("Nombre:"), c);

        nomField = makeStyledField(0);
        c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL;
        registerFormPanel.add(nomField, c);

        // Estado de registro
        c.gridx = 0; c.gridy = 2; c.weightx = 0.0; c.fill = GridBagConstraints.NONE;
        registerFormPanel.add(makeStyledLabel("Est. Reg.:"), c);

        String[] opciones = {"A", "I"};
        estRegFieldBox = makeStyledComboBox(opciones);
        setJComboBoxEditable(estRegFieldBox, false);
        c.gridx = 1; c.gridy = 2; c.weightx = 1.0; c.fill = GridBagConstraints.NONE;
        registerFormPanel.add(estRegFieldBox, c);

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
            field.setBackground(Color.WHITE);
        } else {
            field.setEditable(false);
            field.setFocusable(false);
            field.setBackground(DISABLED);
        }
    }

    private JComboBox<String> makeStyledComboBox(String[] opciones) {
        JComboBox<String> box = new JComboBox<>(opciones);

        box.setUI(new BasicComboBoxUI() {
            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(Color.WHITE); // fondo limpio siempre
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });

        box.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                label.setFont(FIELD_FONT);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                label.setOpaque(true);

                label.setBackground(isSelected ? TABLE_SELECTION : Color.WHITE);

                return label;
            }
        });

        box.setFont(FIELD_FONT);
        box.setOpaque(true);
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        box.setPreferredSize(new Dimension(100, 32));
        return box;
    }

    private void setJComboBoxEditable(JComboBox<String> box, boolean editable) {
        box.setEnabled(editable);
    }

    private JPanel getTablePanel() {
        JPanel tablePanel = getStyledSectionPanel();
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablePanel.setOpaque(false);

        // Titulo de seccion
        JLabel subtitleLabel = new JLabel(" Tabla de datos");
        subtitleLabel.setFont(SECTION_FONT);
        subtitleLabel.setPreferredSize(new Dimension(0, 28));
        subtitleLabel.setBorder(BorderFactory.createMatteBorder(0,3, 0, 0, TABLE_HEADER));
        tablePanel.add(subtitleLabel, BorderLayout.NORTH);

        // Tabla
        model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("Est. Reg.");

        getTableData(model);
        tableRegion = getJTable(model);

        JTableHeader tableHeader = tableRegion.getTableHeader();
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

        for (int i = 0; i < tableRegion.getColumnCount(); i++) {
            tableRegion.getColumnModel()
                    .getColumn(i)
                    .setHeaderRenderer(headerRenderer);
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

        for (int i = 0; i < tableRegion.getColumnCount(); i++) {
            tableRegion.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        tableRegion.getColumnModel().getColumn(0).setPreferredWidth(120);
        tableRegion.getColumnModel().getColumn(1).setPreferredWidth(340);
        tableRegion.getColumnModel().getColumn(2).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(tableRegion);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER  , 1));

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private static JTable getJTable(DefaultTableModel model) {
        JTable tableRegion = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableRegion.setFont(TABLE_FONT);
        tableRegion.setRowHeight(28);
        tableRegion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableRegion.setGridColor(BORDER);
        tableRegion.setSelectionBackground(TABLE_SELECTION);
        tableRegion.setForeground(Color.BLACK);
        tableRegion.setFillsViewportHeight(true);
        return tableRegion;
    }

    private static void getTableData(DefaultTableModel model) {
        RegionDAO dao = new RegionDAO();
        try {
            List<Region> regiones = dao.listarTodo();
            for (Region r : regiones) {
                model.addRow(new Object[]{
                        r.getRegCod(),
                        r.getRegNom(),
                        r.getRegEstReg()
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
                g2.setColor(Color.WHITE);
                g2.dispose();
            }
        };
    }

    // Seccion de botones
    private JPanel getButtonsPanel() {
        JPanel outerPanel = getStyledSectionPanel();
        outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        outerPanel.setOpaque(false);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 4, 8, 8));
        buttonsPanel.setOpaque(false);

        addButton = makeButton("Adicionar");
        buttonsPanel.add(addButton);

        modifyButton = makeButton("Modificar");
        buttonsPanel.add(modifyButton);

        deleteButton = makeButton("Eliminar");
        buttonsPanel.add(deleteButton);

        cancelButton = makeButton("Cancelar");
        buttonsPanel.add(cancelButton);

        inactiveButton = makeButton("Inactivar");
        buttonsPanel.add(inactiveButton);

        reactiveButton = makeButton("Reactivar");
        buttonsPanel.add(reactiveButton);

        updateButton = makeButton("Actualizar");
        buttonsPanel.add(updateButton);

        exitButton = makeButton("Salir");
        buttonsPanel.add(exitButton);

        outerPanel.add(buttonsPanel, BorderLayout.CENTER);
        return outerPanel;
    }

    private JButton makeButton(String text) {
        Color bgColor = TABLE_HEADER;

        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                Color currentColor;

                if (getModel().isPressed()) {
                    currentColor = bgColor.darker();
                } else if (getModel().isRollover()) {
                    currentColor = bgColor.brighter();
                } else {
                    currentColor = bgColor;
                }

                g2.setColor(currentColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);

                g2.setColor(currentColor.darker());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);

                g2.dispose();
                super.paintComponent(g);
        }};

        btn.setFont(BUTTON_FONT);
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
            RegionDAO dao = new RegionDAO();
            Region region = new Region();
            region.setRegNom(nomField.getText());
            region.setRegEstReg(estRegFieldBox.getSelectedItem().toString());
            try {
                dao.agregar(region);
                JOptionPane.showMessageDialog(null, "Region " + region.getRegNom() + " agregada correctamente");
                refreshTable();
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al agregar dato", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        modifyButton.addActionListener(e -> {
            if (carFlaAct == 0) {
                int row = tableRegion.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione en la tabla la fila a modificar");
                    return;
                }

                String cod = tableRegion.getValueAt(row, 0).toString();
                String nom = tableRegion.getValueAt(row, 1).toString();
                String estReg = tableRegion.getValueAt(row, 2).toString();

                codField.setText(cod);
                nomField.setText(nom);
                estRegFieldBox.setSelectedItem(estReg);

                carFlaAct = 1;
            } else {
                try {
                    int cod = Integer.parseInt(codField.getText());
                    String nom = nomField.getText();
                    String estReg = estRegFieldBox.getSelectedItem().toString();

                    RegionDAO dao = new RegionDAO();
                    dao.modificar(new Region(cod, nom, estReg));
                    JOptionPane.showMessageDialog(null, "El registro fue modificado correctamente");

                    carFlaAct = 0;
                    clearFields();
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al modificar el dato", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> {
            carFlaAct = 0;
            clearFields();
        });
    }

    private void generateTableAction() {
        tableRegion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int row = tableRegion.getSelectedRow();
                    if (row == -1) return;

                    codField.setText(tableRegion.getValueAt(row, 0).toString());
                    nomField.setText(tableRegion.getValueAt(row, 1).toString());

                    String estado = tableRegion.getValueAt(row, 2).toString();
                    estRegFieldBox.setSelectedItem(estado);
                }
            }
        });
    }

    private void clearFields() {
        nomField.setText("");
        codField.setText("");
        estRegFieldBox.setSelectedItem("A");
        tableRegion.clearSelection();
    }

    private void refreshTable() {
        model.setRowCount(0);
        getTableData(model);

        int lastRow = model.getRowCount() - 1;
        if (lastRow >= 0) {
            tableRegion.scrollRectToVisible(
                    tableRegion.getCellRect(lastRow, 0, true)
            );
        }
    }

    public static void main(String[] args) {
        UIManager.put("ComboBox.disabledBackground", DISABLED);
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);

        new RegionForm();
    }
}
