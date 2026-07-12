package unsa.bd.commons.utility;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("SameParameterValue")
public abstract class BaseReporte extends JInternalFrame {

    // Colores
    protected static final Color FONDO            = new Color(248, 250, 252);
    protected static final Color BORDER           = new Color(47, 52, 60);
    protected static final Color TABLE_HEADER     = new Color(51, 136, 224);
    protected static final Color TABLE_SELECTION  = new Color(126, 179, 245);

    // Fuentes
    protected static final Font HEADER_FONT  = new Font("SansSerif", Font.BOLD, 25);
    protected static final Font SECTION_FONT = new Font("SansSerif", Font.BOLD, 18);
    protected static final Font TABLE_FONT   = new Font("SansSerif", Font.PLAIN, 14);
    protected static final Font TABLEH_FONT  = new Font("SansSerif", Font.BOLD, 15);
    protected static final Font LABEL_FONT   = new Font("SansSerif", Font.BOLD, 15);

    // Tabla
    protected DefaultTableModel tableModel;
    protected JTable table;

    // Constructor
    public BaseReporte(String title) {
        this.setTitle(title);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setSize(600, 800);
        try { this.setMaximum(true); }
        catch (Exception ignored){}
        buildUI();
        this.setVisible(true);
    }

    protected abstract void loadTableData();
    protected abstract String[] getColumnNames();
    protected abstract String getHeaderTitle();

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
        centerPanel.add(buildFormSection(), BorderLayout.NORTH);
        centerPanel.add(buildTableSection(),   BorderLayout.CENTER);
        return centerPanel;
    }

    // Formulario
    private JPanel buildFormSection() {
        JPanel wrapper = styledCard();
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        wrapper.setBackground(Color.WHITE);

        JLabel subtitle = new JLabel(" Información del reporte");
        subtitle.setFont(SECTION_FONT);
        subtitle.setPreferredSize(new Dimension(0, 28));
        subtitle.setBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, TABLE_HEADER)
        );
        wrapper.add(subtitle, BorderLayout.NORTH);

        JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        info.setOpaque(false);

        String fecha = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        JLabel lblFecha = new JLabel("Fecha de generación: " + fecha);
        lblFecha.setFont(LABEL_FONT);

        info.add(lblFecha);
        wrapper.add(info, BorderLayout.CENTER);

        return wrapper;
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
        autoFitColumnWidths();

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    private void autoFitColumnWidths() {
    for (int col = 0; col < table.getColumnCount(); col++) {
        TableColumn column = table.getColumnModel().getColumn(col);
        int width = 50;

        TableCellRenderer headerRenderer = column.getHeaderRenderer();
        if (headerRenderer == null) headerRenderer = table.getTableHeader().getDefaultRenderer();
        Component headerComp = headerRenderer.getTableCellRendererComponent(
                table, column.getHeaderValue(), false, false, 0, col);
        width = Math.max(width, headerComp.getPreferredSize().width);

        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
            Component comp = table.prepareRenderer(cellRenderer, row, col);
            width = Math.max(width, comp.getPreferredSize().width);
        }

        column.setPreferredWidth(width + 20);
    }
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
}