package model;

import javafx.scene.shape.Polygon;

/**
 * 菱形类，kind 是 Decision
 */
public class Decision extends MyShape {
    // 多边形对象，用于表示菱形
    Polygon polygon;

    /**
     * 构造函数，通过指定坐标和 ID 初始化菱形
     *
     * @param x  坐标 x
     * @param y  坐标 y
     * @param id 图形 ID
     */
    public Decision(double x, double y, int id) {
        super(x, y, 100, 50); // 调用父类构造函数，默认宽度 100，高度 50
        this.id = id; // 设置图形 ID
        polygon = new Polygon(); // 创建多边形对象
        setMyShape(polygon); // 将多边形设置为当前图形的形状
        setShape(); // 初始化图形形状
    }

    /**
     * 构造函数，通过指定坐标、宽度、高度、ID 和文本初始化菱形
     *
     * @param x      坐标 x
     * @param y      坐标 y
     * @param width  宽度
     * @param height 高度
     * @param id     图形 ID
     * @param text   文本内容
     */
    public Decision(double x, double y, double width, double height, int id, String text) {
        super(x, y, width, height); // 调用父类构造函数
        this.id = id; // 设置图形 ID
        polygon = new Polygon(); // 创建多边形对象
        setMyShape(polygon); // 将多边形设置为当前图形的形状
        setShape(); // 初始化图形形状
        this.getText().setText(text); // 设置文本内容
        this.update(); // 更新文本位置
    }

    /**
     * 更新形状坐标
     */
    @Override
    public void setShape() {
        // 计算菱形的四个顶点坐标
        double upLeftX = this.x; // 上顶点 x 坐标
        double upLeftY = this.y - height; // 上顶点 y 坐标
        double upRightX = this.x + width; // 右顶点 x 坐标
        double upRightY = this.y; // 右顶点 y 坐标
        double downLeftX = this.x - width; // 左顶点 x 坐标
        double downLeftY = this.y; // 左顶点 y 坐标
        double downRightX = this.x; // 下顶点 x 坐标
        double downRightY = this.y + height; // 下顶点 y 坐标

        // 创建包含所有顶点坐标的数组
        Double[] list = {
                upLeftX, upLeftY, // 上顶点
                upRightX, upRightY, // 右顶点
                downRightX, downRightY, // 下顶点
                downLeftX, downLeftY // 左顶点
        };

        // 将顶点坐标设置到多边形对象
        polygon.getPoints().setAll(list);
    }
}
