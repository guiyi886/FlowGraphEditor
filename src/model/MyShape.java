package model;

import controller.DrawController;
import controller.RootLayoutController;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.ArrayList;

public abstract class MyShape {
    //哪种图形
    String kind = getClass().getSimpleName();
    // id号
    protected int id;
    // 所在的图形和所在的管理
    protected AnchorPane drawingArea;
    protected DrawController drawController;
    RootLayoutController rootLayoutController;
    // 中心坐标
    protected double x;//图形中间的坐标
    protected double y;
    // 基本属性 长和宽
    protected double width;//图形长的一半
    protected double height;//图形宽的一半
    //只用于删除选中的形状时
    protected boolean isSelected;
    // 图形成员
    protected Shape shape;
    protected Editer editer;
    protected Text text;
    protected DrawPoints drawPoints;
    //连接点信息列表
    ArrayList<ConnectionInfo> connectionInfos = new ArrayList<>();

    private final static Cursor[] hand = {Cursor.NW_RESIZE, Cursor.W_RESIZE, Cursor.SW_RESIZE, Cursor.N_RESIZE,
            Cursor.MOVE, Cursor.S_RESIZE, Cursor.NE_RESIZE, Cursor.E_RESIZE, Cursor.SE_RESIZE};

    //对应到界面里拖的那个textField。在RootLayoutController类里用MyShape.setTextField(textField);来对应
    public static TextField textField;
    //拖拽时isDrag设为true，好在拖拽后触发单击事件时，把选中框设为选中。
    //而如果只单击的话，单击一下设为选中，再单击一下又设为不选中
    boolean isDrag = false;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Shape getShape() {
        return this.shape;
    }

    public void setISelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public DrawPoints getDrawPoints() {
        return this.drawPoints;
    }

    public Editer getEditer() {
        return editer;
    }

    public void setEditer(Editer editer) {
        this.editer = editer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        setShape();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        setShape();
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        setShape();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        setShape();
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public ArrayList<ConnectionInfo> getConnectionInfos() {
        return connectionInfos;
    }

    public abstract void setShape();

    public MyShape(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void setMyShape(Shape shape) {
        this.shape = shape;
        this.editer = new Editer(this.x, this.y, this.height, this.width);
        this.text = new Text();
        this.drawPoints = new DrawPoints();
        createDrawPoints();
        shape.setFill(Color.WHITE);
        shape.setStroke(Color.BLACK);
        addListener();
        editer.myshape = this;
    }

    // 移动形状的四个连接点
    public void createDrawPoints() {
        double leftMidX = this.x - width;
        double leftMidY = this.y;
        double upMidX = this.x;
        double upMidY = this.y - height;
        double rightMidX = this.x + width;
        double rightMidY = this.y;
        double downMidX = this.x;
        double downMidY = this.y + height;
        drawPoints.updataLocation(leftMidX, leftMidY, upMidX, upMidY, rightMidX, rightMidY, downMidX, downMidY);
    }

    //删除连接信息
    public void delConnectionInfo(MyLine line, String connectionPart) {
        ConnectionInfo delInfo = null;
        for (ConnectionInfo info : connectionInfos) {
            //这里坚决不能用等号这样来比：info.getLine()==line&&info.getConnectionPart()==connectionPart，
            //这样比在从文件打开时会两个都生成false。equals比内容，==比地址
            if (info.getLine().equals(line) && info.getConnectionPart().equals(connectionPart)) {
                delInfo = info;
            }
        }
        if (delInfo != null) {
            connectionInfos.remove(delInfo);
        }
    }

    /**
     * 设置图形的连接信息
     */
    public void setCSS(ArrayList<String> connectionInfosString) {
        for (int i = 0; i < connectionInfosString.size(); i++) {
            ConnectionInfo connectionInfo = new ConnectionInfo(connectionInfosString.get(i), drawController);
            connectionInfos.add(connectionInfo);
            if (connectionInfo.getConnectionPart().equals("start")) {
                connectionInfo.getLine().setStartLinkShape(this);
            } else if (connectionInfo.getConnectionPart().equals("end")) {
                connectionInfo.getLine().setEndLinkShape(this);
            }
        }
    }

    // 删除图形
    public void delet() {
        drawingArea.getChildren().remove(shape);
        editer.delEditer(drawingArea);
        drawPoints.delPoint(drawingArea);
        drawingArea.getChildren().remove(text);
    }

    //把选中框、图形、连接点加入到面板里
    public void getPane(AnchorPane drawingArea, DrawController drawController) {
        editer.addEditer(drawingArea);//把选中框加到面板drawingArea
        drawingArea.getChildren().add(shape);//加入形状到面板
        drawingArea.getChildren().addAll(drawPoints.getCircles());//加入连接点到面板
        drawPoints.setAllVisiable(false);

        drawingArea.getChildren().add(text);//加入形状上的字到面板
        this.drawController = drawController;
        this.drawingArea = drawingArea;
        editer.setAllVisiable(true);
    }

    //画布上，可能线在图形上面，可能线在图形下面，这个重画图形，使该图像处在上面
    public void setToTop() {
        delet();
        getPane(drawingArea, drawController);
    }

    // 鼠标在场景中的位置与图形中心点位置之间的偏移量
    double tOFFX;//这两个值只用于鼠标拖动
    double tOFFY;

    public void addListener() {
        shape.setCursor(Cursor.MOVE);
        text.setCursor(Cursor.MOVE);
        shape.setOnMousePressed(e -> {
            tOFFX = e.getSceneX() - getX();
            tOFFY = e.getSceneY() - getY();
        });
        text.setOnMousePressed(e -> {
            tOFFX = e.getSceneX() - getX();
            tOFFY = e.getSceneY() - getY();
        });
        shape.setOnMouseDragged(e ->
        {
            drag(e);
        });
        text.setOnMouseDragged(e -> {
            drag(e);
        });
        shape.setOnMouseClicked(e -> {//单击或双击图形。单击时设置是否选中，双击时设置输入框
            click(e);
        });
        text.setOnMouseClicked(e -> {
            click(e);
        });
        //设置鼠标移到选中框圆点时的光标
        Circle[] circles = editer.getCircles();
        for (int i = 0; i < circles.length; i++) {
            //设置鼠标放选中框圆点上时的光标形状
            circles[i].setCursor(hand[i]);
        }
    }

    //单击或双击图形。单击时设置是否选中，双击时设置输入框
    public void click(MouseEvent e) {
        if (e.getClickCount() == 1) {
            if (this.isDrag == true) {
                //drawController.clearAllOnEdit(); ！！！！
                editer.setAllVisiable(true);
                this.setToTop();//几个图形重叠时，会把当前图形放到其他图形上面
            } else if (isSelected == false) {
                drawController.clearAllOnEdit();
                editer.setAllVisiable(true);
                this.setToTop();//几个图形重叠时，会把当前图形放到其他图形上面
            } else {
                editer.setAllVisiable(false);
            }
            RootLayoutController.clickCount = 1;
        } else if (e.getClickCount() == 2)//只会触发drawingArea的双击事件，不会触发其单击事件
        {
            this.setToTop();

            drawController.writeId = this.id;
            textField.setLayoutX(this.getX() - this.getWidth());
            textField.setLayoutY(this.getY());
            //下方两句把textField控件放到窗口顶层
            drawingArea.getChildren().remove(textField);
            drawingArea.getChildren().add(textField);

            textField.setVisible(true);
            textField.setText(text.getText());
            editer.setAllVisiable(false);
        }
        this.isDrag = false;
    }

    //拖拽后放松鼠标也会触发单击事件，单击事件里设了clickCount=1，所以这里不用加RootLayoutController.clickCount=1;这句
    //只要把连接信息类ConnectionInfo的对象加到了ArrayList<ConnectionInfo> connectionInfos里，打开上次的流程图后，
    //只要一拖拽图形，就会有drag();即重新设置连接点，连接线，图形，选中框，图形上文字
    public void drag(MouseEvent e) {
        isDrag = true;
        double xx = x, yy = y;

        this.setX(e.getSceneX() - tOFFX);
        this.setY(e.getSceneY() - tOFFY);
        createDrawPoints();//移动图形的那四个连接点
        update();//当文字发生改变的时候，更新图形中的文字的内容和文字的位置
        lineMove();//MyShape类里的函数，用来根据图形的连线信息，重新设置连接线位置

        //拖动图形时，设置选中框也跟着移动
        editer.getFrame().setX(this.x - this.width - 10);
        editer.getFrame().setY(this.y - this.height - 10);
        editer.getFrame().setWidth(2 * this.width + 20);
        editer.getFrame().setHeight(2 * this.height + 20);
        editer.getCircles()[0].setCenterX(editer.getFrame().getX());
        editer.getCircles()[1].setCenterX(editer.getFrame().getX());
        editer.getCircles()[2].setCenterX(editer.getFrame().getX());
        editer.getCircles()[3].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth() / 2);
        editer.getCircles()[4].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth() / 2);
        editer.getCircles()[5].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth() / 2);
        editer.getCircles()[6].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth());
        editer.getCircles()[7].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth());
        editer.getCircles()[8].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth());

        editer.getCircles()[0].setCenterY(editer.getFrame().getY());
        editer.getCircles()[1].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight() / 2);
        editer.getCircles()[2].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight());
        editer.getCircles()[3].setCenterY(editer.getFrame().getY());
        editer.getCircles()[4].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight() / 2);
        editer.getCircles()[5].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight());
        editer.getCircles()[6].setCenterY(editer.getFrame().getY());
        editer.getCircles()[7].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight() / 2);
        editer.getCircles()[8].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight());

        if (getIsSelected()) {
            for (int i = 0; i < drawController.getList().size(); i++) {
                if (drawController.getList().get(i).getId() != id && drawController.getList().get(i).getIsSelected()) {
                    drawController.getList().get(i).dragXY(x - xx, y - yy);
                }
            }
            for (int i = 0; i < drawController.getListLine().size(); i++) {
                if (drawController.getListLine().get(i).getId() != id && drawController.getListLine().get(i).getIsSelected()) {
                    drawController.getListLine().get(i).dragXY(x - xx, y - yy);
                }
            }
        }
    }

    public void dragXY(double dx, double dy) {
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
        createDrawPoints();//移动图形的那四个连接点
        update();//当文字发生改变的时候，更新图形中的文字的内容和文字的位置
        lineMove();//MyShape类里的函数，用来根据图形的连线信息，重新设置连接线位置

        //拖动图形时，设置选中框也跟着移动
        editer.getFrame().setX(this.x - this.width - 10);
        editer.getFrame().setY(this.y - this.height - 10);
        editer.getFrame().setWidth(2 * this.width + 20);
        editer.getFrame().setHeight(2 * this.height + 20);
        editer.getCircles()[0].setCenterX(editer.getFrame().getX());
        editer.getCircles()[1].setCenterX(editer.getFrame().getX());
        editer.getCircles()[2].setCenterX(editer.getFrame().getX());
        editer.getCircles()[3].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth() / 2);
        editer.getCircles()[4].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth() / 2);
        editer.getCircles()[5].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth() / 2);
        editer.getCircles()[6].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth());
        editer.getCircles()[7].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth());
        editer.getCircles()[8].setCenterX(editer.getFrame().getX() + editer.getFrame().getWidth());

        editer.getCircles()[0].setCenterY(editer.getFrame().getY());
        editer.getCircles()[1].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight() / 2);
        editer.getCircles()[2].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight());
        editer.getCircles()[3].setCenterY(editer.getFrame().getY());
        editer.getCircles()[4].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight() / 2);
        editer.getCircles()[5].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight());
        editer.getCircles()[6].setCenterY(editer.getFrame().getY());
        editer.getCircles()[7].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight() / 2);
        editer.getCircles()[8].setCenterY(editer.getFrame().getY() + editer.getFrame().getHeight());
    }

    //当文字发生改变的时候，更新图形中的文字的内容和文字的位置
    public void update() {
        int len = text.getText().length() * 7;//汉字的话就在图形中间，字母或数字就在图形偏左
        text.setX(x - len);
        text.setY(y);
    }

    //MyShape类里的函数，用来根据图形的连线信息，重新设置连接线位置
    public void lineMove() {
        for (ConnectionInfo info : connectionInfos) {
            if (info.getConnectionPart().equals("end")) {
                double centerX, centerY;
                centerX = drawPoints.getCircles()[info.getLocation()].getCenterX();
                centerY = drawPoints.getCircles()[info.getLocation()].getCenterY();
                info.getLine().setEX(centerX);
                info.getLine().setEY(centerY);
            } else if (info.getConnectionPart().equals("start")) {
                double centerX, centerY;
                centerX = drawPoints.getCircles()[info.getLocation()].getCenterX();
                centerY = drawPoints.getCircles()[info.getLocation()].getCenterY();
                info.getLine().setSX(centerX);
                info.getLine().setSY(centerY);
            }
        }
    }

}
