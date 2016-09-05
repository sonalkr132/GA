public class Main {
  public static void main(String[] args) {
    int NUMBER_OF_POINTS = 70;
    int POPULATION_SIZE = 40;
    Population p = new Population(NUMBER_OF_POINTS, POPULATION_SIZE); //number of points, population size
    //p.fixed_point_cities();
    //p.random_point_cities(20);
    p.tsplib_cities("/home/addie/current/tsplib/st70.tsp", "points");
    
    // See the map of the cities
    Cities c = p.cities;
    for(int i = 0; i < NUMBER_OF_POINTS; i++){
      for(int j = 0; j < NUMBER_OF_POINTS; j++){
          System.out.print(c.map[i][j] + "     ");
      }
      System.out.println();
    }
    p.initialize_population();

    // See initial population
    for(int i = 0; i < POPULATION_SIZE; i++){
      for(int j = 0; j < NUMBER_OF_POINTS; j++){
        Chromosome ch = p.chromosomes[i];
        System.out.print(ch.genes[j] + " ");
      }
      System.out.println();
    }
    
    System.out.println();
    
    System.out.println("Initial Score:");
    for(int i = 0; i < POPULATION_SIZE; i++){
      System.out.println(p.scores[i]);
    }
    
    System.out.println();
    System.out.println("Initial best score: " + p.best_score + "\n");
    
    int num_of_itr = 80000;
    for(int i = 0; i < num_of_itr; i++){
      p.next_generation();
         
      if(i % 1000 == 0){
        System.out.println("best score: " + p.best_score);
        System.out.println("best chromosome:");
        for(int j = 0; j < NUMBER_OF_POINTS; j++) System.out.print(p.alltime_best_chromosome.genes[j] + " ");
        System.out.println("\n");
      }
      
      if(i + 1 == num_of_itr){
        // See final population
        for(int k = 0; k < POPULATION_SIZE; k++){
          for(int j = 0; j < NUMBER_OF_POINTS; j++){
            Chromosome ch = p.chromosomes[k];
            System.out.print(ch.genes[j] + " ");
          }
          System.out.println();
        }
      }
    }
  }
}
