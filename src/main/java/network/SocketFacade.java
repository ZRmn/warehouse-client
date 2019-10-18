package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketFacade extends Socket
{
    private DataInputStream in;
    private DataOutputStream out;

    public SocketFacade(String host, int port) throws IOException
    {
        super(host, port);
        try
        {
            in = new DataInputStream(this.getInputStream());
            out = new DataOutputStream(this.getOutputStream());
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
            this.close();
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
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
