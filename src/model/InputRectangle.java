package model;

import javafx.scene.shape.Polygon;

//平行四边形，kind是InputRectangle
public class InputRectangle extends MyShape{
	Polygon polygon;
	public InputRectangle(double x, double y,int id) {
		super(x, y, 100,50);
		this.id=id;
		polygon = new Polygon();
		setMyShape(polygon);
		setShape();
	}
	
	public InputRectangle(double x, double y,double width,double height,int id,String text) {
		super(x, y, width,height);
		polygon = new Polygon();
		setMyShape(polygon);
		this.id=id;
		setShape();
		this.getText().setText(text);//设置文本内容
		this.update();//更新文本位于图形的位置
	}

	@Override
	public void  setShape()
	 {
			double upLeftX = this.x-width/2;
			double upLeftY = this.y-height;
			double upRightX = this.x+width;
			double upRightY = upLeftY;
			double downLeftX = this.x-width;
			double downLeftY = this.y + height;
			double downRightX = this.x+width/2;
			double downRightY = this.y+height;
			Double []list={upLeftX,upLeftY,upRightX,upRightY,downRightX,downRightY,downLeftX,downLeftY};
			polygon.getPoints().setAll(list);
	}
	
	@Override
	public void createDrawPoints(){
		double leftMidX = this.x - 3*width/4;
		double leftMidY = this.y ;
		double upMidX = this.x;
		double upMidY = this.y-height;
		double rightMidX = this.x+3*width/4;
		double rightMidY = this.y;
		double downMidX = this.x;
		double downMidY = this.y+height;
		drawPoints.updataLocation(leftMidX, leftMidY, upMidX, upMidY, rightMidX, rightMidY, downMidX, downMidY);
	}
}
