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

        Clause[] KB; //knowledge base

        //todo: decode .cnf files

        return true;

    }




}