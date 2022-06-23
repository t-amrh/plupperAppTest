package com.haw.main;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class AudioService {
    private final int port;
    private TargetDataLine line;
    private DatagramPacket dgp;
    private AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
    private float rate = 48000.0f;
    private int channels = 1;
    private int sampleSize = 16;
    private boolean bigEndian = false;
    private InetAddress addr;
    private Boolean micOpen = true;
    private Boolean isRunning;

    private static AudioFormat format;

    private static DataLine.Info dataLineInfo;
    private static SourceDataLine sourceDataLine;
    private boolean speakerOn = true;

    MulticastSocket mSocket;
    DatagramPacket receivePacket;
    ByteArrayInputStream baiss;


    public AudioService(Boolean isRunning, int port) {
        this.port = port;
        this.isRunning = isRunning;
    }

    public void start() {
        Thread t = new Thread(() -> {
            try {
                System.setProperty("java.net.preferIPv4Stack", "true");
                AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                if (!AudioSystem.isLineSupported(info)) {
                    System.out.println("Data line not supported!");
                }

                line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();
                byte[] data = new byte[4096];

                addr = InetAddress.getLocalHost();
                MulticastSocket multiSocket = new MulticastSocket();

                while (isRunning) {
                    sendAudio(multiSocket, data);
                }
            } catch (IOException | LineUnavailableException e) { /* LineUnavailableException für Audio*/
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void sendAudio(MulticastSocket multiSocket, byte[] data) {
        try {
            while (true) {
                if (this.micOpen) {
                    line.read(data, 0, data.length);
                    dgp = new DatagramPacket(data, data.length, addr, port);
                    multiSocket.send(dgp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void toSpeaker(byte[] soundbytes) {
        try {
            sourceDataLine.write(soundbytes, 0, soundbytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveAudio() {
        try{
            InetAddress group = InetAddress.getLocalHost();
            MulticastSocket mSocket = new MulticastSocket(port);
            this.mSocket = mSocket;
            joinGroup(group);

            byte[] receiveData = new byte[4096];

            format = new AudioFormat(rate, sampleSize, channels, true, bigEndian);
            dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(format);
            sourceDataLine.start();

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            this.receivePacket = receivePacket;
            ByteArrayInputStream baiss = new ByteArrayInputStream(receivePacket.getData());
            this.baiss = baiss;
        }catch(IOException | LineUnavailableException e){
            e.printStackTrace();
        }
        Thread t = new Thread(() -> {
            while (speakerOn) {
                try {
                    mSocket.receive(receivePacket);
                    toSpeaker(receivePacket.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void micSwitch() {
        this.micOpen = !micOpen;
        System.out.println(this.micOpen);
    }

    @Deprecated(since = "14")
    public void joinGroup(InetAddress mcastaddr)
            throws IOException {

    }

    public Boolean getMicOpen() {
        return micOpen;
    }
}
