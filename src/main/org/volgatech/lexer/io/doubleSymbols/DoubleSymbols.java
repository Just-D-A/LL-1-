package main.org.volgatech.lexer.io.doubleSymbols;

import main.org.volgatech.Globals.Globals;
import main.org.volgatech.lexer.domain.Token;

public class DoubleSymbols {

    public Token execute(String val, int line, int position) {
        return new Token(Globals.LOGICAL_OPERATION_KEY, val, line, position);
    }
}