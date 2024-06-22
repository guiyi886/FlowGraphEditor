package model;

import javafx.scene.shape.Polygon;

//菱形，kind是Decision
public class Decision extends MyShape {
	Polygon polygon;
	public Decision(double x, double y,int id) {
		super(x, y, 100,50);
		this.id=id;
		polygon = new Polygon();
		setMyShape(polygon);
		setShape();
	}
	public Decision(double x, double y,double width,double height,int id,String text) {
		super(x, y, width,height);
		polygon = new Polygon();
		setMyShape(polygon);
		this.id=id;
		setShape();
		this.getText().setText(text);//设置文本内容
		this.update();//更新文本位于图形的位置
	}

	//更新形状坐标
	@Override
	public void setShape() {
		double upLeftX = this.x;
		double upLeftY = this.y - height;
		double upRightX = this.x + width;
		double upRightY = this.y;
		double downLeftX = this.x - width;
		double downLeftY = this.y;
		double downRightX = this.x;
		double downRightY = this.y + height;
		Double[] list = { upLeftX, upLeftY, upRightX, upRightY, downRightX, downRightY, downLeftX, downLeftY };
		polygon.getPoints().setAll(list);
	}
}
