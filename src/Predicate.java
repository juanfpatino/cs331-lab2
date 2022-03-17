import java.util.ArrayList;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Predicate predicate = (Predicate) o;
        return positive == predicate.positive && Objects.equals(name, predicate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, positive);
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
