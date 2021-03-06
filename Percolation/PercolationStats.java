import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] thresholds;
    private int trialnum;

    public PercolationStats(int n, int trials){
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        trialnum = trials;
        thresholds = new double[trials];
        int row, column;
        double threshold;
        for(int i = 0; i < trialnum; i++){
            Percolation trialcase = new Percolation(n);
            threshold = 0;
            while(!trialcase.percolates()){
                //When it doesn't perlocate
                do {
                    row = StdRandom.uniform(1, n+1);
                    column = StdRandom.uniform(1, n+1);
                } while(trialcase.isOpen(row, column));
                trialcase.open(row, column);
                threshold += 1;
            }
            thresholds[i] = threshold/(n*n);
        }
    }

    public double mean(){
        // sample mean of percolation threshold
        return StdStats.mean(thresholds);
    }
    public double stddev(){
        // sample standard deviation of percolation threshold
        return StdStats.stddev(thresholds);
    }
    public double confidenceLo(){
        // low  endpoint of 95% confidence interval
        return mean()-1.96*stddev()/Math.sqrt(trialnum);
    }
    public double confidenceHi(){
        // high endpoint of 95% confidence interval
        return mean()+1.96*stddev()/Math.sqrt(trialnum);
    }

    public static void main(String[] args){
        int testn = 200;
        int testtrials = 1000;
        PercolationStats model = new PercolationStats(testn, testtrials);
        System.out.println("mean                    = "+model.mean());
        System.out.println("stddev                  = "+model.stddev());
        System.out.println("95% confidence interval = "+
                model.confidenceLo()+", "+model.confidenceHi());
    }        // test client (described below)
}
