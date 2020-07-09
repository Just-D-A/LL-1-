package main.org.volgatech.table;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class GrammarReader {
    private final String fileGrammarName;

    public GrammarReader(String fileGrammarName) {
        this.fileGrammarName = fileGrammarName;
    }

    public ArrayList<ArrayList<String>> readGrammar() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(fileGrammarName));
        String line;
        ArrayList<ArrayList<String>> grammar = new ArrayList<>();
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            String[] arguments = parseArguments(line);
            ArrayList<String> grammarStr = new ArrayList<>(Arrays.asList(arguments));
            if (checkGrammar(arguments)) {
                grammar.add(grammarStr);
            }
        }
        return grammar;
    }

    private boolean checkGrammar(String[] arguments) {
        char[] firstArg = arguments[0].toCharArray();
        String secondArg = arguments[1];
        if ((firstArg[0] != '<') || (firstArg[firstArg.length - 1] != '>')){
            System.out.println("ERR_1 first arg isn't correct" + firstArg[firstArg.length - 1] );
            return false;
        }
        if (secondArg.length() != 2) {
            System.out.println("ERR_2 second arg isn't correct " + secondArg.length());
            return false;
        }
        for(String arg: arguments) {
            char[] argCharArr = arg.toCharArray();
            if(argCharArr.length > 2) {
                if ((argCharArr[argCharArr.length - 1] != '>') || (argCharArr[0] == '<')) {
                    if (argCharArr[0] == '<') {
                        if ((argCharArr[argCharArr.length - 1] != '>')) {
                            System.out.println("ERR_3 this arg isn't correct" + arg);
                            return false;
                        }
                    }
                    if (argCharArr[argCharArr.length - 1] != '>') {
                        if (argCharArr[0] == '<') {
                            System.out.println("ERR_3 this arg isn't correct" + arg);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private String[] parseArguments(String line) {
        return line.split(" ");
    }
}
