
public class Population {
  public Chromosome[] chromosomes;
  public int population_size, points_size;
  private double crossover_prob, mutation_prob;
  public double[] scores;
  public Cities cities;
  public Chromosome current_best_chromosome, alltime_best_chromosome;
  public double best_score;
  
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
    cities.load_fixed_distances();
  }
  
  // Generates @points_size number of random cities in given range
  //   @params points_range maximum number up to which random numbers will be gen
  public void random_point_cities(int points_range){
    cities = new Cities(points_size);
    cities.gen_random_cities(points_range);
  }
  
  // Intializes chromosomes with random chromosomes
  // also set the best scores array, best_score and best chromosome for current gen
  public void initialize_population(){
    for(int i = 0; i < population_size; i++){
      Chromosome chromosome = new Chromosome(points_size, cities.dist);
      chromosomes[i] = chromosome;
    }
  
    set_best_score();
  }
  
  private void set_best_score(){
    for(int i = 0; i < population_size; i++){
      scores[i] = chromosomes[i].evaluate(cities.dist);
    }

    double current_best = scores[0];
    int current_best_idx = 0;
    for(int i = 0; i < population_size; i++){
      if(scores[i] < current_best){
        current_best_idx = i;
        current_best = scores[i];
      }
    }
    
    current_best_chromosome = chromosomes[current_best_idx];

    if (alltime_best_chromosome == null || best_score > current_best){
      alltime_best_chromosome = chromosomes[current_best_idx];
      best_score = current_best;
    }
  }
}
