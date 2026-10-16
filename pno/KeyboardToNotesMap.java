package pno; import java.awt.event.KeyEvent;
import java.util.HashMap;

public class KeyboardToNotesMap {
    public static final HashMap<Integer, Integer> keyMap = new HashMap<>();;
    public static final String[] existingNoteNames = new String[]{"a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#"};
        static {
            keyMap.put(KeyEvent.VK_Q, 0);
            keyMap.put(KeyEvent.VK_2, 1);
            keyMap.put(KeyEvent.VK_W, 2);
            keyMap.put(KeyEvent.VK_3, 3);
            keyMap.put(KeyEvent.VK_E, 4);
            keyMap.put(KeyEvent.VK_R, 5);
            keyMap.put(KeyEvent.VK_5, 6);
            keyMap.put(KeyEvent.VK_T, 7);
            keyMap.put(KeyEvent.VK_6, 8);
            keyMap.put(KeyEvent.VK_Y, 9);
            keyMap.put(KeyEvent.VK_7, 10);
            keyMap.put(KeyEvent.VK_U, 11);
            keyMap.put(KeyEvent.VK_I, 12);
            keyMap.put(KeyEvent.VK_9, 13);
            keyMap.put(KeyEvent.VK_O, 14);
            keyMap.put(KeyEvent.VK_0, 15);
            keyMap.put(KeyEvent.VK_P, 16);
        }

        public static String keyboardInputToNoteName(int keyEventIndex) {
            return existingNoteNames[keyMap.get(keyEventIndex%12)];
        }

    }