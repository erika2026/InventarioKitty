package vista;

import dao.ProductoDAO;
import modelo.Producto;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStock;
    private JTextField txtCategoria;
    private JTextField txtBuscar;

    private JButton btnInsertar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    private JButton btnCerrarSesion;

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    private ProductoDAO productoDAO;
    private Usuario usuarioActual;

    public VentanaPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;
        this.productoDAO   = new ProductoDAO();
        inicializarComponentes();
        configurarVentana();
        cargarTabla();
    }

    private void inicializarComponentes() {
        // Campos formulario
        txtNombre       = new JTextField(15);
        txtDescripcion  = new JTextField(15);
        txtPrecioCompra = new JTextField(10);
        txtPrecioVenta  = new JTextField(10);
        txtStock        = new JTextField(10);
        txtCategoria    = new JTextField(15);
        txtBuscar       = new JTextField(20);

        // Botones
        btnInsertar     = new JButton("➕ Insertar");
        btnActualizar   = new JButton("✏️ Actualizar");
        btnEliminar     = new JButton("🗑️ Eliminar");
        btnLimpiar      = new JButton("🔄 Limpiar");
        btnBuscar       = new JButton("🔍 Buscar");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        // Colores botones
        btnInsertar.setBackground(new Color(40, 167, 69));
        btnInsertar.setForeground(Color.WHITE);
        btnActualizar.setBackground(new Color(0, 123, 255));
        btnActualizar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnLimpiar.setBackground(new Color(108, 117, 125));
        btnLimpiar.setForeground(Color.WHITE);
        btnBuscar.setBackground(new Color(130, 90, 110));
        btnBuscar.setForeground(Color.WHITE);
        btnCerrarSesion.setBackground(new Color(180, 60, 90));
        btnCerrarSesion.setForeground(Color.WHITE);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Descripción",
                             "P.Compra", "P.Venta",
                             "Stock", "Categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setRowHeight(22);
        tablaProductos.getTableHeader().setBackground(
            new Color(180, 60, 120));
        tablaProductos.getTableHeader().setForeground(Color.WHITE);

        // Clic en fila carga datos
        tablaProductos.getSelectionModel()
            .addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarFilaSeleccionada();
            }
        });

        // Acciones botones
        btnInsertar.addActionListener(e -> accionInsertar());
        btnActualizar.addActionListener(e -> accionActualizar());
        btnEliminar.addActionListener(e -> accionEliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> accionBuscar());
        btnCerrarSesion.addActionListener(e -> {
            int conf = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar sesión?", "Cerrar sesión",
                JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                new VentanaLogin().setVisible(true);
                dispose();
            }
        });
    }

    private void configurarVentana() {
        setTitle("Cosméticos KITTY — Inventario | "
                 + usuarioActual.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        getContentPane().setBackground(new Color(252, 240, 248));

        // Barra superior
        JPanel barraSup = new JPanel(new BorderLayout());
        barraSup.setBackground(new Color(180, 60, 120));
        barraSup.setBorder(BorderFactory.createEmptyBorder(
                            8, 16, 8, 16));
        JLabel lblTitulo = new JLabel(
            "💄 Cosméticos KITTY — Inventario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        barraSup.add(lblTitulo, BorderLayout.WEST);
        barraSup.add(btnCerrarSesion, BorderLayout.EAST);
        add(barraSup, BorderLayout.NORTH);

        // Panel formulario
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(
                new Color(180, 60, 120)), "Datos del Producto"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 8, 4, 8);
        g.fill   = GridBagConstraints.HORIZONTAL;

        // Fila 1
        g.gridy = 0; g.gridx = 0;
        formulario.add(new JLabel("Nombre:"), g);
        g.gridx = 1;
        formulario.add(txtNombre, g);
        g.gridx = 2;
        formulario.add(new JLabel("Descripción:"), g);
        g.gridx = 3;
        formulario.add(txtDescripcion, g);

        // Fila 2
        g.gridy = 1; g.gridx = 0;
        formulario.add(new JLabel("Precio Compra:"), g);
        g.gridx = 1;
        formulario.add(txtPrecioCompra, g);
        g.gridx = 2;
        formulario.add(new JLabel("Precio Venta:"), g);
        g.gridx = 3;
        formulario.add(txtPrecioVenta, g);

        // Fila 3
        g.gridy = 2; g.gridx = 0;
        formulario.add(new JLabel("Stock:"), g);
        g.gridx = 1;
        formulario.add(txtStock, g);
        g.gridx = 2;
        formulario.add(new JLabel("Categoría:"), g);
        g.gridx = 3;
        formulario.add(txtCategoria, g);

        // Búsqueda
        g.gridy = 3; g.gridx = 0;
        formulario.add(new JLabel("Buscar:"), g);
        g.gridx = 1;
        formulario.add(txtBuscar, g);
        g.gridx = 2;
        formulario.add(btnBuscar, g);

        // Botones CRUD
        JPanel pBotones = new JPanel(new FlowLayout(
                           FlowLayout.CENTER, 10, 5));
        pBotones.setBackground(Color.WHITE);
        pBotones.add(btnInsertar);
        pBotones.add(btnActualizar);
        pBotones.add(btnEliminar);
        pBotones.add(btnLimpiar);
        g.gridy = 4; g.gridx = 0; g.gridwidth = 4;
        formulario.add(pBotones, g);

        add(formulario, BorderLayout.NORTH);

        // Tabla
        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(
                new Color(180, 60, 120)), "Inventario"));
        add(scroll, BorderLayout.CENTER);

        setMinimumSize(new Dimension(900, 600));
        pack();
        setLocationRelativeTo(null);
    }

    // ─── CRUD ────────────────────────────────────────────────

    private void accionInsertar() {
        if (!validarCampos()) return;
        Producto p = obtenerProductoFormulario();
        if (productoDAO.insertar(p)) {
            JOptionPane.showMessageDialog(this,
                "Producto insertado correctamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error al insertar producto",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionActualizar() {
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un producto de la tabla",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;
        Producto p = obtenerProductoFormulario();
        p.setIdProducto((int) modeloTabla.getValueAt(fila, 0));
        if (productoDAO.actualizar(p)) {
            JOptionPane.showMessageDialog(this,
                "Producto actualizado correctamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error al actualizar producto",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar() {
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un producto de la tabla",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conf = JOptionPane.showConfirmDialog(this,
            "¿Confirma la eliminación?", "Eliminar",
            JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            if (productoDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this,
                    "Producto eliminado correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar producto",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void accionBuscar() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            cargarTabla();
            return;
        }
        modeloTabla.setRowCount(0);
        List<Producto> lista = productoDAO.buscarPorNombre(texto);
        for (Producto p : lista) {
            agregarFilaTabla(p);
        }
    }

    // ─── Auxiliares ──────────────────────────────────────────

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Producto> lista = productoDAO.consultarTodos();
        for (Producto p : lista) {
            agregarFilaTabla(p);
        }
    }

private void agregarFilaTabla(Producto p) {
    modeloTabla.addRow(new Object[]{
        p.getIdProducto(),
        p.getNombre(),
        p.getDescripcion(),
        "",
        "$" + p.getPrecioVenta(),
        p.getStock(),
        ""
    });
}

private void cargarFilaSeleccionada() {
    int fila = tablaProductos.getSelectedRow();
    if (fila < 0) return;
    txtNombre.setText(
        modeloTabla.getValueAt(fila, 1).toString());
    txtDescripcion.setText(
        modeloTabla.getValueAt(fila, 2).toString());
    txtPrecioCompra.setText("");
    txtPrecioVenta.setText(
        modeloTabla.getValueAt(fila, 4)
        .toString().replace("$", ""));
    txtStock.setText(
        modeloTabla.getValueAt(fila, 5).toString());
    txtCategoria.setText("");
}

    private Producto obtenerProductoFormulario() {
        Producto p = new Producto();
        p.setNombre(txtNombre.getText().trim());
        p.setDescripcion(txtDescripcion.getText().trim());
        String precioCompra = txtPrecioCompra.getText().trim();
String precioVenta  = txtPrecioVenta.getText().trim();

p.setPrecioCompra(precioCompra.isEmpty() ? 0 : 
    Double.parseDouble(precioCompra));
p.setPrecioVenta(precioVenta.isEmpty() ? 0 : 
    Double.parseDouble(precioVenta));
        p.setStock(Integer.parseInt(
            txtStock.getText().trim()));
        p.setCategoria(txtCategoria.getText().trim());
        return p;
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
            txtStock.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nombre y Stock son obligatorios",
                "Campos requeridos",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
        txtStock.setText("");
        txtCategoria.setText("");
        txtBuscar.setText("");
        tablaProductos.clearSelection();
    }
}