
import checkbox.model.Checkbox;
import checkbox.model.CheckboxGroup;
import checkbox.model.CheckboxGroupValues;
import com.sun.deploy.util.StringUtils;
import custom.CheckboxGroups;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.rmi.server.ExportException;

public class Reader {
    public static UserInfoSheet readUserInfoSheet( File xlsFile) throws Exception {
        UserInfoSheet userInfoSheet = new UserInfoSheet();
        userInfoSheet.fileName = xlsFile.getName();
        Workbook workbook = WorkbookFactory.create(xlsFile);
        Sheet sheet = workbook.getSheetAt(0);
        int dataBeginRowIndex = 5;
        int dataRowNum = sheet.getLastRowNum() + 2;
        for (int r = dataBeginRowIndex; r < dataRowNum; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                break;
            }
            UserInfo userInfo = new UserInfo();
            Cell cell = row.getCell(1);
            if (cell == null) {
                break;
            }
            userInfo.userName = cell.getStringCellValue().replaceAll(" ", "").trim();
            if (userInfo.userName == "") {
                break;
            }
            userInfo.gender = row.getCell(2).getStringCellValue().trim();
            userInfo.idcard = row.getCell(3).getStringCellValue().trim();
            userInfoSheet.users.add(userInfo);

            // 开始读checkbox列
            int nowColIndexInRow = 4;
            for (int g = 0; g < CheckboxGroups.ordered.length; g++) {
                CheckboxGroup checkboxGroup = CheckboxGroups.ordered[g];
                CheckboxGroupValues groupValues = new CheckboxGroupValues();
                groupValues.checkboxGroupId = checkboxGroup.id;
                int checkboxCount = checkboxGroup.checkboxes.size();
                for (int checkboxIndexInGroup = 0; checkboxIndexInGroup < checkboxCount; checkboxIndexInGroup++) {
                    boolean checked = false;
                    Cell checkableCell = row.getCell(nowColIndexInRow + checkboxIndexInGroup);
                    if (checkableCell != null) {
                        CellType cellType = checkableCell.getCellTypeEnum();
                        switch (cellType) {
                            case NUMERIC:
                                checked = true;
                                break;
                            case BLANK:
                                checked = false;
                                break;
                            case STRING:
                                checked = !checkableCell.getStringCellValue().trim().isEmpty();
                                break;
                        }
                    }
                    if (checked) {
                        Checkbox checkbox = checkboxGroup.checkboxes.get(checkboxIndexInGroup);
                        groupValues.checkeds.add(checkbox);
                        groupValues.values.add(checkbox.value);
                        if (!checkboxGroup.multiple) {
                            break;
                        }
                    }
                }
                nowColIndexInRow += checkboxCount;
                userInfo.values.add(groupValues);
            }
            //System.out.printf("%s: %s\n", userInfo.userName, userInfo.values);

            cell = row.getCell(nowColIndexInRow);
            if (cell != null) {
                if (!cell.getCellTypeEnum().equals(CellType.BLANK) ||
                    (cell.getCellTypeEnum().equals(CellType.STRING) && !cell.getStringCellValue().trim().isEmpty())) {
                    userInfo.subAddress = cell.getStringCellValue();
                }
                cell = row.getCell(nowColIndexInRow + 1);
                if (cell != null) {
                    if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                        userInfo.extend1 = cell.getStringCellValue();
                    }
                    if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                        userInfo.extend1 = String.valueOf(cell.getNumericCellValue());
                    }
                }
            }
        }

        return userInfoSheet;
    }
}
