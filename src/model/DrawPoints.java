package model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

//形状上连接线的那四个点
public class DrawPoints 
{
	public static final double DRAW_POINTS_RADIUS=5; //编辑点圆的半径
	private Circle[] points;
	public  DrawPoints()
	{
		points = new Circle[4];
		for(int i =0;i<4;i++)
		{
			points[i] = new Circle();
			points[i].setRadius(DRAW_POINTS_RADIUS);
		}
	}
	public void updataLocation(double leftMidX,double leftMidY,double upMidX,double upMidY,double rightMidX,double rightMidY,double downMidX,double downMidY)
	{
		Double[] list = {leftMidX,leftMidY,upMidX,upMidY,rightMidX,rightMidY,downMidX,downMidY};
		int cnt=0;
		for(int i=0;i<8;i+=2)
		{
			points[cnt].setCenterX(list[i]);
			points[cnt].setCenterY(list[i+1]);
			cnt++;
		}
	}
	public void delPoint(Pane pane) {
		pane.getChildren().removeAll(points);
	}
	
	public Circle[] getCircles(){
		return points;
	}
	
	public void setAllVisiable(boolean state)
	{
		for(int i =0;i<points.length;i++)
		{
			points[i].setVisible(state);
		}
	}
}
