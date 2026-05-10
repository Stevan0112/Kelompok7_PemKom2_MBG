package GUI;

import java.awt.*;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    private int radius = 20;
    private Color backgroundColor = new Color(55, 55, 75);

    public RoundedPanel() {
        setOpaque(false);
    }

    // Getter & Setter supaya muncul di Properties panel
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint(); // otomatis update tampilan
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint(); // otomatis update tampilan
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}