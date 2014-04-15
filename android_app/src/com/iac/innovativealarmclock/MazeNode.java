package com.iac.innovativealarmclock;

public class MazeNode {
	private int xCoord;
	private int yCoord;
	private MazeNode parent;
	private String blockType; // can be either "start", "end", "path" or "wall"
	
	public MazeNode(int x, int y)
	{
		xCoord = x;
		yCoord = y;
	}
	public void setParent(MazeNode m)
	{
		parent = m;
	}
	public void setType(String s)
	{
		blockType = s;
	}
	public int getX()
	{
		return xCoord;
	}
	public int getY()
	{
		return yCoord;
	}
	public MazeNode getParent()
	{
		return parent;
	}
	public String getBlockType()
	{
		return blockType;
	}
}