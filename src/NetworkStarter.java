public class NetworkStarter {

    public static final double NETWORK_LEARN_ACCURACY = 0.001;

    public static void main (String[] args){

        int inputsCount  = 2;
        int hiddenLayerCount = 3;
        int outputsCount = 2;
        
        int[] neuronsInLayers = {
                inputsCount,
                hiddenLayerCount,
                outputsCount
        };

        Network Net = new Network();
        Net.initializeNetwork(neuronsInLayers);

        Net.showNetworkData();
        Net.runBPA(getSamples(), getSampleAnswers(), NETWORK_LEARN_ACCURACY);
        Net.showNetworkData();
    }

    private static double[][] getSampleAnswers() {
        return new double[][]{{0,1},{1,0},{1,0},{0,1}};
    }


    private static double[][] getSamples() {
        double[][] sample = new double[4][];
        double[] sample1 = {0,0};
        sample[0] = sample1;
        double[] sample2 = {0,1};
        sample[1] = sample2;
        double[] sample3 = {1,0};
        sample[2] = sample3;
        double[] sample4 = {1,1};
        sample[3] = sample4;
        return sample;
    }

}