import java.util.ArrayList;

public class Predicate {

    public final String name;
    private int n; //n-ary predicate
    private boolean positive = true;
    private final ArrayList<Qualifiable> terms = new ArrayList<>();

    public Predicate(String name){

        this.name = name;

    }

    public String getName(){

        return this.name;

    }

    public void negate(){

        positive = !positive;

    }

    public void qualify(Qualifiable q){

        this.terms.add(q);
        this.n++;

    }

    public Predicate negated(){

        Predicate temp = new Predicate(this.getName());
        temp.negate();
        return temp;

    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        if(!positive) s = new StringBuilder("!");

        s.append(name);

        if(n > 0){

            s.append("(");

            for (int i = 0; i < n; i++) {

                s.append(terms.get(i));

                if(i != n - 1)s.append(","); //if not last term, add a comma separating

            }

            s.append(")");

        }

        return String.valueOf(s);
    }
}
