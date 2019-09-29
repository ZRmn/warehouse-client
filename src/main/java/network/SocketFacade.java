package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketFacade
{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public SocketFacade(String host, int port)
    {
        try
        {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e)
        {
            System.out.println("Cannot connect to server");
        }
    }

    public boolean isConnected()
    {
        return socket.isConnected();
    }

    public void close()
    {
        try
        {
            this.makeRequest("disconnect");
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getResponse()
    {
        try
        {
            return in.readUTF();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void makeRequest(String query)
    {
        try
        {
            out.writeUTF(query);
            out.flush();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}
