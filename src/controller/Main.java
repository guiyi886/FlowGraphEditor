package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage; // 主舞台
    private BorderPane rootLayout; // 根布局

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // 初始化主舞台
        this.primaryStage.setTitle(" 流程图编辑器"); // 设置窗口标题
        initRootLayout(); // 初始化根布局
    }

    /**
     * 加载RootLayout.fxml
     */
    public void initRootLayout() {
        try {
            // 将RootLayout.fxml加载到rootLayout成员变量中
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/controller/RootLayout.fxml")); // 指定FXML文件的位置
            rootLayout = (BorderPane) loader.load(); // 加载FXML文件并初始化根布局

            // 用rootLayout初始化一个scene，放到stage上展示
            Scene scene = new Scene(rootLayout); // 创建一个场景并将根布局添加到场景中
            primaryStage.setScene(scene); // 设置舞台的场景
            primaryStage.setResizable(false); // 禁止调整窗口大小
            primaryStage.show(); // 显示舞台
        } catch (IOException e) {
            e.printStackTrace(); // 捕捉并打印异常
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage; // 获取主舞台
    }

    public static void main(String[] args) {
        launch(args); // 启动JavaFX应用程序
    }
}
