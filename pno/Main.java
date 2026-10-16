package pno;
import javax.sound.sampled.LineUnavailableException;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class Main {
    public static void main(String[] args) throws LineUnavailableException {
        FlatMacDarkLaf.setup();
        Window window = new Window();
    }
}
