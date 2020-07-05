package main.org.volgatech.lexer.io.identifiers;

import main.org.volgatech.lexer.domain.Token;
import main.org.volgatech.Globals.Globals;

public class Identifiers {

    public Token checkIdentifier(String name, int line, int position)  {
        char[] charArr = name.toCharArray();
        char ch;
        if ((!isCorrectFirstSymbol(charArr[0])) || (charArr.length > Globals.MAX_IDENT_VALUE)){
            return null;
        }
        for (int i = 1; i < charArr.length; i++) {
            ch = charArr[i];
            if (!((ch >= 'a') && (ch <= 'z') || (ch >= 'A') && (ch <= 'Z') || (ch >= '0') && (ch <= '9'))) {
                return  null;
            }
        }
        return  new Token(Globals.IDENTIFIER_KEY, name, line, position);

    }

    private boolean isCorrectFirstSymbol(char ch) {
        return (ch >= 'a') && (ch <= 'z') || (ch >= 'A') && (ch <= 'Z');
    }
}
