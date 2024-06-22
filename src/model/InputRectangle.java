package model;

import javafx.scene.shape.Polygon;

/**
 * 平行四边形类，kind是InputRectangle
 */
public class InputRectangle extends MyShape {
    Polygon polygon; // 存储平行四边形的多边形对象

    /**
     * 构造函数，初始化平行四边形的属性
     *
     * @param x  平行四边形的中心 X 坐标
     * @param y  平行四边形的中心 Y 坐标
     * @param id 平行四边形的 ID
     */
    public InputRectangle(double x, double y, int id) {
        super(x, y, 100, 50);
        this.id = id;
        polygon = new Polygon();
        setMyShape(polygon);
        setShape();
    }

    /**
     * 构造函数，初始化平行四边形的属性，并设置文本内容
     *
     * @param x      平行四边形的中心 X 坐标
     * @param y      平行四边形的中心 Y 坐标
     * @param width  平行四边形的宽度
     * @param height 平行四边形的高度
     * @param id     平行四边形的 ID
     * @param text   平行四边形内的文本内容
     */
    public InputRectangle(double x, double y, double width, double height, int id, String text) {
        super(x, y, width, height);
        this.id = id;
        polygon = new Polygon();
        setMyShape(polygon);
        setShape();
        this.getText().setText(text); // 设置文本内容
        this.update(); // 更新文本位于图形的位置
    }

    /**
     * 设置平行四边形的形状
     */
    @Override
    public void setShape() {
        double upLeftX = this.x - width / 2;
        double upLeftY = this.y - height;
        double upRightX = this.x + width;
        double upRightY = upLeftY;
        double downLeftX = this.x - width;
        double downLeftY = this.y + height;
        double downRightX = this.x + width / 2;
        double downRightY = this.y + height;
        Double[] list = {upLeftX, upLeftY, upRightX, upRightY, downRightX, downRightY, downLeftX, downLeftY};
        polygon.getPoints().setAll(list);
    }

    /**
     * 创建绘制平行四边形的连接点
     */
    @Override
    public void createDrawPoints() {
        double leftMidX = this.x - 3 * width / 4;
        double leftMidY = this.y;
        double upMidX = this.x;
        double upMidY = this.y - height;
        double rightMidX = this.x + 3 * width / 4;
        double rightMidY = this.y;
        double downMidX = this.x;
        double downMidY = this.y + height;
        drawPoints.updataLocation(leftMidX, leftMidY, upMidX, upMidY, rightMidX, rightMidY, downMidX, downMidY);
    }
}
