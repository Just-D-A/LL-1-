package main.org.volgatech.table;

import main.org.volgatech.Globals.Globals;
import main.org.volgatech.table.domain.Method;
import main.org.volgatech.table.domain.MethodList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
                    methods.add(method);
                    x++;
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
        //если грамматика заканчивается нетерминалом тогда его значение NEXT = -1
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
                    if (!method.getVal().equals("@"))
                        method.setGuideSet(method.getVal());
                }
            }
        }

        //FOR @ SYM

        for (int i = methodsArr.size() - 1; i >= 0; i--) {
            MethodList methodListToChange = methodsArr.get(i);
            methodListToChange.setFirstGuideSet(methodListToChange.getMethodsRightPart().get(0).getVal());
            for (Method method : methodListToChange.getMethodsRightPart()) {
                if (method.getVal().equals("@")) {
                    ArrayList<String> guideSets = newGuideSetsForExitSym(methodsArr, methodListToChange.getFirstVal(), new ArrayList<>());
                    method.addGuideSets(guideSets);
                }
            }
        }

        for (
                MethodList methodListToChange : methodsArr) {
            Method firstRightMethod = methodListToChange.getFirstRightMethod();
            if (firstRightMethod.getVal().equals("@")) {
                Method firstMethod = methodListToChange.getFirstMethod();
                firstMethod.addGuideSets(firstRightMethod.getGuideSets());
                for(String str: firstRightMethod.getGuideSets()) {
                    System.out.println(str);
                }
            }
        }
        // END FOR @ SYM

        for (
                MethodList methodListToChange : methodsArr) {
            for (Method method : methodListToChange.getMethodsRightPart()) {
                if (!method.getIsTerminale()) {
                    if(method.getGuideSets().isEmpty()) {
                        method.addGuideSets(findNoterminalAllGuideSets(methodsArr, method.getVal()));
                    }
                }
            }
        }

        for (MethodList methodListToChange : methodsArr) {

            Method firstRightMethod = methodListToChange.getFirstRightMethod();
            Method firstMethod = methodListToChange.getFirstMethod();
            if (firstRightMethod.getIsTerminale()) {

                if(!firstRightMethod.getVal().equals("@")) {
                    firstMethod.addGuideSets(firstRightMethod.getGuideSets());
                } else {
                firstMethod.setGuideSet(firstRightMethod.getGuideSet());
                }
            } else {
                firstMethod.addGuideSets(firstRightMethod.getGuideSets());
            }
        }

   /*     for(MethodList methodList: methodsArr) {
            methodList.writeOut();
            System.out.println("_____");
        }*/

        //add table params
        //заполняем параметры таблицы в соответствии с тз
        for (
                MethodList methodListToChange : methodsArr) {
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
                if (firstRightMethod.getIsTerminale()) {
                    if (firstRightMethod.getVal().equals("@")) {
                        guideSetsOfNoterminale.addAll(firstRightMethod.getGuideSets());
                    } else {
                        guideSetsOfNoterminale.add(methodList.getGuideSet());
                    }
                } else {
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
                    System.out.println(" GRAMMAR RIGHT " + firstRightMethod.getVal());
                    return findNoterminaleGuideSet(methodListsToFind, firstRightMethod.getVal());
                }
            }
        }
        return null;
    }

    private ArrayList<String> newGuideSetsForExitSym(ArrayList<MethodList> methodArr, String val, ArrayList<String> store) {
        //   System.out.println("FOR " + val);
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> valResult = new ArrayList<>();
        for (String storeEl : store) {
            if (storeEl.equals(val)) {
                return result;
            }
        }
        store.add(val);
        //    System.out.println("_____IN FOR__________");
        for (MethodList curMethodList : methodArr) {
            ArrayList<Method> rightMethods = curMethodList.getMethodsRightPart();
            for (Method method : rightMethods) {
                if (method.getVal().equals(val)) {
                    //   System.out.println( "I FIND IN CurFirst: " + curMethodList.getFirstVal());
                    if (val.equals(curMethodList.getLastRightMethod().getVal())) {
                        valResult.add(curMethodList.getFirstVal());
                    } else {
                        for (int i = 0; i < rightMethods.size(); i++) {
                            if (rightMethods.get(i).getVal().equals(val)) {
                                Method nextMethod = rightMethods.get(i + 1);
                                if (nextMethod.getIsTerminale()) {
                                    result.add(nextMethod.getVal());
                                } else {
                                    nextMethod.addGuideSets(findNoterminalAllGuideSets(methodArr, nextMethod.getVal()));
                                    result.addAll(nextMethod.getGuideSets());
                                    for (String str : nextMethod.getGuideSets()) {
                                        System.out.println("Added 2 " + str + " to num " + rightMethods.get(i).getNum());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (String vals : valResult) {
            ArrayList<String> added = newGuideSetsForExitSym(methodArr, vals, store);
            result.addAll(added);
        }
        if(result.isEmpty()) {
            result.add(Globals.END_GUIDE_SET_VAL);
        }
        Set<String> set = new HashSet<>(result);
        result.clear();
        result.addAll(set);
        return result;

    }


}