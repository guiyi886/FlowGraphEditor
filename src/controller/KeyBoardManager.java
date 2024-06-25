package controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.MyShapeAndMyLine;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Stack;

// 键盘管理器
public class KeyBoardManager {
    private boolean isCtrl; // 记录是否按下了 Ctrl 键

    private DrawController drawController; // 绘图区控制器
    private AnchorPane drawingArea; // 绘图区
    public static Stack<String> lastDrawingArea = new Stack<>();
    public static int lastNum = 0;

    // 构造函数，接受绘图区控制器实例，并初始化成员变量
    public KeyBoardManager(DrawController drawController) {
        this.drawController = drawController;
        this.drawingArea = drawController.getDrawingArea();
        addListener(); // 添加键盘事件监听器
    }

    // 添加键盘事件监听器
    public void addListener() {
        /*drawingArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                drawingArea.requestFocus(); // 设置焦点以实现键盘事件
                System.out.println(event.getClickCount());
                storeLastDrawingArea();
            }
        });*/
        // 键盘按下事件处理
        drawingArea.setOnKeyPressed(e -> {
            if (e.isControlDown()) // 如果按下了 Ctrl 键
            {
                this.isCtrl = true; // 记录按下了 Ctrl 键
            }
            // 处理删除操作
            if (e.getCode() == KeyCode.DELETE) {
                storeLastDrawingArea();
                drawController.delete(); // 删除选中的形状或线条
            }
            // 处理复制操作
            if (e.getCode() == KeyCode.C && isCtrl) {
                System.out.println("复制");
                drawController.copy(); // 复制选中的形状或线条
            }
            // 处理粘贴操作
            if (e.getCode() == KeyCode.V && isCtrl) {
                System.out.println("粘贴");
                storeLastDrawingArea();
                drawController.paste(); // 粘贴形状或线条
            }
            if (e.getCode() == KeyCode.Z && isCtrl) {
                System.out.println("撤销");
                showLastDrawingArea();
            }
            e.consume(); // 消费事件，防止事件继续传递
        });

        // 键盘释放事件处理
        drawingArea.setOnKeyReleased(e -> {
            this.isCtrl = false; // 释放 Ctrl 键
        });
    }

    // 返回是否按下了 Ctrl 键
    public boolean isCtrl() {
        return isCtrl;
    }

    // 设置是否按下了 Ctrl 键
    public void setCtrl(boolean isCtrl) {
        this.isCtrl = isCtrl;
    }

    public void storeLastDrawingArea() {

        try {
            // 创建文件对象
            String path = "src/controller/graph/last" + lastNum;
            lastNum++;
            File file = new File(path);
            lastDrawingArea.push(path);

            /// 检查文件是否存在并尝试删除文件
            /*if (file.exists()) {
                if (file.delete()) {
                    System.out.println("文件删除成功！");
                } else {
                    System.out.println("文件删除失败！");
                    return;
                }
                // 确保文件系统完成删除操作
                try {
                    Thread.sleep(5000); // 等待100毫秒
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }*/
            // 将绘图区内容序列化并保存到文件
            ArrayList<MyShapeAndMyLine> myShapeAndMyLines = drawController.translate();

            // 使用 Files.newOutputStream 覆盖现有文件的内容
            ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
            oos.writeObject(myShapeAndMyLines);
            oos.close();
            oos.flush();
            System.out.println("文件保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLastDrawingArea() {
        /*ArrayList<MyShape> shapeList = drawController.getList();
        ArrayList<MyLine> listLine = drawController.getListLine();
        for (int i = 0; i < shapeList.size(); i++) {
            shapeList.get(i).delet();
            shapeList.remove(i);
        }
        for (int i = 0; i < listLine.size(); i++) {
            listLine.get(i).delete();
            listLine.remove(i);
        }*/

        drawController.getDrawingArea().getChildren().clear();
        drawController.getList().clear();
        drawController.getListLine().clear();
        drawController.getMyShapeAndMyLines().clear();

        // 调试代码
        /*System.out.println("Cleared drawingArea children: " + drawingArea.getChildren().size());
        System.out.println("Cleared drawController list: " + drawController.getList().size());
        System.out.println("Cleared drawController listLine: " + drawController.getListLine().size());
        System.out.println("Cleared drawController myShapeAndMyLines: " + drawController.getMyShapeAndMyLines().size());
        */

        // 源文件路径
        //Path sourcePath = Paths.get("src/controller/graph/last");

        //String url = "src/controller/graph/lastNow" + Math.random() * 10000;
        //String url = "src/controller/graph/last";

        if (lastDrawingArea.empty()) {
            return;
        }
        String url = lastDrawingArea.pop();
        lastNum--;

        // 目标文件路径
        /*Path targetPath = Paths.get(url);
        try {
            // 使用 Files.copy 方法复制文件
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件复制成功！");
        } catch (Exception e) {
            System.out.println("文件复制失败：" + e.getMessage());
        }*/

        File selectedFile = new File(url);
        /*try {
            // 从文件中读取绘图区内容并创建相应的形状和线条
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile));
            ArrayList<MyShapeAndMyLine> list = (ArrayList<MyShapeAndMyLine>) ois.readObject();
            ArrayList<ArrayList<String>> cssList = new ArrayList<ArrayList<String>>();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() > maxId) {
                    maxId = list.get(i).getId();
                }
                ShapeFactory.produce(list.get(i).getKind(), list.get(i).getX(), list.get(i).getY(), list.get(i).getWidth(), list.get(i).getHeight(), list.get(i).getText(), list.get(i).getId());
                cssList.add(list.get(i).getConnectionInfosString());
            }

            // 设置连接信息
            for (int i = 0; i < drawController.getList().size(); i++) {
                drawController.getList().get(i).setCSS(cssList.get(i));
            }

            ShapeFactory.countShapeID = maxId + 1;
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            // 从文件中读取绘图区内容并创建相应的形状和线条
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile))) {
                ArrayList<MyShapeAndMyLine> list = (ArrayList<MyShapeAndMyLine>) ois.readObject();
                ArrayList<ArrayList<String>> cssList = new ArrayList<>();

                int maxId = 0;
                for (MyShapeAndMyLine item : list) {
                    if (item.getId() > maxId) {
                        maxId = item.getId();
                    }
                    ShapeFactory.produce(item.getKind(), item.getX(), item.getY(), item.getWidth(), item.getHeight(), item.getText(), item.getId());
                    cssList.add(item.getConnectionInfosString());
                }

                // 设置连接信息
                for (int i = 0; i < drawController.getList().size(); i++) {
                    drawController.getList().get(i).setCSS(cssList.get(i));
                }

                ShapeFactory.countShapeID = maxId + 1;
            }
            System.out.println("文件读取并处理成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectedFile.delete();
        /*
        // 等待文件系统处理
        try {
            Thread.sleep(100); // 等待100毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }
}
