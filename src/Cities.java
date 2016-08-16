import java.util.Random;

public class Cities {
  public int[][] ary;
  public int[][] dist;
  private int number_of_points;
  
  Cities(int num){
    number_of_points = num;
  }
  
  public void gen_random_cities(int points_range){
    dist = new int[number_of_points][number_of_points];
    Random rand = new Random();
 
    for(int i = 0; i < number_of_points; i++){
      for(int j = i; j < number_of_points; j++){
        if(Math.random() > 0.2 && i != j){
          int rand_num = rand.nextInt(points_range + 1);
          dist[i][j] = rand_num;
          dist[j][i] = rand_num;
        }
        else dist[i][j] = -1;
      }
    }
  }
  
  public void load_fixed_distances(){
    dist = new int[][]{{-1, 26, -1, -1,  4},
                       {26, -1,  3, 17, -1},
                       {-1,  3, -1,  9, 21},
                       {-1, 17,  9, -1,  7},
                       { 4, -1, 21,  7, -1}
                     };
  }
}
