import java.util.*;

public class Chromosome {
  public int size;
  public int[] genes;

  Chromosome(int n){
    size = n;
    genes = new int[n];
    for(int i = 0; i < size; i++) genes[i] = i;
    shuffleArray(genes);
  }
  
  Chromosome(int n, int[] _genes){
	size = n;
    genes = new int[n];
    for(int i = 0; i < size; i++) genes[i] = _genes[i];
  }
  
  //Evaluates the total sum of distances between the genes/points of the chromosome
  // @param dist 2D array of distance between all the points
  public double evaluate(int[][] dist){
    double sum = dist[genes[0]][genes[size - 1]];
    for(int i = 1; i < size; i++){
      sum += dist[genes[i]][genes[i - 1]];
    }
    return sum;
  }
  
  public Chromosome inversion_mutation(){
    Random rand = new Random();
    Chromosome clone = new Chromosome(this.size, this.genes);
    
    int i, j;
    // find two random indexes i and j such that i < j
    do {
      i = 0; j =0;
      int m = rand.nextInt(size);
      int n = rand.nextInt(size);
      
      //find m
      while(clone.genes[i] != m) i++;
      
      //find n
      while(clone.genes[j] != n) j++;
      
    } while(i >= j);
 
    //invert the subarray between i and j
    for(int k = 0; k <= (j - i)/2 ; k++) swap(clone.genes, i + k, j - k);
    
    return clone;
  }
  
  public Chromosome swap_mutation(){
    Random rand = new Random();
 
    int m = rand.nextInt(size), n = rand.nextInt(size);
    Chromosome clone = new Chromosome(this.size, this.genes);
    int i = 0, j = 0;
    //find m
    while(clone.genes[i] != m) i++;
    
    //find n
    while(clone.genes[j] != n) j++;
    
    //swap m and n
    swap(clone.genes, i, j);  
    return clone;
  }
 
  
  private void shuffleArray(int[] a) {
    int n = a.length;
    Random random = new Random();
    random.nextInt();
    for (int i = 0; i < n; i++) {
      int change = i + random.nextInt(n - i);
      swap(a, i, change);
    }
  }

  private static void swap(int[] a, int i, int change) {
    int helper = a[i];
    a[i] = a[change];
    a[change] = helper;
  }
}
