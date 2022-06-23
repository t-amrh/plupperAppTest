package test;

import com.haw.main.Server;
import com.haw.main.SessionPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    SessionPresenter presenter;
    Server server;

    @BeforeEach
    void setUp() {
        presenter = new SessionPresenter();
        server = new Server(presenter);
        server.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testStartConnectionServiceNotNull() {
        assertNotNull(server.getConnectionService());
    }

    @Test
    void testStartMessageServiceNotNull() {
        assertNotNull(server.getMessageService());
    }

    @Test
    void testStartAudioServiceNotNull() {
        assertNotNull(server.getAudioService());
    }



    @Test
    void testStopIsRunningFalse() {
        server.stop();
        assertFalse(server.getRunning());
    }

    @Test
    void testReceiveMessageEqualsReceived() {
        String msg = "hallo";
        server.receiveMessage(msg);
        assertEquals("hallo", presenter.messages.getFirst());
    }
}