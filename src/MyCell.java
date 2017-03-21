
public class MyCell implements Cell{	
	
	private int row;
	private int col;
	private boolean visibility;
	private boolean ismine;
	private boolean isflag;
	private int neighbormines;
	
	MyCell(int myrow, int mycol, boolean mine, boolean flag){
		row = myrow;
		col = mycol;
		visibility = false;
		ismine = mine;
		isflag = flag;
		neighbormines = 0;
		
	}
	//This goes in the start game function? Where column and row are arguments
	/*
	
	int neighbormines = 0;
		for (int i = -1; i <= row + 1; i ++){
			for (int j = -1; j <= col + 1; j++){
				if (cellarray[i][j].isMine()){
					neighbormines++;
				}
			}
		}
	
	*/

	
	public int getRow(){
		return row;
	}
	  
	public int getCol(){
		return col;
	}
	  
	public boolean isVisible(){
		return visibility;
	}

	public boolean isMine(){
		return ismine;
	}
	  
	public boolean isFlagged(){
		return isflag;
	}
	  
	public int getNeighborMines(){
		return neighbormines;
	}

	public void setmineneighbors(int numberofneighbormines){
		neighbormines = numberofneighbormines;
	}
	
	public boolean toggleflag(){
		if(isflag){
			isflag = false;
		}
		else{
			isflag = true;
		}
		return isflag;
	}
	
	public void makevisible(){
		if(visibility == false){
			visibility = true;
		}
	}
	
}
