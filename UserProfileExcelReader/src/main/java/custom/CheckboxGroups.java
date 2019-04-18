package custom;

import checkbox.model.Checkbox;
import checkbox.model.CheckboxGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 在数组和列表的顺序对应要解析的excel中的选项组列的顺序，选项列顺序
 */
public class CheckboxGroups {
    public static List<CheckboxGroup> checkboxGroups = new ArrayList<>();
    public static CheckboxGroup[] ordered = null;
    static {
        CheckboxGroup g;
        g = new CheckboxGroup();
        g.label = "年龄段";
        g.id = "1";
        g.multiple = false;
        g.add(new Checkbox("1"));
        g.add(new Checkbox("2"));
        g.add(new Checkbox("3"));
        checkboxGroups.add(g);

        g = new CheckboxGroup();
        g.label = "身体状况";
        g.id = "2";
        g.multiple = false;
        g.add(new Checkbox("3"));
        g.add(new Checkbox("2"));
        g.add(new Checkbox("1"));
        checkboxGroups.add(g);

        g = new CheckboxGroup();
        g.label = "居住状况";
        g.id = "4";
        g.multiple = false;
        g.add(new Checkbox("4"));
        g.add(new Checkbox("6"));
        g.add(new Checkbox("2"));
        checkboxGroups.add(g);

        g = new CheckboxGroup();
        g.label = "经济状况";
        g.id = "6";
        g.multiple = false;
        g.add(new Checkbox("4"));
        g.add(new Checkbox("3"));
        g.add(new Checkbox("2"));
        checkboxGroups.add(g);

        g = new CheckboxGroup();
        g.label = "养老服务需求";
        g.id = "7";
        g.add(new Checkbox("2", "助餐"));
        g.add(new Checkbox("3", "助洁"));
        g.add(new Checkbox("4", "助医"));
        g.add(new Checkbox("7", "上门照护"));
        checkboxGroups.add(g);

        ordered = checkboxGroups.toArray(new CheckboxGroup[0]);
    }
}
