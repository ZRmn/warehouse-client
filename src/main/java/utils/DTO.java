package utils;

import network.TcpClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DTO
{
    private String host;
    private int port;
    private TcpClient client;
    private StageController stageController;

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
        ApplicationContext context = new ClassPathXmlApplicationContext("/resources/app-config.xml");
        host = context.getBean("host", String.class);
        port = context.getBean("port", Integer.class);
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public TcpClient getClient()
    {
        return client;
    }

    public void setClient(TcpClient client)
    {
        this.client = client;
    }

    public StageController getStageController()
    {
        return stageController;
    }

    public void setStageController(StageController stageController)
    {
        this.stageController = stageController;
    }
}
