package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class BaseSchema {


    /**
     * @return boolean nonNull Objects
     */
    protected BaseSchema required()  {
        this.addCheck(Objects::nonNull);
        return this;
    }

    private final List<Predicate<Object>> checkList = new ArrayList<>();


    /**
     * @param check Object
     */
    protected void addCheck(Predicate<Object> check) {

        checkList.add(check);
    }


    /**
     * @param value Object
     * @return false
     */
    public boolean isValid(Object value) {
        for (var check : checkList) {
            if (!check.test(value)) {
                return false;
            }
        }
        return true;
    }

}
