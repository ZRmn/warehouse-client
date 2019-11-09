package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpClient
{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String host;
    private int port;

    public TcpClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public void connect()
    {
        try
        {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            this.makeRequest("connect?" + socket.getInetAddress().getHostAddress());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        this.makeRequest("disconnect");

        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String getResponse()
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

    private void makeRequest(String query)
    {
        try
        {
            out.writeUTF(query);
            out.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
