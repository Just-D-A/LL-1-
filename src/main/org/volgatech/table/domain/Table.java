package main.org.volgatech.table.domain;

import java.util.ArrayList;
import java.util.Stack;

public class Table {
    private ArrayList<MethodList> metodLists;
    private ArrayList<Method> methods;


    public Table(ArrayList<MethodList> metodLists) {
        this.metodLists = metodLists;
        methods = new ArrayList<>();

    }

    public ArrayList<Method> createMethodsArr() {
        for (MethodList methodList : metodLists) {
            methods.add(methodList.getFirstMethod());
            ArrayList<Method> methodsRightPart = methodList.getMethodsRightPart();
            for (Method method : methodsRightPart) {
                methods.add(method);
            }
        }
        sort();
        return methods;
    }

    private void sort() {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < methods.size() - 1; i++) {
                Method curMethod = methods.get(i);
                Method nextMethod = methods.get(i + 1);
                if (curMethod.getNum() > nextMethod.getNum()) {
                    methods.set(i, nextMethod);
                    methods.set(i + 1, curMethod);
                    sorted = false;
                }
            }
        }
    }

    public void writeTible() {
        for(Method method: methods) {
            method.writeMethod();
        }
    }

}
