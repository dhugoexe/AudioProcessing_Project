package audio;

import javax.sound.sampled.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** A collection of static utilities related to the audio system. */
public class AudioIO {
    /**
     * Displays every audio mixer available on the current system.
     *
     * @return
     */
    public static Object printAudioMixers() {
        System.out.println("Mixers:");
        Arrays.stream(AudioSystem.getMixerInfo())
                .forEach(e -> System.out.println("- name=\"" + e.getName()
                        + "\" description=\"" + e.getDescription() + " by " + e.getVendor() + "\""));
        return null;
    }

    public static List<String> getAudioMixers() {
        System.out.println("Mixers:");
        List<String> mixers = Arrays.stream(AudioSystem.getMixerInfo())
                .map(e -> e.getName())
                .collect(Collectors.toList());
        return mixers;
    }


    /**
     * @return a Mixer.Info whose name matches the given string. Example of use: getMixerInfo("Macbook default output")
     */
    public static Mixer.Info getMixerInfo(String mixerName) {
        // see how the use of streams is much more compact than for() loops!
        return Arrays.stream(AudioSystem.getMixerInfo())
                .filter(e -> e.getName().equalsIgnoreCase(mixerName)).findFirst().get();
    }

    /**
     * Return a line that's appropriate for recording sound from a microphone.
     * Example of use:
     * TargetDataLine line = obtainInputLine("USB Audio Device", 8000);
     *
     * @param mixerName a string that matches one of the available mixers.
     * @ see AudioSystem.getMixerInfo() which provides a list of all mixers on your system.
     */
    public static TargetDataLine obtainAudioInput(String mixerName, int sampleRate) {
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, true);
        try {
            TargetDataLine targetDataLine = AudioSystem.getTargetDataLine(format, getMixerInfo(mixerName));
            return targetDataLine;
        } catch (LineUnavailableException ignored)
        {
            System.out.println(ignored);
            return null;
        }

    }

    /**
     * Return a line that's appropriate for playing sound to a loudspeaker.
     */
    public static SourceDataLine obtainAudioOutput(String mixerName, int sampleRate) throws LineUnavailableException {
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, true);
        try {
            return (SourceDataLine) AudioSystem.getSourceDataLine(format, getMixerInfo(mixerName));
        }catch (LineUnavailableException exception)
        {
            System.out.println(exception);
            return null;
        }
    }



    public static void main(String[] args) throws LineUnavailableException {

        printAudioMixers();
        TargetDataLine targetDataLine = obtainAudioInput("Port MacBook Air Microphone", 8000);
        SourceDataLine dataLine = obtainAudioOutput("Port MacBook Air Speakers", 8000);
    }

}

