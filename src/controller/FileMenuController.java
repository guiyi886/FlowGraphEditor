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

public class FileMenuController {

    int maxId = 0;

    public void saveDrawingArea(DrawController drawController) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存文件");
        //fileChooser.getExtensionFilters().add(new ExtensionFilter("流程图文件", "*.txt"));

        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile == null) return; // 用户没有选中文件, 已经取消操作
        try {
            // ObjectOutputStream 对象输出流
            ArrayList<MyShapeAndMyLine> myShapeAndMyLines = drawController.translate();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(selectedFile));
            oos.writeObject(myShapeAndMyLines);
            //System.out.println("对象序列化成功！");
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDrawingArea(ShapeFactory shapeFactory, DrawController drawController) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开文件");
        //fileChooser.getExtensionFilters().add(new ExtensionFilter("流程图文件", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) return; // 用户没有选中文件, 已经取消操作
        try {
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
            //必须在导入完所有信息后再保存连接信息，不然会由于连接到图形的线还没有导入，没有指针指向这条线。于是发生空指针错误
            for (int i = 0; i < drawController.getList().size(); i++) {
                drawController.getList().get(i).setCSS(cssList.get(i));
            }
            ShapeFactory.countShapeID = maxId + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportDrawingArea(AnchorPane drawingArea) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("导出");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        File saveFile = fileChooser.showSaveDialog(stage);
        WritableImage image = drawingArea.snapshot(new SnapshotParameters(), null);
        if (saveFile == null) return; // 用户没有选中文件, 已经取消操作
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
