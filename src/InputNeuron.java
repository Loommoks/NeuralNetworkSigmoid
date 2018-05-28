public class InputNeuron extends Neuron {
    public void calculateActivationFunctionOut(){
        setOut(getSum());
    }
}
