package Test;

import TM.DTM;
import Utill.Pair;
import Utill.Triple;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class DTMTest {
    public static DTM DTMTest() {

        String name = "test";
        HashSet<String> Q = new HashSet<>();
        Collections.addAll(Q, "q1", "q2", "q3", "q4", "q5", "qAccept", "qReject");

        HashSet<Character> Shigma = new HashSet<>();
        Collections.addAll(Shigma, '0');

        HashSet<Character> Gamma = new HashSet<>();
        Collections.addAll(Gamma, '0', 'x', '_');

        // q1 delta:
        HashMap<Pair, Triple> delta = new HashMap<>();
        delta.put(new Pair<>("q1", '0'), new Triple<> ("q2", '_', 'R'));
        delta.put(new Pair<>("q1", '_'), new Triple<>("qReject", '_', 'R'));
        delta.put(new Pair<>("q1", 'x'), new Triple<>("qReject", 'x', 'R'));

        // q2 delta:
        delta.put(new Pair<>("q2", '0'), new Triple<>("q3", 'x', 'R'));
        delta.put(new Pair<>("q2", 'x'), new Triple<>("q2", 'x', 'R'));
        delta.put(new Pair<>("q2", '_'), new Triple<>("qAccept", '_', 'R'));

        // q3 delta:
        delta.put(new Pair<>("q3", '0'), new Triple<>("q4", '0', 'R'));
        delta.put(new Pair<>("q3", 'x'), new Triple<>("q3", 'x', 'R'));
        delta.put(new Pair<>("q3", '_'), new Triple<>("q5", '_', 'L'));

        // q4 delta:
        delta.put(new Pair<>("q4", '0'), new Triple<>("q3", 'x', 'R'));
        delta.put(new Pair<>("q4", 'x'), new Triple<>("q4", 'x', 'R'));
        delta.put(new Pair<>("q4", '_'), new Triple<>("qReject", '_', 'R'));

        // q5 delta:
        delta.put(new Pair<>("q5", '0'), new Triple<>("q5", '0', 'L'));
        delta.put(new Pair<>("q5", 'x'), new Triple<>("q5", 'x', 'L'));
        delta.put(new Pair<>("q5", '_'), new Triple<>("q2", '_', 'R'));

        String q0 = "q1";
        String qAccept = "qAccept";
        String qReject = "qReject";

        return new DTM(name, Q, Shigma, Gamma, delta, q0, qAccept, qReject);
    }
}
