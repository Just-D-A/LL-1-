package main.org.volgatech.Globals;

public class Globals {
    public static char              FLOAT_SYMBOL = 'e';
    public static char        FIRST_2_NUM_SYMBOL = '$';
    public static char       FIRST_16_NUM_SYMBOL = '@';

    public static int                 MAX_INT_VALUE =  11;
    public static int               MAX_IDENT_VALUE =  20;

    public static char        STRING_OPEN_SYMBOL = '"';
    public static char        STRING_CLOSE_SYMBOL = '"';
    public static String        COMMENT_OPEN_SYMBOL = "/*";
    public static String        COMMENT_CLOSE_SYMBOL = "*/";

    public static int                  ERROR_KEY =  0;
    public static int   ARITHMETIC_OPERATION_KEY =  1;
    public static int             IDENTIFIER_KEY =  2;
    public static int               KEY_WORD_KEY =  3;
    public static int   COMPARISON_OPERATION_KEY =  4;
    public static int                INTEGER_KEY =  5;
    public static int                 DOUBLE_KEY =  6;
    public static int                  FLOAT_KEY =  7;
    public static int                    HEX_KEY =  8;
    public static int                 BINARY_KEY =  9;
    public static int                 STRING_KEY = 10;
    public static int              SEMICOLON_KEY = 11;
    public static int            COMMENT_VAL_KEY = 12;
    public static int           COMMENT_OPEN_KEY = 13;
    public static int          COMMENT_CLOSE_KEY = 14;
    public static int                  SPACE_KEY = 15;
    public static int             OPEN_SCOPE_KEY = 16;
    public static int            CLOSE_SCOPE_KEY = 17;
    public static int      LOGICAL_OPERATION_KEY = 18;
    public static int                   TYPE_KEY = 19;

    public static String GRAMMAR_FILE_NAME = "./src/main/org/volgatech/files/grammar.txt";
}
