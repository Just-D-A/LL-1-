package main.org.volgatech.lexer.io.doubleSymbols;

import main.org.volgatech.lexer.domain.Token;

public abstract class DoubleSymbols {

    public abstract Token execute(String val, int line, int position) throws Exception;

}