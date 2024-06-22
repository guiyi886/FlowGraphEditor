package model;

import java.io.Serializable;
import java.util.ArrayList;

// 该类用于存储当前绘图的状态，包括形状、线条及其连接点信息
// 实现 Serializable 接口以支持序列化和反序列化
public class MyShapeAndMyLine implements Serializable {
    // 形状和线条的基本信息
    public double x, y, width, height;
    public String text, kind;
    public int id;

    // 连接点信息，以字符串形式存储
    private ArrayList<String> connectionInfosString = new ArrayList<>();

    // 从 MyShape 实例创建 MyShapeAndMyLine 实例
    public MyShapeAndMyLine(MyShape shape) {
        this.x = shape.x;
        this.y = shape.y;
        this.width = shape.width;
        this.height = shape.height;
        this.kind = shape.kind;
        this.text = shape.text.getText();
        this.id = shape.id;
        //把该图形MyShape的所有连接信息ArrayList<ConnectionInfo>变为ArrayList<String>
        for (int i = 0; i < shape.connectionInfos.size(); i++) {
            this.connectionInfosString.add(shape.connectionInfos.get(i).toString());
        }
    }

    // 从 MyLine 实例创建 MyShapeAndMyLine 实例
    public MyShapeAndMyLine(MyLine line) {
        this.x = line.startX;
        this.y = line.startY;
        this.width = line.endX;
        this.height = line.endY;
        this.kind = line.kind;
        this.text = line.text.getText();
        this.id = line.id;
    }

    // Eclipse 自动生成的 getter 和 setter 方法
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getConnectionInfosString() {
        return connectionInfosString;
    }

    public void setConnectionInfosString(ArrayList<String> connectionInfosString) {
        this.connectionInfosString = connectionInfosString;
    }
}
