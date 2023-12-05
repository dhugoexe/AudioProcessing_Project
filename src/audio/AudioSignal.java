package audio;

import math.Complex;

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
        byte[] byteBuffer = new byte[sampleBuffer.length * 2]; // 16 bit samples
        if (audioInput.read(byteBuffer, 0, byteBuffer.length) == -1) return false;
        long sumOfSquares = 0;
        for (int i = 0; i < sampleBuffer.length; i++) {
            int sample = ((byteBuffer[2 * i] << 8) | (byteBuffer[2 * i + 1] & 0xFF));
            sampleBuffer[i] = sample / 32768.0; // big endian
            sumOfSquares += sample * sample;
        }
        double rms = Math.sqrt((double)sumOfSquares / sampleBuffer.length);
        double dBLvl = 20 * Math.log10(rms / 32768.0);

        if (rms == 0) {
            dBLvl = -Double.MAX_VALUE; // Or set to a minimum threshold dB value
        } else {
            dBlevel = 20 * Math.log10(rms);
        }

        this.setdBlevel(dBLvl);


        return true;
    }

    /** Plays the buffer content to the given output.
     * @return false if at end of stream */
    public boolean playTo(SourceDataLine audioOutput) {

        byte[] byteBuffer = new byte[sampleBuffer.length*2];// 16 bit samples
        for (int i=0; i<sampleBuffer.length; i++)
        {
            int sample = (int)(sampleBuffer[i] * 32768.0);
            byteBuffer[2 * i] = (byte)((sample >> 8) & 0xFF);
            byteBuffer[2 * i + 1] = (byte)(sample & 0xFF);
        }

        audioOutput.write(byteBuffer, 0, byteBuffer.length);

        return true;
    }
    // your job: add getters and setters ...    OK
    // double getSample(int i)                  OK
    // void setSample(int i, double value)      OK
    // double getdBLevel()                      OK
    // int getFrameSize()                       OK
    // Can be implemented much later: Complex[] computeFFT()

    public static void main(String[] args) {

        System.out.println();
    }


}
