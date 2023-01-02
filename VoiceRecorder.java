import javax.sound.sampled.*;
import java.io.*;

public class VoiceRecorder {
  // The audio format for the recorded audio
  static AudioFormat getAudioFormat() {
    float sampleRate = 16000;
    int sampleSizeInBits = 8;
    int channels = 2;
    boolean signed = true;
    boolean bigEndian = true;
    return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
  }

  // A function to start recording the audio
  static void start() {
    try {
      // Get the audio format
      AudioFormat format = getAudioFormat();

      // Set the audio record parameters
      DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

      // Check if the system supports the audio record parameters
      if (!AudioSystem.isLineSupported(info)) {
        System.out.println("Line not supported");
        System.exit(0);
      }

      // Get a line to record audio
      final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);

      // Create a thread to start recording the audio
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          // Start the line
          line.start();

          // Create an audio input stream from the line
          AudioInputStream ais = new AudioInputStream(line);

          // Set the output file name
          File file = new File("New_Record.wav");
          // If there's an overwrite, file name will be followed by "(1)"

          // Create an audio file from the audio input stream
          try {
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });

      // Start the recording thread
      thread.start();

      // Prompt the user to press enter to stop the recording
      System.out.println("Press enter to stop the recording...");
      System.in.read();

      // Stop the line
      line.stop();
      line.close();
    } catch (LineUnavailableException | IOException e) {
      e.printStackTrace();
    }
  }

  // Main function
  public static void main(String[] args) {
    // Start the recording
    start();
  }
}
