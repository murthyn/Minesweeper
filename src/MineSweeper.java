import java.io.IOException;

class MineSweeper
{
  public static void main(String[] args) throws IOException 
  {
    MineModel mineModel = new MyMineModel();
    MineView mineView = new MineView(mineModel, 600, 400);
  }
}
