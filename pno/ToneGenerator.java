package pno;
import javax.sound.sampled.*;

public class ToneGenerator {
    static float SAMPLE_RATE = 44100f;

    public static byte[] generateSoundValuesArray(float frequency, int durationMs, int type) {

        byte[] buf = new byte[durationMs * (int)SAMPLE_RATE / 1000];
        float period = SAMPLE_RATE / frequency;

        for (int i = 0; i < buf.length; i++) {
            double attack = (double)(i)/buf.length;
            double decay = 1 - attack;
            double angle = 2.0 * Math.PI * i / (period);
            switch (type) { // sine wave
                case 0:
                    buf[i] = (byte)(Math.sin(angle) * attack * decay * 127); // 127 is max volume for a byte
                    break;
                case 1: // square wave
                    buf[i] = (byte)((Math.floor(Math.sin(angle)) * 2 + 1) * attack * decay * 127);
                    break;
                case 2: // sawtooth wave
                    double rawPosition = (i % period) / period;
                    double value = 2.0 * rawPosition - 1.0;
                    buf[i] = (byte)(value * attack * decay * 127);
                    break;
            }
        }
        return buf;
    }

    public static void playSound(byte[] buf) throws LineUnavailableException{
        new Thread(() -> {
            try {
                AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
                SourceDataLine sdl = AudioSystem.getSourceDataLine(af);

                sdl.open(af);
                sdl.start();
                sdl.write(buf, 0, buf.length);
                sdl.drain();
                sdl.close();
            }
            catch (LineUnavailableException e) {}
        }).start();
    }

}