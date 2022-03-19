import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Unifier {

    public static List<? extends Qualifiable> Unify(KnowledgeBase KB, Qualifiable x, Qualifiable y, List<? extends Qualifiable> theta){

        if(theta.get(0).getName().equals("failure")){ //how we indicate failure

            return theta;

        }
        else if(!(x instanceof Predicate || x instanceof Clause) && x.getClass() == y.getClass()){

            if(x.equals(y)) return theta; //if x and y flat out equal each other, and are not a compound of any kind

        }
        else if(x instanceof Variable){

            return unify_var((Variable) x, y, theta);

        }
        else if(y instanceof Variable){

            return unify_var((Variable) y, x, theta);

        }
        else if(x instanceof Predicate && y instanceof Predicate){//COMPOUND?

            return Unify(KB, x, y, (Objects.requireNonNull(Unify(KB, new Predicate((x).getName()), new Predicate((y).getName()), theta))) );

        }
        else if(x instanceof Clause && y instanceof Clause){ //LIST?

            ArrayList<Predicate> List1 = ((Clause) x).getClause();
            ArrayList<Predicate> List2 = ((Clause) y).getClause();


        }else{

            ArrayList<Constant> failure = new ArrayList<>();

            Constant f = new Constant("failure");
            failure.add(f);
            return failure;

        }

        return null;
    }

    private static List<? extends Qualifiable> unify_var(Variable v, Qualifiable x, List<? extends Qualifiable> theta){

        return null;

    }

}
