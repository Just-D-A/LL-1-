package main.org.volgatech.lexer.domain;


import main.org.volgatech.Globals.Globals;

import java.util.HashMap;

public class TokenType {

    private HashMap<Integer, String> types = new HashMap<>();


    public TokenType() {
        types.put(Globals.ERROR_KEY, "error");
        types.put(Globals.ARITHMETIC_OPERATION_KEY, "arithmetic_operation");
        types.put(Globals.IDENTIFIER_KEY, "id");
        types.put(Globals.KEY_WORD_KEY, "key_word");
        types.put(Globals.COMPARISON_OPERATION_KEY, "comparison Operation");
        types.put(Globals.INTEGER_KEY, "integer");
        types.put(Globals.DOUBLE_KEY, "double");
        types.put(Globals.FLOAT_KEY, "float");
        types.put(Globals.HEX_KEY, "hex");
        types.put(Globals.BINARY_KEY, "binary");
        types.put(Globals.SEMICOLON_KEY, "separator");
        types.put(Globals.STRING_KEY, "string Value");
        types.put(Globals.COMMENT_VAL_KEY, "comment Value");
        types.put(Globals.COMMENT_OPEN_KEY, "open comment scope");
        types.put(Globals.COMMENT_CLOSE_KEY, "close comment scope");
        types.put(Globals.SPACE_KEY, "space");
        types.put(Globals.OPEN_SCOPE_KEY, "open Scope");
        types.put(Globals.CLOSE_SCOPE_KEY, "close Scope");
        types.put(Globals.LOGICAL_OPERATION_KEY, "logic_operation");
        types.put(Globals.LOGICAL_MORE_LESS_KEY, "more_less_operation");
        types.put(Globals.TYPE_KEY, "type");
    }

    public String getTokenType(int i) {
        return types.getOrDefault(i, "ERROR: unknown word");
    }
}
