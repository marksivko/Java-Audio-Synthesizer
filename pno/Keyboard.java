package pno;

import javax.swing.*;
import java.util.HashMap;

public class Keyboard extends JFrame {
    int keysAmt;
    float[] notePitches;
    String[] noteNames;
    HashMap<Integer, Integer> keyMap = new HashMap<>();
    PnoKey[] pnoKeys;

    Keyboard(int keysAmt, WaveVisualizer visualizer){ // the constructor class
        this.keysAmt = keysAmt;
        this.notePitches = new float[keysAmt];
        this.noteNames = new String[keysAmt];

        this.pnoKeys = new PnoKey[keysAmt];



        int currentKeyPosition = 0;
        for (int i = 0; i < keysAmt; i++){
            pnoKeys[i] = new PnoKey(i, 0, 0, visualizer);
            if (pnoKeys[i].isWhite) {
                pnoKeys[i].setLocation(currentKeyPosition, 200);
                currentKeyPosition += pnoKeys[i].widthPixels;
                pnoKeys[i].label.setBounds(currentKeyPosition-15, 235, pnoKeys[i].widthPixels, pnoKeys[i].heightPixels);
            }
            else {
                pnoKeys[i].setLocation(currentKeyPosition - 8, 200);
                pnoKeys[i].label.setBounds(currentKeyPosition - 4, 220, pnoKeys[i].widthPixels, pnoKeys[i].heightPixels);
            }
        }
    }

    public int getKeysAmt(){
        return this.keysAmt;
    }

    public float[] getNotePitches() {
        return this.notePitches;
    }

    public String[] getNoteNames() {
        return noteNames;
    }

    public int keyCodeToNote(int keyCode) {
        if (!KeyboardToNotesMap.keyMap.containsKey(keyCode)) {
            return -1;
        }
        System.out.println(KeyboardToNotesMap.keyMap.get(keyCode));
        return KeyboardToNotesMap.keyMap.get(keyCode) + this.keysAmt/2 - 5;
    }
    public void pressKey(int keyInt) throws Exception {
        this.pnoKeys[keyInt].press();
    }
    public String getKeyInfo(int keyInt) {
        return "Note: " + pnoKeys[keyInt].name + ", " + pnoKeys[keyInt].frequency + " Hz";
    }

    public void changeWaveType(int waveType) {
        for (int i = 0; i < keysAmt; i++) {
            this.pnoKeys[i].waveType = waveType;
        }
    }
    public void toggleDelay() {
        for (int i = 0; i < keysAmt; i++) {
            this.pnoKeys[i].delayOn = !this.pnoKeys[i].delayOn;
        }
    }
}

