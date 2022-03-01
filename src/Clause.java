public class Clause {

    private Predicate[] clause; //disjunction of predicates

    public Clause(Predicate[] clause){

        this.clause = clause;

    }

    public void setClause(Predicate[] clause){

        this.clause = clause;

    }

    public Predicate[] getClause(){

        return this.clause;

    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("[");

        for(int i = 0; i < clause.length; i++){

            s.append(clause[i]);

            if(i != clause.length - 1) s.append(", ");

        }

        s.append("]");

        return String.valueOf(s);
    }
}
