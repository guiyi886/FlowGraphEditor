package model;

import controller.DrawController;

/**
 * 记录连接点信息的类
 */
public class ConnectionInfo {
    // 私有成员变量
    private MyLine line; // 连接的线对象
    private int location; // 连接点的位置编号
    private String connectionPart; // 连接部分（end 或 start）
    private DrawController drawController; // 图形管理者

    // 获取图形管理者
    public DrawController getDrawController() {
        return drawController;
    }

    // 设置图形管理者
    public void setDrawController(DrawController drawController) {
        this.drawController = drawController;
    }

    // 构造函数，通过文本信息初始化
    public ConnectionInfo(String text, DrawController drawController) {
        String[] temp = text.split("_");
        int id = Integer.valueOf(temp[0]); // 解析线的ID
        int pos = Integer.valueOf(temp[1]); // 解析连接点的位置
        String part = temp[2]; // 解析连接部分
        this.line = drawController.getMyLine(id); // 根据ID获取线对象
        this.location = pos;
        this.connectionPart = part;
        this.drawController = drawController;
    }

    // 构造函数，通过具体参数初始化
    public ConnectionInfo(MyLine line, int location, String part, DrawController drawController) {
        this.drawController = drawController;
        this.line = line;
        this.location = location;
        this.connectionPart = part;
    }

    // 获取线对象
    public MyLine getLine() {
        return line;
    }

    // 设置线对象
    public void setLine(MyLine line) {
        this.line = line;
    }

    // 获取连接点位置
    public int getLocation() {
        return location;
    }

    // 设置连接点位置
    public void setLocation(int location) {
        this.location = location;
    }

    // 获取连接部分
    public String getConnectionPart() {
        return connectionPart;
    }

    // 设置连接部分
    public void setConnectionPart(String connectionPart) {
        this.connectionPart = connectionPart;
    }

    // 重写 toString 方法，返回连接信息的字符串表示
    @Override
    public String toString() {
        return String.valueOf(line.getFactoryId()) + "_" + location + "_" + connectionPart;
    }
}
