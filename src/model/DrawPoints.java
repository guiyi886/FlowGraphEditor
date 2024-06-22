package model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * 形状上连接线的四个点，用于表示形状上的连接点
 */
public class DrawPoints {
    public static final double DRAW_POINTS_RADIUS = 5; // 编辑点圆的半径
    private Circle[] points; // 用于存储四个连接点的数组

    /**
     * 构造函数，初始化四个连接点
     */
    public DrawPoints() {
        points = new Circle[4]; // 创建一个长度为4的Circle数组
        for (int i = 0; i < 4; i++) {
            points[i] = new Circle(); // 初始化每个连接点
            points[i].setRadius(DRAW_POINTS_RADIUS); // 设置连接点的半径
        }
    }

    /**
     * 更新连接点的位置
     *
     * @param leftMidX  左中点的 X 坐标
     * @param leftMidY  左中点的 Y 坐标
     * @param upMidX    上中点的 X 坐标
     * @param upMidY    上中点的 Y 坐标
     * @param rightMidX 右中点的 X 坐标
     * @param rightMidY 右中点的 Y 坐标
     * @param downMidX  下中点的 X 坐标
     * @param downMidY  下中点的 Y 坐标
     */
    public void updataLocation(double leftMidX, double leftMidY, double upMidX, double upMidY, double rightMidX, double rightMidY, double downMidX, double downMidY) {
        // 将所有连接点的坐标存储在一个数组中
        Double[] list = {leftMidX, leftMidY, upMidX, upMidY, rightMidX, rightMidY, downMidX, downMidY};
        int cnt = 0; // 计数器，用于遍历 points 数组
        for (int i = 0; i < 8; i += 2) {
            points[cnt].setCenterX(list[i]); // 设置连接点的 X 坐标
            points[cnt].setCenterY(list[i + 1]); // 设置连接点的 Y 坐标
            cnt++;
        }
    }

    /**
     * 从面板中删除所有连接点
     *
     * @param pane 要删除连接点的面板
     */
    public void delPoint(Pane pane) {
        pane.getChildren().removeAll(points); // 从 pane 中移除所有连接点
    }

    /**
     * 获取所有连接点的数组
     *
     * @return 连接点数组
     */
    public Circle[] getCircles() {
        return points;
    }

    /**
     * 设置所有连接点的可见性
     *
     * @param state true 表示可见，false 表示不可见
     */
    public void setAllVisiable(boolean state) {
        for (int i = 0; i < points.length; i++) {
            points[i].setVisible(state); // 设置每个连接点的可见性
        }
    }
}
