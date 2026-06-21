package unsa.bd.views.forms;

import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.function.Function;

@SuppressWarnings("SameParameterValue")
public abstract class BaseForm extends JInternalFrame {

    // Colores
    protected static final Color FONDO            = new Color(248, 250, 252);
    protected static final Color BORDER           = new Color(47, 52, 60);
    protected static final Color TABLE_HEADER     = new Color(51, 136, 224);
    protected static final Color TABLE_SELECTION  = new Color(126, 179, 245);
    protected static final Color DISABLED         = new Color(0xE5E5E5);
    protected static final Color BUTTON_SELECTED  = new Color(255, 31, 31);

    // Fuentes
    protected static final Font HEADER_FONT  = new Font("SansSerif", Font.BOLD, 25);
    protected static final Font BUTTON_FONT  = new Font("SansSerif", Font.BOLD, 14);
    protected static final Font SECTION_FONT = new Font("SansSerif", Font.BOLD, 18);
    protected static final Font LABEL_FONT   = new Font("SansSerif", Font.BOLD, 15);
    protected static final Font FIELD_FONT   = new Font("SansSerif", Font.PLAIN, 15);
    protected static final Font TABLE_FONT   = new Font("SansSerif", Font.PLAIN, 14);
    protected static final Font TABLEH_FONT  = new Font("SansSerif", Font.BOLD, 15);

    // Botones
    protected JButton addButton;
    protected JButton modifyButton;
    protected JButton deleteButton;
    protected JButton cancelButton;
    protected JButton inactiveButton;
    protected JButton reactiveButton;
    protected JButton updateButton;
    protected JButton exitButton;

    // Tabla
    protected DefaultTableModel tableModel;
    protected JTable table;

    // Estados
    protected int    carFlaAct = 0;   // 0 = libre, 1 = en operación
    protected FormMode modo = FormMode.DEFAULT;

    private int actualFormRow = 0;

    // Constructor
    public BaseForm(String title) {
        this.setTitle(title);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(600, 800);
        try { this.setMaximum(true); }
        catch (Exception ignored){}
        buildUI();
        bindButtonActions();
        this.setVisible(true);
    }

    // Metodos abstractos

    // Texto que aparece en el encabezado azul
    protected abstract String getHeaderTitle();

    // Construir panel con campos de entrada
    protected abstract JPanel buildFormFields(JPanel formPanel);

    // Nombres de las columnas en orden
    protected abstract String[] getColumnNames();

    // Metodo para cargar objetos a tabla
    protected abstract void loadTableData();

    // Ejecutar DAO
    protected abstract void onAdd() throws Exception;

    // Ejecutar DAO
    protected abstract void onModify() throws Exception;

    // Ejecutar DAO
    protected abstract void onDelete() throws Exception;

    // Ejecutar DAO
    protected abstract void onInactivate() throws Exception;

    // Ejecutar DAO
    protected abstract void onReactivate() throws Exception;

    // Metodo para llenar tabla con fila seleccionada
    protected abstract void fillFormFromRow(int row);

    // Metodo para limipiar los campos del formulario
    protected abstract void clearFields();

    // Habilitar o desabilitar los campos
    protected abstract void setFieldsEditable(boolean editable);

    // Retorna el estado de registro de la fila seleccionada
    protected abstract String getEstadoFromRow(int row);


    // UI
    private void buildUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(FONDO);
        mainPanel.add(buildHeader(),        BorderLayout.NORTH);
        mainPanel.add(buildCenterContent(), BorderLayout.CENTER);
        this.add(mainPanel);
    }

    // Header
    private JPanel buildHeader() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 60));
        titlePanel.setBackground(TABLE_HEADER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(getHeaderTitle());
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel buildCenterContent() {
        JPanel centerPanel = new JPanel(new BorderLayout(10, 5));
        centerPanel.setOpaque(false);
        centerPanel.add(buildFormSection(),    BorderLayout.NORTH);
        centerPanel.add(buildTableSection(),   BorderLayout.CENTER);
        centerPanel.add(buildButtonsSection(), BorderLayout.SOUTH);
        return centerPanel;
    }

    // Formulario
    private JPanel buildFormSection() {
        JPanel wrapper = styledCard();
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        wrapper.setBackground(Color.WHITE);

        JLabel subtitle = new JLabel(" Formulario de datos");
        subtitle.setFont(SECTION_FONT);
        subtitle.setPreferredSize(new Dimension(0, 28));
        subtitle.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, TABLE_HEADER));
        wrapper.add(subtitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12, 5, 10, 5));
        form.setOpaque(false);
        wrapper.add(buildFormFields(form), BorderLayout.CENTER);

        return wrapper;
    }

    protected void addFieldRowToForm(JPanel form, String label, JTextField field) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 20, 4, 20);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = actualFormRow; c.weightx = 0; c.fill = GridBagConstraints.NONE;
        form.add(makeStyledLabel(label), c);
        setFieldEditable(field, false);
        c.gridx = 1; c.weightx = 1;
        form.add(field, c);
        actualFormRow++;
    }

    protected <T> void addFieldRowToForm(JPanel form, String label, JComboBox<T> box) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 20, 4, 20);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = actualFormRow; c.weightx = 0; c.fill = GridBagConstraints.NONE;
        form.add(makeStyledLabel(label), c);
        setComboEditable(box, false);
        c.gridx = 1; c.weightx = 1;
        form.add(box, c);
        actualFormRow++;
    }

    protected int[] getColumnWidths() {
        return null;
    }

    // Tabla
    private JPanel buildTableSection() {
        JPanel wrapper = styledCard();
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrapper.setOpaque(false);

        JLabel subtitle = new JLabel(" Tabla de datos");
        subtitle.setFont(SECTION_FONT);
        subtitle.setPreferredSize(new Dimension(0, 28));
        subtitle.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, TABLE_HEADER));
        wrapper.add(subtitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        for (String col : getColumnNames()) tableModel.addColumn(col);
        loadTableData();
        table = buildStyledTable(tableModel);

        configureTableHeader();
        configureTableCellRenderer();
        int[] widhts = getColumnWidths();
        if (widhts != null) {
            for (int i = 0; i < widhts.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(widhts[i]);
            }
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    private void configureTableHeader() {
        JTableHeader header = table.getTableHeader();
        DefaultTableCellRenderer hr = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                lbl.setFont(TABLEH_FONT);
                lbl.setForeground(Color.WHITE);
                lbl.setBackground(TABLE_HEADER);
                lbl.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 1, BORDER),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
                return lbl;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setHeaderRenderer(hr);

        header.setPreferredSize(new Dimension(0, 32));
        header.setBackground(TABLE_HEADER);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
        header.setBorder(BorderFactory.createEmptyBorder());
    }

    private void configureTableCellRenderer() {
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                if (comp instanceof JLabel lbl)
                    lbl.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                return comp;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(cr);
    }

    private static JTable buildStyledTable(DefaultTableModel model) {
        JTable t = new JTable(model) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        t.setFont(TABLE_FONT);
        t.setRowHeight(28);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setGridColor(BORDER);
        t.setSelectionBackground(TABLE_SELECTION);
        t.setForeground(Color.BLACK);
        t.setFillsViewportHeight(true);
        return t;
    }


    // Botones
    private JPanel buildButtonsSection() {
        JPanel outer = styledCard();
        outer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        outer.setOpaque(false);

        JPanel grid = new JPanel(new GridLayout(2, 4, 8, 8));
        grid.setOpaque(false);

        addButton      = makeButton("Adicionar");  grid.add(addButton);
        modifyButton   = makeButton("Modificar");  grid.add(modifyButton);
        deleteButton   = makeButton("Eliminar");   grid.add(deleteButton);
        cancelButton   = makeButton("Cancelar");   grid.add(cancelButton);
        inactiveButton = makeButton("Inactivar");  grid.add(inactiveButton);
        reactiveButton = makeButton("Reactivar");  grid.add(reactiveButton);
        updateButton   = makeButton("Actualizar"); grid.add(updateButton);
        exitButton     = makeButton("Salir");      grid.add(exitButton);

        outer.add(grid, BorderLayout.CENTER);
        return outer;
    }

    // Logica de botones
    private void bindButtonActions() {

        // Agregar
        addButton.addActionListener(e -> {
            if (carFlaAct != 0) { showMessageModeActived(); return; }
            carFlaAct = 1;
            modo = FormMode.ADD;
            setFieldsEditable(true);
            addButton.setBackground(BUTTON_SELECTED);
        });

        // Modificar
        modifyButton.addActionListener(e -> {
            if (carFlaAct != 0) { showMessageModeActived(); return; }
            int row = getSelectedRow("modificar"); if (row == -1) return;

            String estado = getEstadoFromRow(row);
            if ("*".equalsIgnoreCase(estado)) {
                JOptionPane.showMessageDialog(null,
                        "No se puede modificar un registro eliminado",
                        "Modificación no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            fillFormFromRow(row);
            setFieldsEditable(true);
            carFlaAct = 1;
            modo = FormMode.MODIFY;
            modifyButton.setBackground(BUTTON_SELECTED);
        });

        // Eliminar
        deleteButton.addActionListener(e -> {
            if (carFlaAct != 0) { showMessageModeActived(); return; }
            int row = getSelectedRow("eliminar"); if (row == -1) return;

            String estado = getEstadoFromRow(row);
            if ("*".equalsIgnoreCase(estado)) {
                JOptionPane.showMessageDialog(null,
                        "El registro ya está eliminado.",
                        "Eliminación no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            fillFormFromRow(row);
            carFlaAct = 1;
            modo = FormMode.DELETE;
            deleteButton.setBackground(BUTTON_SELECTED);
        });

        // Inactivar
        inactiveButton.addActionListener(e -> {
            if (carFlaAct != 0) { showMessageModeActived(); return; }
            int row = getSelectedRow("inactivar"); if (row == -1) return;

            String estado = getEstadoFromRow(row);
            if ("I".equalsIgnoreCase(estado)) {
                JOptionPane.showMessageDialog(null,
                        "El registro ya está inactivado.",
                        "Inactivación no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if ("*".equalsIgnoreCase(estado)) {
                JOptionPane.showMessageDialog(null,
                        "No se puede inactivar un registro eliminado.",
                        "Inactivación no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            fillFormFromRow(row);
            carFlaAct = 1;
            modo = FormMode.INACTIVATE;
            inactiveButton.setBackground(BUTTON_SELECTED);
        });

        // Reactivar
        reactiveButton.addActionListener(e -> {
            if (carFlaAct != 0) { showMessageModeActived(); return; }
            int row = getSelectedRow("reactivar"); if (row == -1) return;

            String estado = getEstadoFromRow(row);
            if ("A".equalsIgnoreCase(estado)) {
                JOptionPane.showMessageDialog(null,
                        "El registro ya está activo",
                        "Reactivación no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            fillFormFromRow(row);
            carFlaAct = 1;
            modo = FormMode.REACTIVATE;
            reactiveButton.setBackground(BUTTON_SELECTED);
        });

        // Cancerlar
        cancelButton.addActionListener(e -> {
            resetButtonColor();
            carFlaAct = 0;
            modo = FormMode.DEFAULT;
            clearFields();
            setFieldsEditable(false);
        });

        // Actualizar
        updateButton.addActionListener(e -> {
            if (carFlaAct == 0) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún comando.");
                return;
            }
            try {
                switch (modo) {
                    case ADD        -> { onAdd();        showSuccessMsg("agregado");   addButton.setBackground(TABLE_HEADER);      refreshTable(true);  }
                    case MODIFY     -> { onModify();     showSuccessMsg("modificado"); modifyButton.setBackground(TABLE_HEADER);   refreshTable(false); }
                    case DELETE     -> { onDelete();     showSuccessMsg("eliminado");  deleteButton.setBackground(TABLE_HEADER);   refreshTable(false); }
                    case INACTIVATE -> { onInactivate(); showSuccessMsg("inactivado"); inactiveButton.setBackground(TABLE_HEADER); refreshTable(false); }
                    case REACTIVATE -> { onReactivate(); showSuccessMsg("reactivado"); reactiveButton.setBackground(TABLE_HEADER); refreshTable(false); }
                }
                carFlaAct = 0;
                modo = FormMode.DEFAULT;
                clearFields();
                setFieldsEditable(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Ocurrió un error al actualizar el registro:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Salir
        exitButton.addActionListener(e -> dispose());
    }


    // Crear etiqueta estilizada
    protected JLabel makeStyledLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(LABEL_FONT);
        lbl.setPreferredSize(new Dimension(100, lbl.getPreferredSize().height));
        return lbl;
    }

    // Crear campo de texto estilizado
    protected JTextField makeStyledField(int cols) {
        JTextField field = cols > 0 ? new JTextField(cols) : new JTextField();
        field.setFont(FIELD_FONT);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 32));
        return field;
    }

    // Metodo para activar o desactivar campo de texto
    protected void setFieldEditable(JTextField field, boolean editable) {
        field.setEditable(editable);
        field.setFocusable(editable);
        field.setBackground(editable ? Color.WHITE : DISABLED);
    }

    protected <T> JComboBox<T> makeStyledComboBox(Function<T, String> labelExtractor) {
        JComboBox<T> box = new JComboBox<>();

        box.setUI(new BasicComboBoxUI() {
            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(Color.WHITE);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });

        box.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                lbl.setFont(FIELD_FONT);
                lbl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                lbl.setOpaque(true);
                lbl.setBackground(isSelected ? TABLE_SELECTION : Color.WHITE);
                if (value != null) {
                    @SuppressWarnings("unchecked")
                    T item = (T) value;
                    lbl.setText(labelExtractor.apply(item));
                }
                return lbl;
            }
        });

        box.setFont(FIELD_FONT);
        box.setOpaque(true);
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        box.setPreferredSize(new Dimension(200, 32));
        return box;
    }

    protected <T> void setComboEditable(JComboBox<T> box, boolean editable) {
        box.setEnabled(editable);
    }


    // Crear boton estilizado
    protected JButton makeButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base    = getBackground();
                Color current = getModel().isPressed()  ? base.darker()
                              : getModel().isRollover() ? base.brighter()
                              : base;
                g2.setColor(current);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2.setColor(current.darker());
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

    // Crear panel estilizado
    protected static JPanel styledCard() {
        return new JPanel(new BorderLayout(0, 5)) {
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

    // Rrefresca la tabla y recarga los datos
    protected void refreshTable(boolean scrollToEnd) {
        tableModel.setRowCount(0);
        loadTableData();
        if (scrollToEnd) {
            int last = tableModel.getRowCount() - 1;
            if (last >= 0) table.scrollRectToVisible(table.getCellRect(last, 0, true));
        }
    }

    // Private
    private int getSelectedRow(String action) {
        int row = table.getSelectedRow();
        if (row == -1)
            JOptionPane.showMessageDialog(null,
                    "Seleccione en la tabla la fila a " + action + ".");
        return row;
    }

    private void showMessageModeActived() {
        JOptionPane.showMessageDialog(null,
                "Cancele la operación actual para cambiar a otra.");
    }

    private void showSuccessMsg(String accion) {
        String titulo = "Registro " + accion;
        JOptionPane.showMessageDialog(null,
                "El registro fue " + accion + " correctamente.",
                titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetButtonColor() {
        switch (modo) {
            case ADD        -> addButton.setBackground(TABLE_HEADER);
            case MODIFY     -> modifyButton.setBackground(TABLE_HEADER);
            case DELETE     -> deleteButton.setBackground(TABLE_HEADER);
            case INACTIVATE -> inactiveButton.setBackground(TABLE_HEADER);
            case REACTIVATE -> reactiveButton.setBackground(TABLE_HEADER);
        }
    }
}