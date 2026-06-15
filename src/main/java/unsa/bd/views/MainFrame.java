package unsa.bd.views;

import unsa.bd.views.forms.EscalaCreditoForm;
import unsa.bd.views.forms.RegionForm;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final Color BLUE       = new Color(51, 136, 224);
    private static final Color BLUE_DARK  = new Color(35, 100, 180);
    private static final Color FONDO      = new Color(248, 250, 252);
    private static final Font  MENU_FONT  = new Font("SansSerif", Font.BOLD, 14);
    private static final Font  TITLE_FONT = new Font("SansSerif", Font.BOLD, 16);

    private JDesktopPane desktopPane;
    private JInternalFrame currentFrame;

    public MainFrame() {
        setTitle("Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 680);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.add(buildSidebar(), BorderLayout.WEST);
        root.add(buildDesktop(), BorderLayout.CENTER);
        setContentPane(root);

        setVisible(true);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BLUE);
        sidebar.setPreferredSize(new Dimension(180, 0));

        JLabel title = new JLabel("TABLAS");
        title.setFont(TITLE_FONT);
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 16, 16, 16));
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        sidebar.add(title);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 60));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(8));

        addItem(sidebar, "Región", () -> openFrame(new RegionForm("Región")));
        addItem(sidebar, "Ciudad", () -> openFrame(placeholder("Provincia")));
        addItem(sidebar, "Departamento", () -> openFrame(placeholder("Distrito")));
        addItem(sidebar, "Cargo", () -> openFrame(placeholder("Departamento")));
        addItem(sidebar, "Tipo de acción", () -> openFrame(placeholder("Banco")));
        addItem(sidebar, "Cargo", () -> openFrame(placeholder("Moneda")));
        addItem(sidebar, "Clasificación Producto", () -> openFrame(placeholder("Categoría")));
        addItem(sidebar, "Estado Factura", () -> openFrame(placeholder("Proveedor")));
        addItem(sidebar, "Estado Pedido", () -> openFrame(placeholder("Tipo Comprobante")));
        addItem(sidebar, "Escala Credito", () -> openFrame(new EscalaCreditoForm("Escala credito")));
        addItem(sidebar, "Rol Usuario", () -> openFrame(placeholder("Estado Civil")));
        addItem(sidebar, "Tipo Cliente", () -> openFrame(placeholder("Tipo Documento")));
        addItem(sidebar, "Tipo Documento", () -> openFrame(placeholder("Tipo Documento")));
        addItem(sidebar, "Tipo Factura", () -> openFrame(placeholder("Tipo Documento")));
        addItem(sidebar, "Unidad Medida", () -> openFrame(placeholder("Tipo Documento")));

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private void addItem(JPanel sidebar, String text, Runnable action) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(getModel().isRollover() || getModel().isPressed()
                        ? BLUE_DARK : BLUE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(MENU_FONT);
        btn.setForeground(Color.WHITE);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(e -> action.run());
        sidebar.add(btn);
    }

    private JDesktopPane buildDesktop() {
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(FONDO);
        return desktopPane;
    }

    private void openFrame(JInternalFrame frame) {
        if (currentFrame != null && !currentFrame.isClosed()) {
            try { currentFrame.setClosed(true); } catch (Exception ignored) {}
        }
        currentFrame = frame;
        desktopPane.add(currentFrame);

        Dimension d = desktopPane.getSize();
        Dimension f = frame.getSize();
        frame.setLocation(Math.max(0, (d.width - f.width) / 2),
                          Math.max(0, (d.height - f.height) / 2));
        frame.setVisible(true);
        try { frame.setSelected(true); } catch (Exception ignored) {}
    }

    private JInternalFrame placeholder(String name) {
        JInternalFrame f = new JInternalFrame(name, true, true, true, true);
        f.setSize(400, 250);
        JLabel lbl = new JLabel("En construcción: " + name, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setForeground(new Color(150, 150, 150));
        f.add(lbl);
        f.setVisible(true);
        return f;
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}