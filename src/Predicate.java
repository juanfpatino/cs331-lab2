import java.util.ArrayList;

public class Predicate {

    public final String name;
    private int n; //n-ary predicate
    private boolean positive = true;
    private ArrayList<Qualifiable> terms;

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

    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        if(!positive) s = new StringBuilder("!");

        s.append(name);

        if(n > 0){

            s.append("(");

            for (int i = 0; i < n; i++) {

                s.append(terms.get(i).getName());

                if(i != n - 1)s.append(","); //if not last term, add a comma separating

            }

            s.append(")");

        }

        return String.valueOf(s);
    }
}
