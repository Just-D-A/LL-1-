package main.org.volgatech.lexer.io.dataTypes;

import main.org.volgatech.Globals.Globals;
import main.org.volgatech.lexer.domain.Token;

public class DataTypes {
        public  Token execute(String val, int line, int position) {
                return new Token(Globals.TYPE_KEY, val, line, position);
        }

}