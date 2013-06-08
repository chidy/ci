package nn.util;

/**
 * Created with IntelliJ IDEA.
 * User: chidimuorah
 * Date: 03/06/2013
 * Time: 23:33
 * To change this template use File | Settings | File Templates.
 */
public class ActivationFunctions {
    private ActivationFn fn;

    public enum ActivationFn { LINEAR, LOGISTIC, SOFTMAX }

    public ActivationFunctions(ActivationFn fn) {
        this.fn = fn;
    }
}
