public class Propagator {

    //todo поменять название метода после миграции/внедрения
    public void startPropagation(Network net, double in[]){
        int[] networkDimensions = net.getNetworkDimension();
        for (int i=0; i<networkDimensions.length;i++){
            if (i==0) {
                for (int j = 0; j < networkDimensions[i]; j++) {
                    net.setInputsValue(i,j,in[j],0);
                    net.calcutaleSummatorForNeuron(i,j);
                    net.calculateActivationFunctionForNeuron(i,j);
                    net.transferSingnal(i,j);
                }
            }
            else {
                for (int j = 0; j < networkDimensions[i]; j++) {
                    net.calcutaleSummatorForNeuron(i,j);
                    net.calculateActivationFunctionForNeuron(i,j);
                    net.transferSingnal(i,j);
                }
            }
        }
    }

    public boolean startPropagationWithTestSample(Network net,double in[],double answer[]){
        startPropagation(net, in);
        int lastLayerNumber = net.getNetworkDimension().length - 1;
        int neuronsInLastLayer = net.getNetworkDimension()[lastLayerNumber];
        double[] result = new double[neuronsInLastLayer];
        int counter=0;
        //int answerIndex=100;
        boolean answerIsRight = false;
        int rightAnswer=-1;
        int index=0;
        for (int j=0; j<neuronsInLastLayer;j++){
            result[j]= net.getOutFromNeuron(lastLayerNumber,j);
            if(result[j]>0.70){
                counter++;
                index=j;
            }
            //System.out.println("Out ["+j+"]: "+result[j]);
        }

        for (int a=0; a<answer.length;a++){
            if(answer[a]>0){
                rightAnswer=a;
            }
        }
        //System.out.print("Правильный ответ: "+rightAnswer+" ");
        if(counter==1){
            //System.out.print("Думаю в семпле число: "+index+" ");
            if (index==rightAnswer){
                //System.out.println("Ура! Угадал  ");
                answerIsRight=true;
            }
        }else {//System.out.println("Что-то пошло не так, не могу определится с ответом");
        }

        return answerIsRight;

    }

    public boolean startPropagationWithTestSampleAllIn(Network net, double in[],double answer[]){
        startPropagation(net, in);
        int lastLayerNumber = net.getNetworkDimension().length - 1;
        int neuronsInLastLayer = net.getNetworkDimension()[lastLayerNumber];
        double[] result = new double[neuronsInLastLayer];
        int counter=0;
        //int answerIndex=100;
        boolean answerIsRight = false;
        int rightAnswer=-1;
        double maxPossibilityAnswer=0;
        int index=0;
        for (int j=0; j<neuronsInLastLayer;j++){
            result[j]= net.getOutFromNeuron(lastLayerNumber,j);
            if(result[j]>maxPossibilityAnswer){
                maxPossibilityAnswer=result[j];
                index=j;
            }
            //System.out.println("Out ["+j+"]: "+result[j]);
        }

        for (int a=0; a<answer.length;a++){
            if(answer[a]>0){
                rightAnswer=a;
            }
        }
        //System.out.print("Правильный ответ: "+rightAnswer+" ");

        //System.out.print("Думаю в семпле число: "+index+" ");
        if (index==rightAnswer){
            //System.out.println("Ура! Угадал  ");
            answerIsRight=true;
        }


        return answerIsRight;

    }

}
