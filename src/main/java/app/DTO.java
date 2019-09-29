package app;

import network.SocketFacade;

public class DTO
{
    private SocketFacade socket;

    private static DTO dto;

    public static DTO getInstance()
    {
        synchronized (DTO.class)
        {
            if (dto == null)
            {
                dto = new DTO();
            }
        }

        return dto;
    }

    private DTO()
    {

    }

    public SocketFacade getSocket()
    {
        return socket;
    }

    public void setSocket(SocketFacade socket)
    {
        this.socket = socket;
    }
}
