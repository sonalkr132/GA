import java.util.Random;

public class Cities {
  public int[][] ary;
  public double[][] dist;
  private int number_of_points;
  
  Cities(int num){
    number_of_points = num;
  }
  
  public void gen_random_points(int points_range){
    ary = new int[number_of_points][2];
    Random rand = new Random();
  
    for(int i = 0; i < number_of_points; i++){
      ary[i][0] = rand.nextInt(points_range + 1);
      ary[i][1] = rand.nextInt(points_range + 1);
    }
  }
  
  public void load_fixed_points(){
    ary = new int[][]{ {16, 26}, {5, 4}, {19, 30}, {27, 1}, {1, 1}, {0, 30}, {26, 25},
                       {14, 29}, {19, 1}, {8, 18}, {18, 6}, {19, 22}, {17, 3}, {10, 25},
                       {25, 9}, {23, 30}, {2, 8}, {22, 3}, {13, 19}, {6, 18}
                      };
  }
  
  public void calc_distances(){
    dist = new double[number_of_points][number_of_points];
    for(int i = 0; i < number_of_points; i++){
      for(int j = 0; j < number_of_points; j++){
        if(Math.random() > 0.5 && i != j) dist[i][j] = distance_between(ary[i], ary[j]);
        else dist[i][j] = -1;
      }
    }
  }
  
  
  private double distance_between(int[] point_i, int[] point_j){
    int dx = point_i[0] - point_j[0];
    int dy = point_i[1] - point_j[1];
    return Math.sqrt(dx * dx + dy * dy);
  }
}
