package model;

import controller.DrawController;
/**
 * 记录连接点信息
 */
public class ConnectionInfo {
	/*
	 * 线，图形连接点位置，图形管理者
	 */
	private MyLine line;
	private int location;//连四个点中的第几个
	private String connectionPart;//end或start，线哪端来连接
	private DrawController drawController;

	public DrawController getDrawController() {
		return drawController;
	}
	public void setDrawController(DrawController drawController) {
		this.drawController = drawController;
	}
	public ConnectionInfo(String text,DrawController drawController) {
			String []temp = text.split("_");
			int id = Integer.valueOf(temp[0]);
			int pos = Integer.valueOf(temp[1]);
			String part = temp[2];
			this.line = drawController.getMyLine(id);
			this.location = pos;
			this.connectionPart = part;
			//System.out.printf("%d,%d,%s。",id,pos,part);
	}
	public ConnectionInfo(MyLine line,int location,String part,DrawController drawController) {
		this.drawController = drawController;
		this.line=line;
		this.location=location;
		this.connectionPart=part;
	}
	public MyLine getLine() {
		return line;
	}
	public void setLine(MyLine line) {
		this.line = line;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public String getConnectionPart() {
		return connectionPart;
	}
	public void setConnectionPart(String connectionPart) {
		connectionPart = connectionPart;
	}
	public String toString(){
		return String.valueOf(line.getFactoryId())+"_"+location+"_"+connectionPart;
	}
}
