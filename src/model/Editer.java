package model;

import controller.RootLayoutController;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * 编辑框类，用于编辑形状的大小和位置
 */
public class Editer {
    private Circle[] circles; // 存储编辑框的九个圆点
    private Rectangle frame; // 编辑框的矩形框
    private double x; // 编辑框的 X 坐标
    private double y; // 编辑框的 Y 坐标
    private double width; // 编辑框的宽度
    private double height; // 编辑框的高度
    MyShape myshape; // 被编辑的形状

    /**
     * 构造函数，初始化编辑框的属性
     *
     * @param x      中心点的 X 坐标
     * @param y      中心点的 Y 坐标
     * @param height 被编辑形状的高度
     * @param width  被编辑形状的宽度
     */
    public Editer(double x, double y, double height, double width) {
        this.x = x - width - 10;
        this.y = y - height - 10;
        this.width = width * 2 + 20;
        this.height = height * 2 + 20;
        this.circles = new Circle[9];

        // 初始化编辑框的矩形框
        frame = new Rectangle(this.x, this.y, this.width, this.height);
        frame.setFill(Color.TRANSPARENT);
        frame.setStroke(Color.BLACK);
        frame.setStrokeWidth(1);

        // 初始化编辑框的九个圆点
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                circles[3 * i + j] = new Circle(0, 0, 5);
                circles[3 * i + j].setCenterX(frame.getX() + frame.getWidth() / 2 + frame.getWidth() / 2 * (i - 1));
                circles[3 * i + j].setCenterY(frame.getY() + frame.getHeight() / 2 + frame.getHeight() / 2 * (j - 1));
                circles[3 * i + j].setRadius(5);
                circles[3 * i + j].setFill(Color.BLUE);
                // 中间的圆点不显示
                if (i == 1 && j == 1) {
                    circles[3 * i + j].setRadius(0);
                }
            }
        }
        addListener(); // 为每个圆点添加拖拽事件
    }

    /**
     * 为编辑框的圆点添加拖拽事件
     */
    public void addListener() {
        // 右中点拖拽事件
        circles[7].setOnMouseDragged(e -> {
            if (e.getX() >= frame.getX() + 20) {
                circles[7].setCenterX(e.getX());
                circles[6].setCenterX(e.getX());
                circles[8].setCenterX(e.getX());
                frame.setWidth(e.getX() - frame.getX());
                circles[3].setCenterX(frame.getX() + frame.getWidth() / 2);
                circles[5].setCenterX(frame.getX() + frame.getWidth() / 2);
                bind();
            }
        });

        // 下中点拖拽事件
        circles[5].setOnMouseDragged(e -> {
            if (e.getY() >= frame.getY() + 20) {
                circles[5].setCenterY(e.getY());
                circles[2].setCenterY(e.getY());
                circles[8].setCenterY(e.getY());
                frame.setHeight(e.getY() - frame.getY());
                circles[1].setCenterY(frame.getY() + frame.getHeight() / 2);
                circles[7].setCenterY(frame.getY() + frame.getHeight() / 2);
                bind();
            }
        });

        // 左中点拖拽事件
        circles[3].setOnMouseDragged(e -> {
            if (e.getY() <= frame.getY() + frame.getHeight() - 20) {
                circles[3].setCenterY(e.getY());
                circles[0].setCenterY(e.getY());
                circles[6].setCenterY(e.getY());
                frame.setY(e.getY());
                frame.heightProperty().bind(circles[5].centerYProperty().subtract(frame.yProperty()));
                circles[1].setCenterY(frame.getY() + frame.getHeight() / 2);
                circles[7].setCenterY(frame.getY() + frame.getHeight() / 2);
                bind();
            }
            frame.heightProperty().unbind();
        });

        // 上中点拖拽事件
        circles[1].setOnMouseDragged(e -> {
            if (e.getX() <= frame.getX() + frame.getWidth() - 20) {
                circles[1].setCenterX(e.getX());
                circles[0].setCenterX(e.getX());
                circles[2].setCenterX(e.getX());
                frame.setX(e.getX());
                frame.widthProperty().bind(circles[7].centerXProperty().subtract(frame.xProperty()));
                circles[3].setCenterX(frame.getX() + frame.getWidth() / 2);
                circles[5].setCenterX(frame.getX() + frame.getWidth() / 2);
                bind();
            }
            frame.widthProperty().unbind();
        });

        // 左上点拖拽事件
        circles[6].setOnMouseDragged(e -> {
            if (e.getX() >= frame.getX() + 20 && e.getY() <= frame.getY() + frame.getHeight() - 20) {
                circles[6].setCenterX(e.getX());
                circles[6].setCenterY(e.getY());
                circles[0].setCenterY(e.getY());
                circles[3].setCenterY(e.getY());
                circles[7].setCenterX(e.getX());
                circles[8].setCenterX(e.getX());
                frame.setWidth(e.getX() - frame.getX());
                frame.setY(e.getY());
                frame.heightProperty().bind(circles[5].centerYProperty().subtract(frame.yProperty()));
                circles[3].setCenterX(frame.getX() + frame.getWidth() / 2);
                circles[7].setCenterY(frame.getY() + frame.getHeight() / 2);
                circles[5].setCenterX(frame.getX() + frame.getWidth() / 2);
                circles[1].setCenterY(frame.getY() + frame.getHeight() / 2);
                bind();
            }
            frame.heightProperty().unbind();
        });

        // 右下点拖拽事件
        circles[8].setOnMouseDragged(e -> {
            if (e.getX() >= frame.getX() + 20 && e.getY() >= frame.getY() + 20) {
                circles[8].setCenterX(e.getX());
                circles[8].setCenterY(e.getY());
                circles[2].setCenterY(e.getY());
                circles[5].setCenterY(e.getY());
                circles[6].setCenterX(e.getX());
                circles[7].setCenterX(e.getX());
                frame.setWidth(e.getX() - frame.getX());
                frame.setHeight(e.getY() - frame.getY());
                circles[3].setCenterX(frame.getX() + frame.getWidth() / 2);
                circles[1].setCenterY(frame.getY() + frame.getHeight() / 2);
                circles[5].setCenterX(frame.getX() + frame.getWidth() / 2);
                circles[7].setCenterY(frame.getY() + frame.getHeight() / 2);
                bind();
            }
        });

        // 左上点拖拽事件
        circles[0].setOnMouseDragged(e -> {
            if (e.getX() <= frame.getX() + frame.getWidth() - 20 && e.getY() <= frame.getY() + frame.getHeight() - 20) {
                circles[0].setCenterX(e.getX());
                circles[0].setCenterY(e.getY());
                circles[1].setCenterX(e.getX());
                circles[2].setCenterX(e.getX());
                circles[3].setCenterY(e.getY());
                circles[6].setCenterY(e.getY());
                frame.setX(e.getX());
                frame.setY(e.getY());
                frame.widthProperty().bind(circles[7].centerXProperty().subtract(frame.xProperty()));
                frame.heightProperty().bind(circles[5].centerYProperty().subtract(frame.yProperty()));
                circles[1].setCenterY(frame.getY() + frame.getHeight() / 2);
                circles[3].setCenterX(frame.getX() + frame.getWidth() / 2);
                circles[7].setCenterY(frame.getY() + frame.getHeight() / 2);
                circles[5].setCenterX(frame.getX() + frame.getWidth() / 2);
                bind();
            }
            frame.widthProperty().unbind();
            frame.heightProperty().unbind();
        });

        // 右上点拖拽事件
        circles[2].setOnMouseDragged(e -> {
            if (e.getX() <= frame.getX() + frame.getWidth() - 20 && e.getY() >= frame.getY() + 20) {
                circles[2].setCenterX(e.getX());
                circles[2].setCenterY(e.getY());
                circles[0].setCenterX(e.getX());
                circles[1].setCenterX(e.getX());
                circles[5].setCenterY(e.getY());
                circles[8].setCenterY(e.getY());
                frame.setHeight(e.getY() - frame.getY());
                frame.setX(e.getX());
                frame.widthProperty().bind(circles[7].centerXProperty().subtract(frame.xProperty()));
                circles[7].setCenterY(frame.getY() + frame.getHeight() / 2);
                circles[3].setCenterX(frame.getX() + frame.getWidth() / 2);
                circles[1].setCenterY(frame.getY() + frame.getHeight() / 2);
                circles[5].setCenterX(frame.getX() + frame.getWidth() / 2);
                bind();
            }
            frame.widthProperty().unbind();
        });
    }

    /**
     * 绑定形状的属性到编辑框
     * 在编辑框大小改变后，同步更新形状的大小和位置
     */
    public void bind() {
        myshape.setX(circles[3].getCenterX());
        myshape.setY(circles[1].getCenterY());
        myshape.setHeight(frame.getHeight() / 2 - 10);
        myshape.setWidth(frame.getWidth() / 2 - 10);
        RootLayoutController.clickCount = 1; // 用于点击空白处取消所有选中
        myshape.drawController.clearAllOnEdit();
        setAllVisiable(true);
        myshape.lineMove();
        myshape.createDrawPoints(); // 移动图形的连接点
        myshape.update(); // 更新图形中的文字内容和位置
    }

    /**
     * 设置编辑框和圆点的可见性
     *
     * @param state true 表示可见，false 表示不可见
     */
    public void setAllVisiable(boolean state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                circles[3 * i + j].setVisible(state);
            }
        }
        frame.setVisible(state);
        myshape.setISelected(state);
    }

    /**
     * 将编辑框添加到指定的 Pane 中
     *
     * @param pane 要添加到的 Pane
     */
    public void addEditer(Pane pane) {
        pane.getChildren().addAll(frame);
        pane.getChildren().addAll(circles);
    }

    /**
     * 从指定的 Pane 中删除编辑框
     *
     * @param pane 要从中删除的 Pane
     */
    public void delEditer(Pane pane) {
        pane.getChildren().removeAll(circles);
        pane.getChildren().removeAll(frame);
    }

    // Getter 和 Setter 方法
    public Circle[] getCircles() {
        return circles;
    }

    public void setCircles(Circle[] circles) {
        this.circles = circles;
    }

    public Rectangle getFrame() {
        return frame;
    }

    public void setFrame(Rectangle frame) {
        this.frame = frame;
    }

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

    public MyShape getMyshape() {
        return myshape;
    }

    public void setMyshape(MyShape myshape) {
        this.myshape = myshape;
    }
}
