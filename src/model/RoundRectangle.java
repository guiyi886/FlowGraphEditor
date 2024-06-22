package model;

import javafx.scene.shape.Rectangle;

// 圆角矩形类，继承自 MyShape 类
// kind 是 "RoundRectangle"，表示图形类型
public class RoundRectangle extends MyShape {
    // 用于绘制圆角矩形的 Rectangle 对象
    private Rectangle rectangle;

    // 构造函数，初始化一个新的圆角矩形，默认宽度为 100，高度为 50
    // 参数 x 和 y 是圆角矩形的中心坐标，id 是圆角矩形的唯一标识符
    public RoundRectangle(double x, double y, int id) {
        super(x, y, 100, 50); // 调用父类构造函数，设置初始中心坐标和默认宽高
        rectangle = new Rectangle(); // 创建一个新的 Rectangle 对象
        setMyShape(rectangle); // 调用父类方法，将 shape 设置为 rectangle
        setShape(); // 设置矩形的几何属性
        this.id = id; // 设置圆角矩形的唯一标识符
    }

    // 构造函数，初始化一个新的圆角矩形，允许自定义宽度和高度
    // 参数 x 和 y 是圆角矩形的中心坐标，width 和 height 是圆角矩形的宽和高的一半
    // id 是圆角矩形的唯一标识符，text 是显示在圆角矩形上的文本
    public RoundRectangle(double x, double y, double width, double height, int id, String text) {
        super(x, y, width, height); // 调用父类构造函数，设置中心坐标和宽高
        rectangle = new Rectangle(); // 创建一个新的 Rectangle 对象
        setMyShape(rectangle); // 调用父类方法，将 shape 设置为 rectangle
        this.id = id; // 设置圆角矩形的唯一标识符
        setShape(); // 设置矩形的几何属性
        this.getText().setText(text); // 设置矩形上的文本内容
        this.update(); // 更新文本的位置，使其位于矩形中心
    }

    // 设置圆角矩形的几何属性
    @Override
    public void setShape() {
        rectangle.setWidth(2 * width); // 设置矩形的宽度为 2 倍的 width
        rectangle.setHeight(2 * height); // 设置矩形的高度为 2 倍的 height
        rectangle.setX(x - width); // 设置矩形的 X 坐标为中心坐标减去 width
        rectangle.setY(y - height); // 设置矩形的 Y 坐标为中心坐标减去 height
        rectangle.setStyle("-fx-arc-height: 100;-fx-arc-width: 100;"); // 设置矩形的圆角效果
    }
}
