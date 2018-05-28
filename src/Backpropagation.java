public class Backpropagation {
    double[] input;
    double[] cOutput;

    public void setInput(double[] i) {
        input = i;
    }

    public double[] getInput() {
        return input;
    }

    public double getInputI(int i) {
        return input[i];
    }

    public void setcOutput(double[] o) {
        cOutput = o;
    }

    public double[] getcOutput(){
        return cOutput;
    }

    public double getcOutputI(int i){
        return cOutput[i];
    }

    public void Run (double[] i, double[] o){
        input = i;
        cOutput = o;

    }

}
