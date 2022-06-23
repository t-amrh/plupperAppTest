package test;

import com.haw.main.Client;
import com.haw.main.Server;
import com.haw.main.SessionPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    SessionPresenter presenter;
    SessionPresenter presenterServer;
    Client client;
    Server server;

    @BeforeEach
    void setup(){
        presenter = new SessionPresenter();
        client = new Client(presenter);

        presenterServer = new SessionPresenter();
        server = new Server(presenterServer);
    }

    @Test
    void testStartConnectionServiceNotNull() {
        client.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(client.getConnectionService());
    }

    @Test
    void testStartMessageServiceNotNull() {
        client.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(client.getMessageService());
    }

    @Test
    void testStartAudioServiceNotNull() {
        client.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(client.getAudioService());
    }

    @Test
    void testStopIsRunningFalse() {
        server.start();
        client.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.stop();
        assertFalse(client.getRunning());
    }

    @Test
    void testSendMessageEqualsReceived() {
        String msg = "hallo";
        server.start();
        client.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.sendMessage(msg);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(msg, presenterServer.messages.getFirst());
    }

    @Test
    void testReceiveMessage() {
        String msg = "hallo";
        client.receiveMessage(msg);
        assertEquals("hallo", presenter.messages.getFirst());
    }
}