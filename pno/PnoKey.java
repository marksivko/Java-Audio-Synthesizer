package pno;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PnoKey extends JButton implements ActionListener {
    int index;
    float frequency;
    String name;
    boolean isWhite;
    int widthPixels;
    int heightPixels;
    int waveType;
    boolean delayOn;
    float delayDamping;
    WaveVisualizer visualizer;
    int delayMs;
    int delayRepetitions;
    JLabel label;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    PnoKey(int index, int xPosition, int yPosition, WaveVisualizer visualizer) {
        this.index = index;
        this.frequency = (float)(440*(Math.pow(2, ((float)(index - 4 - 88/2)/12)))); // array of piano frequencies with F in the middle, full piano if there are 88 keys;
        this.name = KeyboardToNotesMap.existingNoteNames[Math.floorMod((index), 12)]; // index -> 0-11 number -> name
        this.visualizer = visualizer;
        this.waveType = 0;
        this.delayOn = false;
        this.delayDamping = 0.5f;
        this.delayMs = 500;
        this.delayRepetitions = 5;
        switch (index%12) {
            case 4:
            case 6:
            case 9:
            case 11:
            case 1:
                this.isWhite = false;
                break;
            default:
                this.isWhite = true;
                break;
        }

        if (isWhite) {
            this.widthPixels = 20;
            this.heightPixels = 100;
        }
        else {
            this.widthPixels = 15;
            this.heightPixels = 80;
            this.setBackground(Color.BLACK);

        }
        this.label = new JLabel(this.getLabel());
        this.label.setForeground(new Color(150,150,150));

        this.setRolloverEnabled(false);
        this.setSize(widthPixels, heightPixels);
        this.setBounds(xPosition, yPosition, widthPixels, heightPixels);
        this.addActionListener(this);
        this.setFocusable(false);
        this.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this) {
            try {
                this.press();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void press() throws LineUnavailableException{


        byte[] soundData = ToneGenerator.generateSoundValuesArray(frequency, 100, waveType);
        ToneGenerator.playSound(soundData);
        if (this.visualizer != null) {
            visualizer.setPaintValues(soundData);
            visualizer.repaint();
        }
        if (delayOn) {
                repeatSound(soundData, delayDamping, delayRepetitions);

        }
        System.out.println("Note: " + name + ", " + frequency + " Hz (index: " + index + ")" + " isWhite: " + isWhite);

    }

    private void repeatSound(byte[] soundData, float volume, int times) {

        if (times == 0 ) return;

        byte[] echoData = Arrays.copyOf(soundData, soundData.length);

        scheduler.schedule(() -> {
            try {

                for (int i = 0; i < echoData.length; i++) {
                    echoData[i] = (byte) (soundData[i] * volume);
                }
                ToneGenerator.playSound(echoData);

                repeatSound(soundData, volume * volume, times - 1);
            } catch (LineUnavailableException e) {

            }
        }, delayMs, TimeUnit.MILLISECONDS);

    }

    public String getLabel() {
        if (38 <= index && index <= 54) { // key indexes from C4 to E5
            for (int eventIdx : KeyboardToNotesMap.keyMap.keySet()) {
                if (KeyboardToNotesMap.keyMap.get(eventIdx) == index - 39) {
                    return KeyEvent.getKeyText(eventIdx);
                }
            }
        }
        return "";
    }
}
