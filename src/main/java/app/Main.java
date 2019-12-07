package app;

import javafx.application.Application;
import javafx.stage.Stage;
import network.TcpClient;
import utils.DTO;
import utils.StageController;

import java.io.IOException;
import java.net.InetAddress;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        DTO dto = DTO.getInstance();
        dto.setStageController(StageController.getInstance());

        try
        {
            TcpClient client = new TcpClient(InetAddress.getLocalHost().getHostAddress(),
                    dto.getPort());

            dto.setClient(client);

            client.connect();
        }
        catch (IOException e)
        {
            System.out.println("Cannot connect to server");
            System.exit(-1);
        }

        System.out.println("Successfully connected");

        if(dto.getClient().hasAdmin())
        {
            dto.getStageController().setPrimaryScene(StageController.SceneType.SIGN_IN);
        }
        else
        {
            dto.getStageController().setPrimaryScene(StageController.SceneType.SIGN_UP);
        }

        dto.getStageController().getPrimaryStage().setResizable(false);
        dto.getStageController().getPrimaryStage().setOnCloseRequest(windowEvent -> dto.getClient().disconnect());
        dto.getStageController().showPrimary();
    }
}