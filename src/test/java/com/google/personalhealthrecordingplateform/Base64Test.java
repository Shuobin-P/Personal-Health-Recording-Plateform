package com.google.personalhealthrecordingplateform;

import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Test;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/1/10 20:23
 */
public class Base64Test {
    @Test
    public void test() {
        //zA7NeCLVINHsmxHUgoVuuQ==
        //ouUrt5PzNnDh8SiBBr_WyA3_myq0 我
        Base64.decode("zA7NeCLVINHsmxHUgoVuuQ==");
        Base64.decode("ouUrt5PzNnDh8SiBBr_WyA3_myq0");
    }
}
