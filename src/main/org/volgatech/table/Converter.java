package main.org.volgatech.table;

import main.org.volgatech.table.domain.Method;
import main.org.volgatech.table.domain.MethodList;

import java.util.ArrayList;

public class Converter {
    private ArrayList<ArrayList<String>> grammar;

    public Converter(ArrayList<ArrayList<String>> grammar) {
        this.grammar = grammar;
    }

    public ArrayList<MethodList> convertGrammar() {
        return makeMethodList();
    }

    private ArrayList<MethodList> makeMethodList() {
        int x = 1;
        ArrayList<MethodList> methodsArr = new ArrayList<>();

        for (ArrayList<String> grammarStr : grammar) {

            ArrayList<Method> methods = new ArrayList<>();
            String firstMethod = grammarStr.get(0);

            MethodList methodList = new MethodList();
            methodList.addFirstMethod(new Method(firstMethod, x, false, true, false, false));
            x++;

            for (int i = 2; i < grammarStr.size(); i++) {
                String grammarEl = grammarStr.get(i);

                if (grammarEl.equals("|")) {
                    methodList.addRightPartArray(methods);
                    methodsArr.add(methodList);

                    methods = new ArrayList<>();
                    methodList = new MethodList();

                    methodList.addFirstMethod(new Method(firstMethod, x, false, true, false, false));
                    x++;

                } else {
                    Method method = new Method(grammarEl, x);
                    //     if(!isDiscribed(methodsArr, method)) {
                    methods.add(method);
                    x++;
                    //   }
                }

            }
            methodList.addRightPartArray(methods);
            methodsArr.add(methodList);
        }
        return сheckNumericSystem(methodsArr);
    }

    private boolean isDiscribed(ArrayList<MethodList> methodsArr, Method method) {
        if (!method.getIsTerminale()) {
            for (MethodList methodList : methodsArr) {
                String firstVal = methodList.getFirstVal();
                if (firstVal.equals(method.getVal())) {
                    return false;
                }
            }
        }
        return false;
    }

    private ArrayList<MethodList> сheckNumericSystem(ArrayList<MethodList> methodsArr) {
        int countOfRepeat;
        ArrayList<MethodList> repeatedMethods = new ArrayList();
        for (int i = 1; i < methodsArr.size() - 1; i++) {
            countOfRepeat = 0;
            if (methodsArr.get(i + 1).getFirstVal().equals(methodsArr.get(i).getFirstVal())) {//если названия левый частей грамматик совпадают
                ArrayList<MethodList> methodListsToChange = new ArrayList<>();
                methodListsToChange.add(methodsArr.get(i));
                while ((i < methodsArr.size() - 1) && methodsArr.get(i).getFirstVal().equals(methodsArr.get(i + 1).getFirstVal())) {//ищем все одинаковые левые части
                    methodListsToChange.add(methodsArr.get(i + 1));
                    i++;
                }
                changeRepeats(methodListsToChange);
            }
        }
        //add next
        for (MethodList methodListToChange : methodsArr) {
            methodListToChange.setFirstNext();
            for (Method method : methodListToChange.getMethodsRightPart()) {
                if (method.getIsTerminale()) {
                    method.setNext(method.getNum() + 1);
                } else {
                    method.setNext(findNoterminaleNum(methodsArr, method.getVal()));
                }
            }
        }
        //change last next
        //если грамматика заканчивается нетерминалом тогда его значение NEXT = NULL
        for (MethodList methodListToChange : methodsArr) {
            methodListToChange.changeLastNonterminalNext();
        }

        //add guideSets
        //добавляем направляющие множества
        for (MethodList methodListToChange : methodsArr) {
            methodListToChange.setFirstGuideSet(methodListToChange.getMethodsRightPart().get(0).getVal());
            for (Method method : methodListToChange.getMethodsRightPart()) {
                if (!method.getIsTerminale()) {
                    method.setGuideSet(findNoterminaleGuideSet(methodsArr, method.getVal()));

                } else {
                    if (method.getVal().equals("@")) {
                        method.setGuideSet(findExitSymbolGuideSet(methodListToChange, methodsArr, "@"));
                    } else {
                        method.setGuideSet(method.getVal());
                    }
                }
            }
        }
        for (MethodList methodListToChange : methodsArr) {
            //methodListToChange.setFirstGuideSet(methodListToChange.getMethodsRightPart().get(0).getVal())
            Method firstRight = methodListToChange.getFirstRightMethod();
            if (firstRight.getVal().equals("@")) {
                methodListToChange.setFirstGuideSet(firstRight.getGuideSet());
            } else if (!firstRight.getIsTerminale()) {
                methodListToChange.setFirstGuideSet(findNoterminaleGuideSet(methodsArr, firstRight.getVal()));
            }
        }

        for (MethodList methodListToChange : methodsArr) {
            for (Method method : methodListToChange.getMethodsRightPart()) {
                if (!method.getIsTerminale()) {
                    method.addGuideSets(findNoterminalAllGuideSets(methodsArr, method.getVal()));
                }
            }
        }

        for(MethodList methodListToChange : methodsArr) {

            Method firstRightMethod = methodListToChange.getFirstRightMethod();
            if(!firstRightMethod.getIsTerminale()) {
                Method firstMethod = methodListToChange.getFirstMethod();
                firstMethod.addGuideSets(firstRightMethod.getGuideSets());
            }
        }
        //add table params
        //заполняем параметры таблицы в соответствие с тз
        for (MethodList methodListToChange : methodsArr) {
            ArrayList<Method> methodsRightPart = methodListToChange.getMethodsRightPart();
            for (int i = 0; i < methodsRightPart.size(); i++) {
                Method methodRightPart = methodsRightPart.get(i);
                if (methodRightPart.getIsTerminale()) {
                    methodRightPart.setParams(!methodRightPart.getVal().equals("@"), true, false, false);
                } else {
                    methodRightPart.setParams(false, true, (i + 1) < methodsRightPart.size(), false);
                }
            }
        }

        for (int i = 0; i < methodsArr.size() - 1; i++) {
            MethodList methodListToChange = methodsArr.get(i);

            MethodList methodListToChangeNext = methodsArr.get(i + 1);
            if (methodListToChange.getFirstVal().equals(methodListToChangeNext.getFirstVal())) {
                Method firstMethod = methodListToChange.getFirstMethod();
                firstMethod.setError(false);
            }
        }


        return methodsArr;
    }


    private void changeRepeats(ArrayList<MethodList> methodListsToChange) {
        int firstNum = methodListsToChange.get(0).getFirstNum();
        for (MethodList methodListToChange : methodListsToChange) {
            methodListToChange.changeFirstNum(firstNum);
            firstNum++;
        }
        for (MethodList methodListToChange : methodListsToChange) {
            methodListToChange.changeAllNum(firstNum);
            firstNum = methodListToChange.getLastNum() + 1;
        }

    }


    private int findNoterminaleNum(ArrayList<MethodList> methodListsToFind, String val) {
        for (MethodList methodList : methodListsToFind) {
            if (val.equals(methodList.getFirstVal())) {
                return methodList.getFirstNum();
            }
        }
        return 0;
    }

    private ArrayList<String> findNoterminalAllGuideSets(ArrayList<MethodList> methodListsToFind, String val) {
        ArrayList<String> guideSetsOfNoterminale = new ArrayList<>();
        for (MethodList methodList : methodListsToFind) {
            if (val.equals(methodList.getFirstVal())) {
                Method firstRightMethod = methodList.getFirstRightMethod();
                if(firstRightMethod.getIsTerminale()) {
                    guideSetsOfNoterminale.add(methodList.getGuideSet());
                } else {
                 /*   ArrayList<String> guideSetsOfNewNoterminale = findNoterminalAllGuideSets(methodListsToFind, firstRightMethod.getVal());
                    firstRightMethod.addGuideSets(guideSetsOfNewNoterminale);*/
                    guideSetsOfNoterminale.addAll(findNoterminalAllGuideSets(methodListsToFind, firstRightMethod.getVal()));
                }
            }
        }
        return guideSetsOfNoterminale;
    }

    private String findNoterminaleGuideSet(ArrayList<MethodList> methodListsToFind, String val) {
        for (MethodList methodList : methodListsToFind) {
            if (val.equals(methodList.getFirstVal())) {
                Method firstRightMethod = methodList.getFirstRightMethod();
                if (firstRightMethod.getIsTerminale()) {
                    return firstRightMethod.getVal();
                } else {
                    return findNoterminaleGuideSet(methodListsToFind, firstRightMethod.getVal());
                }
            }
        }
        return null;
    }


    private String findExitSymbolGuideSet(MethodList methodListWithExitSym, ArrayList<MethodList> methodsArr, String val) {
        ArrayList<Method> methods = methodListWithExitSym.getMethodsRightPart();
        Method lastRightMethod = methodListWithExitSym.getLastRightMethod();
        String result = "NO";
        if (val.equals(lastRightMethod.getVal())) {
            String firstMethodVal = methodListWithExitSym.getFirstVal();
            for (MethodList methodList : methodsArr) {
                ArrayList<Method> methods1 = methodList.getMethodsRightPart();
                for (Method method : methods1) {
                    if (firstMethodVal.equals(method.getVal())) {
                        return findExitSymbolGuideSet(methodList, methodsArr, firstMethodVal);
                    }
                }
            }
        } else {
            int x = 0;
            for (int i = 0; i < methods.size(); i++) {
                if (val.equals(methods.get(i).getVal())) {
                    x = i;
                    break;
                }
            }
            return methods.get(x + 1).getGuideSet();
        }
        return result;
    }
}