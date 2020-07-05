package main.org.volgatech.lexer.io.specialSymbols;

import main.org.volgatech.Globals.Globals;
import main.org.volgatech.lexer.domain.Token;

public class CloseScope extends SpecialSymbols {
    @Override
    public Token execute(String val, int line, int position) {
        return new Token(Globals.CLOSE_SCOPE_KEY, val, line, position);
    }
}
