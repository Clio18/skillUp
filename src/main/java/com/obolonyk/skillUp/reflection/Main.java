package com.obolonyk.skillUp.reflection;

import java.lang.reflect.*;
import java.util.*;

public class Main {

    //Метод принимает класс и возвращает созданный объект этого класса
    public static <T> T giveTheObjectFromTheClass(Class<T> clazz) throws ReflectiveOperationException {
        Class<?>[] allFieldsClasses = getAllFieldsClasses(Ford.class);
        Object[] defaults = getDefaultValuesForFields(allFieldsClasses);
        return clazz.getDeclaredConstructor(allFieldsClasses).newInstance(defaults);
    }

    public static <T> T giveTheObjectFromTheClassWithDefaultConstructor(Class<T> clazz)
            throws ReflectiveOperationException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    //Метод принимает object и вызывает у него все методы без параметров
    public static Map<String, Object> giveTheAllMethodsWithoutParameters(Object object) throws ReflectiveOperationException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getParameters().length == 0) {
                method.setAccessible(true);
                Object result = method.invoke(object);
                map.put(method.getName(), result);
            }
        }
        return map;
    }

    //Метод принимает object и выводит на экран все сигнатуры методов в который есть final
    public static List<String> giveTheAllFinalMethodsSignatures(Object object) {
        List<String> list = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            StringBuilder methodSignature = new StringBuilder();
            method.setAccessible(true);
            if (Modifier.isFinal(method.getModifiers())) {
                String name = method.getName();
                methodSignature.append(name);
                methodSignature.append(": ");
                StringJoiner stringJoiner = new StringJoiner(", ", "", "");
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    StringBuilder pair = new StringBuilder();
                    pair.append(parameter.getType().toString());
                    pair.append(" -> ");
                    pair.append(parameter.getName());
                    stringJoiner.add(pair.toString());
                }
                String methodParameters = stringJoiner.toString();
                methodSignature.append(methodParameters);
                list.add(methodSignature.toString());
            }
        }
        return list;
    }

    public void giveTheAllFinalMethodsSignaturesAndShowIt(Object object) {
        List<String> methodsSignatures = giveTheAllFinalMethodsSignatures(object);
        for (String methodsSignature : methodsSignatures) {
            System.out.println(methodsSignature);
        }
    }

    //Метод принимает Class и выводит все не публичные методы этого класса
    public static List<Method> giveTheAllNotPublicMethods(Class clazz) throws ReflectiveOperationException {
        List<Method> list = new ArrayList<>();
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            if (!Modifier.isPublic(method.getModifiers())) {
                if (method.getParameters().length == 0) {
                    method.invoke(instance);
                } else {
                    Parameter[] parameters = method.getParameters();
                    for (Parameter parameter : parameters) {
                        if (parameter.getType().equals(String.class)) {
                            method.invoke(instance, "Hi");
                        }
                    }
                }
                list.add(method);
            }
        }
        return list;
    }

    //Метод принимает Class и выводит всех предков класса и все интерфейсы которое класс имплементирует
    public static List<String> getAllAncestorClassesAndInterfaces(Class clazz) {
        List<String> list = new ArrayList<>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            if (!c.getSimpleName().equals(clazz.getSimpleName())) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Class name: ");
                stringBuilder.append(c.getName());
                list.add(stringBuilder.toString());
            }
        }
        Class[] interfaces = clazz.getInterfaces();
        for (Class anInterface : interfaces) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Interface name: ");
            stringBuilder.append(anInterface.getName());
            list.add(stringBuilder.toString());
        }
        return list;
    }


    private static Class<?>[] getAllFieldsClasses(Class<?> type) {
        List<Field> gatheredFields = getFields(type);
        Field[] array = convertListToArray(gatheredFields, Field.class);
        Class<?>[] classes = new Class[array.length];
        for (int i = 0; i < array.length; i++) {
            classes[i] = array[i].getType();
        }
        return classes;
    }

    private static <T> T[] convertListToArray(List<T> list, Class<T> clazz) {
        T[] array = (T[]) Array.newInstance(clazz, list.size());
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        list.toArray(array);
        return array;
    }

    private static List<Field> getFields(Class<?> type) {
        List<Field[]> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.add(c.getDeclaredFields());
        }

        Collections.reverse(fields);
        List<Field> gatheredFields = new ArrayList<>();
        for (Field[] fieldGroup : fields) {
            for (Field field : fieldGroup) {
                gatheredFields.add(field);
            }
        }
        return gatheredFields;
    }

    //Метод принимает объект и меняет всего его приватные поля на их нулевые значение (null, 0, false etc)
    public static <T> T getObjectWithDefaultValuesOfFields(T t) throws IllegalAccessException {
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (Modifier.isPrivate(field.getModifiers())) {
                if (field.getType().equals(int.class)) {
                    field.set(t, 0);
                } else if (field.getType().equals(double.class)) {
                    field.set(t, 0.0);
                } else if (field.getType().equals(String.class)) {
                    field.set(t, null);
                } else if (field.getType().equals(boolean.class)) {
                    field.set(t, false);
                }
            }
        }
        return t;
    }

    private static Object[] getDefaultValuesForFields(Class<?>[] allFieldsClasses) {
        List<Object> defaultValues = new ArrayList<>();
        for (Class<?> fieldsClass : allFieldsClasses) {
            if (fieldsClass.equals(int.class)) {
                defaultValues.add(0);
            } else if (fieldsClass.equals(double.class)) {
                defaultValues.add(0.0);
            } else if (fieldsClass.equals(String.class)) {
                defaultValues.add(null);
            } else if (fieldsClass.equals(boolean.class)) {
                defaultValues.add(false);
            }
        }
        return convertListToArray(defaultValues, Object.class);
    }


}
