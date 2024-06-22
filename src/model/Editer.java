package model;

import controller.RootLayoutController;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
/**
 * 编辑框类
 */
public class Editer {
	private Circle circles[];
	private Rectangle frame;
	private double x;
	private double y;
	private double width;
	private double height;
	MyShape myshape;
	public Editer(double x,double y,double height,double width)
	{
		this.x=x-width-10;
		this.y=y-height-10;
		this.width=width*2+20;
		this.height=height*2+20;
		this.circles=new Circle[9];
        frame= new Rectangle(this.x,this.y,this.width,this.height);
        frame.setFill(Color.TRANSPARENT);
        frame.setStroke(Color.BLACK);
        frame.setStrokeWidth(1);
        
        for(int i =0;i<3;i++)
		{
			for(int j =0;j<3;j++)
			{
				circles[3*i+j]=new Circle(0,0,5);
				circles[3*i+j].setCenterX(frame.getX()+frame.getWidth()/2+frame.getWidth()/2*(i-1));
				circles[3*i+j].setCenterY(frame.getY()+frame.getHeight()/2+frame.getHeight()/2*(j-1));
				circles[3*i+j].setRadius(5);
				circles[3*i+j].setFill(Color.BLUE);
				if(i==1&&j==1)
				{
					circles[3*i+j].setRadius(0);
				}
			}
		}
        addListener();
	}
	
	public void addListener()
	{
		circles[7].setOnMouseDragged(e -> {
			//因为选中框比图形多10，如果不加20，选中框可以缩成一条线，然后把图形反过来显示，下方其他20同理
        	if(e.getX()>=frame.getX()+20)
        	{
        		circles[7].setCenterX(e.getX());
        		circles[6].setCenterX(e.getX());
        		circles[8].setCenterX(e.getX());
        		frame.setWidth(e.getX()-frame.getX());
        		circles[3].setCenterX(frame.getX()+frame.getWidth()/2);
        		circles[5].setCenterX(frame.getX()+frame.getWidth()/2);
        		bind();
        	}
        });
        circles[5].setOnMouseDragged(e -> {
        	if(e.getY()>=frame.getY()+20)
        	{
        		circles[5].setCenterY(e.getY());
        		circles[2].setCenterY(e.getY());
        		circles[8].setCenterY(e.getY());
        		frame.setHeight(e.getY()-frame.getY());
        		circles[1].setCenterY(frame.getY()+frame.getHeight()/2);
        		circles[7].setCenterY(frame.getY()+frame.getHeight()/2);
        		bind();
        	}	
        });
        
        circles[3].setOnMouseDragged(e -> {
        	if(e.getY()<=frame.getY()+frame.getHeight()-20)
        	{
        		circles[3].setCenterY(e.getY());
        		circles[0].setCenterY(e.getY());
        		circles[6].setCenterY(e.getY());
        		frame.setY(e.getY());
        		frame.heightProperty().bind(circles[5].centerYProperty().subtract(frame.yProperty()));
        		circles[1].setCenterY(frame.getY()+frame.getHeight()/2);
        		circles[7].setCenterY(frame.getY()+frame.getHeight()/2);
        		bind();
        	}	
        	frame.heightProperty().unbind();
        });
        
        circles[1].setOnMouseDragged(e -> {
        	if(e.getX()<=frame.getX()+frame.getWidth()-20)
        	{
        		circles[1].setCenterX(e.getX());
        		circles[0].setCenterX(e.getX());
        		circles[2].setCenterX(e.getX());
        		frame.setX(e.getX());
        		frame.widthProperty().bind(circles[7].centerXProperty().subtract(frame.xProperty()));
        		circles[3].setCenterX(frame.getX()+frame.getWidth()/2);
        		circles[5].setCenterX(frame.getX()+frame.getWidth()/2);
        		bind();
        	}	
        	frame.widthProperty().unbind();       		
        });
        
        circles[6].setOnMouseDragged(e->{
        	if(e.getX()>=frame.getX()+20&&e.getY()<=frame.getY()+frame.getHeight()-20)
        	{
        		circles[6].setCenterX(e.getX());
        		circles[6].setCenterY(e.getY());
        		circles[0].setCenterY(e.getY());
        		circles[3].setCenterY(e.getY());
        		circles[7].setCenterX(e.getX());
        		circles[8].setCenterX(e.getX());
        		frame.setWidth(e.getX()-frame.getX());
        		frame.setY(e.getY());
        		frame.heightProperty().bind(circles[5].centerYProperty().subtract(frame.yProperty()));
        		circles[3].setCenterX(frame.getX()+frame.getWidth()/2);
        		circles[7].setCenterY(frame.getY()+frame.getHeight()/2);
        		circles[5].setCenterX(frame.getX()+frame.getWidth()/2);
        		circles[1].setCenterY(frame.getY()+frame.getHeight()/2);
        		bind();
        	}
        	frame.heightProperty().unbind();
        });
        
        circles[8].setOnMouseDragged(e->{
        	if(e.getX()>=frame.getX()+20&&e.getY()>=frame.getY()+20)
        	{
        		circles[8].setCenterX(e.getX());
        		circles[8].setCenterY(e.getY());
        		circles[2].setCenterY(e.getY());
        		circles[5].setCenterY(e.getY());
        		circles[6].setCenterX(e.getX());
        		circles[7].setCenterX(e.getX());
        		frame.setWidth(e.getX()-frame.getX());
        		frame.setHeight(e.getY()-frame.getY());
        		circles[3].setCenterX(frame.getX()+frame.getWidth()/2);
        		circles[1].setCenterY(frame.getY()+frame.getHeight()/2);
        		circles[5].setCenterX(frame.getX()+frame.getWidth()/2);
        		circles[7].setCenterY(frame.getY()+frame.getHeight()/2);
        		bind();
        	}
        });
        
        circles[0].setOnMouseDragged(e->{
        	if(e.getX()<=frame.getX()+frame.getWidth()-20&&e.getY()<=frame.getY()+frame.getHeight()-20)
        	{
        		circles[0].setCenterX(e.getX());
        		circles[0].setCenterY(e.getY());
        		circles[1].setCenterX(e.getX());
        		circles[2].setCenterX(e.getX());
        		circles[3].setCenterY(e.getY());
        		circles[6].setCenterY(e.getY());
        		frame.setX(e.getX());
        		frame.setY(e.getY());
        		frame.widthProperty().bind(circles[7].centerXProperty().subtract(frame.xProperty()));
        		frame.heightProperty().bind(circles[5].centerYProperty().subtract(frame.yProperty()));
        		circles[1].setCenterY(frame.getY()+frame.getHeight()/2);
        		circles[3].setCenterX(frame.getX()+frame.getWidth()/2);
        		circles[7].setCenterY(frame.getY()+frame.getHeight()/2);
        		circles[5].setCenterX(frame.getX()+frame.getWidth()/2);
        		bind();
        	}
        	frame.widthProperty().unbind();
        	frame.heightProperty().unbind();
        });
        
        circles[2].setOnMouseDragged(e->{
        	if(e.getX()<=frame.getX()+frame.getWidth()-20&&e.getY()>=frame.getY()+20)
        	{
        		circles[2].setCenterX(e.getX());
        		circles[2].setCenterY(e.getY());
        		circles[0].setCenterX(e.getX());
        		circles[1].setCenterX(e.getX());
        		circles[5].setCenterY(e.getY());
        		circles[8].setCenterY(e.getY());
        		frame.setHeight(e.getY()-frame.getY());
        		frame.setX(e.getX());
        		frame.widthProperty().bind(circles[7].centerXProperty().subtract(frame.xProperty()));
        		circles[7].setCenterY(frame.getY()+frame.getHeight()/2);
        		circles[3].setCenterX(frame.getX()+frame.getWidth()/2);
        		circles[1].setCenterY(frame.getY()+frame.getHeight()/2);
        		circles[5].setCenterX(frame.getX()+frame.getWidth()/2);
        		bind();
        		}
        	frame.widthProperty().unbind();
        });  
	}
	
	//用在上方addListener()函数里，在每次选中框大小改变后，其内图形也跟着改变
	public void bind()
	{
		myshape.setX(circles[3].getCenterX());
		myshape.setY(circles[1].getCenterY());
		myshape.setHeight(frame.getHeight()/2-10);
		myshape.setWidth(frame.getWidth()/2-10);
		RootLayoutController.clickCount=1;//RootLayoutController.clickCount用于点击空白处取消所有选中
		myshape.drawController.clearAllOnEdit();
		setAllVisiable(true);
		myshape.lineMove();
		myshape.createDrawPoints();//移动图形的那四个连接点
    	myshape.update();//当文字发生改变的时候，更新图形中的文字的内容和文字的位置
	}
	
	//设置编辑框消失或出现
	public void setAllVisiable(boolean state)
	{
		for(int i =0;i<3;i++)
		 {
			for(int j =0;j<3;j++)
			{
				circles[3*i+j].setVisible(state);
			}
		 }
		frame.setVisible(state);
        myshape.setISelected(state);
	}
	
	public void  addEditer(Pane pane){
		pane.getChildren().addAll(frame);
		pane.getChildren().addAll(circles);
	}

	public void delEditer(Pane pane) {
		pane.getChildren().removeAll(circles);
		pane.getChildren().removeAll(frame);
	}
	
	public Circle[] getCircles()
	{
		return circles;
	}

	public void setCircles(Circle[] circles)
	{
		this.circles = circles;
	}

	public Rectangle getFrame()
	{
		return frame;
	}

	public void setFrame(Rectangle frame)
	{
		this.frame = frame;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public MyShape getMyshape()
	{
		return myshape;
	}

	public void setMyshape(MyShape myshape)
	{
		this.myshape = myshape;
	}

}
