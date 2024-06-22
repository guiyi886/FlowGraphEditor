package model;

import javafx.scene.shape.Polygon;

//曲边矩形，kind是CurvedRectangle
public class CurvedRectangle extends MyShape {
	public static  final int ARC_NUM = 100; //曲边矩形的点个个数，用100个点形成一条波浪线
	Polygon polygon;
	public CurvedRectangle(double x, double y,int id) {
		super(x, y, 100,50);
		polygon = new Polygon();
		setMyShape(polygon);
		this.id=id;
		setShape();
	}
	public CurvedRectangle(double x, double y,double width,double height,int id,String text) {
		super(x, y, width,height);
		polygon = new Polygon();
		setMyShape(polygon);
		this.id=id;
		setShape();
		this.getText().setText(text);//设置文本内容
		this.update();//更新文本位于图形的位置
	}
	/* 
	 * 曲边矩形，重写更新坐标方法
	 */
	@Override
	public void setShape() {
		double downLeftX = this.x-width;
		double downLeftY = this.y + 3.0 * height / 4;
		double upLeftX = this.x-width;
		double upLeftY = this.y -height;
		double upRightX = this.x + width;
		double upRightY = this.y - height;
		double y;
		double A = height / 4;
		double dx = 2 * width / (ARC_NUM);
		double p = Math.PI / width;
		double x = 0;
		
		Double[] list = new Double[2 * ARC_NUM + 6];
		//下方for循环用100个点形成一条波浪线
		for (int i = 0; i <= 2 * ARC_NUM; i += 2) 
		{
			y = A * Math.sin(p * x);
			list[i] = downLeftX + x;
			list[i + 1] = downLeftY + y;
			x = x + dx;
		}
		
		//这里把右上、左上两个点加进来
		list[2 *ARC_NUM + 2] = upRightX;
		list[2 *ARC_NUM + 3] = upRightY;
		list[2 *ARC_NUM + 4] = upLeftX;
		list[2 *ARC_NUM + 5] = upLeftY;
		
		polygon.getPoints().setAll(list);
	}
}
