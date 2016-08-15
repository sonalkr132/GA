
public class Population {
  public Chromosome[] chromosomes;
  public int population_size, points_size;
  private double crossover_prob, mutation_prob;
  public double[] scores;
  public Cities cities;
  
  Population(int _points_size, int _population_size){
    population_size = _population_size;
    crossover_prob = 0.9;
    mutation_prob = 0.01;
    points_size = _points_size;

    scores = new double[population_size];
    chromosomes = new Chromosome[population_size];
  }
  
  public void fixed_point_cities(){
    cities = new Cities(points_size);
    cities.load_fixed_points();
    cities.calc_distances();
  }
  
  //Intializes chromosomes with random chromosomes
  // also set the best scores array, best_score and best chromosome for current gen
  public void initialize_population(){
    for(int i = 0; i < population_size; i++){
      Chromosome chromosome = new Chromosome(points_size, cities.dist);
      chromosomes[i] = chromosome;
    }
  
    //set_best_score();
  }
}
