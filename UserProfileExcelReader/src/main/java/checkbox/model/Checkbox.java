package checkbox.model;

public class Checkbox {
    public String value;
    public String label;

    public Checkbox() { }
    public Checkbox(String value) {
        this.value = value;
    }
    public Checkbox(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
