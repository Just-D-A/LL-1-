package main.org.volgatech.lexer.io.specialSymbols;

import main.org.volgatech.lexer.domain.Token;

public abstract class SpecialSymbols{

    public abstract Token execute(String val, int line, int position) throws Exception;

}