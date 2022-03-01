import java.util.*;
import java.io.*;

//Juan Francisco Patino

public class lab2 {

    public static void main(String[] args) throws IOException { //args[0] = KB.cnf

        Scanner s = new Scanner(new File(args[0]));

        if(resolution(s)){

            System.out.println("yes");

        }
        else{

            System.out.println("no");

        }

    }

    public static boolean resolution(Scanner s){ //main resolution function

        KnowledgeBase KB = new KnowledgeBase();

        DecodeKB(s, KB);

        return true;

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
            KB.addClause(new Clause(p));

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