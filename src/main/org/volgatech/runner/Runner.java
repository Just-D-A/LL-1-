package main.org.volgatech.runner;

import main.org.volgatech.Globals.Globals;
import main.org.volgatech.lexer.domain.Token;
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
        if(!checkGrammarGuideSets()) {
            return new Token(0, "GRAMMAR SHOUD BE CHANGING", 0, 0);
        }
        for (Token token : tokenList) {

            if (!goToTable(token)) {
                return token;
            }
        }

        if (curr != -1) {
            return new Token(0, "NOT END OF FILE", 0, 0);
        }
        return null;
    }

    private boolean goToTable(Token token) {
        Method curMethod = methods.get(curr - 1);
        if ((!curMethod.getIsRightMethod()) || (curMethod.getVal().equals("@"))) {
            goNext(curMethod);
            return goToTable(token);
        } else if (!curMethod.getIsTerminale()) {
            ArrayList<String> guideSets = curMethod.getGuideSets();
            if (guideSets != null) {
                for (String guideSet : guideSets) {
                    if (equlesTerminaleOrType(token, guideSet)) {
                        curMethod.setNext(findMethodByGuideSet(curMethod.getVal(), guideSet, token));
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
                        if((guideSets.get(i).equals(guideSets.get(j))) && ( i != j)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void goNext(Method curMethod) {
        if (curMethod.getNeedStack()) {
            curr++;
            stack.push(curr);
        }

        curr = curMethod.getNext();
        if ((curr == -1) && (!stack.empty())) {
            curr = stack.pop();
        }
    }

    private boolean equlesTerminaleOrType(Token token, String guideSet) {
        return ((token.getValue().equals(guideSet)) || ((token.getTokenType() == Globals.IDENTIFIER_KEY) && (guideSet.equals("id"))));
    }

    private int findMethodByGuideSet(String val, String guideSet, Token token) {
        for (Method method : methods) {

            if ((val.equals(method.getVal())) && (!method.getIsRightMethod()) && (guideSet.equals(method.getGuideSet()))) {
                return method.getNum();
            }
        }
        return 0;
    }
}
