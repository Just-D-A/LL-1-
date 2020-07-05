package main.org.volgatech;

import main.org.volgatech.Globals.Globals;
import main.org.volgatech.lexer.io.LexerReader;
import main.org.volgatech.table.Converter;
import main.org.volgatech.table.GrammarReader;
import main.org.volgatech.table.domain.MethodList;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        LexerReader lexerReader = new LexerReader();
        lexerReader.start("./src/main/org/volgatech/files/ProgrameFile.txt");

        GrammarReader grammarReader = new GrammarReader(Globals.GRAMMAR_FILE_NAME);
        ArrayList<ArrayList<String>> grammar = grammarReader.readGrammar();

        Converter converter = new Converter(grammar);
        ArrayList<MethodList> methods = converter.convertGrammar();
        for(MethodList methodsList: methods) {
            methodsList.writeOut();
        }
        System.out.println("*** Convecter complited ***");
    }

}