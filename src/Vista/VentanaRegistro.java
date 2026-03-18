package vista;

import dao.UsuarioDAO;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class VentanaRegistro extends JFrame {

    private JTextField txtNombre;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmar;
    private JComboBox<String> cmbRol;
    private JButton btnRegistrar;
    private JButton btnVolver;
    private JLabel lblMensaje;

    private UsuarioDAO usuarioDAO;

    public VentanaRegistro() {
        this.usuarioDAO = new UsuarioDAO();
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        txtNombre     = new JTextField(20);
        txtUsuario    = new JTextField(20);
        txtContrasena = new JPasswordField(20);
        txtConfirmar  = new JPasswordField(20);
        cmbRol        = new JComboBox<>(new String[]{
                            "Empleado", "Administrador"});

        btnRegistrar = new JButton("Registrar");
        btnVolver    = new JButton("← Volver");

        btnRegistrar.setBackground(new Color(180, 60, 120));
        btnRegistrar.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(130, 90, 110));
        btnVolver.setForeground(Color.WHITE);

        lblMensaje = new JLabel(" ", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));

        btnRegistrar.addActionListener(e -> accionRegistrar());
        btnVolver.addActionListener(e -> {
            new VentanaLogin().setVisible(true);
            dispose();
        });
    }

    private void configurarVentana() {
        setTitle("Cosméticos KITTY — Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 230, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(
                         25, 40, 25, 40));

        GridBagConstraints g = new GridBagConstraints();
        g.insets    = new Insets(6, 6, 6, 6);
        g.fill      = GridBagConstraints.HORIZONTAL;
        g.gridwidth = 2;

        // Título
        JLabel lblTitulo = new JLabel("Registro de Usuario",
                                       SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(180, 60, 120));
        g.gridy = 0;
        panel.add(lblTitulo, g);

        // Campos
        g.gridy = 1;
        panel.add(new JLabel("Nombre completo:"), g);
        g.gridy = 2;
        panel.add(txtNombre, g);

        g.gridy = 3;
        panel.add(new JLabel("Usuario:"), g);
        g.gridy = 4;
        panel.add(txtUsuario, g);

        g.gridy = 5;
        panel.add(new JLabel("Contraseña:"), g);
        g.gridy = 6;
        panel.add(txtContrasena, g);

        g.gridy = 7;
        panel.add(new JLabel("Confirmar contraseña:"), g);
        g.gridy = 8;
        panel.add(txtConfirmar, g);

        g.gridy = 9;
        panel.add(new JLabel("Rol:"), g);
        g.gridy = 10;
        panel.add(cmbRol, g);

        g.gridy = 11;
        panel.add(lblMensaje, g);

        // Botones
        g.gridy = 12; g.gridwidth = 1;
        panel.add(btnRegistrar, g);
        g.gridx = 1;
        panel.add(btnVolver, g);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void accionRegistrar() {
        String nombre    = txtNombre.getText().trim();
        String usuario   = txtUsuario.getText().trim();
        String clave     = new String(
                           txtContrasena.getPassword()).trim();
        String confirmar = new String(
                           txtConfirmar.getPassword()).trim();
        String rol       = (String) cmbRol.getSelectedItem();

        // Validaciones
        if (nombre.isEmpty() || usuario.isEmpty() || 
            clave.isEmpty()) {
            mostrarError("⚠️ Todos los campos son obligatorios");
            return;
        }
        if (clave.length() < 4) {
            mostrarError("⚠️ Contraseña mínimo 4 caracteres");
            return;
        }
        if (!clave.equals(confirmar)) {
            mostrarError("⚠️ Las contraseñas no coinciden");
            return;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setUsuario(usuario);
        nuevoUsuario.setContrasena(clave);
        nuevoUsuario.setRol(rol);

        if (usuarioDAO.insertar(nuevoUsuario)) {
            lblMensaje.setForeground(new Color(30, 130, 70));
            lblMensaje.setText("✅ Usuario registrado correctamente");
            Timer t = new Timer(2000, e -> {
                new VentanaLogin().setVisible(true);
                dispose();
            });
            t.setRepeats(false);
            t.start();
        } else {
            mostrarError("⚠️ Error al registrar usuario");
        }
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setText(mensaje);
    }
}