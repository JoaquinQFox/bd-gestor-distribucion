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

        // CLIENTES (Subsistema D1)
        addMenuHeader(sidebar, "CLIENTES");
        addItem(sidebar, "Cliente", () -> openFrame(new ClienteForm("D13001 - CLIENTE")));
        addItem(sidebar, "Empresa", () -> openFrame(new EmpresaForm("D13002 - EMPRESA")));
        addItem(sidebar, "Persona", () -> openFrame(new PersonaForm("D13003 - PERSONA")));
        addItem(sidebar, "Tipo Cliente", () -> openFrame(new TipoClienteForm("D13004 - TIPO CLIENTE")));
        addItem(sidebar, "Tipo Documento", () -> openFrame(new TipoDocumentoForm("D13005 - TIPO DOCUMENTO")));
        addItem(sidebar, "Escala Credito", () -> openFrame(new EscalaCreditoForm("D13006 - ESCALA CRÉDITO")));

        // VENTAS (Subsistema D2)
        addMenuHeader(sidebar, "VENTAS");
        addItem(sidebar, "Oficina", () -> openFrame(new OficinaForm("D23001 - OFICINA")));
        addItem(sidebar, "Rep. Ventas", () -> openFrame(new RepVentasForm("D23002 - REPRESENTANTE DE VENTAS")));
        addItem(sidebar, "Cargo", () -> openFrame(new CargoForm("D23003 - CARGO")));
        addItem(sidebar, "Region", () -> openFrame(new RegionForm("D23004 - REGIÓN")));
        addItem(sidebar, "Departamento", () -> openFrame(new DepartamentoForm("D23005 - DEPARTAMENTO")));
        addItem(sidebar, "Ciudad", () -> openFrame(new CiudadForm("D23006 - CIUDAD")));

        // FACTURACIÓN (Subsistema D3)
        addMenuHeader(sidebar, "FACTURACIÓN");
        addItem(sidebar, "Factura Cab", () -> openFrame(new FacturaCabForm("D33001 - FACTURA CABECERA")));
        addItem(sidebar, "Factura Det", () -> openFrame(new FacturaDetForm("D33002 - FACTURA DETALLE")));
        addItem(sidebar, "Pedido Cab", () -> openFrame(new PedidoCabForm("D33003 - PEDIDO CABECERA")));
        addItem(sidebar, "Pedido Det", () -> openFrame(new PedidoDetForm("D33004 - PEDIDO DETALLE")));
        addItem(sidebar, "Estado Factura", () -> openFrame(new EstadoFacturaForm("D33005 - ESTADO FACTURA")));
        addItem(sidebar, "Estado Pedido", () -> openFrame(new EstadoPedidoForm("D33006 - ESTADO PEDIDO")));
        addItem(sidebar, "Tipo Factura", () -> openFrame(new TipoFacturaForm("D33007 - TIPO FACTURA")));

        // INVENTARIO (Subsistema D4)
        addMenuHeader(sidebar, "INVENTARIO");
        addItem(sidebar, "Compra Cab", () -> openFrame(new CompraCabForm("D43001 - COMPRA CABECERA")));
        addItem(sidebar, "Compra Det", () -> openFrame(new CompraDetForm("D43002 - COMPRA DETALLE")));
        addItem(sidebar, "Almacen", () -> openFrame(new AlmacenForm("D43003 - ALMACÉN")));
        addItem(sidebar, "Producto", () -> openFrame(new ProductoForm("D43004 - PRODUCTO")));
        addItem(sidebar, "Stock", () -> openFrame(new StockForm("D43005 - STOCK")));
        addItem(sidebar, "Proveedor", () -> openFrame(new ProveedorForm("D43006 - PROVEEDOR")));
        addItem(sidebar, "Fabricante", () -> openFrame(new FabricanteForm("D43007 - FABRICANTE")));
        addItem(sidebar, "Unidad Medida", () -> openFrame(new UnidadMedidaForm("D43008 - UNIDAD DE MEDIDA")));
        addItem(sidebar, "Clasificación Producto", () -> openFrame(new ClasificacionProductoForm("D43009 - CLASIFICACIÓN PRODUCTO")));

        // SEGURIDAD (Subsistema D5)
        addMenuHeader(sidebar, "SEGURIDAD");
        addItem(sidebar, "Usuario", () -> openFrame(new UsuarioForm("D53001 - USUARIO")));
        addItem(sidebar, "Rol Usuario", () -> openFrame(new RolUsuarioForm("D53002 - ROL USUARIO")));
        addItem(sidebar, "Movimiento Usuario", () -> openFrame(new MovUsuarioForm("D53003 - MOVIMIENTO USUARIO")));
        addItem(sidebar, "Accion", () -> openFrame(new AccionForm("D33004 - ACCIÓN")));

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }


    private void addMenuHeader(JPanel sidebar, String text) {
        sidebar.add(Box.createVerticalStrut(12));
        JLabel header = new JLabel(text);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setForeground(new Color(255, 255, 255)); // Blanco sutil semitransparente
        header.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(header);
        sidebar.add(Box.createVerticalStrut(4));
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