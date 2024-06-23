package model;

import controller.DrawController;
import controller.RootLayoutController;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * 直线类，kind是MyLine
 */
public class MyLine {
    // 类别
    String kind = getClass().getSimpleName();

    // 基本属性
    protected int id;

    public int getId() {
        return id;
    }

    protected AnchorPane drawingArea;
    protected DrawController drawController;
    protected double startX; // 有箭头的一端
    protected double startY;
    protected double endX; // 无箭头的一端
    protected double endY;
    protected double lastX; // 用于拖动线
    protected double lastY;

    // 图形组件
    protected Polygon polygon; // 箭头
    protected Text text;
    protected Line line;
    protected Circle[] circles;

    // 连接信息
    protected MyShape startLinkShape;
    protected MyShape endLinkShape;
    protected boolean isSelected; // 只用于删除选中的线时
    boolean isDrag = false;

    // 对应界面的单行输入框
    public static TextField textField;

    // 获取起始点和结束点坐标的方法
    public double getSX() {
        return startX;
    }

    public void setSX(double startX) {
        this.startX = startX;
        setShape();
    }

    public double getSY() {
        return startY;
    }

    public void setSY(double startY) {
        this.startY = startY;
        setShape();
    }

    public double getEX() {
        return endX;
    }

    public void setEX(double endX) {
        this.endX = endX;
        setShape();
    }

    public double getEY() {
        return endY;
    }

    public void setEY(double endY) {
        this.endY = endY;
        setShape();
    }

    public int getFactoryId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setStartLinkShape(MyShape shape) {
        this.startLinkShape = shape;
    }

    public void setEndLinkShape(MyShape shape) {
        this.endLinkShape = shape;
    }

    public Circle[] getCircle() {
        return this.circles;
    }

    public Line getLine() {
        return this.line;
    }

    public Polygon getPolygon() {
        return this.polygon;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
        setShape();
    }

    // 子类BrokenLine新建时调用的构造函数
    MyLine() {

    }

    /**
     * 构造函数，创建直线对象
     *
     * @param startX 起始点X坐标
     * @param startY 起始点Y坐标
     * @param endX   结束点X坐标
     * @param endY   结束点Y坐标
     * @param id     直线ID
     */
    public MyLine(double startX, double startY, double endX, double endY, int id) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.id = id;

        line = new Line(startX, startY, endX, endY);
        line.setStrokeWidth(3);

        circles = new Circle[2];
        circles[0] = new Circle();
        circles[0].setCenterX(startX); // 起始点圆
        circles[0].setCenterY(startY);
        circles[0].setRadius(5);
        circles[0].setFill(Color.BLUE);
        circles[1] = new Circle();
        circles[1].setCenterX(endX); // 结束点圆
        circles[1].setCenterY(endY);
        circles[1].setRadius(5);
        circles[1].setFill(Color.BLUE);

        polygon = new Polygon();
        text = new Text();
        setShape();
        addMyLineListener();
    }

    /**
     * 设置直线形状，包括箭头、圆点、线和文本的位置
     */
    public void setShape() {
        // 重置箭头位置
        double dx = startX - endX;
        double dy = startY - endY;
        double lineLength = Math.sqrt(dx * dx + dy * dy); // 线长
        double sin = dy / lineLength;
        double cos = dx / lineLength;
        double centerX = startX - 25 * cos; // 箭头等腰三角形的高
        double centerY = startY - 25 * sin;
        double triaX = startX;
        double triaY = startY;
        double tribX = centerX + 10 * sin; // 箭头等腰三角形底的一半
        double tribY = centerY - 10 * cos;
        double tricX = centerX - 10 * sin;
        double tricY = centerY + 10 * cos;
        polygon.getPoints().setAll(new Double[]{triaX, triaY, tribX, tribY, tricX, tricY});

        // 重置圆点位置
        circles[0].setCenterX(startX);
        circles[0].setCenterY(startY);
        circles[1].setCenterX(endX);
        circles[1].setCenterY(endY);

        // 重置线位置
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(this.endX);
        line.setEndY(this.endY);

        // 重置文本位置
        text.setX((startX + endX) / 2);
        text.setY((startY + endY) / 2);

        //setAllVisiable(true); // 设置可见状态 ！！！！！
    }

    /**
     * 将直线添加到绘图区域中
     *
     * @param drawingArea    绘图区域
     * @param drawController 绘图控制器
     */
    public void getPane(AnchorPane drawingArea, DrawController drawController) {
        drawingArea.getChildren().add(line);
        drawingArea.getChildren().add(polygon);
        drawingArea.getChildren().add(text);
        drawingArea.getChildren().addAll(circles);
        this.drawingArea = drawingArea;
        this.drawController = drawController;
        setAllVisiable(true);
    }

    /**
     * 将直线置于最顶层
     */
    public void setToTop() {
        delete();
        getPane(drawingArea, drawController);
    }

    /**
     * 删除直线
     */
    public void delete() {
        drawingArea.getChildren().remove(line);
        drawingArea.getChildren().remove(polygon);
        drawingArea.getChildren().remove(text);
        drawingArea.getChildren().removeAll(circles);
    }

    /**
     * 处理点击事件
     *
     * @param e 鼠标事件
     */
    public void click(MouseEvent e) {
        if (e.getClickCount() == 1) {
            if (this.isDrag == true) {
                drawController.clearAllOnEdit();
                setAllVisiable(true);
                this.setToTop(); // 几个图形重叠时，将当前图形置于其他图形之上
            } else if (isSelected == false) {
                drawController.clearAllOnEdit();
                setAllVisiable(true);
                this.setToTop(); // 几个图形重叠时，将当前图形置于其他图形之上
            } else {
                setAllVisiable(false);
            }
            RootLayoutController.clickCount = 1;
        } else if (e.getClickCount() == 2) { // 双击事件
            this.setToTop();
            drawController.writeId = this.id;
            textField.setLayoutX((this.startX + this.endX) / 2);
            textField.setLayoutY((this.startY + this.endY) / 2);

            // 将 textField 控件置于窗口顶层
            drawingArea.getChildren().remove(textField);
            drawingArea.getChildren().add(textField);

            textField.setVisible(true);
            textField.setText(text.getText());
            setAllVisiable(false);
        }
        this.isDrag = false;
    }

    /**
     * 为直线添加点击事件监听器
     */
    public void clickListener() {
        line.setOnMouseClicked(e -> click(e));
        polygon.setOnMouseClicked(e -> click(e));
        text.setOnMouseClicked(e -> click(e));
    }

    /**
     * 为直线添加各种事件监听器
     */
    protected void addMyLineListener() {
        clickListener();
        circles[0].setCursor(Cursor.HAND);//手光标
        circles[1].setCursor(Cursor.HAND);
        line.setCursor(Cursor.MOVE);//十字光标
        polygon.setCursor(Cursor.MOVE);
        text.setCursor(Cursor.MOVE);

        //线头,箭头处，start,circles[0]
        circles[0].setOnMousePressed(e -> {
            this.setToTop();//从面板上删除该线，再把该线移回面板。使得在画布上，该线在图形上面
            lastX = e.getX();
            lastY = e.getY();
        });
        circles[0].setOnMouseDragged(e -> {
            double dx = e.getX() - lastX;//dx、dx为鼠标移动的横轴、纵轴距离
            double dy = e.getY() - lastY;
            lastX = e.getX();
            lastY = e.getY();
            this.setSX(circles[0].getCenterX() + dx);
            this.setSY(circles[0].getCenterY() + dy);
            drawController.checkDistanceToPoints(e.getX(), e.getY(), this);//离近了会显示连接点
        });
        circles[0].setOnMouseReleased(e -> {
            if (startLinkShape != null)//指有箭头的那端连接的图形
                startLinkShape.delConnectionInfo(this, "start");
            drawController.connect(e.getX(), e.getY(), "start", this);//增加连接信息并且连接（即改变线一端的（x，y），然后重绘这条线）
            RootLayoutController.clickCount = 1;
        });

        //线尾,无箭头处，end,circles[1]
        circles[1].setOnMousePressed(e -> {
            this.setToTop();
            lastX = e.getX();
            lastY = e.getY();
        });
        circles[1].setOnMouseDragged(e -> {
            double dx = e.getX() - lastX;
            double dy = e.getY() - lastY;
            lastX = e.getX();
            lastY = e.getY();
            this.setEX(circles[1].getCenterX() + dx);
            this.setEY(circles[1].getCenterY() + dy);
            drawController.checkDistanceToPoints(e.getX(), e.getY(), this);
        });
        circles[1].setOnMouseReleased(e -> {
            if (endLinkShape != null)
                endLinkShape.delConnectionInfo(this, "end");
            drawController.connect(e.getX(), e.getY(), "end", this);
            RootLayoutController.clickCount = 1;
        });

        //按压，再拖拽然后释放，这也触发一次点击事件
        //线头的箭头，start
        polygon.setOnMousePressed(e -> {
            this.isDrag = false;
            lastX = e.getX();
            lastY = e.getY();
        });
        polygon.setOnMouseDragged(e -> {
            double dx = e.getX() - lastX;
            double dy = e.getY() - lastY;
            lastX = e.getX();
            lastY = e.getY();
            this.setSX(this.startX + dx);//这里面有setShape（）函数里有setAllVisiable（true）函数，所以拖拽时，设为了选中
            this.setSY(this.startY + dy);
            this.setEX(this.endX + dx);
            this.setEY(this.endY + dy);
            drawController.checkDistanceToPoints(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), this);
            this.isDrag = true;
        });
        polygon.setOnMouseReleased(e -> {
            if (startLinkShape != null)
                startLinkShape.delConnectionInfo(this, "start");
            if (endLinkShape != null)
                endLinkShape.delConnectionInfo(this, "end");
            drawController.connect(line.getEndX(), line.getEndY(), "end", this);
            drawController.connect(line.getStartX(), line.getStartY(), "start", this);
        });

        //线，线的头部坐标（startX，startY）等于箭头等腰三角形顶头那点，等于circles[0]的中心点
        line.setOnMousePressed(e -> {
            this.isDrag = false;
            lastX = e.getX();
            lastY = e.getY();
        });
        line.setOnMouseDragged(e -> {
            drag(e);
        });
        line.setOnMouseReleased(e -> {
            if (startLinkShape != null)
                startLinkShape.delConnectionInfo(this, "start");
            if (endLinkShape != null)
                endLinkShape.delConnectionInfo(this, "end");
            drawController.connect(line.getEndX(), line.getEndY(), "end", this);
            drawController.connect(line.getStartX(), line.getStartY(), "start", this);
        });

        //线上面的文本
        text.setOnMousePressed(e -> {
            this.isDrag = false;
            lastX = e.getX();
            lastY = e.getY();
        });
        text.setOnMouseDragged(e -> {
            double dx = e.getX() - lastX;
            double dy = e.getY() - lastY;
            lastX = e.getX();
            lastY = e.getY();
            this.setSX(this.startX + dx);
            this.setSY(this.startY + dy);
            this.setEX(this.endX + dx);
            this.setEY(this.endY + dy);
            drawController.checkDistanceToPoints(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), this);
            this.isDrag = true;
        });
        text.setOnMouseReleased(e -> {
            if (startLinkShape != null)
                startLinkShape.delConnectionInfo(this, "start");
            if (endLinkShape != null)
                endLinkShape.delConnectionInfo(this, "end");
            drawController.connect(line.getEndX(), line.getEndY(), "end", this);
            drawController.connect(line.getStartX(), line.getStartY(), "start", this);
        });
    }

    public void drag(MouseEvent e) {
        double dx = e.getX() - lastX;
        double dy = e.getY() - lastY;
        lastX = e.getX();
        lastY = e.getY();
        this.setSX(this.startX + dx);
        this.setSY(this.startY + dy);
        this.setEX(this.endX + dx);
        this.setEY(this.endY + dy);
        drawController.checkDistanceToPoints(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), this);
        this.isDrag = true;

        for (int i = 0; i < drawController.getList().size(); i++) {
            if (drawController.getList().get(i).getId() != id && drawController.getList().get(i).getIsSelected()) {
                drawController.getList().get(i).dragXY(dx, dy);
            }
        }
        for (int i = 0; i < drawController.getListLine().size(); i++) {
            if (drawController.getListLine().get(i).getId() != id && drawController.getListLine().get(i).getIsSelected()) {
                drawController.getListLine().get(i).dragXY(dx, dy);
            }
        }
    }

    public void dragXY(double dx, double dy) {
        this.setSX(this.startX + dx);
        this.setSY(this.startY + dy);
        this.setEX(this.endX + dx);
        this.setEY(this.endY + dy);
        drawController.checkDistanceToPoints(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), this);
        this.isDrag = true;
    }

    public void setAllVisiable(boolean state) {
        for (int i = 0; i < 2; i++) {
            circles[i].setVisible(state);
        }
        isSelected = state;
    }
}
