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


    /**
     */
    @BeforeEach
    public void start() {
        v = new Validator();
    }
    public NumberSchema numberSchema;

    @Test
    void testStringValidator1() {
        StringSchema stringSchema = v.string();

        assertTrue(stringSchema.isValid(""));
        assertTrue(stringSchema.isValid(null));

        stringSchema.required();

        assertTrue(stringSchema.isValid("what does the fox say"));
        assertTrue(stringSchema.isValid("hexlet"));

        assertFalse(stringSchema.isValid(null));
        assertFalse(stringSchema.isValid(5));
        assertFalse(stringSchema.isValid(""));
    }
    @Test
    void testStringValidator2() {
        StringSchema stringSchema = v.string();
        assertTrue(stringSchema.contains("wh").isValid(null));
        assertTrue(stringSchema.contains("wh").isValid("what does the fox say"));
        assertTrue(stringSchema.contains("what").isValid(null));
        assertTrue(stringSchema.contains("what").isValid("what does the fox say"));
        assertTrue(stringSchema.minLength(5).isValid(null));
        assertTrue(stringSchema.minLength(5).isValid("what does the fox say"));
        assertFalse(stringSchema.contains("whatthe").isValid("what does the fox say"));
        assertFalse(stringSchema.isValid("what does the fox say"));
        assertFalse(stringSchema.minLength(10).isValid("whatthe"));
    }
    @Test
    void testStringValidatorNull() {
        StringSchema stringSchema = v.string();
        assertTrue(stringSchema.contains("wh").isValid(null));
        assertTrue(stringSchema.contains("what").isValid(null));
        assertTrue(stringSchema.minLength(5).isValid(null));
        assertTrue(stringSchema.contains("whatthe").isValid(null));
        assertFalse(stringSchema.required().isValid(null));
    }

    @Test
    void testNumberValidator() {
        numberSchema = v.number();
        assertTrue(numberSchema.isValid(null));
        assertTrue(numberSchema.isValid(77));
        assertTrue(numberSchema.isValid(-77));
        assertTrue(numberSchema.isValid(0));
        assertFalse(numberSchema.isValid("Hexlet"));
    }
    @Test
    void testNumberValidatorPositive() {
        numberSchema = v.number();
        numberSchema.positive();

        assertTrue(numberSchema.isValid(null));
        assertTrue(numberSchema.isValid(77));
        assertFalse(numberSchema.isValid(-77));
        assertFalse(numberSchema.isValid(0));
    }
    @Test
    void testNumberValidatorRequired() {
        numberSchema = v.number();
        numberSchema.required();

        assertFalse(numberSchema.isValid(null));
        assertTrue(numberSchema.isValid(77));
        assertTrue(numberSchema.isValid(-77));
        assertTrue(numberSchema.isValid(0));
    }
    @Test
    void testNumberValidatorRange()  {
        numberSchema = v.number();
        numberSchema.range(50, 100);

        assertTrue(numberSchema.isValid(null));
        assertTrue(numberSchema.isValid(77));
        assertTrue(numberSchema.isValid(50));
        assertTrue(numberSchema.isValid(100));
        assertFalse(numberSchema.isValid(25));
        assertFalse(numberSchema.isValid(125));
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
    void testShapeMapValidator() {
        MapSchema shapeMapSchema = v.map();
        Map<String, BaseSchema> schemas = new HashMap<>();

        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());


        shapeMapSchema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);
        assertThat(shapeMapSchema.isValid(human1)).isTrue();

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertThat(shapeMapSchema.isValid(human2)).isTrue();

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertThat(shapeMapSchema.isValid(human3)).isFalse();

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", -5);
        assertThat(shapeMapSchema.isValid(human4)).isFalse();
    }
}
