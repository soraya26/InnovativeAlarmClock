package com.iac.innovativealarmclock;

import java.util.ArrayList;
import java.util.List;

public class Maze {
	//private List<List<MazeNode>> Maze = new ArrayList<List<MazeNode>>();
	private MazeNode[][] maze;
	private int width; // width in blocks
	private int height; // height in blocks
	private MazeNode startNode;
	private MazeNode endNode;
	
	public Maze(int x, int y, MazeNode z, MazeNode w)
	{
		width = x;
		height = y;
		startNode = z;
		endNode = w;
		maze = new MazeNode[x][y];
		
		// Generate main array of stuff
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				MazeNode tempNode = new MazeNode(i, j);
				tempNode.setType("wall");
				maze[i][j] = tempNode;
			}
		}
		
		
		
		
	}
}