package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {

    public StringSchema() {
        this.addCheck(v -> v == null || v instanceof String);
    }

    @Override
    public  StringSchema required() {
        this.addCheck(v -> v instanceof String string && !string.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        this.addCheck(v -> v instanceof String string && string.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        this.addCheck(v -> v != null && ((String) v).contains(substring));
        return this;
    }

}
