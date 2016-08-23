import java.util.*;

public class Chromosome {
  public int size;
  public int[] genes;
  private int[][] map;

  Chromosome(int n, int[][] _map){
    size = n;
    genes = new int[n];
    map = _map;
    genes = find_random_tour(-1, 0);
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
  
  private int[] find_random_tour(int start, int pos){
    int x = pos;
    Random rand = new Random();
    ArrayList<Integer> res = new ArrayList<Integer>();
    ArrayList<Integer> available_nodes = new ArrayList<Integer>();
    int cur_node;
    int blacklisted_node = -1;
    
    //set the initial node to a random city if start node is not provided
    if(start == -1) cur_node = rand.nextInt(size);
    else cur_node = start;
    available_nodes = find_connected_cities(res, cur_node, blacklisted_node);
    x += 1;
   
    int ctr = 0, printed = 0;
    while(x <= size){
 
      // while there are no available neighbors to go to, keep
      // picking
      if( ctr > 100 && printed < 10){
    	 System.out.println(x + " " + cur_node);
    	 for(int i = 0; i < res.size(); i++) System.out.print(res.get(i) + " ");
    	 System.out.println();
    	 for(int i = 0; i < available_nodes.size(); i++) System.out.print(available_nodes.get(i) + " ");
    	 System.out.println();
    	 printed+=1;
      }
      else ctr+=1;
      while(available_nodes.isEmpty()){
        res.remove(new Integer(cur_node));
        blacklisted_node = cur_node;
        x -= 1;

        //find the last node and its available nodes
        cur_node = res.get(res.size() - 1);
        available_nodes = find_connected_cities(res, cur_node, blacklisted_node);
      }
 
      int m_idx = rand.nextInt(available_nodes.size());
      cur_node = available_nodes.get(m_idx);
      res.add(cur_node);
      x += 1;
      blacklisted_node = -1;
      
      available_nodes = find_connected_cities(res, cur_node, blacklisted_node);
    }
    
    
    // conversion of arraylist to array
    int[] array = new int[res.size()];
    for(int i = 0; i < res.size(); i++) array[i] = res.get(i);

    return array;
  }
  
  private ArrayList<Integer> find_connected_cities(ArrayList<Integer> res, int idx, int blacklisted_node){
    ArrayList<Integer> available_nodes = new ArrayList<Integer>();
    for(int i = 0; i < size; i++)
      if(map[idx][i] != -1 && !res.contains(i) && i != blacklisted_node) available_nodes.add(i);
    
    return available_nodes;
  }
}
