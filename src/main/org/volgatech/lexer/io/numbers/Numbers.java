package main.org.volgatech.lexer.io.numbers;

import main.org.volgatech.lexer.domain.Token;
import main.org.volgatech.Globals.Globals;

public class Numbers {
    private char[] num;

    public Token checkNum(String number, int line, int position) throws Exception {
        this.num = number.toCharArray();
        if (num.length > Globals.MAX_INT_VALUE) {
            return null;
        }

        if (checkIntNum()) {
            return new Token(Globals.INTEGER_KEY, number, line, position);
        }
        if (checkDoubleNum()) {
            return new Token(Globals.DOUBLE_KEY, number, line, position);
        }
        if (checkFloatNum()) {
            return new Token(Globals.FLOAT_KEY, number, line, position);
        }
        if (check2Num()) {
            return new Token(Globals.BINARY_KEY, number, line, position);
        }
        if (check16Num()) {
            return new Token(Globals.HEX_KEY, number, line, position);
        }

        return null;
    }

    private boolean checkFloatNum() {
        boolean hasOnePoint = false;

        for (int i = 0; i < num.length; i++) {
            if (!(((num[i] >= '0') && (num[i] <= '9')) || ((num[i] == '.') && (!hasOnePoint)))) {
                if (num[i] == 'e') {
                    return checkAfterE(i);
                } else {
                    return false;
                }
            }
            if (num[i] == '.') {
                hasOnePoint = true;
            }
        }

        return true;
    }

    private boolean checkAfterE(int i) {
        if ((i + 1 < num.length) && ((num[i + 1] == '+') || (num[i + 1] == '-'))) {
            i++;
            if (!(i + 1 < num.length)) {
                return false;
            }
        } else if (!(i + 1 < num.length)) {
            return false;
        }
        for (int index = i + 1; index < num.length; index++) {
            if (!((num[index] >= '0') && (num[index] <= '9'))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIntNum() {

        for (char n : num) {
            if (!((n >= '0') && (n <= '9'))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDoubleNum() {
        if (num.length > Globals.MAX_INT_VALUE) {
            return false;
        }
        boolean hasOnePoint = false;
        for (char n : num) {
            if (!(((n >= '0') && (n <= '9')) || ((n == '.') && (!hasOnePoint)))) {
                return false;
            }
            if (n == '.') {
                hasOnePoint = true;
            }
        }
        return true;
    }

    private boolean check2Num() {
        if (num[0] == Globals.FIRST_2_NUM_SYMBOL) { //символ
            for (int i = 1; i < num.length; i++) {
                if (!((num[i] == '0') || (num[i] == '1'))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean check16Num() {
        if (num[0] == Globals.FIRST_16_NUM_SYMBOL) { //символ
            for (int i = 1; i < num.length; i++) {
                if (!(((num[i] >= '0') && (num[i] <= '9')) || ((num[i] >= 'A') && (num[i] <= 'F')))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
