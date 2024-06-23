package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.MyShapeAndMyLine;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

// 文件菜单控制器
public class FileMenuController {

    int maxId = 0; // 最大ID

    // 保存绘图区内容
    public void saveDrawingArea(DrawController drawController) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存文件");

        // 弹出保存文件对话框
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile == null) {
            return; // 用户取消操作
        }

        try {
            // 将绘图区内容序列化并保存到文件
            ArrayList<MyShapeAndMyLine> myShapeAndMyLines = drawController.translate();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(selectedFile));
            oos.writeObject(myShapeAndMyLines);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打开文件获取绘图区内容
    public void getDrawingArea(ShapeFactory shapeFactory, DrawController drawController) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开文件");

        // 弹出打开文件对话框
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            return; // 用户取消操作
        }

        try {
            // 从文件中读取绘图区内容并创建相应的形状和线条
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile));
            ArrayList<MyShapeAndMyLine> list = (ArrayList<MyShapeAndMyLine>) ois.readObject();
            ArrayList<ArrayList<String>> cssList = new ArrayList<ArrayList<String>>();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() > maxId) {
                    maxId = list.get(i).getId();
                }
                shapeFactory.produce(list.get(i).getKind(), list.get(i).getX(), list.get(i).getY(), list.get(i).getWidth(), list.get(i).getHeight(), list.get(i).getText(), list.get(i).getId());
                cssList.add(list.get(i).getConnectionInfosString());
            }

            // 设置连接信息
            for (int i = 0; i < drawController.getList().size(); i++) {
                drawController.getList().get(i).setCSS(cssList.get(i));
            }

            ShapeFactory.countShapeID = maxId + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuBeginEnd(ShapeFactory shapeFactory, DrawController drawController) {
        File selectedFile = new File("src/controller/graph/begin&end");
        try {
            // 从文件中读取绘图区内容并创建相应的形状和线条
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile));
            ArrayList<MyShapeAndMyLine> list = (ArrayList<MyShapeAndMyLine>) ois.readObject();
            ArrayList<ArrayList<String>> cssList = new ArrayList<ArrayList<String>>();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() > maxId) {
                    maxId = list.get(i).getId();
                }
                shapeFactory.produce(list.get(i).getKind(), list.get(i).getX(), list.get(i).getY(), list.get(i).getWidth(), list.get(i).getHeight(), list.get(i).getText(), list.get(i).getId());
                cssList.add(list.get(i).getConnectionInfosString());
            }

            // 设置连接信息
            for (int i = 0; i < drawController.getList().size(); i++) {
                drawController.getList().get(i).setCSS(cssList.get(i));
            }

            ShapeFactory.countShapeID = maxId + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuInOut(ShapeFactory shapeFactory, DrawController drawController) {
        File selectedFile = new File("src/controller/graph/in&out");
        try {
            // 从文件中读取绘图区内容并创建相应的形状和线条
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile));
            ArrayList<MyShapeAndMyLine> list = (ArrayList<MyShapeAndMyLine>) ois.readObject();
            ArrayList<ArrayList<String>> cssList = new ArrayList<ArrayList<String>>();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() > maxId) {
                    maxId = list.get(i).getId();
                }
                shapeFactory.produce(list.get(i).getKind(), list.get(i).getX(), list.get(i).getY(), list.get(i).getWidth(), list.get(i).getHeight(), list.get(i).getText(), list.get(i).getId());
                cssList.add(list.get(i).getConnectionInfosString());
            }

            // 设置连接信息
            for (int i = 0; i < drawController.getList().size(); i++) {
                drawController.getList().get(i).setCSS(cssList.get(i));
            }

            ShapeFactory.countShapeID = maxId + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 导出绘图区内容为图片
    public void exportDrawingArea(AnchorPane drawingArea) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("导出");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        File saveFile = fileChooser.showSaveDialog(stage);
        WritableImage image = drawingArea.snapshot(new SnapshotParameters(), null);
        if (saveFile == null) {
            return; // 用户取消操作
        }
        try {
            // 将绘图区内容导出为图片
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 显示帮助信息
    public void help() {
        Stage stage1 = new Stage();
        BorderPane root = new BorderPane();
        Image image = new Image("/controller/image/help.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        root.setCenter(imageView);

        Scene scene = new Scene(root, 770, 550);
        stage1.setScene(scene);
        stage1.setTitle("help");
        stage1.show();
    }


}
