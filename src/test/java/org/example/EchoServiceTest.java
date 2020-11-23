package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EchoServiceTest {
    @Test
    public void givenValidRequestAndResponse_whenEcho_thenTrueIsResponded() throws IOException {
        //Given:
        EchoService echoService =new EchoService();
        String request = "Hello World!";
        byte []messageInBytes = new byte[]{
                'H','e','l','l','o',' ','W','o','r','l','d','!'
        };

        OutputStream outputStream = mock(OutputStream.class);
        InputStream inputStream = mock(InputStream.class);

        when(inputStream.readAllBytes()).thenReturn(messageInBytes);

        //When:

        boolean response = echoService.sendEchoMessage(request, outputStream, inputStream);


        //Then:
        verify(inputStream).readAllBytes();
        verify(outputStream).write(messageInBytes);
        verifyNoMoreInteractions(inputStream, outputStream);

        assertTrue(response);
    }

    @Test
    public void givenValidRequestAndWrongResponse_whenEcho_thenFalseIsResponded() throws IOException {
        //Given:
        EchoService echoService = new EchoService();
        String request = "Hello World!";
        byte [] messageResponse = new byte[]{
                'H','e','l','l','o',' ','W','o','r','l','d','!','!',
        };
        byte []messageRequest = new byte[]{
                'H','e','l','l','o',' ','W','o','r','l','d','!'
        };

        OutputStream outputStream = mock(OutputStream.class);
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.readAllBytes()).thenReturn(messageResponse);

        //When:
        boolean response = echoService.sendEchoMessage(request, outputStream, inputStream);

        //Then:
        verify(outputStream).write(messageRequest);
        verify(inputStream).readAllBytes();
        verifyNoMoreInteractions(inputStream, outputStream);
        assertFalse(response);
    }

}