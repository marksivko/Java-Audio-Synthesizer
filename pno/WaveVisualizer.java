package pno;

import javax.swing.*;
import java.awt.*;

public class WaveVisualizer extends JPanel {
    byte[] buf = ToneGenerator.generateSoundValuesArray(0,100, 2);
    WaveVisualizer() {
        this.setBounds(0,-30,470,130);

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < buf.length - 1; i++) {
            g2d.drawLine(i*441/4110, buf[i] + 100, i*441/4110, buf[i + 1] + 100);
        }

        g2d.drawLine(0, 100, 470, 100);
    }
    public void setPaintValues(byte[] buf) {
        this.buf = buf;
    }
}
