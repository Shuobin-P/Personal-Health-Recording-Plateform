package com.google.personalhealthrecordingplateform;

import com.google.personalhealthrecordingplateform.util.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void test1() {
        TokenUtils tokenUtils = new TokenUtils();
        System.out.println("Fuck you Idiot");
        Map<String, Object> map = new HashMap<>();
        map.put("username", "ouUrt5PzNnDh8SiBBr_WyA3_myq0");
        map.put("created", new Date());
        String token = tokenUtils.generateToken(map);
        System.out.println(token);
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