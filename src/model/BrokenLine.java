package model;

import controller.DrawController;
import controller.RootLayoutController;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;


//折线，kind是BrokenLine
public class BrokenLine extends MyLine{
	private Polyline polyline;

	public BrokenLine(double startX, double startY, double endX, double endY,int id) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.id = id;
		polyline = new Polyline();//折线类
	    polyline.getPoints().addAll(new Double[]{
	        startX,startY,
	        startX, endY,
	        endX,endY });
	    polyline.setStrokeWidth(3);
		
	    circles=new Circle[2];
	    circles[0]=new Circle();
	    circles[0].setCenterX(startX);//箭头上的点
		circles[0].setCenterY(startY);
		circles[0].setRadius(5);
		circles[0].setFill(Color.BLUE);
		circles[1]=new Circle();
		circles[1].setCenterX(endX);//圆点
		circles[1].setCenterY(endY);
		circles[1].setRadius(5);
		circles[1].setFill(Color.BLUE);
		
		polygon = new Polygon();
		text = new Text();
		setShape();
		super.line=new Line();//不加这句会在走到super.addMyLineListener()里的line.setCursor(Cursor.MOVE)时报错
		super.addMyLineListener();
		addBrokenLineListening();
	}

	/**
	 * 每次更新了数据之后，都要更新图形，这样才能使得图形发生变化。
	 */
	@Override
	public void setShape() {
		// 这一段重置箭头位置
		double dx=startX-startX;
		double dy=startY-endY;
		double lineLength = Math.sqrt(dx * dx + dy * dy);// 线长
		double sin = dy / lineLength;
		double cos = dx / lineLength;
		double centerX = startX - 25 * cos;// 25是箭头长,即箭头等腰三角形的高。center指箭头在线上的那个点
		double centerY = startY - 25 * sin;
		double triaX = startX;
		double triaY = startY;
		double tribX = centerX + 10 * sin;// 10是箭头等腰三角形底的一半
		double tribY = centerY - 10 * cos;
		double tricX = centerX - 10 * sin;
		double tricY = centerY + 10 * cos;
		polygon.getPoints().setAll(
				new Double[]{ triaX, triaY, tribX, tribY, tricX, tricY }
				);
		
		
		// 重置两个圆点位置
		circles[0].setCenterX(startX);
		circles[0].setCenterY(startY);
		circles[1].setCenterX(endX);
		circles[1].setCenterY(endY);

		//重置线位置
		polyline.getPoints().setAll(
				new Double[]{ startX,startY,startX, endY,endX,endY}
				);
				
		//重置文本位置
		text.setX((startX+endX)/2);
		text.setY(endY);

		setAllVisiable(true);	
	}
	/**
	 * 加入图形
	 */
	@Override
	public void getPane(AnchorPane drawingArea, DrawController drawController) {
		drawingArea.getChildren().add(polyline);
		drawingArea.getChildren().add(polygon);
		drawingArea.getChildren().add(text);
		drawingArea.getChildren().addAll(circles);
		this.drawingArea = drawingArea;
		this.drawController = drawController;
		setAllVisiable(true);	
	}
	/**
	 * 可以删除这个图形
	 */
	public void delete(){
		drawingArea.getChildren().remove(polyline);
		drawingArea.getChildren().remove(polygon);
		drawingArea.getChildren().remove(text);
		drawingArea.getChildren().removeAll(circles);
	}
	/**
	 *能够将图形设置在最顶层
	 */
	@Override
	public void setToTop() {
		delete();
		getPane(drawingArea, drawController);
	}
	
	@Override
	public void click(MouseEvent e)
	{
		if (e.getClickCount() == 1) 
		{		
			if(this.isDrag==true)
			{
				drawController.clearAllOnEdit();
				setAllVisiable(true);
				this.setToTop();//几个图形重叠时，会把当前图形放到其他图形上面
			}
			else if(isSelected == false)
			{
				drawController.clearAllOnEdit();
				setAllVisiable(true);
				this.setToTop();//几个图形重叠时，会把当前图形放到其他图形上面
			}else
			{
				setAllVisiable(false);
			}
			RootLayoutController.clickCount=1;
		} 
		else if(e.getClickCount() == 2)//只会触发drawingArea的双击事件，不会触发其单击事件
		{
			this.setToTop();

			drawController.writeId=this.id;
			textField.setLayoutX((this.startX+this.endX)/2);
			textField.setLayoutY(endY);
			//下方两句把textField控件放到窗口顶层
			drawingArea.getChildren().remove(textField);
			drawingArea.getChildren().add(textField);
			
			textField.setVisible(true);
			textField.setText(text.getText());
			setAllVisiable(false);
		}
	}
	@Override
	public void clickListener()
	{
		polyline.setOnMouseClicked(e -> {
			click(e);
		});
		polygon.setOnMouseClicked(e -> {
			click(e);
		});
		text.setOnMouseClicked(e->{
			click(e);
		});
	}
	
	public void addBrokenLineListening() {
		clickListener();
		//折线
		polyline.setCursor(Cursor.MOVE);
		polyline.setOnMousePressed(e -> {
			this.isDrag=false;
	    	lastX = e.getX();
			lastY = e.getY();
		});
		polyline.setOnMouseDragged(e -> {
			double dx = e.getX() - lastX;
			double dy = e.getY() - lastY;
			lastX = e.getX();
			lastY = e.getY();
			this.setSX(this.startX+dx);
			this.setSY(this.startY+dy);
			this.setEX(this.endX+dx);
			this.setEY(this.endY+dy);
			drawController.checkDistanceToPoints(polyline.getPoints().get(0),polyline.getPoints().get(1),polyline.getPoints().get(4),polyline.getPoints().get(5),this);
			this.isDrag=true;
		});
		polyline.setOnMouseReleased(e -> {
			if (startLinkShape != null)
				startLinkShape.delConnectionInfo(this,"start");
			if (endLinkShape != null)
				endLinkShape.delConnectionInfo(this,"end");
			drawController.connect(polyline.getPoints().get(0),polyline.getPoints().get(1),"start", this);
			drawController.connect(polyline.getPoints().get(4),polyline.getPoints().get(5),"end", this);
		});	
	}
}
