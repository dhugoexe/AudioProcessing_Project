package audio;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioSignal {

    private double[] sampleBuffer; // floating point representation of audio samples
    private double dBlevel; // current signal level

    public double[] getSampleBuffer() {
        return sampleBuffer;
    }

    public void setSampleBuffer(double[] sampleBuffer) {
        this.sampleBuffer = sampleBuffer;
    }

    public double getdBlevel() {
        return dBlevel;
    }

    public void setdBlevel(double dBlevel) {
        this.dBlevel = dBlevel;
    }

    public double getSample(int i)
    {
        return sampleBuffer[i];
    }

    public void setSample(int i, double value)
    {
        sampleBuffer[i] = value;
    }

    public int getFrameSize()
    {
        return sampleBuffer.length;
    }

    /** Construct an AudioSignal that may contain up to "frameSize" samples.
     * @param frameSize the number of samples in one audio frame (Normalement c'est un type FrameSize)  */
    public AudioSignal(int frameSize) {

        sampleBuffer = new double[frameSize];
    }
    /** Sets the content of this signal from another signal.
     * @param other other.length must not be lower than the length of this signal. */
    public void setFrom(AudioSignal other) throws Exception {
       if (getFrameSize() > other.getFrameSize())
       {
           throw new Exception("Frame size of signal shorter than other one");
       }else {
           sampleBuffer = other.getSampleBuffer();
       }
    }
    /** Fills the buffer content from the given input. Byte's are converted on the fly to double's.
     * @return false if at end of stream */
    public boolean recordFrom(TargetDataLine audioInput) {

        int i = 0;
        byte[] byteBuffer = new byte[sampleBuffer.length*2];
        // 16 bit samples if (audioInput.read(byteBuffer, 0, byteBuffer.length)==-1) return false;
        // for (int i=0; i<sampleBuffer.length; i++)
        sampleBuffer[i] = ((byteBuffer[2*i]<<8)+byteBuffer[2*i+1]) / 32768.0; // big endian // ... TODO : dBlevel = update signal level in dB here ...
        return true;
    }
    /** Plays the buffer content to the given output.
     * @return false if at end of stream */
    public boolean playTo(SourceDataLine audioOutput) {
        return true;
    }
    // your job: add getters and setters ...    OK
    // double getSample(int i)                  OK
    // void setSample(int i, double value)      OK
    // double getdBLevel()                      OK
    // int getFrameSize()                       OK
    // Can be implemented much later: Complex[] computeFFT()

    public static void main(String[] args) {


    }


}
