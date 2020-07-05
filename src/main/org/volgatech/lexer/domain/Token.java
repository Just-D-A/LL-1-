package main.org.volgatech.lexer.domain;

import main.org.volgatech.Globals.Globals;

public class Token {
    private int tokenType;
    private String value;
    private int line;
    private int position;
    private TokenType tt;

    public Token(int tokenType, String value, int line, int position) {
        this.tokenType = tokenType;
        this.value = value;
        this.line = line;
        this.position = position + 1;
        this.tt = new TokenType();
    }

    public Token createErrorToken(String value, int line, int position) {
        return new Token(Globals.ERROR_KEY, value, line, position);
    }

    public void writeToken() throws Exception {
        System.out.println("______________________________________________");
        System.out.println("Type: " + tt.getTokenType(tokenType));
        System.out.println("Value: " + value);
        System.out.println("Line: " + line);
        System.out.println("Position: " + position);
        System.out.println("______________________________________________");
    }

    public int getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    public int getLine() {
        return line;
    }

    public int getPosition() {
        return position;
    }
}
