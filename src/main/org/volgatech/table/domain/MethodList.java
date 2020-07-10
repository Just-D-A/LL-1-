package main.org.volgatech.table.domain;

import java.util.ArrayList;

public class MethodList {
    private Method firstMethod;
    private ArrayList<Method> methodsRightPart;

    public MethodList() {

    }

    public void addFirstMethod(Method firstMethod) {
        this.firstMethod = firstMethod;
        this.firstMethod.setIsRightMethod(false);
    }

    public void addRightPartArray(ArrayList<Method> methodsRightPart) {
        this.methodsRightPart = methodsRightPart;
        for(Method methodRightPart: this.methodsRightPart) {
            methodRightPart.setIsRightMethod(true);
        }
    }

    public void writeOut() {
        //System.out.println("***");
        firstMethod.writeMethod();
       // System.out.println("______");
        for (Method methodRightPart : methodsRightPart) {
            methodRightPart.writeMethod();
        }
        //System.out.println("***");
    }

    public ArrayList<Method> getMethodsRightPart() {
        return methodsRightPart;
    }

    public Method getFirstMethod() {
        return firstMethod;
    }

    public String getFirstVal() {
        return firstMethod.getVal();
    }

    public int getFirstNum() {
        return firstMethod.getNum();
    }

    public Method getFirstRightMethod() {
        return methodsRightPart.get(0);
    }

    public Method getLastRightMethod() {
        return methodsRightPart.get(methodsRightPart.size()-1);
    }

    public int getLastNum() {
        return methodsRightPart.get(methodsRightPart.size() - 1).getNum();
    }

    public void changeAllNum(int num) {
        for (Method method : methodsRightPart) {
            method.changeNum(num);
            num++;
        }
    }

    public void changeLastNonterminalNext() {
        Method lastMethod = methodsRightPart.get(methodsRightPart.size() - 1);
        if(lastMethod.getIsTerminale()) {
            lastMethod.setNext(-1);
        }

    }

    public void changeFirstNum(int x) {
        this.firstMethod.changeNum(x);
    }

    public void setFirstNext() {
        firstMethod.setNext(methodsRightPart.get(0).getNum());
    }

    public void setFirstGuideSet(String guideSet) {
        firstMethod.setGuideSet(guideSet);
    }

    public String getGuideSet() {
        return firstMethod.getGuideSet();
    }
}
