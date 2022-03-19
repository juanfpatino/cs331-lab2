import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Unifier {

    public static HashMap<Variable, Qualifiable> Unify(Qualifiable x, Qualifiable y, HashMap<Variable, Qualifiable> theta){

        if(theta.containsKey(new Variable("failure"))){ //how we indicate failure

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

            return Unify(x, y, (Objects.requireNonNull(Unify(new Predicate((x).getName()), new Predicate((y).getName()), theta))) );

        }
        else if(x instanceof Clause && y instanceof Clause){ //LIST?

            ArrayList<Predicate> list1 = ((Clause) x).getClause();
            ArrayList<Predicate> list2 = ((Clause) y).getClause();

            Qualifiable arg1 = null;
            Qualifiable arg2;

            if(list1.size() == 2){

                arg1 = list1.get(1);

            }
            else{

                list1.remove(0);
                arg1 = new Clause(list1);

            }

            if(list2.size()==2){

                arg2 = list1.get(1);

            }
            else{

                list2.remove(0);
                arg2 = new Clause(list2);

            }

            return Unify(arg1, arg2, Objects.requireNonNull(Unify(list1.get(0), list2.get(0), theta)));


        }else{

            return failure();

        }

        return null;
    }

    private static HashMap<Variable, Qualifiable> unify_var(Variable v, Qualifiable x, HashMap<Variable, Qualifiable> theta){

        for (Qualifiable z: theta.values()
             ) {

            if(z.equals(v)) return Unify(z, x, theta);

        }
        for (Qualifiable z: theta.values()
        ) {

            if(z.equals(x)) return Unify(v, x, theta);

        }
        for(Qualifiable z: theta.values()
        ){


        }

        theta.put(v, x);
        return theta;

    }

    private static HashMap<Variable, Qualifiable> failure(){

        HashMap<Variable, Qualifiable> failure = new HashMap<>();
        Variable f = new Variable("failure");
        failure.put(f, null);
        return failure;

    }

}
