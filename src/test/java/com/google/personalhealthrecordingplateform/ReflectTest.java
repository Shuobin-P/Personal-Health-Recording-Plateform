package com.google.personalhealthrecordingplateform;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/16 9:11
 */
public class ReflectTest {
    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<BlackPeople> cl = BlackPeople.class;
        //访问构造方法
        BlackPeople blackPeople = cl.getDeclaredConstructor(int.class).newInstance(23);
        System.out.println(blackPeople.getSkiColor());
        blackPeople.setSkiColor(3);
        //访问字段
        Field field = cl.getField("skiColor");
        System.out.println(blackPeople.getSkiColor());
        field.set(blackPeople, 2);
        System.out.println(blackPeople.getSkiColor());
        //访问方法
        cl.getMethod("setSkiColor", int.class).invoke(blackPeople, 2222);
        System.out.println(blackPeople.getSkiColor());

    }
}

class People {
    int age;
    int sex;

    People() {
        System.out.println("People类的无参构造");
    }
}

class BlackPeople extends People {
    public static int skiColor = 0;

    public BlackPeople() {

    }

    public BlackPeople(int skiColor) {
        this.skiColor = skiColor;
    }

    protected void setSkiColor(int skiColor) {
        this.skiColor = skiColor;
    }

    public int getSkiColor() {
        return this.skiColor;
    }
}