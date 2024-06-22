package model;

import javafx.scene.shape.Rectangle;

//圆角矩形，kind是RoundRectangle
public class RoundRectangle extends MyShape {
	private Rectangle rectangle;
    public RoundRectangle(double x,double y,int id){
        super(x,y,100,50);
        rectangle = new Rectangle();
		setMyShape(rectangle);
		setShape();
		this.id=id;
    }
    
    public RoundRectangle(double x, double y,double width,double height,int id,String text) {
		super(x, y, width,height);
		rectangle = new Rectangle();
		setMyShape(rectangle);
		this.id=id;
		setShape();
		this.getText().setText(text);//设置文本内容
		this.update();//更新文本位于图形的位置
	}
    
    public void setShape(){
    	rectangle.setWidth(2*width);
    	rectangle.setHeight(2*height);
    	rectangle.setX(x-width);
    	rectangle.setY(y-height);
    	rectangle.setStyle("-fx-arc-height: 100;-fx-arc-width: 100;");//圆角
    }
}
