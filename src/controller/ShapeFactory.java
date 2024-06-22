package controller;

import model.*;

// 由 ShapeFactory 类里的函数生成画布上的对象
public class ShapeFactory {
    // 只要当前程序不关闭，那么存在过的形状或线（从左边选择区新建的或打开过去的流程图的）都有个不同的 countShapeID，
    // 这个数是只增不减的。目的是为了知道是哪个 id 号的线连接的图形
    public static int countShapeID = 0;
    private DrawController drawController;

    public ShapeFactory(DrawController drawController) {
        this.drawController = drawController;
    }

    public DrawController getDrawController() {
        return drawController;
    }

    public void setDrawController(DrawController drawController) {
        this.drawController = drawController;
    }

    public int getCountShapeID() {
        return countShapeID;
    }

    public void setCountShapeID(int countShapeID) {
        this.countShapeID = countShapeID;
    }

    /**
     * 通过指定的参数在绘图区生成指定类型的形状或线
     *
     * @param kind   形状或线的类型
     * @param x      形状或线的横坐标
     * @param y      形状或线的纵坐标
     * @param width  形状或线的宽度
     * @param height 形状或线的高度
     * @param text   形状或线的文本内容
     * @param id     形状或线的唯一标识符
     */
    public void produce(String kind, double x, double y, double width, double height, String text, int id) {
        if (kind.indexOf("Line") != -1) { // 线。BrokenLine 和 MyLine 里都有 Line
            MyLine shape = null;
            switch (kind) {
                case "MyLine":
                    shape = new MyLine(x, y, width, height, id);
                    break;
                case "BrokenLine":
                    shape = new BrokenLine(x, y, width, height, id);
                    break;
                default:
                    break;
            }
            shape.getText().setText(text);
            drawController.addLine(shape); // 把这条线加到 DrawController 类里的线集合里，并且在绘图区显示
        } else { // 形状
            MyShape shape = null;
            // 这里用文件生成图形时，直接用图形保存时的 id，不然由于图形连接信息里的连接线的 id 会对不上现有的任何一条线，
            // 会在用线的 id 号得到这条线时产生空指针错误
            switch (kind) {
                case "CurvedRectangle":
                    shape = new CurvedRectangle(x, y, width, height, id, text);
                    break;
                case "Decision":
                    shape = new Decision(x, y, width, height, id, text);
                    break;
                case "InputRectangle":
                    shape = new InputRectangle(x, y, width, height, id, text);
                    break;
                case "MyCircle":
                    shape = new MyCircle(x, y, width, height, id, text);
                    break;
                case "RoundRectangle":
                    shape = new RoundRectangle(x, y, width, height, id, text);
                    break;
                case "MyRectangle":
                    shape = new MyRectangle(x, y, width, height, id, text);
                    break;
                default:
                    break;
            }
            drawController.addShape(shape); // 把这个形状加到 DrawController 类里的形状集合里，并且在绘图区显示
        }
    }

    /**
     * 根据指定类型在绘图区生成默认大小的形状或线
     *
     * @param kind 形状或线的类型
     * @param x    形状或线的横坐标
     * @param y    形状或线的纵坐标
     */
    public void produce(String kind, double x, double y) {
        countShapeID++; // 给新图形分配一个比上个图形的 id 号加了 1 的 id 号
        if (kind.indexOf("Line") != -1) { // 两种线分别叫 MyLine 和 BrokenLine，都有 Line 字符
            MyLine shape = null;
            switch (kind) {
                case "MyLine":
                    shape = new MyLine(x, y + 100, x, y, countShapeID);
                    break;
                case "BrokenLine":
                    shape = new BrokenLine(x + 100, y + 100, x, y, countShapeID);
                    break;

                default:
                    break;
            }
            drawController.addLine(shape); // 把这条线加到 DrawController 类里的线集合里，并且在绘图区显示
        } else {
            MyShape shape = null;
            switch (kind) {
                case "CurvedRectangle":
                    shape = new CurvedRectangle(x, y, countShapeID);
                    break;
                case "Decision":
                    shape = new Decision(x, y, countShapeID);
                    break;
                case "InputRectangle":
                    shape = new InputRectangle(x, y, countShapeID);
                    break;
                case "Circular":
                    shape = new MyCircle(x, y, countShapeID);
                    break;
                case "RoundRectangle":
                    shape = new RoundRectangle(x, y, countShapeID);
                    break;
                case "Rectangle":
                    shape = new MyRectangle(x, y, countShapeID);
                    break;
                default:
                    break;
            }
            drawController.addShape(shape); // 把这个形状加到 DrawController 类里的形状集合里，并且在绘图区显示(即加到面板里)
        }
    }
}
