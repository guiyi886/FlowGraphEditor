package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * 圆形类，kind是Circular
 */
public class MyCircle extends MyShape {
    private Ellipse circle; // 圆形对象

    /**
     * 构造函数，创建一个圆形对象
     *
     * @param x  圆形的中心 X 坐标
     * @param y  圆形的中心 Y 坐标
     * @param id 圆形的 ID
     */
    public MyCircle(double x, double y, int id) {
        this(x, y, 50.0);
        this.id = id;
    }

    /**
     * 构造函数，创建一个指定半径的圆形对象
     *
     * @param x      圆形的中心 X 坐标
     * @param y      圆形的中心 Y 坐标
     * @param radius 圆形的半径
     */
    public MyCircle(double x, double y, double radius) {
        super(x, y, radius, radius);
        this.circle = new Ellipse(this.x, this.y, radius, radius);
        this.circle.setFill(Color.WHITE);
        this.circle.setStroke(Color.BLACK);
        super.setMyShape(this.circle);
    }

    /**
     * 构造函数，创建一个指定宽度、高度的圆形对象，并设置文本内容
     *
     * @param x      圆形的中心 X 坐标
     * @param y      圆形的中心 Y 坐标
     * @param width  圆形的宽度
     * @param height 圆形的高度
     * @param id     圆形的 ID
     * @param text   圆形内的文本内容
     */
    public MyCircle(double x, double y, double width, double height, int id, String text) {
        super(x, y, width, height);
        this.circle = new Ellipse(this.x, this.y, width, height);
        setMyShape(circle);
        this.id = id;
        setShape();
        this.getText().setText(text); // 设置文本内容
        this.update(); // 更新文本位于图形的位置
    }

    /**
     * 设置圆形的形状
     */
    @Override
    public void setShape() {
        circle.setRadiusX(width);
        circle.setRadiusY(height);
        circle.setCenterX(this.x);
        circle.setCenterY(this.y);
    }

    /**
     * 设置圆形的中心 X 坐标
     */
    @Override
    public void setX(double x) {
        this.x = x;
        setShape();
    }

    /**
     * 设置圆形的中心 Y 坐标
     */
    @Override
    public void setY(double y) {
        this.y = y;
        setShape();
    }

    /**
     * 设置圆形的宽度
     */
    @Override
    public void setWidth(double width) {
        this.width = width;
        setShape();
    }

    /**
     * 设置圆形的高度
     */
    @Override
    public void setHeight(double height) {
        this.height = height;
        setShape();
    }
}
