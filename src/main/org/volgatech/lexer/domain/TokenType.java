package main.org.volgatech.lexer.domain;


import main.org.volgatech.Globals.Globals;

import java.util.HashMap;

public class TokenType {

    private HashMap<Integer, String> types = new HashMap<>();


    public TokenType() {
        types.put(Globals.ERROR_KEY, "Error");
        types.put(Globals.ARITHMETIC_OPERATION_KEY, "Arithmetic Operation");
        types.put(Globals.IDENTIFIER_KEY, "Identifier");
        types.put(Globals.KEY_WORD_KEY, "KeyWord");
        types.put(Globals.COMPARISON_OPERATION_KEY, "Comparison Operation");
        types.put(Globals.INTEGER_KEY, "Integer");
        types.put(Globals.DOUBLE_KEY, "Double");
        types.put(Globals.FLOAT_KEY, "Float");
        types.put(Globals.HEX_KEY, "Hex");
        types.put(Globals.BINARY_KEY, "Binary");
        types.put(Globals.SEMICOLON_KEY, "Separator");
        types.put(Globals.STRING_KEY, "String Value");
        types.put(Globals.COMMENT_VAL_KEY, "Comment Value");
        types.put(Globals.COMMENT_OPEN_KEY, "Open comment scope");
        types.put(Globals.COMMENT_CLOSE_KEY, "Close comment scope");
        types.put(Globals.SPACE_KEY, "Space");
        types.put(Globals.SEMICOLON_KEY, "Separator");
        types.put(Globals.OPEN_SCOPE_KEY, "Open Scope");
        types.put(Globals.CLOSE_SCOPE_KEY, "Close Scope");
        types.put(Globals.LOGICAL_OPERATION_KEY, "Logical operation");
        types.put(Globals.TYPE_KEY, "Type");
    }

    public String getTokenType(int i) throws Exception {
        if (types.containsKey(i)) {
            return types.get(i);
        } else {
            throw new Exception("ERROR: unknown word");
        }
    }
}
