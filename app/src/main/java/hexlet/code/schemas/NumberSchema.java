package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {

    public NumberSchema() {
        this.addCheck(v -> v == null || v instanceof Number);
    }

    @Override
    public NumberSchema required() {
        super.required();
        return this;
    }

    public NumberSchema positive() {
        this.addCheck(v -> (v == null) || ((int) v) > 0);
        return this;
    }

    public NumberSchema range(int begin, int end) {
        this.addCheck(v -> ((int) v) >= begin && ((int) v) <= end);
        return this;
    }

}
