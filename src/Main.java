
public class Main {
  public static void main(String[] args) {
    int NUMBER_OF_POINTS = 20;
    int POPULATION_SIZE = 30;
    Population p = new Population(NUMBER_OF_POINTS, POPULATION_SIZE); //number of points, population size
    p.fixed_point_cities();
    
// Remove comment if you want to see the map of the cities
//    Cities c = p.cities;
//    for(int i = 0; i < NUMBER_OF_POINTS; i++){
//      for(int j = 0; j < NUMBER_OF_POINTS; j++){
//          System.out.print(c.dist[i][j] + " ");
//      }
//      System.out.println();
//    }
    p.initialize_population();

// See initial population
    for(int i = 0; i < POPULATION_SIZE; i++){
      for(int j = 0; j < NUMBER_OF_POINTS; j++){
        Chromosome c = p.chromosomes[i];
        System.out.print(c.genes[j] + " ");
      }
      System.out.println();
    }

  }
}
