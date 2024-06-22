package controller;

import model.BrokenLine;
import model.CurvedRectangle;
import model.Decision;
import model.InputRectangle;
import model.MyCircle;
import model.MyLine;
import model.MyRectangle;
import model.MyShape;
import model.RoundRectangle;

//由ShapeFactory类里的函数生成画布上的对象
public class ShapeFactory {
	//只要当前程序不关闭，那么存在过的形状或线（从左边选择区新建的或打开过去的流程图的）都有个不同的countShapeID，
	//这个数是只增不减的。目的是为了知道是哪个id号的线连接的图形
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


	//这个是类FileMenuController里的函数调用的
	public void produce(String kind,double x,double y,double width,double height,String text,int id) 
	{
		if (kind.indexOf("Line") != -1) //线。BrokenLine和MyLine里都有Line
		{
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
			drawController.addLine(shape);//把这条线加到DrawController类里的线集合里，并且在绘图区显示	
		} 
		else //形状
		{
			MyShape shape = null;
			//这里用文件生成图形时，直接用图形保存时的id，不然由于图形连接信息里的连接线的id会对不上现有的任何一条线，
			//会在用线的id号得到这条线时产生空指针错误
			switch (kind) {
			case "CurvedRectangle":
				shape = new CurvedRectangle(x, y, width,height,id,text);
				break;
			case "Decision":
				shape = new Decision(x, y, width,height,id,text);
				break;
			case "InputRectangle":
				shape = new InputRectangle(x, y, width,height,id,text);
				break;
			case "MyCircle":
				shape = new MyCircle(x, y, width,height,id,text);
				break;
			case "RoundRectangle":
				shape = new RoundRectangle(x, y, width,height,id,text);
				break;
			case "MyRectangle":
				shape = new MyRectangle(x, y, width,height,id,text);
				break;
			default:
				break;
			}
			drawController.addShape(shape);//把这个形状加到DrawController类里的形状集合里，并且在绘图区显示			
		}
	}

	public void produce(String kind, double x, double y) 
	{
		countShapeID++;//给新图形分配一个比上个图形的id号加了1的id号
		if (kind.indexOf("Line") != -1) //两种线分别叫MyLine和BrokenLine，都有Line字符
		{
			MyLine shape = null;
			switch (kind) 
			{
			case "MyLine":
				shape =new MyLine(x,y+100,x,y,countShapeID);
				break;
			case "BrokenLine":
				shape =new BrokenLine(x+100,y + 100,x,y,countShapeID);
				break;
			
			default:
				break;
			}
			drawController.addLine(shape);//把这条线加到DrawController类里的线集合里，并且在绘图区显示
		} 
		else 
		{
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
			drawController.addShape(shape);//把这个形状加到DrawController类里的形状集合里，并且在绘图区显示(即加到面板里)
		}
	}
}
