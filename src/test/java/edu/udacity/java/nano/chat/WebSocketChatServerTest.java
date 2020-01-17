package edu.udacity.java.nano.chat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.nio.cs.ext.MacTurkish;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class WebSocketChatServerTest {

    @Mock
    private Session session;

    @Mock
    private Session session2;

    @Mock
    private RemoteEndpoint.Basic remoteEndpointBasic;

    @Mock
    private RemoteEndpoint.Basic remoteEndpointBasic2;

    @Autowired
    private WebSocketChatServer webSocketChatServer;

    @Test
    public void setUp() {
        assertNotNull(webSocketChatServer);
    }

    @Test
    public void verifyOnOpenSendMessage() throws IOException {
        Message message = new Message();
        message.setOnlineCount("1");
        when(session.getId()).thenReturn("0");
        when(session.getBasicRemote()).thenReturn(remoteEndpointBasic);

        webSocketChatServer.onOpen(session);

        verify(remoteEndpointBasic, times(1)).sendText(Message.toJson(message));

        webSocketChatServer.onClose(session);
    }

    @Test
    public void verifyOnCloseSendMessage() throws IOException {
        Message message = new Message();
        message.setOnlineCount("1");
        when(session.getId()).thenReturn("0");
        when(session2.getId()).thenReturn("1");

        when(session.getBasicRemote()).thenReturn(remoteEndpointBasic);
        when(session2.getBasicRemote()).thenReturn(remoteEndpointBasic2);

        webSocketChatServer.onOpen(session);
        webSocketChatServer.onOpen(session2);

        webSocketChatServer.onClose(session);

        verify(remoteEndpointBasic2, times(1)).sendText(Message.toJson(message));

        webSocketChatServer.onClose(session2);
    }

    @Test
    public void verifyOnMessageSendMessage() throws IOException {
        when(session.getId()).thenReturn("0");
        when(session.getBasicRemote()).thenReturn(remoteEndpointBasic);

        Message expectedMessage = new Message();
        expectedMessage.setOnlineCount("1");
        expectedMessage.setType("SPEAK");
        expectedMessage.setUsername("Justin");
        expectedMessage.setMessage("Hi");

        Message actualMessage = new Message();
        actualMessage.setUsername("Justin");
        actualMessage.setMessage("Hi");

        webSocketChatServer.onOpen(session);
        webSocketChatServer.onMessage(session, Message.toJson(actualMessage));

        verify(remoteEndpointBasic, times(1)).sendText(Message.toJson(expectedMessage));

        webSocketChatServer.onClose(session);
    }
}
