import java.util.ArrayList;

public class KnowledgeBase {

    private final ArrayList<Predicate> p = new ArrayList<>(); //predicates should be immutable after being added
    private final ArrayList<Variable> v = new ArrayList<>();
    private final ArrayList<Constant> c = new ArrayList<>();
    private final ArrayList<SkolemFunction> skf = new ArrayList<>();
    private final ArrayList<Clause> C = new ArrayList<>();

    public KnowledgeBase(){}

    public void addPredicate(Predicate pred){

        this.p.add(pred);

    }

    public void addVariable(Variable var){

        this.v.add(var);

    }

    public void addConstant(Constant con){

        this.c.add(con);

    }

    public void addFunc(SkolemFunction skol){

        this.skf.add(skol);

    }

    public void addClause(Clause clause){

        this.C.add(clause);

    }

    public ArrayList<Predicate> getPredicates() {
        return p;
    }

    public ArrayList<Variable> getVariables() {
        return v;
    }

    public ArrayList<Constant> getConstants() {
        return c;
    }

    public ArrayList<SkolemFunction> getSkolemFunctions() {
        return skf;
    }

    public ArrayList<Clause> getClauses(){

        return C;

    }

    public Qualifiable find(String s){

        for (Variable v: v
             ) {

            if(v.getName().contains(s) || s.contains(v.getName())) return v;

        }

        for (Constant c: c
             ) {

            if(c.getName().contains(s) || s.contains(c.getName())) return c;

        }

        for (SkolemFunction sk: skf
             ) {

            if(sk.getName().contains(s) || s.contains(sk.getName())) return sk;

        }

        return null;

    }

}
