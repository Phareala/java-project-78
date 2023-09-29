package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema {

    public MapSchema() {
        this.addCheck(v -> v == null || v instanceof Map);
    }

    @Override
    public MapSchema required() {
        super.required();
        return this;
    }

    public MapSchema sizeof(int size) {
        this.addCheck(v -> ((Map<?, ?>) v).size() == size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        for (var pair : schemas.entrySet()) {
            String key = pair.getKey();
            BaseSchema schema = pair.getValue();
            this.addCheck(v -> (schema.isValid(((Map<?, ?>) v).get(key))));
        }
        return this;
    }

}
