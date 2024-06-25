package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.MyShape;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static model.MyLine.colorMap;

// 根面板的控制器
// 这个类中要把用到的控件都加进来，不然这些控件就不存在
public class RootLayoutController implements Initializable {
    @FXML
    private AnchorPane drawingArea; // 绘图区
    @FXML
    private VBox shapeArea; // 选符号区
    @FXML
    private TextField textField; // 单行输入框

    // shape
    @FXML
    private ImageView RoundRectangle; // 圆角矩形
    @FXML
    private ImageView Rectangle; // 矩形
    @FXML
    private ImageView Decision; // 菱形
    @FXML
    private ImageView InputRectangle; // 平行四边形
    @FXML
    private ImageView Circular; // 圆
    @FXML
    private ImageView CurvedRectangle; // 波浪线矩形
    // line
    @FXML
    private ImageView MyLine;
    @FXML
    private ImageView BrokenLine;

    @FXML
    private Label colorLabel1;
    @FXML
    private Label colorLabel2;
    @FXML
    private Label colorLabel3;
    @FXML
    private Label colorLabel4;
    @FXML
    private Label colorLabel5;
    @FXML
    private Label colorLabel6;
    @FXML
    private Label colorLabel7;
    @FXML
    private Label colorLabel8;

    //private final Color[] colors = {Color.BLUE, Color.RED, Color.PURPLE};
    private final String[] colorNames = {"白", "黑", "蓝", "红", "绿", "黄", "紫",};
    //private int colorIndex = 0;
    private final Map<String, Integer> myColorIndex = new HashMap<>();
    public static final Map<String, String> imageToColorMaps = new HashMap<>();

    /*@FXML
    private void initialize() {
        // 添加调试信息
        System.out.println("colorLabel1: " + colorLabel1);
        System.out.println("colorLabel2: " + colorLabel2);
        System.out.println("colorLabel3: " + colorLabel3);
        System.out.println("colorLabel4: " + colorLabel4);
        System.out.println("colorLabel5: " + colorLabel5);
        System.out.println("colorLabel6: " + colorLabel6);
        System.out.println("colorLabel7: " + colorLabel7);
        System.out.println("colorLabel8: " + colorLabel8);

        setupImageView(RoundRectangle, colorLabel1);
        setupImageView(Rectangle, colorLabel2);
        setupImageView(Decision, colorLabel3);
        setupImageView(InputRectangle, colorLabel4);
        setupImageView(Circular, colorLabel5);
        setupImageView(CurvedRectangle, colorLabel6);
        setupImageView(MyLine, colorLabel7);
        setupImageView(BrokenLine, colorLabel8);

        // 设置鼠标手势
        colorLabel1.setCursor(Cursor.HAND);
        colorLabel2.setCursor(Cursor.HAND);
        colorLabel3.setCursor(Cursor.HAND);
        colorLabel4.setCursor(Cursor.HAND);
        colorLabel5.setCursor(Cursor.HAND);
        colorLabel6.setCursor(Cursor.HAND);
        colorLabel7.setCursor(Cursor.HAND);
        colorLabel8.setCursor(Cursor.HAND);
    }*/

    private void setupImageView(ImageView imageView, Label colorLabel) {
        // 初始化颜色、颜色文本值、颜色文本框颜色
        String shapeName = imageView.getId();
        if (shapeName.equals("Rectangle")) {
            //System.out.println(111);
            shapeName = "MyRectangle";
        } else if (shapeName.equals("Circular")) {
            shapeName = "MyCircle";
        }
        if (imageView.getId().equals("MyLine") || imageView.getId().equals("BrokenLine")) {
            imageToColorMaps.put(shapeName, colorNames[1]);
            myColorIndex.put(shapeName, 1);
            String newColorName = colorNames[1];
            colorLabel.setText(newColorName);
            String argbColor = colorMap.get(newColorName).toString(); // 32位的ARGB颜色值
            String color = argbColor.substring(2, 8);
            colorLabel.setStyle("-fx-background-color: #" + color + ";");

        } else {
            imageToColorMaps.put(shapeName, colorNames[0]);
            myColorIndex.put(shapeName, 0);
            String newColorName = colorNames[0];
            colorLabel.setText(newColorName);
            String argbColor = colorMap.get(newColorName).toString(); // 32位的ARGB颜色值
            String color = argbColor.substring(2, 8);
            colorLabel.setStyle("-fx-background-color: #" + color + ";");
        }

        colorLabel.setOnMouseClicked(event -> {
            //String str=imageView.getId();
            //System.out.println(imageView.getId());


            String shapeName2 = imageView.getId();
            if (shapeName2.equals("Rectangle")) {
                //System.out.println(111);
                shapeName2 = "MyRectangle";
            } else if (shapeName2.equals("Circular")) {
                shapeName2 = "MyCircle";
            }/*else if(shapeName.equals("BrokenLine")){
                shapeName="MyRectangle";
            }*/

            // 切换颜色
            int colorIndex = (myColorIndex.get(shapeName2) + 1) % colorNames.length;
            String newColorName = colorNames[colorIndex];
            myColorIndex.put(shapeName2, colorIndex);

            // 更新颜色文本
            colorLabel.setText(newColorName);
            String argbColor = colorMap.get(newColorName).toString(); // 32位的ARGB颜色值

            String color = argbColor.substring(2, 8);
            System.out.println(newColorName + " " + color);
            //System.out.println(colorMap.get(newColorName));
            //String color = String.format("#%08X", colorMap.get(newColorName));
            colorLabel.setStyle("-fx-background-color: #" + color + ";");
            imageToColorMaps.put(shapeName2, newColorName);
        });
    }

    // 当形状或线的对象被单击、拖拽、改变大小时，clickCount 置为 1，clickCount 为 0 说明点的是空白处，要把所有图形取消选中
    public static int clickCount = 0;

    // 图层相关的控制器，工厂
    private ShapeFactory shapeFactory;
    private DrawController drawController;
    private FileMenuController menuController;

    // 新建菜单操作，清除绘图区和相关集合
    public void menuNew() {
        drawingArea.getChildren().clear();
        drawController.getList().clear();
        drawController.getListLine().clear();
        drawController.getMyShapeAndMyLines().clear();
        setupImageView(RoundRectangle, colorLabel1);
        setupImageView(Rectangle, colorLabel2);
        setupImageView(Decision, colorLabel3);
        setupImageView(InputRectangle, colorLabel4);
        setupImageView(Circular, colorLabel5);
        setupImageView(CurvedRectangle, colorLabel6);
        setupImageView(MyLine, colorLabel7);
        setupImageView(BrokenLine, colorLabel8);
    }

    // 保存菜单操作，调用保存方法
    public void menuSave() {
        menuController.saveDrawingArea(drawController);
    }

    // 打开菜单操作，清除当前状态，然后打开文件
    public void menuOpen() {
        menuNew();
        menuController.getDrawingArea(shapeFactory, drawController);
    }

    // 导出菜单操作，导出绘图区为图片
    public void menuExport() {
        menuController.exportDrawingArea(drawingArea);
    }

    public void menuBeginEnd() {
        menuNew();
        menuController.menuBeginEnd(shapeFactory, drawController);
    }

    public void menuInOut() {
        menuNew();
        menuController.menuInOut(shapeFactory, drawController);
    }

    // 帮助菜单操作，显示帮助页面
    public void menuHelp() {
        menuController.help();
    }

    private String selectShape = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupImageView(RoundRectangle, colorLabel1);
        setupImageView(Rectangle, colorLabel2);
        setupImageView(Decision, colorLabel3);
        setupImageView(InputRectangle, colorLabel4);
        setupImageView(Circular, colorLabel5);
        setupImageView(CurvedRectangle, colorLabel6);
        setupImageView(MyLine, colorLabel7);
        setupImageView(BrokenLine, colorLabel8);

        // 设置鼠标手势
        RoundRectangle.setCursor(Cursor.HAND);
        Rectangle.setCursor(Cursor.HAND);
        Decision.setCursor(Cursor.HAND);
        InputRectangle.setCursor(Cursor.HAND);
        Circular.setCursor(Cursor.HAND);
        CurvedRectangle.setCursor(Cursor.HAND);
        MyLine.setCursor(Cursor.HAND);
        BrokenLine.setCursor(Cursor.HAND);

        // 初始化控制器和工厂
        drawController = new DrawController(drawingArea);
        shapeFactory = new ShapeFactory(drawController);

        // 使 MyShape、MyLine、DrawController 这三个类里的 textField 都指的是界面里这个单行文本框
        MyShape.textField = textField;
        model.MyLine.textField = textField;
        DrawController.textField = textField;

        drawController.setKeyBoardManager();
        menuController = new FileMenuController();

        // 模式初始化
        shapeArea.setVisible(true);

        // 设置添加图形的动作，需要绘图区和图标区
        // 已选中图形，绘制到图形绘制区
        drawingArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                drawController.getKeyBoardManager().storeLastDrawingArea();
                drawingArea.requestFocus(); // 设置焦点以实现键盘事件

                if (event.getClickCount() == 1 && selectShape != null) {
                    System.out.println(1111);
                    double x, y;
                    x = event.getX();
                    y = event.getY();
                    shapeFactory.produce(selectShape, x, y);
                    selectShape = null;
                } else if (event.getClickCount() == 1 && selectShape == null) {
                    // 点击 drawingArea 空白处时，所有形状设置为未选中
                    if (clickCount == 1) {
                        System.out.println(2222);
                        clickCount = 0;
                        if (textField.isVisible() == true) // 文本框可见时
                        {
                            System.out.println(22221111);
                            textField.setVisible(false);
                            String text = textField.getText();
                            drawController.setText(text);
                        }
                    } else if (clickCount == 0) // 点空白处
                    {
                        System.out.println(3333);
                        drawController.clearAllOnEdit();
                        if (textField.isVisible() == true) // 文本框可见时
                        {
                            System.out.println(33331111);
                            textField.setVisible(false);
                            String text = textField.getText();
                            drawController.setText(text);
                        }
                    }
                } else if (event.getClickCount() == 2) {
                    System.out.println(4444);
                    // 双击图形时，触发的事件是：先单击了图形，再单击了面板，再双击图形，再双击面板。
                    // 所以双击图形后生成的单行输入框，给该单行输入框设置焦点要在这里设置
                    textField.requestFocus();
                }
            }
        });

        // 双击图形选择区的图形的情况和单击图形选择区的图形选中图形的情况
        shapeArea.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    drawController.getKeyBoardManager().storeLastDrawingArea();
                    if (event.getTarget().getClass() == ImageView.class) {
                        int x, y;
                        x = 550;
                        y = 400;
                        selectShape = ((ImageView) event.getTarget()).getId(); // 得到界面那里右侧框 code 下的 fx:id
                        shapeFactory.produce(selectShape, x, y);
                        selectShape = null;
                    }
                } else if (event.getClickCount() == 1) {
                    if (event.getTarget().getClass() == ImageView.class) {
                        ImageView nowImage = (ImageView) event.getTarget();
                        selectShape = nowImage.getId();
                    }
                }
            }
        });

        textField.setOnAction(e -> { // 在单行文本框内按回车
            textField.setVisible(false);
            String text = textField.getText();
            drawController.setText(text);
        });
    }
}
