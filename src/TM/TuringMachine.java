package TM;

import Utill.Pair;

import java.util.HashMap;
import java.util.HashSet;

abstract public class TuringMachine<T> {

    protected final String name;
    protected final HashSet<String> Q;
    protected final HashSet<Character> Shigma;
    protected final HashSet<Character> Gamma;
    protected final HashMap<Pair, T> delta;
    protected final String q0;
    protected final String qAccept;
    protected final String qReject;

    public TuringMachine(String name, HashSet<String> Q, HashSet<Character> Shigma, HashSet<Character> Gamma, HashMap<Pair, T> delta, String q0, String qAccept, String qReject) {
        this.name = name;
        this.Q = Q;
        this.Shigma = Shigma;
        this.Gamma = Gamma;
        this.delta = delta;
        this.q0 = q0;
        this.qAccept = qAccept;
        this.qReject = qReject;

        if(qAccept.equals(qReject))
            throw new IllegalArgumentException();

        if(!Gamma.contains('_'))
            throw new IllegalArgumentException();

        if(!Gamma.containsAll(Shigma))
            throw new IllegalArgumentException();
    }

    abstract public boolean isAccept(Tape tape);

    abstract public T getNextState(String currentStateSet, Character c);

    @Override
    public String toString() {
        return "test";
    }
}
