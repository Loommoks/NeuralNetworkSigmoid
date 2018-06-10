public class NetworkStarter {

    public static double NETWORK_LEARN_ACCURACY = 0.005;
    protected static int inputsCount  = 35;
    protected static int hiddenLayerOneCount = 22;
    protected static int hiddenLayerTwoCount = 16;
    protected static int outputsCount = 10;

    public static int getInputsCount(){return inputsCount;}
    public static int getHiddenLayerOneCount(){return hiddenLayerOneCount;}
    public static int getHiddenLayerTwoCount(){return hiddenLayerTwoCount;}
    public static int getOutputsCount(){return outputsCount;}
    public static double getNetworkLearnAccuracy(){return NETWORK_LEARN_ACCURACY;}

    public static void setInputsCount(int inputsCount) {
        NetworkStarter.inputsCount = inputsCount;
        System.out.println("Установлено количество нейронов входного слоя: "+inputsCount);
    }

    public static void setNetworkLearnAccuracy(double newAccuracy){
        NetworkStarter.NETWORK_LEARN_ACCURACY = newAccuracy;
        System.out.println("Установлено новая точность обучения сети: "+NETWORK_LEARN_ACCURACY);
        GUIPanel.writeToConsole("Установлено новая точность обучения сети: "+Double.toString(NETWORK_LEARN_ACCURACY)+"\n");
    }

    public static int[] getNeuronsInLayers(){
        int[] neuronsInLayers = {
            inputsCount,
            hiddenLayerOneCount,
            hiddenLayerTwoCount,
            outputsCount
        };
        return neuronsInLayers;
    }

    public static void Start (Network Net, double[][] inputs, double[][] outputs){

        int[] neuronsInLayers = {
                inputsCount,
                hiddenLayerOneCount,
                hiddenLayerTwoCount,
                outputsCount
        };

        Net.initializeNetwork(neuronsInLayers);

        Net.showNetworkData();
        Net.runBPA(inputs, outputs, NETWORK_LEARN_ACCURACY);
        Net.showNetworkData();
    }
}