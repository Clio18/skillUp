package com.obolonyk.skillUp.reflection;

import com.obolonyk.skillUp.reflection.entity.Ford;
import com.obolonyk.skillUp.reflection.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    Ford fordWithDefaultValueOfTheField;
    Ford fordWithDefaultConstructor;
    Util util;


    @BeforeEach
    void init() {
        fordWithDefaultValueOfTheField = new Ford(0, 0.0, null, false, 0);
        fordWithDefaultConstructor = new Ford();
        util = new Util();
    }

    @Test
    @DisplayName("Test Give The Object From The Class And Check The Class")
    void testGiveTheObjectFromTheClassAndCheckTheClass() throws ReflectiveOperationException {
        Ford objectFromTheClass = Main.giveTheObjectFromTheClass(Ford.class);
        assertEquals(fordWithDefaultValueOfTheField.getClass(), objectFromTheClass.getClass());
    }

    @Test
    @DisplayName("Test Give The Object From The Class And Check Equivalence Of Field Value")
    void testGiveTheObjectFromTheClassAndCheckEquivalenceOfFieldValue() throws ReflectiveOperationException {
        Ford objectFromTheClass = Main.giveTheObjectFromTheClass(Ford.class);
        assertEquals(fordWithDefaultValueOfTheField.getType(), objectFromTheClass.getType());
        assertEquals(fordWithDefaultValueOfTheField.getWeight(), objectFromTheClass.getWeight());
        assertEquals(fordWithDefaultValueOfTheField.getWheels(), objectFromTheClass.getWheels());
        assertEquals(fordWithDefaultValueOfTheField.getPassengers(), objectFromTheClass.getPassengers());
        assertEquals(fordWithDefaultValueOfTheField.isRunAndDrive(), objectFromTheClass.isRunAndDrive());
    }

    @Test
    @DisplayName("Test Give The Object From The Class And Check The Equivalence")
    void testGiveTheObjectFromTheClassAndCheckTheEquivalence() throws ReflectiveOperationException {
        Ford objectFromTheClass = Main.giveTheObjectFromTheClass(Ford.class);
        assertTrue(objectFromTheClass.equals(fordWithDefaultValueOfTheField));
    }

    @Test
    @DisplayName("Test Give The Object From The Class With Default Constructor And Check The Class")
    void testGiveTheObjectFromTheClassWithDefaultConstructorAndCheckTheClass() throws ReflectiveOperationException {
        Ford objectFromTheClass = Main.giveTheObjectFromTheClassWithDefaultConstructor(Ford.class);
        assertEquals(fordWithDefaultConstructor.getClass(), objectFromTheClass.getClass());
    }

    @Test
    @DisplayName("Test Give The Object From The Class With Default Constructor And Check The Equivalence")
    void testGiveTheObjectFromTheClassWithDefaultConstructorAndCheckTheEquivalence() throws ReflectiveOperationException {
        Ford objectFromTheClass = Main.giveTheObjectFromTheClassWithDefaultConstructor(Ford.class);
        assertTrue(objectFromTheClass.equals(fordWithDefaultConstructor));
    }


    @Test
    @DisplayName("Test The All Methods Without Parameters And Check The Amount Of Such Methods")
    void testTheAllMethodsWithoutParametersAndCheckTheAmountOfSuchMethods() throws ReflectiveOperationException {
        Map<String, Object> map = Main.giveTheAllMethodsWithoutParameters(util);
        assertEquals(3, map.size());
    }

    @Test
    @DisplayName("Test Get The All Methods Without Parameters And Check The Returned Value")
    void testGetTheAllMethodsWithoutParametersAndCheckTheReturnedValue() throws ReflectiveOperationException {
        Map<String, Object> map = Main.giveTheAllMethodsWithoutParameters(util);
        double distance = Util.getTheDistance();
        String license = Util.keepMyLicense();
        assertEquals(distance, map.get("getTheDistance"));
        assertEquals(license, map.get("keepMyLicense"));
    }

    @Test
    @DisplayName("Test Get The All Final Methods Signatures And Check The Size Of The List")
    void testGetTheAllFinalMethodsSignaturesAndCheckTheSizeOfTheList() {
        List<String> methodsSignatures = Main.giveTheAllFinalMethodsSignatures(util);
        assertEquals(2, methodsSignatures.size());
    }

    @Test
    @DisplayName("Test The All Not Public Methods And Check The Size Of The List")
    void testTheAllNotPublicMethodsAndCheckTheSizeOfTheList() throws ReflectiveOperationException {
        List<Method> methods = Main.giveTheAllNotPublicMethods(Util.class);
        assertEquals(3, methods.size());
    }

    @Test
    @DisplayName("Test Get All Ancestor Classes And Interfaces And Check List Size")
    void testGetAllAncestorClassesAndInterfacesAndCheckListSize() {
        List<String> list = Main.getAllAncestorClassesAndInterfaces(Ford.class);
        assertEquals(4, list.size());
    }

    @Test
    @DisplayName("Test Get Default Values For Fields And Check Object Has Changed")
    void testGetDefaultValuesForFieldsAndCheckObjectHasChanged() throws IllegalAccessException {
        Ford ford = new Ford("Race", true, 10);
        Ford object = Main.getObjectWithDefaultValuesOfFields(ford);
        assertEquals(null, object.getType());
        assertEquals(false, object.isRunAndDrive());
        assertEquals(0, object.getWheels());
    }
}