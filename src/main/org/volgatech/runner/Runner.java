package main.org.volgatech.runner;

import main.org.volgatech.Globals.Globals;
import main.org.volgatech.lexer.domain.Token;
import main.org.volgatech.lexer.domain.TokenType;
import main.org.volgatech.table.domain.Method;

import java.util.ArrayList;
import java.util.Stack;

public class Runner {
    private ArrayList<Token> tokenList;
    private ArrayList<Method> methods;
    private Stack<Integer> stack;

    private int curr;

    public Runner(ArrayList<Token> tokenList, ArrayList<Method> methods) {
        this.tokenList = tokenList;
        this.methods = methods;
        stack = new Stack<>();
        curr = 1;
        fixTokenList();
    }

    private void fixTokenList() {
        ArrayList<Token> fixedTokenList = new ArrayList<>();
        for (Token token : tokenList) {
            if (token != null) {
                fixedTokenList.add(token);
            }
        }
        tokenList = fixedTokenList;
    }


    public Token run() throws Exception {
        if (!checkGrammarGuideSets()) {
            return new Token(0, "GRAMMAR SHOUD BE CHANGING", 0, 0);
        }
        for (Token token : tokenList) {

            if (!goToTable(token)) {
                return token;
            }
        }
   //     System.out.println(curr);
        if (curr != -1) {
            Method lastMethod = methods.get(curr-1);
            ArrayList<String> guideSets = lastMethod.getGuideSets();

            for(String guideSet: guideSets) {
                if(guideSet.equals(Globals.END_GUIDE_SET_VAL)) {
                    return null;
                }
            }
            return new Token(0, "NOT END OF FILE", 0, 0);
        }
        return null;
    }



    private boolean goToTable(Token token) {
        if (curr <= 0) {
            return false;
        }
        Method curMethod = methods.get(curr - 1);
        //ЯРОСЛАВ ВОТ ТУТ
        //ЭТО ФУНКЦИЯ ВЫЗЫВАЕТСЯ МНОГО РАЗ И ВЫЗЫВАЕТСЯ РЕКУРСИВНО ОТКРЫТЬ ФАЙЛ МНЕ КАЖЕТСЯ ЛУЧШЕ В КОНСТРУКТОРЕ, А ПОТОМ ТУПО ПИСАТЬ В НЕГО
        System.out.println(token.getValue() + ";" + curMethod.getOutString());
        //ЯРОСЛАВ ДАЛЬШЕ НЕ НАДО
        if ((!curMethod.getIsRightMethod()) | (curMethod.getVal().equals("@")) ) {
                goNext(curMethod);
                return goToTable(token);

        } else if (!curMethod.getIsTerminale()) {
            ArrayList<String> guideSets = curMethod.getGuideSets();
            if (guideSets != null) {
                for (String guideSet : guideSets) {
                    if (equlesTerminaleOrType(token, guideSet)) {
                        curMethod.setNext(findMethodByGuideSet(curMethod.getVal(), guideSet, token, curMethod.getNum()));
                        goNext(curMethod);
                        return goToTable(token);
                    }
                }

            } else {
                goNext(curMethod);
                return goToTable(token);
            }
            return false;
        } else {
            goNext(curMethod);
            return equlesTerminaleOrType(token, curMethod.getGuideSet());
        }
    }



    private boolean checkGrammarGuideSets() {
        for (Method method : methods) {
            if ((method.getIsRightMethod()) && (!method.getIsTerminale())) {
                ArrayList<String> guideSets = method.getGuideSets();
                for (int i = 0; i < guideSets.size(); i++) {
                    for (int j = 0; j < guideSets.size(); j++) {
                        if ((guideSets.get(i).equals(guideSets.get(j))) && (i != j)) {
                            System.out.println(method.getNum() + " " + method.getVal() + " " + guideSets.get(i));
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    /*
    арифметика + логика + сравнение
     */

    private void goNext(Method curMethod) {
        if (curMethod.getNeedStack()) {
            curr++;
            System.out.println("add to stack " + curr);
            stack.push(curr);
        }

        curr = curMethod.getNext();
        if ((curr == -1) && (!stack.empty())) {
            curr = stack.pop();
            System.out.println("pop from stack " + curr);
        }
    }

    private boolean equlesTerminaleOrType(Token token, String guideSet) {
        String tokenVal = token.getValue();
        int tokenTypeIndex = token.getTokenType();
        TokenType type = new TokenType();
        String valueOfType = type.getTokenType(tokenTypeIndex);
     //   System.out.println(guideSet + " " + ((tokenTypeIndex >= Globals.INTEGER_KEY) &
      //          (tokenTypeIndex <= Globals.FLOAT_KEY) & (guideSet.equals(Globals.GRAMMAR_AND_TOKEN_NUMBER_SYMBOL))));
        return (tokenVal.equals(guideSet)) ^ (guideSet.equals(valueOfType)) ^
                ((tokenTypeIndex >= Globals.INTEGER_KEY) & (tokenTypeIndex <= Globals.FLOAT_KEY) & (guideSet.equals(Globals.GRAMMAR_AND_TOKEN_NUMBER_SYMBOL)));
    }

    private int findMethodByGuideSet(String val, String guideSet, Token token, int num) {
        for (Method method : methods) {
            if ((val.equals(method.getVal())) & (!method.getIsRightMethod()) & (guideSet.equals(method.getGuideSet())) & (num != method.getNum()) ) {
                return method.getNum();
            }
        }

        for (Method method : methods) {
            if ((val.equals(method.getVal())) && (!method.getIsRightMethod()) & (num != method.getNum())) {
          //      Method nextMethod = methods.get(method.getNext()-1);
                System.out.println(method.getNum());
                for(String guideSetFor: method.getGuideSets()) {
                    if(guideSetFor.equals(guideSet)) {
                        return method.getNum();
                    }
                }
            /*    if(!nextMethod.getIsTerminale()) {
                    ArrayList<String> guideSets = nextMethod.getGuideSets();
                    for(String guideSetFor: guideSets) {
                        if(guideSetFor.equals(guideSet)){
                            return method.getNum();
                        }
                    }
                }*/

            }
        }
        return 0;
    }
}
