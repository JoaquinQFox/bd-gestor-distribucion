package unsa.bd.views;

import unsa.bd.views.forms.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final Color DISABLED = new Color(0xE5E5E5);
    private static final Color BLUE       = new Color(51, 136, 224);
    private static final Color BLUE_DARK  = new Color(35, 100, 180);
    private static final Color FONDO      = new Color(248, 250, 252);
    private static final Font  MENU_FONT  = new Font("SansSerif", Font.BOLD, 14);
    private static final Font  TITLE_FONT = new Font("SansSerif", Font.BOLD, 16);

    private JDesktopPane desktopPane;
    private JInternalFrame currentFrame;

    public MainFrame() {
        setTitle("Sistema de Distribución");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 680);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());

        JPanel sidebar = buildSidebar();

        JScrollPane sideBarScroll = new JScrollPane(sidebar);
        sideBarScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sideBarScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sideBarScroll.setBorder(null);
        sideBarScroll.setPreferredSize(new Dimension(196, 0));
        sideBarScroll.getVerticalScrollBar().setUnitIncrement(16);

        root.add(sideBarScroll, BorderLayout.WEST);
        root.add(buildDesktop(), BorderLayout.CENTER);
        setContentPane(root);

        setVisible(true);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BLUE);

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

        addItem(sidebar, "Region nuevo", () -> openFrame(new RegionForm()));
        addItem(sidebar, "Ciudad", () -> openFrame(new CiudadForm()));
        addItem(sidebar, "Departamento", () -> openFrame(new DepartamentoForm()));
        addItem(sidebar, "Cargo", () -> openFrame(new CargoForm()));
        addItem(sidebar, "Accion", () -> openFrame(new AccionForm()));
        addItem(sidebar, "Clasificación Producto", () -> openFrame(new ClasificacionProductoForm()));
        addItem(sidebar, "Estado Factura", () -> openFrame(new EstadoFacturaForm()));
        addItem(sidebar, "Estado Pedido", () -> openFrame(new EstadoPedidoForm()));
        addItem(sidebar, "Escala Credito", () -> openFrame(new EscalaCreditoForm()));
        addItem(sidebar, "Unidad Medida", () -> openFrame(new UnidadMedidaForm()));
        addItem(sidebar, "Rol Usuario", () -> openFrame(new RolUsuarioForm()));
        addItem(sidebar, "Tipo Cliente", () -> openFrame(new TipoClienteForm()));
        addItem(sidebar, "Tipo Factura", () -> openFrame(new TipoFacturaForm()));
        addItem(sidebar, "Fabricante", () -> openFrame(new FabricanteForm()));
        addItem(sidebar, "Producto", () -> openFrame(new ProductoForm()));
        addItem(sidebar, "Oficina", () -> openFrame(new OficinaForm()));
        addItem(sidebar, "Rep. Ventas", () -> openFrame(new RepVentasForm()));
        addItem(sidebar, "Cliente", () -> openFrame(new ClienteForm()));
        addItem(sidebar, "Almacen", () -> openFrame(new AlmacenForm()));
        addItem(sidebar, "Empresa", () -> openFrame(new EmpresaForm()));

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

    public static void main(String[] args) {
        UIManager.put("ComboBox.disabledBackground", DISABLED);
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);
        new MainFrame();
    }
}