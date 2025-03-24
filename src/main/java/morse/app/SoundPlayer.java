package morse.app;

import javax.sound.sampled.*;

public class SoundPlayer {
    private static final float sampleRate = 44100; // samples per second
    private static final double frequency = 440; // frequency
    private static final double timeUnit = 0.1; // seconds for one morse short pulse
    private static final double shortTime = timeUnit; // timeUnits for one morse short pulse
    private static final double longTime = 3*timeUnit; // timeUnits for one morse long pulse
    private static final double interToneTime = timeUnit; // time units for one morse new character
    private static final double interCharacterTime = 2*timeUnit; // timeUnits for one morse new character. Additive upon interToneTime
    private static final double spaceTime = 4*timeUnit; // timeUnits for one morse space pulse. Additive upon interCharacterTime
    private static boolean playing = false;

    public static void playSequence(byte[] data){
        new Thread(() -> {
            synchronized (SoundPlayer.class){
                if(playing){
                    return;
                }
                playing = true;
            } 
            try {
                runPlaySequence(data);
                System.out.println("Playing sequence");
            } finally {
                playing = false;
            }
        }).start(); 
    }

    private static void runPlaySequence(byte[] data){
    AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
    SourceDataLine line;
    try {
        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();

        for(byte character : data){
            playCharacter(character, line);
        }

        line.drain();
        line.close();
    } catch (Exception e) {
        e.printStackTrace();
        return;
    }
}
    private static void playCharacter(byte character, SourceDataLine line){
        if(character == (byte) 0b11111111){
            playTone(spaceTime, false, line);
        }
        else{
            boolean started = false;
            for(int i = 5; i >= 0; i--){
                if(started){
                    double duration = (character & (1 << i)) != 0 ? longTime: shortTime;
                    playTone(duration, true, line);
                    playTone(interToneTime, false, line);
                }
                started = started || (((character & (1 << i))!=0));
            }
            playTone(interCharacterTime, false, line);   
        }
    }

    private static void playTone(double duration, boolean on, SourceDataLine line){
        
        // Calculate the total number of samples needed for the specified duration
        int numSamples = (int) (duration * sampleRate);

        // Create the buffer to hold the audio data
        byte[] buffer = new byte[numSamples];

        // Generate the tone only if 'on' is true
        if (on) {
            for (int i = 0; i < numSamples; i++) {
                double angle = 2.0 * Math.PI * frequency * i / sampleRate; // Angle for the sine wave
                buffer[i] = (byte) (Math.sin(angle) * 127); // Sine wave scaled to byte range
            }
        } else {
            // Fill the buffer with silence (all zeroes)
            for (int i = 0; i < numSamples; i++) {
                buffer[i] = 0;
            }
        }

        // Write the buffer to the audio line
        line.write(buffer, 0, buffer.length);
    }
}
