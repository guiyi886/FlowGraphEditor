package model;

import javafx.scene.shape.Polygon;

/**
 * 曲边矩形类，kind 是 CurvedRectangle
 */
public class CurvedRectangle extends MyShape {
    // 常量，曲边矩形的点个数，用100个点形成一条波浪线
    public static final int ARC_NUM = 100;
    // 多边形对象，用于表示曲边矩形
    Polygon polygon;

    /**
     * 构造函数，通过指定坐标和 ID 初始化曲边矩形
     *
     * @param x  坐标 x
     * @param y  坐标 y
     * @param id 图形 ID
     */
    public CurvedRectangle(double x, double y, int id) {
        super(x, y, 100, 50); // 调用父类构造函数，默认宽度 100，高度 50
        polygon = new Polygon(); // 创建多边形对象
        setMyShape(polygon); // 将多边形设置为当前图形的形状
        this.id = id; // 设置图形 ID
        setShape(); // 初始化图形形状
    }

    /**
     * 构造函数，通过指定坐标、宽度、高度、ID 和文本初始化曲边矩形
     *
     * @param x      坐标 x
     * @param y      坐标 y
     * @param width  宽度
     * @param height 高度
     * @param id     图形 ID
     * @param text   文本内容
     */
    public CurvedRectangle(double x, double y, double width, double height, int id, String text) {
        super(x, y, width, height); // 调用父类构造函数
        polygon = new Polygon(); // 创建多边形对象
        setMyShape(polygon); // 将多边形设置为当前图形的形状
        this.id = id; // 设置图形 ID
        setShape(); // 初始化图形形状
        this.getText().setText(text); // 设置文本内容
        this.update(); // 更新文本位置
    }

    /**
     * 曲边矩形，重写更新坐标方法
     */
    @Override
    public void setShape() {
        // 计算矩形的四个角坐标
        double downLeftX = this.x - width;
        double downLeftY = this.y + 3.0 * height / 4;
        double upLeftX = this.x - width;
        double upLeftY = this.y - height;
        double upRightX = this.x + width;
        double upRightY = this.y - height;

        // 波浪线的参数
        double A = height / 4; // 振幅
        double dx = 2 * width / ARC_NUM; // 点之间的水平距离
        double p = Math.PI / width; // 波的频率
        double x = 0;

        // 创建一个包含所有点坐标的数组
        Double[] list = new Double[2 * ARC_NUM + 6];

        // 使用100个点形成一条波浪线
        for (int i = 0; i <= 2 * ARC_NUM; i += 2) {
            y = A * Math.sin(p * x); // 计算 y 坐标
            list[i] = downLeftX + x; // 设置 x 坐标
            list[i + 1] = downLeftY + y; // 设置 y 坐标
            x = x + dx; // 增加 x 坐标
        }

        // 添加右上和左上两个点
        list[2 * ARC_NUM + 2] = upRightX;
        list[2 * ARC_NUM + 3] = upRightY;
        list[2 * ARC_NUM + 4] = upLeftX;
        list[2 * ARC_NUM + 5] = upLeftY;

        // 将点坐标设置到多边形对象
        polygon.getPoints().setAll(list);
    }
}
