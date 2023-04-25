import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.util.Random;

public class StartGame extends JFrame implements ActionListener {
	
	private final int easy = 1;
	private final int medium = 2;
	private final int hard = 3;	
	
	private final int bomb = -1;
	private final int redBomb = -999;
	private final int wrongFlag = -99;
	private final int blankSquare = 0;
	private final int square = 10;	
	private final int flag = 100;
	private final int one = 1;
	private final int two = 2;
	private final int three = 3;
	private final int four = 4;
	private final int five = 5;
	private final int six = 6;
	private final int seven = 7;
	private final int eight = 8;	
	
	private final int notChecked = 0;
	private final int checked = 1;
	
	private Button [][] gBoard;
	private int [][] lBoard;
	private int [][] checkBoard;
	
	private static final int[][] neighbours = {
		    {-1, -1}, {-1, 0}, {-1, +1},
		    { 0, -1},          { 0, +1},
		    {+1, -1}, {+1, 0}, {+1, +1}};
	
	int rows = 0;
    int columns = 0;
	int numOfBombs = 0;
	
	JMenuBar jmb = new JMenuBar();
	JMenuItem newGame = new JMenuItem("new game");

	public StartGame() {	
		menuDifficulty();
		initBoard();
		
		setTitle("mineSweeper");		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,450);
		setVisible(true);		
		
		setJMenuBar(jmb);
		jmb.add(newGame);
		newGame.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == newGame) {
			dispose();
			new StartGame();
			
		}
	}
	
	public void initBoard()
	{
		gBoard = new Button[rows][columns];
		lBoard = new int[rows][columns];		
		checkBoard = new int[rows][columns];
		
		setLayout(new GridLayout(rows, columns));
		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{				
				lBoard[i][j] = square;
				ImageIcon icon = new ImageIcon("square.png");
				Image img = icon.getImage();
				gBoard[i][j] = new Button(img);
				gBoard[i][j].addActionListener(new AL(i,j));
				gBoard[i][j].addMouseListener(new MyListener(i,j));
				add(gBoard[i][j]);	
				checkBoard[i][j] = notChecked;
			}	
		}	
		randomizeBoard();
	}
	
	public void randomizeBoard() {
		Random rnd = new Random();
		int numRows;
		int numColumns;
		
		for (int i = 0; i < numOfBombs; i++) {			
			do{
				numRows = rnd.nextInt(rows);
				numColumns = rnd.nextInt(columns);
			}
			while(lBoard[numRows][numColumns] == bomb); 			
			
			lBoard[numRows][numColumns] = bomb;	
		}
		
		setBoardNumbers();
	}
	
	public void setBoardNumbers() {
		for (int i = 0; i < rows; i++) {			
			for (int j = 0; j < columns; j++) {	
				if (lBoard[i][j] != bomb) {
					lBoard[i][j] = countNeighbours(i, j);
					//setCellImage(lBoard[i][j], i, j);
				}	
				else {
					//setCellImage(bomb, i, j);
				}
			}
		}
	}
	public int countNeighbours(int i , int j) {
	    int counter = 0;
	    for (int[] offset : neighbours) {
	    	int neighbour1 = i + offset[0];
	    	int neighbour2 = j + offset[1];
	    	if (neighbour1 >= 0 && neighbour2 >= 0 && neighbour1 < rows && neighbour2 < columns) {
	    		if (lBoard[neighbour1][neighbour2] == bomb) {
		        	counter++;
		        }
	    	}	        
	    }
	    return counter;
	 }		

	public void setCellImage(int type, int i, int j) {
		ImageIcon icon = null;
		switch (type) {
			case square:
				icon = new ImageIcon("square.png");
				break;
				
			case blankSquare:
				icon = new ImageIcon("blankSquare.png");
				break;
				
			case flag:
				icon = new ImageIcon("flag.jpg");
				break;
				
			case one:
				icon = new ImageIcon("1.png");
				break;
				
			case two:
				icon = new ImageIcon("2.png");
				break;
				
			case three:
				icon = new ImageIcon("3.png");
				break;
				
			case four:
				icon = new ImageIcon("4.png");
				break;
				
			case five:
				icon = new ImageIcon("5.png");
				break;
				
			case six:
				icon = new ImageIcon("6.png");
				break;
				
			case seven:
				icon = new ImageIcon("7.png");
				break;
				
			case eight:
				icon = new ImageIcon("8.png");
				break;		
				
			case bomb:
				icon = new ImageIcon("mine.jpg");
				break;
				
			case redBomb:
				icon = new ImageIcon("redBomb.jpg");
				break;
				
			case wrongFlag:
				icon = new ImageIcon("wrongFlag.jpg");
				break;
				
			default:
				break;
		}
		
		Image img = icon.getImage();
		gBoard[i][j].setImg(img);
	}
	
	class MyListener extends MouseAdapter
	{
		private int row;
		private int col;
		
		public MyListener(int row, int col) {			
			this.row = row;			
			this.col = col;			
			
		}
		
		public void mouseClicked (MouseEvent e) 
	    {       			
			ImageIcon iconSquare = new ImageIcon("square.png");
			ImageIcon iconFlag = new ImageIcon("flag.jpg");
			Image imgSquare = iconSquare.getImage();
			Image imgFlag = iconFlag.getImage();
			if (e.getModifiers() == MouseEvent.BUTTON3_MASK)
			{					
				if (gBoard[row][col].getImg().equals(imgSquare))
				{
					setCellImage(flag, row, col);									
					gBoard[row][col].repaint();
				}
				else
				{
					if (gBoard[row][col].getImg().equals(imgFlag)) {
						setCellImage(square, row, col);									
						gBoard[row][col].repaint();
					}
				}
			}
	    }
	}
	
	class AL implements ActionListener {
		private int row;
		private int col;
		
		public AL(int row, int col) {			
			this.row = row;			
			this.col = col;			
			
		}
		
		public void actionPerformed(ActionEvent e) {	
			ImageIcon iconFlag = new ImageIcon("flag.jpg");
			Image imgFlag = iconFlag.getImage();
			ImageIcon iconWrongFlag = new ImageIcon("wrongFlag.jpg");
			Image WrongFlag = iconWrongFlag.getImage();
			int choosenCell = lBoard[row][col];
			int num;			
			if (!gBoard[row][col].getImg().equals(imgFlag)) {
				if (choosenCell == bomb) {
					for (int i = 0; i < rows; i++) {			
						for (int j = 0; j < columns; j++) {	
							if (lBoard[i][j] == bomb && !gBoard[i][j].getImg().equals(imgFlag)) {
								setCellImage(redBomb, i, j);									
	   	    				    gBoard[i][j].repaint();	   	    				   
							}
							
							if (gBoard[i][j].getImg().equals(imgFlag) && lBoard[i][j] != bomb) {
								setCellImage(wrongFlag, i, j);									
	   	    				    gBoard[i][j].repaint();	  
							}
						}						
					}
					JOptionPane.showMessageDialog(StartGame.this,"game over");					
				}
				else {
					num = countNeighbours(row, col);
					if (num != blankSquare) {						
						setCellImage(num, row, col);									
	   				    gBoard[row][col].repaint();	 
	   				    
	   				    if (checkWinning()) {
	   				    	JOptionPane.showMessageDialog(StartGame.this,"you win");
		   				}
					}
					else
					{
						setCellImage(num, row, col);									
					    gBoard[row][col].repaint();
						untilEndOfBlankSqures(num, row,col);
					}
				}
			}		
		}
		
		public boolean checkWinning() {
			ImageIcon iconSquare = new ImageIcon("square.png");			
			Image imgSquare = iconSquare.getImage();
			
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					if (gBoard[i][j].getImg().equals(imgSquare) && lBoard[i][j] != bomb) {
						return false;
					}
				}
			}
			
			return true;
		}
		
		public void untilEndOfBlankSqures(int num, int i, int j) {
			if (checkBoard[i][j] == checked) {
				return;
			}
			
			if (num != blankSquare) {		
				setCellImage(num, i, j);									
    			gBoard[i][j].repaint();
				return;
			}
		
		    for (int[] offset : neighbours) {
			    int neighbour1 = i + offset[0];
			    int neighbour2 = j + offset[1];
			    if (neighbour1 >= 0 && neighbour2 >= 0 && neighbour1 < rows && neighbour2 < columns) {	
			    	if (checkBoard[neighbour1][neighbour2] == notChecked) {			    		 
			    		checkBoard[i][j] = checked;
			    		setCellImage(num, neighbour1, neighbour2);									
			    		gBoard[neighbour1][neighbour2].repaint();
			    		untilEndOfBlankSqures(lBoard[neighbour1][neighbour2], neighbour1, neighbour2);				    		
			    	}
			    }	  
			}		    
		}
	}
	
	
	public void menuDifficulty() {
		String[] options =  {"easy", "medium", "hard", "custom"};
		int response = JOptionPane.showOptionDialog(null, "Choose difficulty", 
				"Starting Game Options",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		switch(response)
		{		    
			case -1:
				System.out.println("Option Dialog Window Was Closed");
				System.exit(0);
			
			case 0: 
				rows = 9;
				columns = 9;
				numOfBombs = 10;
				break;
				
			case 1:
				rows = 16;
				columns = 16;
				numOfBombs = 40;
                break;
                
			case 2:
				rows = 16;
				columns = 30;
				numOfBombs = 99;
				break;
				
			case 3:
				menuCustom();
				break;
                
			default:
				break;				
		}
	}
	
	public void menuCustom() {
		boolean done;
		String s;
		do
		{
			try
			{
				s = JOptionPane.showInputDialog(null, "enter number of rows");
				rows = Integer.parseInt(s);				
				done = true;
			}
			catch(NumberFormatException ex)
			{
				done = false;
				JOptionPane.showInputDialog(null, "wrong input");
			}
		}while(!done || rows < 9);
		done = false;
		do
		{
			try
			{
				s = JOptionPane.showInputDialog(null, "enter number of columns");
				columns = Integer.parseInt(s);
				done = true;
			}
			catch(NumberFormatException ex)
			{
				done = false;
				JOptionPane.showInputDialog(null, "wrong input");
			}
		}while(!done || columns < 9);
		done = false;
		do
		{
			try
			{
				s = JOptionPane.showInputDialog(null, "enter number of bombs");
				numOfBombs = Integer.parseInt(s);
				done = true;
			}
			catch(NumberFormatException ex)
			{
				done = false;
				JOptionPane.showInputDialog(null, "wrong input");
			}
		}while(!done || numOfBombs < 9 ||(numOfBombs > rows * columns));
	}
	
	public static void main(String[] args) {	
		new StartGame();
	}
}
