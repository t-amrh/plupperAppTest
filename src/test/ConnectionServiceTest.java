package test;

import com.haw.main.ConnectionService;
import com.haw.main.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionServiceTest {

    ConnectionService connectionService;
    LinkedList<BufferedWriter> writer;
    LinkedList<BufferedReader> reader;

    Server testServer;

    @BeforeEach
    void setUp() {
        writer = new LinkedList<>();
        reader = new LinkedList<>();
        connectionService = new ConnectionService("localhost", 50005, true, reader, writer, null);

        testServer = new Server(null);
        testServer.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testConnectSocketNotNull() {
        connectionService.connect();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(connectionService.getSocket());
    }

    @Test
    void testEstablishConnectionReaderIsReady() {
        connectionService.connect();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            assertNotNull(connectionService.getReader().getFirst().ready());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEstablishConnectionReaderNotNull() {
        connectionService.connect();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(connectionService.getReader().getFirst());
    }

    @Test
    void testEstablishConnectionWriterNotNull() {
        connectionService.connect();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(connectionService.getWriter().getFirst());
    }

    @Test
    void testDisconnectSocketIsClosed() {
        connectionService.connect();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connectionService.disconnect();
        Socket socket = (Socket) connectionService.getSocket();
        assertTrue(socket.isClosed());
    }

}