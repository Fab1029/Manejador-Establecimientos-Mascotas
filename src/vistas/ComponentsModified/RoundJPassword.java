package vistas.ComponentsModified;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundJPassword extends JPasswordField {

    private Shape shape;
    private Color borderColor = new Color(0xF3FEF2); // Color predeterminado del borde
    private Color hoverBorderColor = Color.ORANGE;

    public RoundJPassword(int size) {
        super(size);
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Cambiar el color del borde al hacer clic
                borderColor = Color.ORANGE;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el color del borde al pasar el mouse
                borderColor = hoverBorderColor;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar el color del borde cuando el mouse sale del área del JTextField
                borderColor = new Color(0xF3FEF2);
                repaint();
            }
        });
    }
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        g.setColor(borderColor);
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        return shape.contains(x, y);
    }



}
