package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {
    private Integer minLength;
    private String contains;

    public NumberSchema() {
        this.addCheck(v -> v == null || v instanceof Number);
    }

    @Override
    public NumberSchema required() {
        super.required();
        return null;
    }

    public NumberSchema positive() {
        this.addCheck(v -> (v == null) || ((int) v) > 0);
        return this;
    }

    public NumberSchema range(int begin, int end) {
        this.addCheck(v -> v == null || ((int) v) >= begin && ((int) v) <= end);
        return this;
    }

}
