import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BestNetworkTrainer {
    private static int networksToTestAmount = 100;
    private static int bestNetworksAmount = 3;

    ArrayList<Sample> samples;
    ArrayList<Network> learningNetworks;
    int[] networkErrors;

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
        chooseAndSaveBestNetworks();

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
            /*GUIPanel.writeToConsole("Обучена сеть ["+Integer.toString(i)+"], " +
                    "количество итераций: "
                    +Integer.toString(learningNetworks.get(i).getLearningIterations())+'\n');*/
        }
    }

    public void loadTestSamples(){
        ReadAFile readAFile = new ReadAFile();
        samples = readAFile.read();
    }

    public void testAllNetworksWithSamples(){
        double[][] input = ReadAFile.arrayListToArrayConvertorInput(samples);
        double[][] output = ReadAFile.arrayListToArrayConvertorOutput(samples);
        networkErrors=new int[learningNetworks.size()];

        for (int i=0;i<learningNetworks.size();i++){
            networkErrors[i] = testNetworkWithSamples(input,output,i);
            GUIPanel.writeToConsole("Сеть [" +Integer.toString(i) +"], "
                    +" Итераций: [" +Integer.toString(learningNetworks.get(i).getLearningIterations())+"] "
                    +" Ошибок: [" +Integer.toString(networkErrors[i])+"] "+'\n');
        }
    }

    public int testNetworkWithSamples(double[][] input, double[][] output, int networkIndex){
        int networkErrorsMade=0;
        for (int k=0; k<input.length;k++) {
            if (learningNetworks.get(networkIndex).startNetworkingWithTestSample(input[k],output[k])){

            } else {networkErrorsMade++;}

        }
        return networkErrorsMade;
    }

    public void chooseAndSaveBestNetworks(){
        int bestNetworkscounter =0;
        ArrayList<Integer> bestNetworksIndexes = new ArrayList<>();
        //ArrayList<Integer> bestNetworksIterations = new ArrayList<>();

        for(int i =0; i<networkErrors.length ;i++){
            if(networkErrors[i]==0){
                bestNetworkscounter++;
                bestNetworksIndexes.add(i);
            } else {}

            //bestNetworksIterations.add(learningNetworks.get(i).learningIterations);
        }

        if (bestNetworksIndexes.size()>=bestNetworksAmount){
            if(bestNetworksIndexes.size()==bestNetworksAmount){
                writeAndSaveWinners(bestNetworksIndexes);
            }else {

                while (bestNetworksIndexes.size()>bestNetworksAmount)
                {
                    int indexToDelete =0;
                    int maxIterations=0;
                    for (int k=0; k<bestNetworksIndexes.size();k++){
                        if (learningNetworks.get(k).learningIterations>maxIterations){
                            maxIterations=learningNetworks.get(k).learningIterations;
                            indexToDelete = k;
                        } else {

                        }
                    }
                    bestNetworksIndexes.remove(indexToDelete);
                }
                writeAndSaveWinners(bestNetworksIndexes);
            }

        }else {
            System.out.println("Неудачный забег, победителей меньше, чем ожидалось -" +bestNetworksIndexes.size());
        }


    }

    private void writeAndSaveWinners(ArrayList<Integer> bestNetworksIndexes) {
        System.out.println("We have "+bestNetworksAmount+" Winners :");
        for (int k=0;k<bestNetworksIndexes.size();k++){
            System.out.println("Winner ["+k+"], " +
                    "Errors with testSamples [" + networkErrors[bestNetworksIndexes.get(k)] +"] " +
                    "Learning iterations [" + learningNetworks.get(bestNetworksIndexes.get(k)).learningIterations
                    +"\n");
            WrireAFile wrireAFile = new WrireAFile();
            wrireAFile.autoSave(learningNetworks.get(bestNetworksIndexes.get(k)),k);
        }
    }

    public class BestNetworkChooser{
        private int[] bestNetsIndexes;
        private int[] bestNetsErrors;
        private int[] bestNetsIterations;
    }

}
