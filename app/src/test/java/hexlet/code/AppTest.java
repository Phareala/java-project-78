package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private Validator v;
    @BeforeEach
    public void start() {
        v = new Validator();
    }
    @Test
    void testStringValidator() {
        StringSchema schema = v.string();

        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));

        schema.required();

        assertTrue(schema.isValid("what does the fox say"));
        assertTrue(schema.isValid("hexlet"));

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(5));
        assertFalse(schema.isValid(""));

        assertTrue(schema.contains("wh").isValid("what does the fox say"));
        assertTrue(schema.contains("what").isValid("what does the fox say"));
        assertTrue(schema.minLength(5).isValid("what does the fox say"));

        assertFalse(schema.contains("whatthe").isValid("what does the fox say"));
        assertFalse(schema.isValid("what does the fox say"));
        assertFalse(schema.minLength(10).isValid("whatthe"));
    }

    @Test
    void testNumberValidator()  {
        NumberSchema schema = v.number();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(77));
        assertTrue(schema.isValid(-77));
        assertTrue(schema.isValid(0));
        assertFalse(schema.isValid("Hexlet"));

        schema.positive();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(77));
        assertFalse(schema.isValid(-77));
        assertFalse(schema.isValid(0));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(77));
        assertFalse(schema.isValid(-77));
        assertFalse(schema.isValid(0));

        schema.range(50, 100);

        assertTrue(schema.isValid(77));
        assertTrue(schema.isValid(50));
        assertTrue(schema.isValid(100));
        assertFalse(schema.isValid(25));
        assertFalse(schema.isValid(125));
    }

    @Test
    void testMapValidator()  {
        MapSchema schema = v.map();

        assertTrue(schema.isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data));

        schema.sizeof(2);
        assertFalse(schema.isValid(data));
        data.put("key2", "value2");
        assertTrue(schema.isValid(data));
        assertFalse(schema.sizeof(15).isValid(data));
    }

    @Test
    void testShapeMapValidator()  {
        MapSchema schema = v.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);
        assertThat(schema.isValid(human1)).isTrue();

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertThat(schema.isValid(human2)).isTrue();

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertThat(schema.isValid(human3)).isFalse();

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", -5);
        assertThat(schema.isValid(human4)).isFalse();
    }
}
