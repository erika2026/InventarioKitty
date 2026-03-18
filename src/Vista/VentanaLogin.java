package vista;

import dao.UsuarioDAO;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnRegistrarse;
    private JLabel lblError;

    private UsuarioDAO usuarioDAO;

    public VentanaLogin() {
        this.usuarioDAO = new UsuarioDAO();
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Campos
        txtUsuario    = new JTextField(20);
        txtContrasena = new JPasswordField(20);

        // Botones
        btnIngresar   = new JButton("Ingresar");
        btnRegistrarse = new JButton("Registrarse");

        // Colores botones
        btnIngresar.setBackground(new Color(180, 60, 120));
        btnIngresar.setForeground(Color.WHITE);
        btnRegistrarse.setBackground(new Color(130, 90, 110));
        btnRegistrarse.setForeground(Color.WHITE);

        // Mensaje error
        lblError = new JLabel(" ", SwingConstants.CENTER);
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Arial", Font.BOLD, 12));

        // Acciones botones
        btnIngresar.addActionListener(e -> accionIngresar());
        btnRegistrarse.addActionListener(e -> {
            new VentanaRegistro().setVisible(true);
            dispose();
        });
    }

    private void configurarVentana() {
        setTitle("Cosméticos KITTY — Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 230, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("💄 COSMÉTICOS KITTY", 
                                       SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(180, 60, 120));
        g.gridy = 0; g.gridwidth = 2;
        panel.add(lblTitulo, g);

        // Subtítulo
        JLabel lblSub = new JLabel("Sistema de Inventario", 
                                    SwingConstants.CENTER);
        lblSub.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSub.setForeground(new Color(120, 80, 100));
        g.gridy = 1;
        panel.add(lblSub, g);

        // Separador
        g.gridy = 2;
        panel.add(new JSeparator(), g);

        // Campo usuario
        g.gridy = 3; g.gridwidth = 2;
        panel.add(new JLabel("Usuario:"), g);
        g.gridy = 4;
        panel.add(txtUsuario, g);

        // Campo contraseña
        g.gridy = 5;
        panel.add(new JLabel("Contraseña:"), g);
        g.gridy = 6;
        panel.add(txtContrasena, g);

        // Mensaje error
        g.gridy = 7;
        panel.add(lblError, g);

        // Botones
        g.gridy = 8; g.gridwidth = 1;
        panel.add(btnIngresar, g);
        g.gridx = 1;
        panel.add(btnRegistrarse, g);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void accionIngresar() {
        String usuario   = txtUsuario.getText().trim();
        String contrasena = new String(
                            txtContrasena.getPassword()).trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            lblError.setText("⚠️ Ingrese usuario y contraseña");
            return;
        }

        Usuario u = usuarioDAO.login(usuario, contrasena);

        if (u != null) {
            lblError.setText(" ");
          
            
           new VentanaPrincipal(u).setVisible(true);
            dispose();
        } else {
            lblError.setText("⚠️ Usuario o contraseña incorrectos");
            txtContrasena.setText("");
        }
    }
}