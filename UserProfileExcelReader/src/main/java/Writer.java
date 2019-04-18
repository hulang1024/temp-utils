import checkbox.model.CheckboxGroupValues;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class Writer {
    public void write(List<UserInfo> userInfos) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet userBasicSheet = workbook.createSheet("用户基本信息");
        int rowIndex = 0;
        HSSFRow row;
        row =  userBasicSheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("性别");
        row.createCell(2).setCellValue("身份证");
        row.createCell(3).setCellValue("社区");
        row.createCell(4).setCellValue("附加地址");
        row.createCell(5).setCellValue("原表格最后一列");
        for (UserInfo user : userInfos) {
            row =  userBasicSheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(user.userName);
            row.createCell(1).setCellValue(user.gender);
            row.createCell(2).setCellValue(user.idcard);
            row.createCell(3).setCellValue(user.orgName);
            row.createCell(4).setCellValue(user.subAddress);
            row.createCell(5).setCellValue(user.extend1);
        }

        HSSFSheet attrSheet = workbook.createSheet("用户属性信息");
        rowIndex = 0;
        row =  attrSheet.createRow(rowIndex++);
        row.createCell(0).setCellValue("身份证");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("user_profile.item_key");
        row.createCell(3).setCellValue("user_profile.item_value");

        for (UserInfo user : userInfos) {
            for (CheckboxGroupValues values : user.values) {
                if (!values.values.isEmpty()) {
                    row = attrSheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(user.idcard);
                    row.createCell(1).setCellValue(user.userName);
                    row.createCell(2).setCellValue(values.checkboxGroupId);
                    row.createCell(3).setCellValue(values.values.stream().collect(Collectors.joining(",")));
                }
            }
        }


        File outputFile = new File("整理输出.xls");
        FileOutputStream fos = new FileOutputStream(outputFile);
        workbook.write(fos);
    }
}
