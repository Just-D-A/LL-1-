package main.org.volgatech.lexer.io;

import main.org.volgatech.lexer.domain.Token;
import main.org.volgatech.Globals.Globals;
import main.org.volgatech.lexer.io.dataTypes.*;
import main.org.volgatech.lexer.io.doubleSymbols.*;
import main.org.volgatech.lexer.io.identifiers.Identifiers;
import main.org.volgatech.lexer.io.keyWords.*;
import main.org.volgatech.lexer.io.numbers.Numbers;
import main.org.volgatech.lexer.io.specialSymbols.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class LexerReader {

    private ArrayList<Token> tokenList;

    private HashMap<String, KeyWord> keyWords;
    private HashMap<String, DataTypes> dataTypes;
    private HashMap<Character, SpecialSymbols> specialSymbols;
    private HashMap<String, DoubleSymbols> doubleSymbols;

    private Numbers numbers;
    private Identifiers identifiers;


    public LexerReader() {
        tokenList = new ArrayList<>();
        //______________
        dataTypes = new HashMap<>();
        dataTypes.put("int",     new DataTypes());
        dataTypes.put("double",  new DataTypes());
        dataTypes.put("boolean", new DataTypes());
        dataTypes.put("float",   new DataTypes());
        dataTypes.put("string",  new DataTypes());
        //______________
        keyWords = new HashMap<>();
        keyWords.put("write", new KeyWord());
        keyWords.put("PROG", new KeyWord());
        keyWords.put("VAR", new KeyWord());
        keyWords.put("BEGIN", new KeyWord());
        keyWords.put("END", new KeyWord());
        keyWords.put("while", new KeyWord());
        keyWords.put("for",   new KeyWord());
        keyWords.put("if",    new KeyWord());
        //______________
        specialSymbols = new HashMap<>();
        specialSymbols.put('+', new ArithmeticOperation());
        specialSymbols.put('-', new ArithmeticOperation());
        specialSymbols.put('/', new ArithmeticOperation());
        specialSymbols.put('*', new ArithmeticOperation());
        specialSymbols.put(',', new ArithmeticOperation());
        /*specialSymbols.put('>', new DoubleSymbols());
        specialSymbols.put('<', new DoubleSymbols());*/
        specialSymbols.put(' ', new Space());
        specialSymbols.put('(', new OpenScope());
        specialSymbols.put(')', new CloseScope());
        //______________
        doubleSymbols = new HashMap<>();
        doubleSymbols.put("&&", new DoubleSymbols());
        doubleSymbols.put("||", new DoubleSymbols());
        doubleSymbols.put("==", new DoubleSymbols());
        doubleSymbols.put("!=", new DoubleSymbols());
        doubleSymbols.put(">=", new DoubleSymbols());
        doubleSymbols.put("<=", new DoubleSymbols());
        //______________
        numbers = new Numbers();
        identifiers = new Identifiers();
    }

    public ArrayList<Token> start(String fileName) throws Exception {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(fileName));
        String line = "";
        int lineIndex = 1;
        Token token;

        do {
                if(line != null) {
                    line = reader.readLine();
                }
            try {
                if (line == null) {
                    break;
                }
                char[] charLine = line.toCharArray();
                String substring = "";

                for (int i = 0; i < charLine.length; i++) { //посимвольно рассматриваем строку

                    if (charLine[i] == Globals.STRING_OPEN_SYMBOL) { //проверка на наличие ' " ' - открытия строки
                        token = checkSubstring(substring, lineIndex, i);
                        if (token != null) {
                            tokenList.add(token);
                        }
                        substring = "";
                        String str = "";
                        int indexBegin = i;
                        int indexBeginLine = lineIndex;

                        for (int y = i + 1; y < charLine.length; y++) {
                            str += charLine[y];
                        }

                        int index = str.indexOf(Globals.STRING_CLOSE_SYMBOL);
                        boolean isEnd = false;
                        while (index == -1) {
                            line = reader.readLine();
                            if (line == null) {
                                token = new Token(Globals.ERROR_KEY, Character.toString(Globals.STRING_OPEN_SYMBOL), indexBeginLine, indexBegin);
                                tokenList.add(token);
                                isEnd = true;
                                break;
                            }
                            lineIndex++;
                            str += line;
                            index = str.indexOf(Globals.STRING_CLOSE_SYMBOL);
                        }

                        if(isEnd) {
                            break;
                        }
                        str = '"' + str;
                        if (index + 1 < str.length()) {
                            i = line.length() - (str.length() - index) + 1;// длинна текущей строки - длинна от конца строки до закрывающего символа
                            str = str.substring(0, index + 1);
                        } else {
                            line = reader.readLine();
                            i = 0;
                        }
                        str = str + '"';
                        token = new Token(Globals.STRING_KEY, str, indexBeginLine, indexBegin);
                        tokenList.add(token);

                        charLine = line.toCharArray();
                    } else if (((i + 1) < charLine.length) && (doubleSymbols.containsKey("" + charLine[i] + charLine[i + 1]))) {
                        token = checkSubstring(substring, lineIndex, i);
                        String twoSym = "" + charLine[i] + charLine[i + 1];
                        if (token != null) {
                            tokenList.add(token);
                        }
                        substring = "";
                        DoubleSymbols doubleSymbol = doubleSymbols.get(twoSym);
                        token = doubleSymbol.execute(twoSym, lineIndex, i);
                        tokenList.add(token);
                        i++;
                    } else if (specialSymbols.containsKey(charLine[i])) { //проверка на спец символы
                        if (((i + 1) < charLine.length) && (("" + charLine[i] + charLine[i + 1]).equals(Globals.COMMENT_CLOSE_SYMBOL))) { //проверить следующий символ "*/"
                            String twoSym = "" + charLine[i] + charLine[i + 1];
                            token = checkSubstring(substring, lineIndex, i);
                            if (token != null) {
                                tokenList.add(token);
                            }
                            substring = "";
                            token = new Token(Globals.ERROR_KEY, Globals.COMMENT_CLOSE_SYMBOL, lineIndex, i);
                            tokenList.add(token);
                            i++;

                        } else if (((i + 1) < charLine.length) && (("" + charLine[i] + charLine[i + 1]).equals(Globals.COMMENT_OPEN_SYMBOL))) { //проверить следующий символ "/*"
                            String twoSym = "" + charLine[i] + charLine[i + 1];
                            token = checkSubstring(substring, lineIndex, i);
                            if (token != null) {
                                tokenList.add(token);
                            }
                            substring = "";
                            i++;

                            //_________обработка комментария___________
                            if (twoSym.equals(Globals.COMMENT_OPEN_SYMBOL)) {
                                int indexBegin = i - 1;
                                int indexBeginLine = lineIndex;

                                String str = "";

                                for (int y = i + 1; y < charLine.length; y++) {
                                    str += charLine[y];
                                }

                                int index = str.indexOf(Globals.COMMENT_CLOSE_SYMBOL);
                                boolean isEnd = false;
                                while (index == -1) {
                                    line = reader.readLine();
                                    if (line == null) {
                                        token = new Token(Globals.ERROR_KEY, Globals.COMMENT_OPEN_SYMBOL, indexBeginLine, indexBegin);
                                        tokenList.add(token);
                                        isEnd = true;
                                        break;
                                    }
                                    lineIndex++;
                                    str += line;
                                    index = str.indexOf(Globals.COMMENT_CLOSE_SYMBOL);
                                }

                                if(isEnd) {
                                    break;
                                }
                                if (index + 1 < str.length()) {
                                    i = line.length() - (str.length() - index) + 1;// длинна текущей строки - длинна от конца строки до закрывающего символа
                                    // str = str.substring(0, index);
                                } else {
                                    line = reader.readLine();
                                    i = 0;
                                }

                                /*token = new Token(Globals.COMMENT_VAL_KEY, str, indexBeginLine, indexBegin);
                                tokenList.add(token);
                                token = new Token(Globals.COMMENT_CLOSE_KEY, Globals.COMMENT_CLOSE_SYMBOL, lineIndex, i);
                                tokenList.add(token);*/ //создание токенов комментария, они не нужны

                                charLine = line.toCharArray();
                            }
                            //_____________________
                        } else if ((i - 2 >= 0) && ((charLine[i-2] >= '0') && (charLine[i-2] <= '9')) && (charLine[i-1] == Globals.FLOAT_SYMBOL)
                                                                                                      && ((charLine[i] == '-') || (charLine[i] == '+')))
                        {
                            substring += charLine[i];

                        } else { //обработка спецсимвола
                                token = checkSubstring(substring, lineIndex, i);
                                if (token != null) {
                                    tokenList.add(token);
                                }
                                SpecialSymbols specialSymbol = specialSymbols.get(charLine[i]);
                                tokenList.add(specialSymbol.execute("" + charLine[i], lineIndex, i));
                                substring = "";
                        }
                    } else {
                        substring += charLine[i];
                    }
                }
                token = checkSubstring(substring, lineIndex, charLine.length);
                if (token != null) {
                    tokenList.add(token);
                }
                lineIndex++;

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } while (line != null);

        return tokenList;
    }

    private Token checkSubstring(String subStr, int lineInd, int ind) throws Exception { //Проверка строки
        if (subStr.equals("")) {
            return null;
        }
        ind -= subStr.length();
        Token token;

        if (keyWords.containsKey(subStr)) {
            KeyWord keyWord = keyWords.get(subStr);
            token = keyWord.execute(subStr, lineInd, ind);
            return token;
        }

        if (dataTypes.containsKey(subStr)) {
            DataTypes dataType = dataTypes.get(subStr);
            token = dataType.execute(subStr, lineInd, ind);
            return token;
        }
        char[] charArr = subStr.toCharArray();

        //добавить по первому символу
        token = identifiers.checkIdentifier(subStr, lineInd, ind);
        if (token != null) {
            return token;
        }

        token = numbers.checkNum(subStr, lineInd, ind);
        if (token != null) {
            return token;
        }

        token = new Token(Globals.ERROR_KEY, subStr, lineInd, ind);
        return token;

    }


}
