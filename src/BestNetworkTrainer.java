import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BestNetworkTrainer {
    private static int networksToTestAmount = 100;
    private static int bestNetworksAmount = 3;

    ArrayList<Sample> samples;
    ArrayList<Network> learningNetworks;
    int[] accuracy;

    public static int getNetworksToTestAmount(){return networksToTestAmount;}
    public static void setNetworksToTestAmount(int newAmount){
        networksToTestAmount = newAmount;
        GUIPanel.writeToConsole("Установлено новое количество сетей в выборке: "
                +Integer.toString(networksToTestAmount)+"\n");
    }

    public static int getBestNetworksAmount(){return bestNetworksAmount;}
    public static void setBestNetworksAmount(int newWinnersAmount){
        bestNetworksAmount=newWinnersAmount;
        GUIPanel.writeToConsole("Установлено новое количество сетей победителей: "
                +Integer.toString(bestNetworksAmount)+"\n");

    }

    public void runTest(){
        //todo 1 Загрузка семплов
        loadLearningSamples();
        //todo 2 Метод обучающий сети, возвращает количество итераций
        learnNetworks();
        //todo 3 Метод загрузки проверочных сэмплов
        loadTestSamples();
        //todo 4 Метод проверяющий сети, возвращает количество ошибок сети на проверочных семплах
        testAllNetworksWithSamples();
        //todo 5 Метод сохраняющий победителей

    }

    public void loadLearningSamples() {
        ReadAFile readAFile = new ReadAFile();
        samples = readAFile.read();
    }

    public void learnNetworks() {
        learningNetworks = new ArrayList<>();
        for (int i=0;i<networksToTestAmount;i++) {
            learningNetworks.add(new Network());
            double[][] inputs = ReadAFile.arrayListToArrayConvertorInput(samples);
            double[][] answers = ReadAFile.arrayListToArrayConvertorOutput(samples);
            NetworkStarter.Start(learningNetworks.get(i), inputs, answers);
            GUIPanel.writeToConsole("Обучена сеть ["+Integer.toString(i)+"], " +
                    "количество итераций: "
                    +Integer.toString(learningNetworks.get(i).getLearningIterations())+'\n');
        }
    }

    public void loadTestSamples(){
        ReadAFile readAFile = new ReadAFile();
        samples = readAFile.read();
    }

    public void testAllNetworksWithSamples(){
        double[][] input = ReadAFile.arrayListToArrayConvertorInput(samples);
        double[][] output = ReadAFile.arrayListToArrayConvertorOutput(samples);
        accuracy=new int[learningNetworks.size()];

        for (int i=0;i<learningNetworks.size();i++){
            testNetworkWithSamples(input,output,i);
        }
    }

    public void testNetworkWithSamples(double[][] input, double[][] output, int networkIndex){
        for (int k=0; k<input.length;k++) {
            learningNetworks.get(networkIndex).startNetworkingWithTestSample(input[k],output[k]);
        }
    }

}
