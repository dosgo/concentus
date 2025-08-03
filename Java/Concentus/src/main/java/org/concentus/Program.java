package org.concentus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.concentus.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lostromb
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


   
        //javac -encoding UTF-8  -d bin *.java

        //cd bin
        //java  org.concentus.Program

       test();
    }
    
    public static void test()
    {
        try {
            FileInputStream fileIn = new FileInputStream("./48Khz Stereo.raw");
            OpusEncoder encoder = new OpusEncoder(48000, 2, OpusApplication.OPUS_APPLICATION_AUDIO);
            encoder.setBitrate(96000);
            encoder.setForceMode(OpusMode.MODE_CELT_ONLY);
            encoder.setSignalType(OpusSignal.OPUS_SIGNAL_MUSIC);
            encoder.setComplexity(0);
            
            OpusDecoder decoder = new OpusDecoder(48000, 2);

            FileOutputStream fileOut = new FileOutputStream("./out_j.raw");
            int packetSamples = 960;
            byte[] inBuf = new byte[packetSamples * 2 * 2];
            byte[] data_packet = new byte[1275];
            long start = System.currentTimeMillis();
            while (fileIn.available() >= inBuf.length) {
                int bytesRead = fileIn.read(inBuf, 0, inBuf.length);
                short[] pcm = BytesToShorts(inBuf, 0, inBuf.length);
                int bytesEncoded = encoder.encode(pcm, 0, packetSamples, data_packet, 0, 1275);
                //System.out.println(bytesEncoded + " bytes encoded");

                   System.out.println(bytesEncoded + " bytes encoded");
                  
                   StringBuilder hexBuilder = new StringBuilder();
                    for (byte b : data_packet) {
                        hexBuilder.append(String.format("%d ",b & 0xFF));
                    }

                    System.out.println("data_packet:" + hexBuilder);

                int samplesDecoded = decoder.decode(data_packet, 0, bytesEncoded, pcm, 0, packetSamples, false);
                //System.out.println(samplesDecoded + " samples decoded");
                byte[] bytesOut = ShortsToBytes(pcm);
                fileOut.write(bytesOut, 0, bytesOut.length);
                break;
            }
            
            long end = System.currentTimeMillis();
            System.out.println("Time was " + (end - start) + "ms");
            fileIn.close();
            fileOut.close();
            System.out.println("Done!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (OpusException e) {
            System.out.println(e.getMessage());
        }
    }

    /// <summary>
    /// Converts interleaved byte samples (such as what you get from a capture device)
    /// into linear short samples (that are much easier to work with)
    /// </summary>
    /// <param name="input"></param>
    /// <returns></returns>
    public static short[] BytesToShorts(byte[] input) {
        return BytesToShorts(input, 0, input.length);
    }

    /// <summary>
    /// Converts interleaved byte samples (such as what you get from a capture device)
    /// into linear short samples (that are much easier to work with)
    /// </summary>
    /// <param name="input"></param>
    /// <returns></returns>
    public static short[] BytesToShorts(byte[] input, int offset, int length) {
        short[] processedValues = new short[length / 2];
        for (int c = 0; c < processedValues.length; c++) {
            short a = (short) (((int) input[(c * 2) + offset]) & 0xFF);
            short b = (short) (((int) input[(c * 2) + 1 + offset]) << 8);
            processedValues[c] = (short) (a | b);
        }

        return processedValues;
    }

    /// <summary>
    /// Converts linear short samples into interleaved byte samples, for writing to a file, waveout device, etc.
    /// </summary>
    /// <param name="input"></param>
    /// <returns></returns>
    public static byte[] ShortsToBytes(short[] input) {
        return ShortsToBytes(input, 0, input.length);
    }

    /// <summary>
    /// Converts linear short samples into interleaved byte samples, for writing to a file, waveout device, etc.
    /// </summary>
    /// <param name="input"></param>
    /// <returns></returns>
    public static byte[] ShortsToBytes(short[] input, int offset, int length) {
        byte[] processedValues = new byte[length * 2];
        for (int c = 0; c < length; c++) {
            processedValues[c * 2] = (byte) (input[c + offset] & 0xFF);
            processedValues[c * 2 + 1] = (byte) ((input[c + offset] >> 8) & 0xFF);
        }

        return processedValues;
    }
}
