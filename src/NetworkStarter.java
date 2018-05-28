import java.util.ArrayList;

public class NetworkStarter {
    public static void main (String[] args){

        Network Net = new Network();
        int[] l ={2,4,1};
        Net.initializeNetwork(l);
        double[] input = new double[l[0]];
        for (int i=0;i<input.length;i++){
            input[i]=(Math.random());
        }
        double[] test = new double[2];
        test [0] = 1;test [1] = 0;
        double[][] sample = new double[4][];
        double[][] sampleAnsws = {{0},{1},{1},{0}};
        double[] sample1 = {0,0};
        sample[0] = sample1;
        double[] sample2 = {0,1};
        sample[1] = sample2;
        double[] sample3 = {1,0};
        sample[2] = sample3;
        double[] sample4 = {1,1};
        sample[3] = sample4;
        double[] testanswer1 = {0};
        double[] testanswer2 = new double[1];
        testanswer2[0]=1;
        double[] testanswer3 = {1};
        double[] testanswer4 = {0};
        Net.showNetworkData();
        Net.RunBPA(sample,sampleAnsws,0.001);
        Net.showNetworkData();

    }

}