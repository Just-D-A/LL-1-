package main.org.volgatech.table.domain;

import java.util.ArrayList;

public class Method {
    private String val;
    private int num;
    private boolean isTerminal;
    private int next;
    private String guideSet; //направляющее множество
    private boolean shift; // сдвиг
    private boolean error;
    private boolean needStack;
    private boolean isEnd;
    private ArrayList<String> guideSets;
    private boolean isRightMethod;



    public Method(String val, int num) {
        this.val = val;
        this.num = num;
        isTerminal = checkTerminal();
        next = -1;
        guideSet = "NO";
        guideSets = new ArrayList<>();
    }


    public Method(String val, int num, boolean shift, boolean error, boolean needStack, boolean isEnd) {
        this.val = val;
        this.num = num;
        this.shift = shift;
        this.error = error;
        this.needStack = needStack;
        this.isEnd = isEnd;
        isTerminal = false;
        next = -1;
        guideSet = "NO";
    }

    public void setParams(boolean shift, boolean error, boolean needStack, boolean isEnd) {
        this.shift = shift;
        this.error = error;
        this.needStack = needStack;
        this.isEnd = isEnd;
    }

    public boolean getIsTerminale() {
        return isTerminal;
    }

    private boolean checkTerminal() {
        char[] valCharArr = val.toCharArray();
        return (valCharArr[0] != '<');
    }

    public void writeMethod() {
        String guideSetsStr;
        if ((isRightMethod) && (guideSets != null) && (guideSets.size() > 1)) {
            guideSetsStr = guideSets.get(0);
            for (int i = 1; i < guideSets.size(); i++) {
                guideSetsStr += "||" + guideSets.get(i);
            }
        } else {
            guideSetsStr = guideSet;
        }

        System.out.println(num + ";" + val + ";" + guideSetsStr + ";" + next +
                ";" + shift + ";" + error + ";" + needStack + ";" + isEnd + ";");
    }

    public void changeNum(int num) {
        this.num = num;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getNum() {
        return num;
    }


    public String getVal() {
        return val;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public void setGuideSet(String guideSet) {
        this.guideSet = guideSet;
    }

    public void addGuideSets(ArrayList<String> guideSets) {
        this.guideSets = guideSets;
    }

    public ArrayList<String> getGuideSets() {
        return guideSets;
    }

    public String getGuideSet() {
        return guideSet;
    }

    public boolean getNeedStack() {
        return needStack;
    }

    public int getNext() {
        return next;
    }

    public void setIsRightMethod(boolean isRightMethod) {
        this.isRightMethod = isRightMethod;
    }

    public boolean getIsRightMethod() {
        return isRightMethod;
    }

    public String getOutString() {
        String guideSetsStr;
        if ((isRightMethod) && (guideSets != null) && (guideSets.size() > 1)) {
            guideSetsStr = guideSets.get(0);
            for (int i = 1; i < guideSets.size(); i++) {
                guideSetsStr += "||" + guideSets.get(i);
            }
        } else {
            guideSetsStr = guideSet;
        }

        return num + ";" + val + ";" + guideSetsStr + ";" + next +
                ";" + shift + ";" + error + ";" + needStack + ";" + isEnd + ";";
    }
}
