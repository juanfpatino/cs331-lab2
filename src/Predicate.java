import java.util.ArrayList;
import java.util.Objects;

public class Predicate implements Qualifiable {

    public final String name;
    private int n; //n-ary predicate
    private boolean positive = true;
    private final ArrayList<Qualifiable> terms = new ArrayList<>();

    public Predicate(String name){

        this.name = name;

    }

    public void replace(Variable v, Qualifiable y){

        ArrayList<Qualifiable> temp = new ArrayList<>();

        int i = 0;
        for (Qualifiable q: getTerms()
             ) {

            if(q.equals(v)) {

                temp.add(y);

            }
            else{

                temp.add(getTerms().get(i));

            }
            i++;


        }



    }

    public String getName(){

        return this.name;

    }

    public void qualify(Qualifiable q){

        this.terms.add(q);
        this.n++;

    }

    public ArrayList<Qualifiable> getTerms() {
        return terms;
    }

    public void negate(){

        positive = !positive;

    }

    public Predicate negated(){

        Predicate temp;

        if(this.positive){

            temp = new Predicate("!" + this.getName());

            for (Qualifiable q: this.getTerms()
                 ) {

                temp.qualify(q);

            }

            return temp;

        }

        temp = new Predicate(this.getName());

        for (Qualifiable q: this.getTerms()
        ) {

            temp.qualify(q);

        }

        return temp;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Predicate predicate = (Predicate) o;
        return positive == predicate.positive && Objects.equals(name, predicate.name)
                && (getTerms().equals(predicate.getTerms()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, positive, getTerms());
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
