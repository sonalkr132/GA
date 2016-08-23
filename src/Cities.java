import java.util.Random;

public class Cities {
  public int[][] ary;
  public int[][] map;
  private int number_of_points;
  
  Cities(int num){
    number_of_points = num;
  }
  
  
  // generates a random 2d matrix for map
  public void gen_random_cities(int points_range){
    map = new int[number_of_points][number_of_points];
    Random rand = new Random();
 
    for(int i = 0; i < number_of_points; i++){
      for(int j = i; j < number_of_points; j++){
        if(Math.random() > 0.2 && i != j){
          int rand_num = 1 + rand.nextInt(points_range + 1);
          map[i][j] = rand_num;
          map[j][i] = rand_num;
        }
        else map[i][j] = -1;
      }
    }
  }
  
  // loads the fixed map of cities
  public void load_fixed_distances(){
    map = new int[][]{ {-1, 26, -1, -1,  4},
                       {26, -1,  3, 17, -1},
                       {-1,  3, -1,  9, 21},
                       {-1, 17,  9, -1,  7},
                       { 4, -1, 21,  7, -1}
                     };
  }
}
