import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Clause {

    private ArrayList<Predicate> clause; //disjunction of predicates
    private boolean positive = true;

    public Clause(ArrayList<Predicate> clause){

        this.clause = clause;

    }

    public void setClause(ArrayList<Predicate> clause){

        this.clause = clause;

    }

    public ArrayList<Predicate> getClause(){

        return this.clause;

    }

    public void negate(){

        this.positive = false;

    }

    public boolean isPositive() {
        return positive;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        if(!positive) s.append("!");

        s.append("[");

        for(int i = 0; i < clause.size(); i++){

            s.append(clause.get(i));

            if(i != clause.size() - 1) s.append(", ");

        }

        s.append("]");

        return String.valueOf(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clause clause1 = (Clause) o;
        return getClause().containsAll(clause1.getClause()) || clause1.getClause().containsAll(getClause());
    }

    @Override
    public int hashCode() {
        return Objects.hash(clause);
    }
}
