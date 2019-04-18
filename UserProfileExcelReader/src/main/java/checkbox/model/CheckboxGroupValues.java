package checkbox.model;

import java.util.ArrayList;
import java.util.List;

public class CheckboxGroupValues {
    public String checkboxGroupId;
    public List<String> values = new ArrayList<>();
    public List<Checkbox> checkeds = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s = %s", checkboxGroupId, values);
    }
}
