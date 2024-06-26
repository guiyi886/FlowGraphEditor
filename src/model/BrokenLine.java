package model;

import controller.DrawController;
import controller.RootLayoutController;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;

// 折线，kind 是 BrokenLine
public class BrokenLine extends MyLine {
    private Polyline polyline;

    // 构造函数，初始化折线的起点和终点
    public BrokenLine(double startX, double startY, double endX, double endY, int id) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.id = id;
        polyline = new Polyline(); // 创建折线对象
        polyline.getPoints().addAll(new Double[]{startX, startY, startX, endY, endX, endY}); // 设置折线的点坐标
        polyline.setStrokeWidth(3); // 设置折线的宽度

        circles = new Circle[2]; // 创建圆点数组
        circles[0] = new Circle();
        circles[0].setCenterX(startX); // 设置起点圆点的位置
        circles[0].setCenterY(startY);
        circles[0].setRadius(5); // 设置圆点的半径
        circles[0].setFill(Color.BLUE); // 设置圆点的颜色
        circles[1] = new Circle();
        circles[1].setCenterX(endX); // 设置终点圆点的位置
        circles[1].setCenterY(endY);
        circles[1].setRadius(5);
        circles[1].setFill(Color.BLUE);

        polygon = new Polygon(); // 创建箭头的多边形
        text = new Text(); // 创建文本对象
        setShape(); // 初始化图形的形状
        super.line = new Line(); // 创建线对象
        super.addMyLineListener(); // 添加监听器
        addBrokenLineListening(); // 添加折线的监听器
    }

    /**
     * 每次更新了数据之后，都要更新图形，这样才能使得图形发生变化。
     */
    @Override
    public void setShape() {
        // 计算箭头的位置和角度
        double dx = startX - startX;
        double dy = startY - endY;
        double lineLength = Math.sqrt(dx * dx + dy * dy); // 计算线的长度
        double sin = dy / lineLength;
        double cos = dx / lineLength;
        double centerX = startX - 25 * cos; // 计算箭头中心点的位置
        double centerY = startY - 25 * sin;
        double triaX = startX;
        double triaY = startY;
        double tribX = centerX + 10 * sin; // 计算箭头顶部点的位置
        double tribY = centerY - 10 * cos;
        double tricX = centerX - 10 * sin; // 计算箭头底部点的位置
        double tricY = centerY + 10 * cos;
        polygon.getPoints().setAll(new Double[]{triaX, triaY, tribX, tribY, tricX, tricY});

        // 设置圆点的位置
        circles[0].setCenterX(startX);
        circles[0].setCenterY(startY);
        circles[1].setCenterX(endX);
        circles[1].setCenterY(endY);

        // 设置折线的位置
        polyline.getPoints().setAll(new Double[]{startX, startY, startX, endY, endX, endY});

        // 设置文本的位置
        text.setX((startX + endX) / 2);
        text.setY(endY);

        //System.out.println(kind);
        polyline.setStroke(colorMap.get(RootLayoutController.imageToColorMaps.get(kind)));
        polygon.setFill(colorMap.get(RootLayoutController.imageToColorMaps.get(kind)));

        //setAllVisiable(true); // 设置图形为可见 ！！！！
    }

    /**
     * 加入图形
     */
    @Override
    public void getPane(AnchorPane drawingArea, DrawController drawController) {
        // 将折线、箭头、文本和圆点加入到绘图区
        drawingArea.getChildren().add(polyline);
        drawingArea.getChildren().add(polygon);
        drawingArea.getChildren().add(text);
        drawingArea.getChildren().addAll(circles);
        this.drawingArea = drawingArea;
        this.drawController = drawController;
        setAllVisiable(true); // 设置图形为可见
    }

    /**
     * 可以删除这个图形
     */
    public void delete() {
        // 从绘图区移除折线、箭头、文本和圆点
        drawingArea.getChildren().remove(polyline);
        drawingArea.getChildren().remove(line);
        drawingArea.getChildren().remove(polygon);
        drawingArea.getChildren().remove(text);
        drawingArea.getChildren().removeAll(circles);
    }

    /**
     * 能够将图形设置在最顶层
     */
    @Override
    public void setToTop() {
        // 删除图形，再重新添加到顶层
        delete();
        getPane(drawingArea, drawController);
    }

    @Override
    public void click(MouseEvent e) {
        if (e.getClickCount() == 1) {
            if (this.isDrag == true) {
                // 如果正在拖拽，则清除其他选中的图形，并将当前图形置于顶层
                drawController.clearAllOnEdit();
                setAllVisiable(true);
                this.setToTop(); // 几个图形重叠时，会把当前图形放到其他图形上面
            } else if (isSelected == false) {
                // 如果当前图形未被选中，则清除其他选中的图形，并将当前图形置于顶层
                drawController.clearAllOnEdit();
                setAllVisiable(true);
                this.setToTop(); // 几个图形重叠时，会把当前图形放到其他图形上面
            } else {
                // 如果当前图形已被选中，则隐藏图形
                setAllVisiable(false);
            }
            RootLayoutController.clickCount = 1;
        } else if (e.getClickCount() == 2) {
            // 双击时，将当前图形置于顶层，并显示文本框
            this.setToTop();

            // 将文本框设置在折线的中点
            drawController.writeId = this.id;
            textField.setLayoutX((this.startX + this.endX) / 2);
            textField.setLayoutY(endY);
            // 将文本框置于顶层
            drawingArea.getChildren().remove(textField);
            drawingArea.getChildren().add(textField);

            textField.setVisible(true); // 显示文本框
            textField.setText(text.getText());
            setAllVisiable(false); // 隐藏图形
        }
    }

    @Override
    public void clickListener() {
        // 添加鼠标点击事件监听器
        polyline.setOnMouseClicked(e -> {
            click(e);
        });
        polygon.setOnMouseClicked(e -> {
            click(e);
        });
        text.setOnMouseClicked(e -> {
            click(e);
        });
    }

    // 添加折线的鼠标事件监听器
    public void addBrokenLineListening() {
        clickListener();
        // 设置折线的鼠标手势
        polyline.setCursor(Cursor.MOVE);
        polyline.setOnMousePressed(e -> {
            this.isDrag = false;
            lastX = e.getX();
            lastY = e.getY();
        });
        polyline.setOnMouseDragged(e -> {
            double dx = e.getX() - lastX;
            double dy = e.getY() - lastY;
            lastX = e.getX();
            lastY = e.getY();
            this.setSX(this.startX + dx);
            this.setSY(this.startY + dy);
            this.setEX(this.endX + dx);
            this.setEY(this.endY + dy);
            drawController.checkDistanceToPoints(polyline.getPoints().get(0), polyline.getPoints().get(1),
                    polyline.getPoints().get(4), polyline.getPoints().get(5), this);
            this.isDrag = true;
        });
        polyline.setOnMouseReleased(e -> {
            if (startLinkShape != null) {
                startLinkShape.delConnectionInfo(this, "start");
            }
            if (endLinkShape != null) {
                endLinkShape.delConnectionInfo(this, "end");
            }
            drawController.connect(polyline.getPoints().get(0), polyline.getPoints().get(1), "start", this);
            drawController.connect(polyline.getPoints().get(4), polyline.getPoints().get(5), "end", this);
        });
    }
}
