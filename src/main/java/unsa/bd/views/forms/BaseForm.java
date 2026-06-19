package unsa.bd.views.forms;

import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.beans.PropertyVetoException;

public abstract class BaseForm extends JInternalFrame {
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

    private int carFlaAct = 0;
    private FormMode modo = FormMode.DEFAULT;
    private String headerTitle;
    private int fieldsNum = 0;

    protected abstract void buildFormFields(JPanel formPanel);

    public BaseForm(String windowTitle, String headerTitle) {
        setTitle(windowTitle);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setSize(600, 800);

        try {
            setMaximum(true);
        } catch (Exception ignored) {}

        this.headerTitle = headerTitle;
        buildContent();
        setVisible(true);
    }

    private void buildContent() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(FONDO);

        mainPanel.add(buildHeader(), BorderLayout.NORTH);
        mainPanel.add(buildCenter(), BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel buildHeader() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(titlePanel.getWidth(), 40));
        titlePanel.setBackground(TABLE_HEADER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(this.headerTitle);
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        return titlePanel;
    }

    private JPanel buildCenter() {
        JPanel centerPanel = new JPanel(new BorderLayout(10, 5));
        centerPanel.setOpaque(false);
        centerPanel.add(this.buildFormPanel(), BorderLayout.NORTH);
//        centerPanel.add(this.buildTablePanel(), BorderLayout.CENTER);
//        centerPanel.add(this.buildButtonsPanel(), BorderLayout.SOUTH);
        return centerPanel;
    }

    private JPanel buildFormPanel() {
        JPanel registerPanel = new SectionPanel();
        registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        registerPanel.setBackground(Color.WHITE);

        // Titulo de seccion
        JLabel subtitleLabel = new JLabel(" Formulario del registro");
        subtitleLabel.setFont(SECTION_FONT);
        subtitleLabel.setPreferredSize(new Dimension(0, 28));
        subtitleLabel.setBorder(BorderFactory.createMatteBorder(0,3, 0, 0, TABLE_HEADER));
        registerPanel.add(subtitleLabel, BorderLayout.NORTH);

        // Formulario
        JPanel registerFormPanel = new JPanel(new GridBagLayout());
        registerFormPanel.setBorder(BorderFactory.createEmptyBorder(12, 5, 10, 5));
        registerFormPanel.setOpaque(false);
        buildFormFields(registerFormPanel);

        registerPanel.add(registerFormPanel, BorderLayout.CENTER);
        return registerPanel;
    }

    protected JTextField addFormRow(JPanel formPanel, String label, int cols) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 20, 10, 20);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0; c.gridy = fieldsNum; c.weightx = 0.0; c.fill = GridBagConstraints.NONE;
        formPanel.add(makeStyledLabel(label), c);
        JTextField field = makeStyledField(cols);
//        setFieldEditable(codField, false);
        c.gridx = 1; c.gridy = fieldsNum; c.weightx = 1.0; c.fill = GridBagConstraints.NONE;
        formPanel.add(field, c);
        fieldsNum++;

        return field;
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

    private <E> JComboBox<E> makeStyledComboBox(E[] opciones) {
        JComboBox<E> box = new JComboBox<E>(opciones);
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

//    private JPanel buildTablePanel() {
//
//    }
//
//    private JPanel buildButtonsPanel() {
//
//    }

    private static class SectionPanel extends JPanel {
        public SectionPanel() {
            super();
            setLayout(new BorderLayout());
        }

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
    }
}
