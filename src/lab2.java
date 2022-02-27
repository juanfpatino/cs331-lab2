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

                    SkolemFunction sk = new SkolemFunction((int)word.charAt(3));
                    KB.addFunc(sk);

                }

            }
            lineCount++;
            ss = s.nextLine();
        }

        while(ss != null){//add clauses

            String[] words = ss.split(" ");
            if(words[0].equals("Clauses:")) s.nextLine();

            Predicate[] p = new Predicate[words.length];

            for (String word : words) {

                Predicate pred = findPredicate(KB, word);

                if (word.charAt(0) == '!') //positive
                {
                    assert pred != null;
                    pred.negate();
                }

                StringBuilder S = new StringBuilder();

                boolean adding = false; //true if we are adding something to this predicate

                for (char c : word.toCharArray() //add variables/constants/funtions to this prediate
                ) {

                    if (c == '(') {

                        adding = true;

                    } else if (c == ',') {

                        //add what the stringbuilder has to this predicate
                        //adding still true

                        assert pred != null;
                        pred.qualify(KB.find(S.toString()));
                        S = new StringBuilder();


                    } else if (c == ')') {

                        adding = false;
                        assert pred != null;
                        pred.qualify(KB.find(S.toString()));


                    }

                    if (adding) {

                        S.append(c);

                    }

                }


            }

            ss = s.nextLine();
        }

        return true;

    }

    private static Predicate findPredicate(KnowledgeBase KB, String word){

        for (Predicate p: KB.getPredicates()
             ) {

            if(p.getName().contains(word)){

                return p;

            }

        }

        return null;

    }


}