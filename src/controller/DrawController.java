package controller;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import model.ConnectionInfo;
import model.MyLine;
import model.MyShape;
import model.MyShapeAndMyLine;

import java.util.ArrayList;

// 绘图区的控制器
public class DrawController {

    private AnchorPane drawingArea = null; // 绘图区面板
    private ArrayList<MyShape> list = new ArrayList<MyShape>(); // 存储所有形状的列表
    private ArrayList<MyLine> listLine = new ArrayList<MyLine>(); // 存储所有线条的列表
    private ArrayList<MyShapeAndMyLine> myShapeAndMyLines = new ArrayList<MyShapeAndMyLine>(); // 存储形状和线条组合的列表
    private KeyBoardManager keyBoardManager; // 键盘管理器
    private Circle nearPoint; // 最近的连接点
    private double maxDistance = 50; // 最大连接距离
    public static TextField textField; // 用于文本输入的文本框

    // 最后一次双击的图形（线或形状）的id
    // 不管哪个图形被双击，都把它的id赋值过来，好知道输入的文本最终要给哪个图形
    public static int writeId;

    private static ArrayList<MyShape> listCopy = new ArrayList<MyShape>(); // 复制一份 存储所有形状的列表
    private static ArrayList<MyLine> listLineCopy = new ArrayList<MyLine>(); // 复制一份 存储所有线条的列表

    // 构造方法，初始化绘图区面板
    public DrawController(AnchorPane drawArea) {
        drawingArea = drawArea;
    }

    // 获取键盘管理器
    public KeyBoardManager getKeyBoardManager() {
        return keyBoardManager;
    }

    // 设置键盘管理器
    public void setKeyBoardManager() {
        this.keyBoardManager = new KeyBoardManager(this);
    }

    // 获取绘图区面板
    public AnchorPane getDrawingArea() {
        return drawingArea;
    }

    // 设置绘图区面板
    public void setDrawingArea(AnchorPane drawingArea) {
        this.drawingArea = drawingArea;
    }

    // 获取形状和线条组合的列表
    public ArrayList<MyShapeAndMyLine> getMyShapeAndMyLines() {
        return myShapeAndMyLines;
    }

    // 设置形状和线条组合的列表
    public void setMyShapeAndMyLines(ArrayList<MyShapeAndMyLine> myShapeAndMyLines) {
        this.myShapeAndMyLines = myShapeAndMyLines;
    }

    // 获取形状列表
    public ArrayList<MyShape> getList() {
        return list;
    }

    // 设置形状列表
    public void setList(ArrayList<MyShape> list) {
        this.list = list;
    }

    // 获取线条列表
    public ArrayList<MyLine> getListLine() {
        return listLine;
    }

    // 设置线条列表
    public void setListLine(ArrayList<MyLine> listLine) {
        this.listLine = listLine;
    }

    // 设置最近的连接点
    public void setNearPoint(Circle nearPoint) {
        this.nearPoint = nearPoint;
    }

    // 将形状和线条转换为形状和线条组合
    public ArrayList<MyShapeAndMyLine> translate() {
        myShapeAndMyLines.clear();  //！！！！！！
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                myShapeAndMyLines.add(new MyShapeAndMyLine(list.get(i)));
            }
        }
        if (listLine != null && listLine.size() != 0) {
            for (int i = 0; i < listLine.size(); i++) {
                myShapeAndMyLines.add(new MyShapeAndMyLine(listLine.get(i)));
            }
        }
        return myShapeAndMyLines;
    }

    // 根据id获取线条
    public MyLine getMyLine(int id) {
        MyLine line = null;
        for (int i = 0; i < listLine.size(); i++) {
            if (listLine.get(i).getFactoryId() == id) {
                line = listLine.get(i);
            }
        }
        return line;
    }

    // 连接线和形状上的连接点的函数
    // x1、y1为鼠标拖着线头或线尾位置。type为end或start，代表线的头或尾。line为正在拖线头或线尾的线
    public void connect(double x1, double y1, String type, MyLine line) {
        boolean isSelected = line.getIsSelected();
        double minDistance = 100000;
        nearPoint = null;
        MyShape nearShape = null;
        int location = 0;
        String part = type; // end或start，线的头或尾
        for (MyShape nowShape : list) {
            Circle[] circles = nowShape.getDrawPoints().getCircles();
            for (int i = 0; i < 4; i++) // 连接点：左0，上1，右2，下3
            {
                Circle nowCircle = circles[i];
                nowCircle.setVisible(false); // 设置连接点不可见
                double x2, y2;
                x2 = nowCircle.getCenterX();
                y2 = nowCircle.getCenterY();
                double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                if (distance < maxDistance && distance < minDistance) {
                    nearPoint = nowCircle;
                    nearShape = nowShape;
                    location = i; // 图形的哪个点
                    minDistance = distance;
                }
            }
        }
        if (nearPoint != null) {
            if (nearShape != null) {
                nearShape.getConnectionInfos().add(new ConnectionInfo(line, location, part, this));
            }
            if (type.equals("end")) {
                // setEX()、setEY()、setSX()、setSY()函数里有都setShape()函数可以重新设置这条线各个部分的位置
                line.setEX(nearPoint.getCenterX());
                line.setEY(nearPoint.getCenterY());
                line.setEndLinkShape(nearShape);
            } else if (type.equals("start")) {
                line.setSX(nearPoint.getCenterX());
                line.setSY(nearPoint.getCenterY());
                line.setStartLinkShape(nearShape);
            }
        }
        line.setAllVisiable(isSelected);
    }

    // 离坐标(x1,y1)最近的会显示连接点
    // 具体做法：把所有点都设置为不可见，并找出最近的那个点，把其设置为可见
    // 当整个线拖动时，如果用两遍这个函数，先设置离这个线起点的坐标最近的点可见，再设置离这个线终点的坐标最近的点可见，
    // 会在第二遍运行这个函数，即设置离这个线终点的坐标最近的点可见时，把离起点最近的点设置为不可见，
    // 即最终只有离这个线终点的坐标最近的点可见。所以为解决此问题，设置了另一个函数同时置离线起点和线终点的最近点可见
    public void checkDistanceToPoints(double x1, double y1, MyLine line) {
        double minDistance = 100000;
        Circle nearPoint = null;
        for (MyShape nowShape : list) {
            for (Circle nowCircle : nowShape.getDrawPoints().getCircles()) {
                nowCircle.setVisible(false); // 设置连接点不可见
                double x, y;
                x = nowCircle.getCenterX();
                y = nowCircle.getCenterY();
                double distance = Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
                if (distance < maxDistance && distance < minDistance) {
                    nearPoint = nowCircle;
                    minDistance = distance;
                }
            }
        }
        if (nearPoint != null) {
            nearPoint.setVisible(true); // 设置最近的连接点可见
        }
    }

    // 离坐标(x1,y1)和(x2,y2)最近的点设置为可见
    public void checkDistanceToPoints(double x1, double y1, double x2, double y2, MyLine line) {
        double minDistance1 = 100000;
        double minDistance2 = 100000;
        Circle nearPoint1 = null;
        Circle nearPoint2 = null;
        for (MyShape nowShape : list) {
            for (Circle nowCircle : nowShape.getDrawPoints().getCircles()) {
                nowCircle.setVisible(false);
                double x, y;
                x = nowCircle.getCenterX();
                y = nowCircle.getCenterY();
                double distance1 = Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
                double distance2 = Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
                if (distance1 < maxDistance && distance1 < minDistance1) {
                    nearPoint1 = nowCircle;
                    minDistance1 = distance1;
                }
                if (distance2 < maxDistance && distance2 < minDistance2) {
                    nearPoint2 = nowCircle;
                    minDistance2 = distance2;
                }
            }
        }
        if (nearPoint1 != null) {
            nearPoint1.setVisible(true);
        }
        if (nearPoint2 != null) {
            nearPoint2.setVisible(true);
        }
    }

    // 添加形状到列表并添加到绘图区
    public void addShape(MyShape shape) {
        if (shape != null) {
            list.add(shape);
            shape.getPane(drawingArea, this);
        }
    }

    // 添加线条到列表并添加到绘图区
    public void addLine(MyLine shape) {
        if (shape != null) {
            listLine.add(shape);
            shape.getPane(drawingArea, this);
        }
    }

    // 删除选中的形状和线条
    public void delete() {
        boolean remain = false;
        while (true) // 粘贴选中的形状，键盘操作
        {
            remain = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIsSelected()) {
                    list.get(i).delet();
                    list.remove(i);
                    remain = true;
                    break;
                }
            }
            if (!remain) {
                break;
            }
        }
        while (true) // 粘贴选中的线
        {
            remain = false;
            for (int i = 0; i < listLine.size(); i++) {
                if (listLine.get(i).getIsSelected()) {
                    listLine.get(i).delete();
                    listLine.remove(i);
                    remain = true;
                    break;
                }
            }
            if (!remain) {
                break;
            }
        }
        // todo delete的时候应该清除myShapeAndMyLines相关数据
    }

    // 复制形状或线条
    public void copy() {
        listCopy.clear();
        listLineCopy.clear();
        listCopy.addAll(list);
        listLineCopy.addAll(listLine);
    }

    // 粘贴形状或线条
    public void paste() {
        for (int i = 0; i < listCopy.size(); i++) {
            if (listCopy.get(i).getIsSelected()) {
                ShapeFactory.produce(listCopy.get(i).getKind(), listCopy.get(i).getX() + 20, listCopy.get(i).getY() + +20,
                        listCopy.get(i).getWidth(), listCopy.get(i).getHeight(), listCopy.get(i).getText().getText(),
                        ++ShapeFactory.countShapeID);
                listCopy.get(i).setISelected(false);
                listCopy.get(i).getEditer().setAllVisiable(false);
            }
        }

        for (int i = 0; i < listLineCopy.size(); i++) {
            if (listLineCopy.get(i).getIsSelected()) {
                ShapeFactory.produce(listLineCopy.get(i).getKind(), listLineCopy.get(i).getSX() + 20, listLineCopy.get(i).getSY() + 20,
                        listLineCopy.get(i).getEX(), listLineCopy.get(i).getEY(), listLineCopy.get(i).getText().getText(),
                        ++ShapeFactory.countShapeID);
                listLineCopy.get(i).setIsSelected(false);
                listLineCopy.get(i).setAllVisiable(false);
            }
        }
    }

    // 清除所有编辑状态
    public void clearAllOnEdit() {
        if (!keyBoardManager.isCtrl()) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).getEditer().setAllVisiable(false);
            }
            for (int i = 0; i < listLine.size(); i++) {
                listLine.get(i).setAllVisiable(false);
            }
        }
    }

    // 根据id获取形状
    public MyShape getMyShape(int id) {
        MyShape shape = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                shape = list.get(i);
            }
        }
        return shape;
    }

    // 设置文本到指定的形状或线条
    public void setText(String text) {
        for (int i = 0; i < list.size(); i++) {
            MyShape myShape = list.get(i);
            if (myShape.getId() == writeId) // writeId是上一次读写的图形的id
            {
                myShape.getText().setText(text);
                myShape.update(); // 用来设置文本在形状上的位置
            }
        }
        for (int i = 0; i < listLine.size(); i++) {
            MyLine myLine = listLine.get(i);
            if (myLine.getFactoryId() == writeId) {
                myLine.getText().setText(text);
            }
        }
    }
}
