import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
  public Chromosome[] chromosomes;
  public int population_size, cities_size;
  private double crossover_prob, mutation_prob;
  public double[] roulette;
  public double[] scores;
  public Cities cities;
  public Chromosome current_best_chromosome, alltime_best_chromosome;
  public double best_score;
  
  Population(int _points_size, int _population_size){
    population_size = _population_size;
    crossover_prob = 0.9;
    mutation_prob = 0.01;
    cities_size = _points_size;

    scores = new double[population_size];
    chromosomes = new Chromosome[population_size];
  }
  
  // loads 5x5 matrix of cities' map
  public void fixed_point_cities(){
    cities = new Cities(cities_size);
    cities.load_fixed_distances();
  }
  
  // Generates @points_size number of random cities in given range
  public void random_point_cities(int points_range){
    cities = new Cities(cities_size);
    cities.gen_random_cities(points_range);
  }
  
  // Loads cities data from tsplib file
  public void tsplib_cities(String filename, String type){
    cities = new Cities(cities_size);
    if(type == "dist") cities.load_tsplib(filename);
    else if(type == "points") cities.load_tsplib_points(filename);
  }
  
  // Intializes chromosomes with random chromosomes
  // also set the best scores array, best_score and best chromosome for current generation
  public void initialize_population(){
    for(int i = 0; i < population_size; i++){
      Chromosome chromosome = new Chromosome(cities_size);
      chromosomes[i] = chromosome;
    }
  
    set_best_score();
  }
  
  public void next_generation(){
    selection();
    crossover();
    mutation();
    set_best_score();
  }
  
  
  // selection has elitism of 4.
  // It uses roulette wheel selection
  public void selection(){
    Chromosome[] parents = new Chromosome[population_size];
    parents[0] = current_best_chromosome;
    parents[1] = alltime_best_chromosome;
    parents[2] = alltime_best_chromosome.inversion_mutation();
    parents[3] = alltime_best_chromosome.swap_mutation();

    set_roulette();

    Random prang = new Random();
    for(int i = 4; i < population_size; i++){
      parents[i] = chromosomes[spin_wheel(prang.nextInt())];
    }

    chromosomes = parents;
  }
  
  // Order 1 crossover
  public void crossover(){
    ArrayList<Integer> queue = new ArrayList<Integer>();
    
    for(int i=0; i< population_size; i++) {
      if( Math.random() < crossover_prob ) {
        queue.add(i);
      }
    }

    Collections.shuffle(queue);
    for(int i = 0, j = queue.size() -1 ; i < j; i += 2) {
      do_crossover(queue.get(i), queue.get(i + 1));
    }
  }
  
  private void do_crossover(int x, int y){
    Chromosome parent1 = chromosomes[x];
    Chromosome parent2 = chromosomes[y];
    
    Random rand = new Random();
    int m, n;
    do {
      m = rand.nextInt(cities_size - 2);
      n = rand.nextInt(cities_size);
    } while(m >= n);
    
    int[] c1 = new int[cities_size], c2 = new int[cities_size];
    
    //check_child1 is the array list used for *contains*
    ArrayList<Integer> check_child1 = new ArrayList<Integer>();
    ArrayList<Integer> check_child2 = new ArrayList<Integer>();
    //copy elements [m..n] from _parent1_ to _child2_ and _parent2_ to _child1_
    for(int i = m; i <= n; i++){
      c2[i] = parent1.genes[i]; check_child2.add(c2[i]);
      c1[i] = parent2.genes[i]; check_child1.add(c1[i]);
    }
    
    //create iterators for child and parent genes
    int itr_c = (n + 1) % cities_size, itr_p = (n + 1) % cities_size;
    
    //copy elements from parent1 to child1 in order
    while(itr_c != m){
      //allie of parent1 not in child1
      if(!check_child1.contains(new Integer(parent1.genes[itr_p]))){
        c1[itr_c] = parent1.genes[itr_p];
        itr_c = (itr_c + 1) % cities_size;
      }
      itr_p = (itr_p + 1) % cities_size;
    }
    
    itr_c = (n + 1) % cities_size; itr_p = (n + 1) % cities_size;
    //copy elements from parent2 to child2 in order
    while(itr_c != m){
      if(!check_child2.contains(new Integer(parent2.genes[itr_p]))){
        c2[itr_c] = parent2.genes[itr_p];
        itr_c = (itr_c + 1) % cities_size;
      }
      itr_p = (itr_p + 1) % cities_size;
    }
    
    chromosomes[x] = new Chromosome(cities_size, c1);
    chromosomes[y] = new Chromosome(cities_size, c2);
  }
  
  public void mutation(){
    for(int i = 0; i < population_size; i++) {
      if(Math.random() < mutation_prob) {
        if(Math.random() > 0.5) {
          chromosomes[i] = chromosomes[i].swap_mutation();
        } else {
          chromosomes[i] = chromosomes[i].inversion_mutation();
        }
        i--;
      }
    }
  }

  // evaluates the best score of current population and changes
  // the alltime best chromosome and best score if required
  private void set_best_score(){
    for(int i = 0; i < population_size; i++){
      scores[i] = chromosomes[i].evaluate(cities.map);
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
  
  
  // creates the roulette wheel used in selection
  // Fitness of chromosome, F(x) = 1/score(x)
  // Assume you have 10 items to choose from and you choose by generating
  // a random number between 0 and 1. You divide the range 0 to 1 up into
  // ten non-overlapping segments, each proportional to the fitness of one
  // of the ten items. For example, this might look like this:
  //0 - 0.3 is item 1
  //0.3 - 0.4 is item 2
  //0.4 - 0.5 is item 3
  //0.5 - 0.57 is item 4...  
  private void set_roulette(){
    double sum = 0.0;
    double[] fitness = new double[population_size];
    for(int i = 0; i < population_size; i++){
      fitness[i] = 1.0 / scores[i];
      sum += fitness[i];
    }

    roulette = new double[population_size];
    for(int i = 0; i < population_size; i++) roulette[i] = (fitness[i]/sum)*100;
    for(int i = 1; i < population_size; i++) roulette[i] += roulette[i - 1];
  }

  private int spin_wheel(int random_number){
    int i;
    for(i = 0; i < population_size; i++){
      if(random_number < roulette[i]) break; 
    }
    return i;
  }
}
