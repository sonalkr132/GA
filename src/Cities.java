import java.util.Random;

public class Cities {
  public int[][] ary;
  public int[][] map;
  private int number_of_points;
  private int MAX_DIST = 1000000;
  
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
        else{
          map[i][j] = MAX_DIST;
          map[j][i] = MAX_DIST;
        }
      }
    }
  }
  
  // loads the fixed map of cities
  public void load_fixed_distances(){
    map = new int[][]{ {MAX_DIST,       26, MAX_DIST, MAX_DIST,       4},
                       {      26, MAX_DIST,        3,       17, MAX_DIST},
                       {MAX_DIST,        3, MAX_DIST,        9,       21},
                       {MAX_DIST,       17,        9, MAX_DIST,        7},
                       {       4, MAX_DIST,       21,        7, MAX_DIST}
                     };
  }
}
