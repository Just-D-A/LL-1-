package main.org.volgatech;

import main.org.volgatech.Globals.Globals;
import main.org.volgatech.lexer.domain.Token;
import main.org.volgatech.lexer.io.LexerReader;
import main.org.volgatech.runner.Runner;
import main.org.volgatech.table.Converter;
import main.org.volgatech.table.GrammarReader;
import main.org.volgatech.table.domain.Method;
import main.org.volgatech.table.domain.MethodList;
import main.org.volgatech.table.domain.Table;

import java.util.ArrayList;

public class Main {

    final int mark = 2+2+1;

    public static void main(String[] args) throws Exception {
        LexerReader lexerReader = new LexerReader();
        ArrayList<Token> tokenList = lexerReader.start(Globals.PROGRAM_FILE_NAME);
        System.out.println("*** Lexer complited ***");

        GrammarReader grammarReader = new GrammarReader(Globals.GRAMMAR_FILE_NAME);
        ArrayList<ArrayList<String>> grammar = grammarReader.readGrammar();
        System.out.println("*** Grammar success ***");

        Converter converter = new Converter(grammar);
        ArrayList<MethodList> methodList = converter.convertGrammar();
        Table table = new Table(methodList);
        ArrayList<Method> methods = table.createMethodsArr();

        table.writeTible();

        System.out.println("*** Table complited ***");
        Runner runner = new Runner(tokenList, methods);
        Token errorToken = runner.run();

        if (errorToken == null) {

            System.out.println("          **");
            System.out.println("         *0**");
            System.out.println("        ****0*");
            System.out.println("       ****0***");
            System.out.println("      *0*****0**");
            System.out.println("     ****0*****0*");
            System.out.println("    **0***0***0***");
            System.out.println("   *0***0**0***0***");
            System.out.println("   *0***0**0***0***");
            System.out.println("  0** Complited! ***");
            System.out.println(" *** LL1  success *0*");
            System.out.println("**0 HAPPY NEW YEAR ***");

        } else {
            System.out.println("ERROR");
            errorToken.writeToken();
        }


    }
}