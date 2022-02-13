package ru.clevertec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import ru.clevertec.data.Cat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonUtilsTest {

    @Test
    public void testGetJson() {
        Gson gson = new GsonBuilder().create();
        Cat cat = new Cat();

        assertEquals(gson.toJson(cat), JsonUtils.getJson(cat));
    }
}
