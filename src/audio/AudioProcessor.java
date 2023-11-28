package audio;

import audio.*;
import javax.sound.sampled.*;
import java.util.Arrays;

/** The main audio processing class, implemented as a Runnable  * to be run in a separated execution Thread. */
public class AudioProcessor implements Runnable {

    private AudioSignal inputSignal;

    private AudioSignal outputSignal;
    private TargetDataLine audioInput;
    private SourceDataLine audioOutput;
    private boolean isThreadRunning; // makes it possible to "terminate" thread

    /**
     * Creates an AudioProcessor that takes input from the given TargetDataLine, and plays back
     * to the given SourceDataLine.
     * Oparam framelize the size of the audio buffer. The shorter, the lower the latency.
     */

    public AudioProcessor(TargetDataLine audioInput, SourceDataLine audioOutput, int frameSize) {
        this.audioInput = audioInput;
        this.audioOutput = audioOutput;

        this.inputSignal = new AudioSignal(frameSize);
        this.outputSignal = new AudioSignal(frameSize);
    }


    /**
     * Audio processing thread code. Basically an infinite loop that continuously fills the sample
     * buffer with audio data fed by a TargetDataLine and then applies some audio effect, if any,
     * and finally copies data back to a SourceDataLine.
     */
    @Override
    public void run() {
        isThreadRunning = true;
        while (isThreadRunning) {
            inputSignal.recordFrom(audioInput);
// your job: copy inputSignal to outputsignal with some audio effect
            outputSignal.playTo(audioOutput);
        }
    }

    /* Tells the thread loop to break as soon as possible. This is an asynchronous process. */
    public void terminateAudioThread() {
        isThreadRunning = false;
    }

    public AudioSignal getInputSignal() {
        return inputSignal;
    }

    public void setInputSignal(AudioSignal inputSignal) {
        this.inputSignal = inputSignal;
    }

    public AudioSignal getOutputSignal() {
        return outputSignal;
    }

    public void setOutputSignal(AudioSignal outputSignal) {
        this.outputSignal = outputSignal;
    }

    public TargetDataLine getAudioInput() {
        return audioInput;
    }

    public void setAudioInput(TargetDataLine audioInput) {
        this.audioInput = audioInput;
    }

    public SourceDataLine getAudioOutput() {
        return audioOutput;
    }

    public void setAudioOutput(SourceDataLine audioOutput) {
        this.audioOutput = audioOutput;
    }

    public boolean isThreadRunning() {
        return isThreadRunning;
    }

    public void setThreadRunning(boolean threadRunning) {
        isThreadRunning = threadRunning;
    }


    /* an example of a possible test code */
    public static void main(String[] args) throws LineUnavailableException {
        TargetDataLine inLine = AudioIO.obtainAudioInput("Default Audio Device", 16000);
        SourceDataLine outLine = AudioIO.obtainAudioOutput("Default Audio Device", 16000);
        AudioProcessor as = new AudioProcessor(inLine, outLine, 1024);
        inLine.open();
        inLine.start();

        inLine.close();

        outLine.open();
        outLine.start();
        new Thread(as).start();
        System.out.println("A new thread has been created!");


    }
}

