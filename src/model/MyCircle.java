package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

//kind是Circular
public class MyCircle extends MyShape 
{
	private Ellipse circle;
	public MyCircle(double x,double y,int id){
		this(x,y,50.0);
		this.id=id;
	}
	public MyCircle(double x,double y,double radius)
	{
			super(x,y,radius,radius);
			this.circle=new Ellipse(this.x, this.y, radius, radius);
			this.circle.setFill(Color.WHITE);
			this.circle.setStroke(Color.BLACK);
			super.setMyShape(this.circle);
	}
	public MyCircle(double x, double y,double width,double height,int id,String text) {
		super(x, y, width,height);
		this.circle=new Ellipse(this.x, this.y, width, height);
		setMyShape(circle);
		this.id=id;
		setShape();
		this.getText().setText(text);//设置文本内容
		this.update();//更新文本位于图形的位置
	}
	
	public void setShape()
	{
			circle.setRadiusX(width);
			circle.setRadiusY(height);
			circle.setCenterX(this.x);
			circle.setCenterY(this.y);
	}
	
	public void setX(double x) {
		this.x = x;
		setShape();
	}

	public void setY(double y) {
		this.y = y;
		setShape();
	}

	public void setWidth(double width) {
		this.width = width;
		setShape();
	}

	public void setHeight(double height) {
		this.height = height;
		setShape();
	}
}
