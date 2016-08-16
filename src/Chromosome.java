import java.util.*;

public class Chromosome {
  public int size;
  public int[] genes;

  Chromosome(int n, int[][] dist){
    size = n;
    genes = new int[n];
    genes = find_random_tour(dist);
  }
  
 //Evaluates the total sum of distances between the genes/points of the chromosome
 // @param dist 2D array of distance between all the points
 public double evaluate(int[][] dist){
   double sum = 0.0;
   for(int i = 1; i < size; i++){
     sum += dist[genes[i]][genes[i - 1]];
     //System.out.println(dist[genes[i]][genes[i - 1]]);
   }
   return sum;
 }
  
  private int[] find_random_tour(int[][] dist){
    int x = 1;
    Random rand = new Random();
    ArrayList<Integer> res = new ArrayList<Integer>();
    ArrayList<Integer> available_nodes = new ArrayList<Integer>();
    int cur_node = -1;
    
    while(x != size){
      while(available_nodes.isEmpty()){
        if(cur_node != -1) res.remove(new Integer(cur_node));

        cur_node = rand.nextInt(size);
        res.add(cur_node);
        available_nodes = find_connected_cities(res, dist, cur_node);
      }
 
      int m_idx = rand.nextInt(available_nodes.size());
      cur_node = available_nodes.get(m_idx);
      res.add(cur_node);
      x += 1;
      
      available_nodes = find_connected_cities(res, dist, cur_node);
    }
    
    int[] array = new int[res.size()];
    for(int i = 0; i < res.size(); i++) array[i] = res.get(i);

    return array;
  }
  
  private ArrayList<Integer> find_connected_cities(ArrayList<Integer> res, int[][] dist, int idx){
    ArrayList<Integer> available_nodes = new ArrayList<Integer>();
    for(int i = 0; i < size; i++)
      if(dist[idx][i] != -1 && !res.contains(i)) available_nodes.add(i);
    
    return available_nodes;
  }
}
