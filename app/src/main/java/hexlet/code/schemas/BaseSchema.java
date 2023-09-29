package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class BaseSchema {



    public BaseSchema required()  {
        this.addCheck(Objects::nonNull);
        return this;
    }

    protected final List<Predicate<Object>> checkList = new ArrayList<>();


    void addCheck(Predicate<Object> check) {

        checkList.add(check);
    }


    public boolean isValid(Object value) {
        for (var check : checkList) {
            if (!check.test(value)) {
                return false;
            }
        }
        return true;
    }
}
