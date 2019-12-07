package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StageController
{
    public enum SceneType
    {
        SIGN_UP, SIGN_IN, ADMIN_USERS, ADMIN_USERS_ADD_EDIT, CHECKMAN_MENU,
        CHECKMAN_PRODUCTS, CHECKMAN_PRODUCTS_ADD_EDIT, CHECKMAN_BOXES,
        CHECKMAN_BOXES_ADD_EDIT, CHECKMAN_PLACES, CHECKMAN_PLACES_ADD_EDIT,
        CUSTOMER_MENU, CUSTOMER_ORDERS, CHECKMAN_ADDRESSES, CHECKMAN_ADDRESSES_ADD_EDIT,
        CHECKMAN_WAREHOUSE
    }

    private Stage primaryStage;
    private Stage modalStage;

    private static StageController stageController;

    public static StageController getInstance()
    {
        synchronized (DTO.class)
        {
            if (stageController == null)
            {
                stageController = new StageController();
            }
        }

        return stageController;
    }

    private StageController()
    {
        primaryStage = new Stage();
        primaryStage.setResizable(true);
        primaryStage.getIcons().add(new Image("/resources/images/gear.png"));
        primaryStage.setTitle("App");

        modalStage = new Stage();
        modalStage.setResizable(false);
        modalStage.initModality(Modality.WINDOW_MODAL);
        modalStage.initOwner(primaryStage);
        modalStage.getIcons().add(new Image("/resources/images/gear.png"));
        modalStage.setTitle("");
    }

    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    public Stage getModalStage()
    {
        return modalStage;
    }

    public void setModalStage(Stage modalStage)
    {
        this.modalStage = modalStage;
    }

    public void showPrimary()
    {
        primaryStage.show();
    }

    public void showModal()
    {
        modalStage.showAndWait();
    }

    public void hidePrimary()
    {
        primaryStage.hide();
    }

    public void hideModal()
    {
        modalStage.hide();
    }

    public void setPrimaryScene(SceneType type)
    {
        try
        {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(getScenePath(type))));
            scene.getStylesheets().add(0, "/resources/styles/style.css");
            primaryStage.setScene(scene);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setModalScene(SceneType type, Object controller)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(getScenePath(type)));
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(0, "/resources/styles/style.css");
            modalStage.setScene(scene);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String getScenePath(SceneType type)
    {
        String path = "/resources/views/";

        switch (type)
        {
            case SIGN_IN:
            {
                path += "sign-in.fxml";
                break;
            }
            case SIGN_UP:
            {
                path += "sign-up.fxml";
                break;
            }

            case ADMIN_USERS:
            {
                path += "admin-users.fxml";
                break;
            }

            case ADMIN_USERS_ADD_EDIT:
            {
                path += "admin-users-add-edit.fxml";
                break;
            }

            case CHECKMAN_MENU:
            {
                path += "checkman-menu.fxml";
                break;
            }

            case CHECKMAN_PRODUCTS:
            {
                path += "checkman-products.fxml";
                break;
            }

            case CHECKMAN_PRODUCTS_ADD_EDIT:
            {
                path += "checkman-products-add-edit.fxml";
                break;
            }

            case CHECKMAN_BOXES:
            {
                path += "checkman-boxes.fxml";
                break;
            }

            case CHECKMAN_BOXES_ADD_EDIT:
            {
                path += "checkman-boxes-add-edit.fxml";
                break;
            }

            case CHECKMAN_PLACES:
            {
                path += "checkman-places.fxml";
                break;
            }

            case CHECKMAN_PLACES_ADD_EDIT:
            {
                path += "checkman-places-add-edit.fxml";
                break;
            }

            case CHECKMAN_ADDRESSES:
            {
                path += "checkman-addresses.fxml";
                break;
            }

            case CHECKMAN_ADDRESSES_ADD_EDIT:
            {
                path += "checkman-addresses-add-edit.fxml";
                break;
            }

            case CHECKMAN_WAREHOUSE:
            {
                path += "checkman-warehouse.fxml";
                break;
            }
        }

        return path;
    }
}