package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("流程图编辑器");
        initRootLayout();
    }

    /**
     * 加载RootLayout.fxml
     */
    public void initRootLayout() {
        try {
            //将RootLayout.fxml加载到rootLayout成员变量中
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/controller/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            //用rootLayout初始化一个scene，放到stage上展示
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
