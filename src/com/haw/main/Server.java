package com.haw.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.LinkedList;

public class Server implements IService {

    private IPresenter presenter;
    private MessageService messageService;
    private ConnectionService connectionService;
    private AudioService audioService;

    private Boolean isRunning;
    private int port;
    private LinkedList<BufferedReader> readers;
    private LinkedList<BufferedWriter> writers;

    public Server(SessionPresenter presenter) {
        this.presenter = presenter;
        this.isRunning = true;
        this.port = 50005;
        this.readers = new LinkedList<>();
        this.writers = new LinkedList<>();
    }

    public void start(){
            System.out.println("Starting server");
            messageService = new MessageService(isRunning, writers, this);

            connectionService = new ConnectionService(port, isRunning, readers, writers, messageService);
            connectionService.listen();

            audioService = new AudioService(isRunning, port);
            audioService.start();
    }

    public void stop() {
        System.out.println("Closing server");
        isRunning = false;
        connectionService.disconnect();
    }

    public void micSwitch(){
        audioService.micSwitch();
    }

    @Override
    public void sendMessage(String msg) {
       messageService.broadcastMessage(msg);
    }

    @Override
    public void receiveMessage(String msg){
        sendMessage(msg);
        presenter.receiveMessage(msg);
    }


    public IPresenter getPresenter() {
        return presenter;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public ConnectionService getConnectionService() {
        return connectionService;
    }

    public AudioService getAudioService() {
        return audioService;
    }

    public Boolean getRunning() {
        return isRunning;
    }

    public int getPort() {
        return port;
    }

    public LinkedList<BufferedReader> getReaders() {
        return readers;
    }

    public LinkedList<BufferedWriter> getWriters() {
        return writers;
    }
}
