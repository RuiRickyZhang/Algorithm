import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class Percolation {

    private WeightedQuickUnionUF model;
    private boolean[] opened;               // denote the state of open
    private int sidelen;                    // side length (n) of the object
    private boolean[] connectTop;           // denote the connection to top
    private boolean[] connectBottom;        // denote the connection to bottom
    private boolean percolated;             // denote percolation
    private int numberOfOpen;

    private boolean legalsite(int row, int column) {   // is the site legal?
        return (row >= 1) && (row <= sidelen) && (column >= 1) && (column <= sidelen);
    }

    public Percolation(int n){// create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();//Deal with the invalid input of n
        numberOfOpen = 0;
        model = new WeightedQuickUnionUF(n*n);
        opened = new boolean[n*n];
        sidelen = n;
        connectTop = new boolean[n*n]; // To test if this block is connected to the virtual top?
        connectBottom = new boolean[n*n]; // To test if the blick is connected to the virtual bottom?
        percolated = false;

        for (int id = 0; id < n*n; id++) {
            opened[id] = false;
            connectTop[id] = false;
            connectBottom[id] = false;
        } // Set all of them to false as indicated they are all closed at first
        for(int i = 0; i < n; i++){
            connectTop[i] = true; // The first line are all connected to top
        }
        for(int j = n*n - n; j < n*n; j++){
            connectBottom[j] = true;
        }
    }

    public void open(int row, int col){
        if(!legalsite(row, col))
            throw new java.lang.IllegalArgumentException();

        // open site (row, col) if it is not open already
        int i = row - 1;
        int j = col - 1;
        //if(opened[i*n+j] != true)  opened[i*n+j] = true;

        int siteid = i*sidelen+j;
        boolean toTop = connectTop[model.find(siteid)];
        boolean toBottom = connectBottom[model.find(siteid)];

        if(!opened[siteid]){
            opened[siteid] = true;
            numberOfOpen++;

            if(i >= 1){
                int up = (i-1)*sidelen + j;
                if(opened[up]){
                    if(connectTop[model.find(up)])
                        toTop = true;
                    if(connectBottom[model.find(up)])
                        toBottom = true;
                    model.union(up, siteid);
                }
            }

            if(i <= sidelen - 2){
                int down = (i+1)*sidelen + j;
                if(opened[down]){
                    if(connectTop[model.find(down)])
                        toTop = true;
                    if(connectBottom[model.find(down)])
                        toBottom = true;
                    model.union(down, siteid);
                }
            }

            if(j >= 1){
                int left = i*sidelen + j - 1;
                if(opened[left]){
                    if(connectTop[model.find(left)])
                        toTop = true;
                    if(connectBottom[model.find(left)])
                        toBottom = true;
                    model.union(left, siteid);
                }
            }

            if(j <= sidelen - 2){
                int right = i*sidelen +j+1;
                if(opened[right]){
                    if(connectTop[model.find(right)])
                        toTop = true;
                    if(connectBottom[model.find(right)])
                        toBottom = true;
                    model.union(right, siteid);
                }

            }
        }

        connectTop[model.find(siteid)] = toTop;
        connectTop[siteid] = toTop;
        connectBottom[model.find(siteid)] = toBottom;
        connectBottom[siteid] = toBottom;

        percolated |= toTop && toBottom;

    }

    public boolean isOpen(int row, int col){
        if(!legalsite(row, col))
            throw new java.lang.IllegalArgumentException();
        // is site (row, col) open?
        int i = row - 1;
        int j = col - 1;
        return opened[i*sidelen+j];
    }

    public boolean isFull(int row, int col){
        // is site (row, col) full?
        if (!legalsite(row, col))
            throw new java.lang.IndexOutOfBoundsException();

        int i = row - 1;
        int j = col - 1;
        return opened[i*sidelen+j] && connectTop[model.find(i*sidelen+j)];
    }

    public int numberOfOpenSites(){
        // number of open sites
        return numberOfOpen;
    }

    public boolean percolates(){
        // does the system percolate?
        return percolated;

    }

    public static void main(String[] args){
        // test client (optional)
        Percolation model = new Percolation(3);
        model.open(1,3);
        System.out.println(model.numberOfOpenSites());
        System.out.println(model.percolates());
        model.open(2,3);
        System.out.println(model.numberOfOpenSites());
        System.out.println(model.percolates());
        model.open(3,3);
        System.out.println(model.numberOfOpenSites());
        System.out.println(model.percolates());
        model.open(3,1);
        System.out.println(model.numberOfOpenSites());
        System.out.println(model.percolates());
        System.out.println(model.isFull(3,1));
        System.out.println(model.numberOfOpenSites());
        System.out.println(model.percolates());
        model.open(2,1);
        System.out.println(model.numberOfOpenSites());
        System.out.println(model.percolates());
        model.open(1,1);
        System.out.println(model.numberOfOpenSites());
        System.out.println(model.percolates());
    }
}
