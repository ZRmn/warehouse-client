package utils;

import network.TcpClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DTO
{
    private TcpClient client;
    private ApplicationContext context;


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
        context = new ClassPathXmlApplicationContext("/resources/app-config.xml");
    }

    public TcpClient getClient()
    {
        return client;
    }

    public void setClient(TcpClient client)
    {
        this.client = client;
    }

    public ApplicationContext getContext()
    {
        return context;
    }

    public void setContext(ApplicationContext context)
    {
        this.context = context;
    }
}
