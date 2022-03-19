import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

//Juan Francisco Patino

public class lab2 {

    private static final ArrayList<Clause> clauses_seen = new ArrayList<>();

    public static void main(String[] args) throws IOException { //args[0] = KB.cnf

        Scanner S = new Scanner(new File(args[0]));

        if(resolution(S)){

            System.out.println("yes");

        }
        else{

            System.out.println("no");

        }

    }

    public static boolean resolution(Scanner S){

        KnowledgeBase KB = new KnowledgeBase();

        DecodeKB(S, KB);

        ArrayList<Clause> clauses = KB.getClauses(); //disjunction of positive predicates

        clauses_seen.addAll(clauses);

        ArrayList<Clause> NEW = new ArrayList<>();

        while(true){

            for(int i = 0; i < clauses.size(); i++){

                for(int j = 1; j < clauses.size(); j++){

                    ArrayList<Clause> resolvents = resolve(clauses.get(i), clauses.get(j));

                    for (Clause c: resolvents
                         ) {

                        if(c.getClause().size() == 0) return false;

                    }

                    boolean in = false;

                    for (Clause x: resolvents){

                        for (Clause y: clauses_seen
                             ) {

                            if(x.equals(y)) in = true;

                        }

                        if(!in)
                            clauses_seen.add(x);
                        in = false;

                    }

                    NEW.addAll(resolvents);

                }

            }


            boolean newFlag = false;

            for (Clause x: NEW
            ) {

                boolean newFlagClause = true;

                for (Clause y: KB.getClauses()
                ) {

                    if(x.equals(y)){

                        newFlagClause = false;
                        break;

                    }

                }

                if(newFlagClause)
                    newFlag = true;

            }

            if(!newFlag){

                //System.out.println("NOTHING NEW IS IN HERE!!!");
                return true;

            }
            //else

            for (Clause n: NEW
            ) {

                KB.addClause(n);

            }


        }

    }


    public static boolean isSubset(ArrayList<Clause> NEW, ArrayList<Clause> clauses){

        //if everything in NEW is also in clauses


        ArrayList<Predicate> NEWToPredicateArray = new ArrayList<>();

        for (Clause n: NEW
        ) {

            NEWToPredicateArray.addAll(n.getClause());

        }

        ArrayList<Predicate> clausesToPredicateArray = new ArrayList<>();


        for (Clause c: clauses
        ) {

            clausesToPredicateArray.addAll(c.getClause());

        }

        for (Predicate n: NEWToPredicateArray
        ) {

            if(!clausesToPredicateArray.contains(n)) return false;

        }


        return true;

    }

    public static boolean isSubset(Clause NEW, ArrayList<Clause> clauses){

        //if everything in NEW is also in clauses


        ArrayList<Predicate> clausesToPredicateArray = new ArrayList<>();


        for (Clause c: clauses
             ) {

            clausesToPredicateArray.addAll(c.getClause());


        }

        for (Predicate n: NEW.getClause()
             ) {

            if(!clausesToPredicateArray.contains(n)) return false;

        }


        return true;

    }

    public static ArrayList<Clause> resolve(Clause Ci, Clause Cj){ //"while true do for each pair of clauses

        ArrayList<Clause> resolvents = new ArrayList<>();

        for (Predicate p1: Ci.getClause()
             ) {

            for (Predicate p2: Cj.getClause()
                 ) {

                if(p2.getName().equals(p1.getName()) && !p2.negated().equals(p1.negated())){

                    ArrayList<Predicate> newPredicates = new ArrayList<>();

                    ArrayList<Predicate> newPredicate1 = Ci.getClause();


                    //remove pred 1
                    for (int i = 0; i < newPredicate1.size(); i++
                    ) {

                        Predicate x = newPredicate1.get(i);

                        if(x.equals(p1)){
                            
                            newPredicate1.remove(p1);

                        }

                    }

                    ArrayList<Predicate> newPredicate2 = Cj.getClause();

                    //remove pred 2
                    for (int i = 0; i < newPredicate2.size(); i++
                    ) {

                        Predicate x = newPredicate2.get(i);

                        if(x.equals(p2)){

                            newPredicate2.remove(p2);

                        }

                    }
                    
                    newPredicates.addAll(newPredicate1);
                    newPredicates.addAll(newPredicate2);
                    
                    //deal w duplicates
                    ArrayList<Predicate> seen = new ArrayList<>();

                    for (Predicate p: newPredicates
                         ) {
                        
                        if(!seen.contains(p)) seen.add(p);
                        
                    }

                    //convert to Clause object
                    Clause tempClause = new Clause(seen); //newpreds

                    boolean seenForReal = false;

                    for (Clause c: clauses_seen
                         ) {

                        if (c.equals(tempClause)) {
                            seenForReal = true;
                            break;
                        }
                        
                    }

                    if(!seenForReal) resolvents.add(tempClause);
                    

                }

            }

        }

        return resolvents;

    }

    private static void DecodeKB(Scanner s, KnowledgeBase KB) {
        String ss = s.nextLine();

        int lineCount = 0;

        while(lineCount < 4){ //presets //we might not even ever use this

            String[] words = ss.split(" ");

            for(int i = 1; i < words.length; i++){

                String word = words[i];

                if(lineCount == 0){//predicate

                    Predicate p = new Predicate(word);
                    KB.addPredicate(p);

                }
                else if(lineCount == 1){//variables

                    Variable v = new Variable(word);
                    KB.addVariable(v);

                }
                else if(lineCount == 2){

                    Constant c = new Constant(word);
                    KB.addConstant(c);

                }
                else{

                    SkolemFunction sk = new SkolemFunction(word.charAt(3)); //this is fixed with ascii math
                    KB.addFunc(sk);

                }

            }
            lineCount++;
            ss = s.nextLine();
        }

        while(ss != null){//add clauses

            String[] words = ss.split(" ");
            if(words[0].equals("Clauses:")) {

                ss = s.nextLine();
                words = ss.split(" ");

            }

            Predicate[] p = new Predicate[words.length];

            int predIDX = 0;
            for (String word : words) {

                Predicate pred = findPredicate(KB, word);

                if (word.charAt(0) == '!') //positive
                {
                    assert pred != null;
                    pred.negate();
                }

                StringBuilder S = new StringBuilder();

                boolean adding = false; //true if we are adding something to this predicate

                for (int charIdx = 0; charIdx < word.length(); charIdx++ //add variables/constants/funtions to this prediate
                ) {

                    char c = word.toCharArray()[charIdx];

                    if (c == '(') {

                        //if this is the inside of a skolem function


                        if(String.valueOf(S).contains("SKF")){

                            //variables always two characters long

                            SkolemFunction sk = new SkolemFunction(String.valueOf(S).charAt(3));

                            sk.setX(new Variable((word.toCharArray()[charIdx+ 1] + "" +  word.toCharArray()[charIdx+2])));
                            assert pred != null;
                            pred.qualify(sk);
                            adding = false;
                            charIdx = charIdx + 2;
                            S = new StringBuilder();

                        }
                        else{

                            adding = true;
                            continue;

                        }


                    } else if (c == ',') {

                        //add what the stringbuilder has to this predicate
                        //adding still true

                        assert pred != null;
                        pred.qualify(KB.find(S.toString()));
                        S = new StringBuilder();
                        continue;

                        //now ready to add another qualifiable

                    } else if (c == ')') {

                        adding = false;
                        assert pred != null;

                        if(!String.valueOf(S).equals(""))
                        pred.qualify(KB.find(S.toString()));

                    }

                    if (adding) {

                        S.append(c);

                    }


                }

                p[predIDX] = pred;
                predIDX++;

            }

            KB.addClause(new Clause(new ArrayList<>(List.of(p))));

            try{

                ss = s.nextLine();

            }
            catch(NoSuchElementException n){

                break;

            }
        }

        int debug = 69;
    }

    private static Predicate findPredicate(KnowledgeBase KB, String word){

        for (Predicate p: KB.getPredicates()
             ) {

            if(word.contains(p.getName())){

                return new Predicate(p.getName()); //maybe don't do this

            }

        }

        return null;

    }


}