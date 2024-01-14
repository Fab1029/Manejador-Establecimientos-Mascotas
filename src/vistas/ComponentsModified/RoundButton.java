package vistas.ComponentsModified;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {

    private Color backgroundColor;
    private Color hoverColor;

    public RoundButton(String text, Dimension size) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(true);

        backgroundColor = new Color(0xF3FEF2); // Color de fondo predeterminado
        hoverColor = new Color(0xFFE6C7); // Color de fondo al pasar el ratón

        setPreferredSize(size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int diameter = Math.min(getWidth(), getHeight());
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), diameter, diameter);
        g2.setColor(isHover() ? hoverColor : backgroundColor);
        g2.fill(shape);
        super.paintComponent(g);
    }

    private boolean isHover() {
        return getModel().isRollover();
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }
}