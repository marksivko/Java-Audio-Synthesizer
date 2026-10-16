package pno;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements KeyListener, ActionListener {
    JLabel label;
    JLabel noteInfo;
    Keyboard keyboard;
    WaveVisualizer visualizer;
    JLayeredPane layeredPane;

    ButtonGroup buttonGroup;
    JRadioButton sinWaveButton;
    JRadioButton sawWaveButton;
    JRadioButton squareWaveButton;
    JRadioButton delaySwitch;

    Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1055, 400);
        this.setLocationRelativeTo(null);
        this.visualizer = new WaveVisualizer();
        visualizer.setBounds((this.getWidth()/2) - 235, 20, 900, 132);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 960, 400); // Размер панели совпадает с окном

        this.keyboard = new Keyboard(88, visualizer);
        for (int i = 0; i < keyboard.keysAmt; i++) {
            PnoKey key = keyboard.pnoKeys[i];
            key.addActionListener(this);
            if (key.isWhite) {
                layeredPane.add(key, JLayeredPane.DEFAULT_LAYER);
            } else {
                layeredPane.add(key, JLayeredPane.PALETTE_LAYER);
            }
            layeredPane.add(key.label, JLayeredPane.MODAL_LAYER);
        }

        label = new JLabel("Synthesizer");
        label.setBounds(0, 0, this.getWidth(), this.getHeight());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(new Font("Serif", Font.PLAIN,  30));


        noteInfo = new JLabel("Note: ");
        noteInfo.setBounds(20, 100, 200, 30);
        noteInfo.setFont(new Font("Serif", Font.PLAIN,  20));

       sinWaveButton = new JRadioButton("Sine");
       squareWaveButton = new JRadioButton("Square");
       sawWaveButton = new JRadioButton("Sawtooth");

       sinWaveButton.setFocusable(false);
       squareWaveButton.setFocusable(false);
       sawWaveButton.setFocusable(false);

       sinWaveButton.setBounds(10, 10, 80, 30);
       squareWaveButton.setBounds(10, 30, 80, 30);
       sawWaveButton.setBounds(10, 50, 80, 30);

       sinWaveButton.addActionListener(this);
       squareWaveButton.addActionListener(this);
       sawWaveButton.addActionListener(this);

       sinWaveButton.doClick();


        this.buttonGroup = new ButtonGroup();
        buttonGroup.add(sinWaveButton);
        buttonGroup.add(squareWaveButton);
        buttonGroup.add(sawWaveButton);

        delaySwitch = new JRadioButton("Delay");
        delaySwitch.setFocusable(false);
        delaySwitch.setBounds(100, 10, 120 , 30);
        delaySwitch.addActionListener(this);


        layeredPane.add(label, JLayeredPane.MODAL_LAYER);
        layeredPane.add(noteInfo, JLayeredPane.MODAL_LAYER);
        layeredPane.add(this.visualizer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(sinWaveButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(squareWaveButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(sawWaveButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(delaySwitch, JLayeredPane.MODAL_LAYER);
        this.add(layeredPane);
        this.addKeyListener(this);
        this.setResizable(false);
        this.setVisible(true);

    }
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int noteIndex = keyboard.keyCodeToNote(keyCode);
        if (noteIndex == -1) return;

        this.noteInfo.setText(keyboard.getKeyInfo(noteIndex)); // TODO: make it actually appear
        try {
            this.keyboard.pressKey(noteIndex);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e) { }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sinWaveButton) {
            keyboard.changeWaveType(0);
        }
        if (e.getSource() == squareWaveButton) {
            keyboard.changeWaveType(1);
        }
        if (e.getSource() == sawWaveButton) {
            keyboard.changeWaveType(2);
        }
        if (e.getSource() == delaySwitch) {
            keyboard.toggleDelay();
            System.out.println("ActionEvent");
        }
        if (e.getSource() instanceof PnoKey) {
            this.noteInfo.setText(keyboard.getKeyInfo(((PnoKey) e.getSource()).index));
        }
    }
}