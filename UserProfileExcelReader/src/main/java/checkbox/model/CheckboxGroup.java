package checkbox.model;

import java.util.ArrayList;
import java.util.List;

public class CheckboxGroup {
    public String id;
    public String label;
    public boolean multiple = true;
    public List<Checkbox> checkboxes = new ArrayList<>();

    public void add(Checkbox checkbox) {
        checkboxes.add(checkbox);
    }
}
