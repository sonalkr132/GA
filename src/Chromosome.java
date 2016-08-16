import java.util.*;

public class Chromosome {
  public int size;
  public int[] genes;
  private int[][] map;

  Chromosome(int n, int[][] _map){
    size = n;
    genes = new int[n];
    map = _map;
    genes = find_random_tour();
  }
  
  Chromosome(int n, int[][] _map, int[] _genes){
    size = n;
    genes = new int[n];
    map = _map;
    for(int i = 0; i < size; i++) genes[i] = _genes[i];
  }
  
  //Evaluates the total sum of distances between the genes/points of the chromosome
  // @param dist 2D array of distance between all the points
  public double evaluate(){
    double sum = 0.0;
    for(int i = 1; i < size; i++){
      sum += map[genes[i]][genes[i - 1]];
      //System.out.println(map[genes[i]][genes[i - 1]]);
    }
    return sum;
  }
 
  public Chromosome mutate(){
    Chromosome clone = new Chromosome(this.size, this.map, this.genes);
    return clone;
  } 
  
  private int[] find_random_tour(){
    int x = 1;
    Random rand = new Random();
    ArrayList<Integer> res = new ArrayList<Integer>();
    ArrayList<Integer> available_nodes = new ArrayList<Integer>();
    int cur_node = -1;
    
    while(x != size){
      
      // while there are no available neighbors to go to, keep
      // picking 
      while(available_nodes.isEmpty()){
        if(cur_node != -1) res.remove(new Integer(cur_node));

        cur_node = rand.nextInt(size);
        res.add(cur_node);
        available_nodes = find_connected_cities(res, cur_node);
      }
 
      int m_idx = rand.nextInt(available_nodes.size());
      cur_node = available_nodes.get(m_idx);
      res.add(cur_node);
      x += 1;
      
      available_nodes = find_connected_cities(res, cur_node);
    }
    
    
    // conversion of arraylist to array
    int[] array = new int[res.size()];
    for(int i = 0; i < res.size(); i++) array[i] = res.get(i);

    return array;
  }
  
  private ArrayList<Integer> find_connected_cities(ArrayList<Integer> res, int idx){
    ArrayList<Integer> available_nodes = new ArrayList<Integer>();
    for(int i = 0; i < size; i++)
      if(map[idx][i] != -1 && !res.contains(i)) available_nodes.add(i);
    
    return available_nodes;
  }
}
