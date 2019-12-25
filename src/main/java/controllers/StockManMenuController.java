package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.*;
import utils.DTO;
import utils.StageController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockManMenuController
{
    @FXML
    private Label successLabel;

    @FXML
    private Label errorLabel;


    @FXML
    private ImageView back;

    @FXML
    void onBack()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(false);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.SIGN_IN);
    }

    @FXML
    void onBackHovered()
    {
        back.setFitWidth(30);
        back.setFitHeight(30);
    }


    @FXML
    void onBackExited()
    {
        back.setFitWidth(25);
        back.setFitHeight(25);
    }


    @FXML
    void onGet()
    {
        successLabel.setVisible(false);
        errorLabel.setVisible(false);


        List<Order> orders = DTO.getInstance().getClient().getOrders();
        List<Place> places = DTO.getInstance().getClient().getPlaces();

        List<WayList> ways = new ArrayList<>();

        if (orders.isEmpty())
        {
            errorLabel.setVisible(true);
            return;
        }

        successLabel.setVisible(true);

        Order order = orders.get(0);

        boolean nextItem = false;

        for (Item item : order.getItems())
        {
            for (Place place : places)
            {
                for (Pallet pallet : place.getPallets())
                {
                    if (nextItem)
                    {
                        break;
                    }

                    if (pallet.getBox().equals(item.getBox()) && pallet.getCount() >= item.getCount())
                    {
                        ways.add(new WayList(place.getPosition(), pallet.getBox(), pallet.getCount()));

                        DTO.getInstance().getClient().deletePlace(place);
                        nextItem = true;

                        if(item.getCount() < pallet.getCount())
                        {
                            pallet.setCount(pallet.getCount() - item.getCount());
                        }
                        else
                        {
                            place.getPallets().remove(pallet);
                        }

                        DTO.getInstance().getClient().addPlace(place);
                        break;
                    }
                }
            }

            if (!nextItem)
            {
                ways.add(new WayList("Нет на складе", item.getBox(), item.getCount()));
            }

            nextItem = false;
        }

        DTO.getInstance().getClient().deleteOrder(order);

        String fileName = "Лист_Отбора_" + DTO.getInstance().getUser().getFullName().split(" ")[0] + ".txt";

        try (FileWriter writer = new FileWriter(fileName))
        {
            writer.write("--------------------------------------------------------------------------------------------------------------------------------------\n");
            writer.write(" Лист отбора\n");
            writer.write(" Исполнитель: " + DTO.getInstance().getUser().getFullName() + "\n");
            writer.write("--------------------------------------------------------------------------------------------------------------------------------------\n");
            writer.write(String.format("|%20s|%100s|%10s|\n", "Место", "Коробка" , "Количество"));


            for (WayList way: ways)
            {
                writer.write(String.format("|%20s|%100s|%10s|\n", way.getPosition(), way.getBox().toString(), way.getCount()));
            }

            writer.flush();
        }
        catch (IOException ex)
        {
            //
        }
    }

    @FXML
    void initialize()
    {
        successLabel.setVisible(false);
        errorLabel.setVisible(false);

        back.setImage(new Image(getClass().getResource("/resources/images/back.png").toString()));

    }
}
