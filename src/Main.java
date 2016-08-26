public class Main {
  public static void main(String[] args) {
    int NUMBER_OF_POINTS = 15;
    int POPULATION_SIZE = 30;
    Population p = new Population(NUMBER_OF_POINTS, POPULATION_SIZE); //number of points, population size
    //p.fixed_point_cities();
    p.random_point_cities(20);
    
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
    
    for(int i = 0; i < 200; i++){
      p.next_generation();
         
      if(i % 10 == 0){
        System.out.println("best score: " + p.best_score);
        System.out.println("best chromosome:");
        for(int j = 0; j < NUMBER_OF_POINTS; j++) System.out.print(p.alltime_best_chromosome.genes[j] + " ");
        System.out.println("\n");
      }
    }

    // See final population
    for(int i = 0; i < POPULATION_SIZE; i++){
      for(int j = 0; j < NUMBER_OF_POINTS; j++){
        Chromosome ch = p.chromosomes[i];
        System.out.print(ch.genes[j] + " ");
      }
      System.out.println();
    }
  }
}
