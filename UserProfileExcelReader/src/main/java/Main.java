import checkbox.model.Checkbox;
import custom.CheckboxGroups;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Count {
    int n;

    public int inc() {
        return n++;
    }
}

public class Main {

    public static void main(String[] args) throws Exception {
        File dir = new File("/Users/hulang/Downloads/0418名单");
        File[] files = dir.listFiles();
        System.out.println("社区\t\t\t\t老人数量\t\t\t服务需求情况");
        System.out.printf("  \t\t\t    %32s\n",
            CheckboxGroups.ordered[4].checkboxes.stream().map(c -> c.label).collect(Collectors.joining("\t\t")));

        List<UserInfo> allUserInfos = new ArrayList<>();
        int userTotal = 0;
        for (File file : files) {
            if (file.getName().endsWith(".xls")) {
                UserInfoSheet userInfoSheet = Reader.readUserInfoSheet(file);
                allUserInfos.addAll(userInfoSheet.users);
                String orgName = userInfoSheet.fileName.split("-")[1];
                Map<String, Count> checkedCounts = new HashMap<>();
                for (Checkbox checkbox : CheckboxGroups.ordered[4].checkboxes) {
                    checkedCounts.put(checkbox.value, new Count());
                }
                for (UserInfo user : userInfoSheet.users) {
                    for (Checkbox checkbox : user.values.get(4).checkeds) {
                        checkedCounts.get(checkbox.value).inc();
                    }
                    user.orgName = orgName;
                }
                System.out.printf("%-4s\t\t%6d\t\t\t", orgName, userInfoSheet.users.size());
                System.out.print(checkedCounts.values().stream().map(c -> String.valueOf(c.n)).collect(Collectors.joining("\t\t")));
                System.out.println();

                userTotal += userInfoSheet.users.size();
            }
        }
        System.out.printf("%-4s\t\t%6d\n", "累计", userTotal);

        new Writer().write(allUserInfos);
    }
}
