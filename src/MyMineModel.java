import java.util.*;

public class MyMineModel implements MineModel{
	private int numberRows;
	private int numberCols;
	private int numberMines;
	private long time;
	private int numberflags;
	private boolean GameStarted;
	private boolean GameEnded;
	private Cell[][] grid;

	Random randgen = new Random();
	
	public void newGame(int numRows, int numCols, int numMines){
		time = System.currentTimeMillis();
		
		numberflags = 0;
		numberRows = numRows;
		numberCols = numCols;
		numberMines = numMines;
		GameStarted = true;
		GameEnded = false;
		
		int[][] minesarray = new int[numMines][2];
		for (int i = 0; i < numMines; i++){
			int randomrow = randgen.nextInt(numRows);
			int randomcol = randgen.nextInt(numCols);
			boolean ok = true;
			for (int[] mine: minesarray){
				if (mine[0] == randomrow && mine[1] == randomcol){
					ok = false;
				}
			}
			if (ok) {
				minesarray[i][0] = randomrow;
				minesarray[i][1] = randomcol;
			} else i--;
		}
		
		grid = new Cell[numRows][numCols];
		
		for (int row = 0; row < numRows; row++){
			for(int col = 0; col < numCols; col++){
				boolean ismine = false;
				for (int i = 0; i < numMines; i++){
					if (minesarray[i][0] == row && minesarray[i][1] == col){
						ismine = true;
					}
				}
				boolean isflag = false;
				//flag is always false at the beginning
				grid[row][col] = new MyCell(row, col, ismine, isflag);				
			}
		}
		int neighbormines;
		for (int row = 0; row < numRows; row++){
			for(int col = 0; col < numCols; col++){
				neighbormines = 0;
				for (int i = -1; i <= 1; i++){
					for (int j = -1; j <= 1; j++){
						if ((row + i) < numRows && (row + i) >= 0 && (col + j) < numCols && (col + j) >= 0){
							if (grid[row + i][col + j].isMine()){
								neighbormines ++;
							}
						}
					}
				}
				((MyCell) grid[row][col]).setmineneighbors(neighbormines);
			}
		}
	}

	 public int getNumRows(){
		 return numberRows;
	 }
	  
	 public int getNumCols(){
		 return numberCols;
	 }
	 
	 public int getNumMines(){
		 return numberMines;
	 }
	  
	 public int getNumFlags(){
		 return numberflags;
	 }
	  
	 public int getElapsedSeconds(){
		 int elapsedtime;
		 elapsedtime = (int) System.currentTimeMillis() - (int) time;
		 return (int) elapsedtime/1000;
	 }
	  
	 public Cell getCell(int row, int col){
		 return grid[row][col];
	 }
	  
	 public void stepOnCell(int row, int col){
		 MyCell currentcell = (MyCell)getCell(row, col);
		 boolean minestatus = currentcell.isMine();
		 if (minestatus){
			 GameEnded = true;
		 }
		 else{
			 currentcell.makevisible();
			 if (currentcell.getNeighborMines() == 0){
				 for (int i = -1; i <= 1; i++){
					 for (int j = -1; j <= 1; j++){
						 int newrow = row + i;
						 int newcol = col + j;
						 if ((row + i) < numberRows && (row + i) >= 0 && (col + j) < numberCols && (col + j) >= 0){
							 MyCell newcell = (MyCell)getCell(newrow, newcol);
							 if (newcell.isVisible() == false){
								 newcell.makevisible(); 
								 if (newcell.getNeighborMines() == 0){
									 stepOnCell(newrow, newcol);
								 }
							 }
						 }
					 }
				 }
			 }
		 }
	 }
	  
	 public void placeOrRemoveFlagOnCell(int row, int col){
		 MyCell currentcell = (MyCell)getCell(row, col);
		 if (currentcell.isVisible() == false){
			 boolean stateOfFlag = currentcell.toggleflag();
			 if (stateOfFlag){
				 numberflags ++;
			 }
			 else{
				 numberflags --;
			 }
		 }
	 }
	 public boolean isGameStarted(){
		 return GameStarted;
	 }
	  
	 public boolean isGameOver(){
		 if (isPlayerDead() || isGameWon()){
			 return true;
		 }
		 else{
			 return false;
		 }
	 }
	  
	 public boolean isPlayerDead(){
		 if (GameEnded == true){
			 for (int i = 0; i < numberRows; i++){
				 for (int j = 0; j < numberCols; j++){
					 MyCell currentcell = (MyCell)getCell(i, j);
					 if (currentcell.isMine()){
						 currentcell.makevisible(); 
					 }
				 }
			 }
		 }
		return GameEnded;
	 }
	  
	 public boolean isGameWon(){
		 for (int i = 0; i < numberRows; i++){
			 for (int j = 0; j < numberCols; j++){
				 MyCell currentcell = (MyCell)getCell(i, j);
				 if (currentcell.isFlagged()){
					 if (currentcell.isMine() != currentcell.isFlagged()){
						 return false;
					 }
				 }
				 else if (currentcell.isVisible() == false){
					 return false;
				 }
			 }
		 }
		 for (int i = 0; i < numberRows; i++){
			 for (int j = 0; j < numberCols; j++){
				 MyCell currentcell = (MyCell)getCell(i, j);
				 if (currentcell.isMine()){
					 currentcell.makevisible(); 
					 currentcell.toggleflag();
				 }
			 }
		 }
	 return true;
	 }	
}