package model;

import javafx.scene.shape.Rectangle;

/**
 * 矩形类，kind是MyRectangle
 */
public class MyRectangle extends MyShape {
    private Rectangle rectangle;

    /**
     * 构造函数，创建一个矩形对象
     *
     * @param x  矩形中心点的X坐标
     * @param y  矩形中心点的Y坐标
     * @param id 矩形的ID
     */
    public MyRectangle(double x, double y, int id) {
        super(x, y, 100, 50);
        rectangle = new Rectangle();
        setMyShape(rectangle);
        setShape();
        this.id = id;
    }

    /**
     * 构造函数，创建一个带宽度、高度和文本的矩形对象
     *
     * @param x      矩形中心点的X坐标
     * @param y      矩形中心点的Y坐标
     * @param width  矩形的宽度
     * @param height 矩形的高度
     * @param id     矩形的ID
     * @param text   矩形上的文本
     */
    public MyRectangle(double x, double y, double width, double height, int id, String text) {
        super(x, y, width, height);
        rectangle = new Rectangle();
        setMyShape(rectangle);
        this.id = id;
        setShape();
        this.getText().setText(text); // 设置文本内容
        this.update(); // 更新文本位于图形的位置
    }

    /**
     * 设置矩形的形状
     */
    @Override
    public void setShape() {
        rectangle.setWidth(2 * width);
        rectangle.setHeight(2 * height);
        rectangle.setX(x - width);
        rectangle.setY(y - height);
    }
}
