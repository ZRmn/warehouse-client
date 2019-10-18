package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import network.SocketFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.InetAddress;

public class Main extends Application
{
    private DTO dto;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        dto = DTO.getInstance();
        ApplicationContext context = new ClassPathXmlApplicationContext("/resources/app-config.xml");

        try
        {
            SocketFacade socketFacade = new SocketFacade(context.getBean("host", String.class),
                    context.getBean("port", Integer.class));

            dto.setSocket(socketFacade);
        }
        catch (IOException e)
        {
            System.out.println("Cannot connect to server");
            System.exit(-1);
        }

        System.out.println("Successfully connected");

        try
        {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/resources/views/main.fxml")));
            scene.getStylesheets().add(0, "/resources/styles/style.css");

            stage.getIcons().add(new Image("/resources/images/gear.png"));
            stage.setTitle("App");
            stage.setResizable(true);
            stage.setScene(scene);

            stage.setOnCloseRequest(windowEvent ->
            {
                dto.getSocket().disconnect();
            });

            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}