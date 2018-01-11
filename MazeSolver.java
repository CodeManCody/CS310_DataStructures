/*  Cody Morgan
    cssc0928
 */

import data_structures.*;

public class MazeSolver {
    
    Stack<GridCell> stackList;
    Queue<GridCell> queueList;
    private int memberDimension;
    private MazeGrid grid;
    private int x0, x1, x2, x3;
    private int y0, y1, y2, y3;
    private GridCell currentCell0, currentCell1, currentCell2, currentCell3;
    private int distance;
    private GridCell currentCell, terminalCell;

    public MazeSolver(int dimension)
    {
        memberDimension = dimension;
        grid = new MazeGrid(this, dimension);
        stackList = new Stack<GridCell>();
        queueList = new Queue<GridCell>();
    }
    
    public void mark()
    {
        currentCell = grid.getCell(0,0);
        currentCell.setDistance(0);
        queueList.enqueue(currentCell);
        
        while(!queueList.isEmpty())
        {
            currentCell = queueList.dequeue();
            
            x0 = currentCell.getX() - 1;
            y0 = currentCell.getY();
            x1 = currentCell.getX() + 1;
            y1 = y0;
            x2 = currentCell.getX();
            y2 = currentCell.getY() + 1;
            x3 = x2;
            y3 = currentCell.getY() - 1;
                    
            currentCell0 = grid.getCell(x0, y0);
            currentCell1 = grid.getCell(x1, y1);
            currentCell2 = grid.getCell(x2, y2);
            currentCell3 = grid.getCell(x3, y3);
            
            if(grid.isValidMove(currentCell0))
                if(!currentCell0.wasVisited())
                {
                    currentCell0.setDistance(currentCell.getDistance() + 1);
                    grid.markDistance(currentCell0);
                    queueList.enqueue(currentCell0);
                }
                    
            if(grid.isValidMove(currentCell1))
                if(!currentCell1.wasVisited())
                {
                    currentCell1.setDistance(currentCell.getDistance() + 1);
                    grid.markDistance(currentCell1);
                    queueList.enqueue(currentCell1);
                }
                    
            if(grid.isValidMove(currentCell2))
                if(!currentCell2.wasVisited())
                {
                    currentCell2.setDistance(currentCell.getDistance() + 1);
                    grid.markDistance(currentCell2);
                    queueList.enqueue(currentCell2);
                }
                    
            if(grid.isValidMove(currentCell3))
                if(!currentCell3.wasVisited())
                {
                    currentCell3.setDistance(currentCell.getDistance() + 1);
                    grid.markDistance(currentCell3);
                    queueList.enqueue(currentCell3);
                }   
        }
    }
    
    public boolean move()
    {
        terminalCell = grid.getCell(memberDimension-1, memberDimension-1);
        distance = terminalCell.getDistance();
        
        if(distance == -1)
            return false;
        
        currentCell = terminalCell;
        stackList.push(currentCell);
        int min = distance;
        
        while(distance != 0)
        {
            x0 = currentCell.getX() - 1;
            y0 = currentCell.getY();
            x1 = currentCell.getX() + 1;
            y1 = y0;
            x2 = currentCell.getX();
            y2 = currentCell.getY() + 1;
            x3 = x2;
            y3 = currentCell.getY() - 1;
                    
            currentCell0 = grid.getCell(x0, y0);
            currentCell1 = grid.getCell(x1, y1);
            currentCell2 = grid.getCell(x2, y2);
            currentCell3 = grid.getCell(x3, y3);
            
            if(grid.isValidMove(currentCell0))
                if(currentCell0.getDistance() < min)
                {
                    min = currentCell0.getDistance();
                    currentCell = currentCell0;
                    stackList.push(currentCell);
                }
            
            if(grid.isValidMove(currentCell1))
                if(currentCell1.getDistance() < min)
                {
                    min = currentCell1.getDistance();
                    currentCell = currentCell1;
                    stackList.push(currentCell);
                }
            
            if(grid.isValidMove(currentCell2))
                if(currentCell2.getDistance() < min)
                {
                    min = currentCell2.getDistance();
                    currentCell = currentCell2;
                    stackList.push(currentCell);
                }
            
            if(grid.isValidMove(currentCell3))
                if(currentCell3.getDistance() < min)
                {
                    min = currentCell3.getDistance();
                    currentCell = currentCell3;
                    stackList.push(currentCell);
                }
            distance = currentCell.getDistance();
        }
        
        while(!stackList.isEmpty())
        {
            currentCell = stackList.pop();
            grid.markMove(currentCell);
        }
        return true;
    }
    
    public void reset()
    {
        stackList.makeEmpty();
        queueList.makeEmpty();
    }
    
    public static void main(String[] args) {
        new MazeSolver(30);
    }
}
