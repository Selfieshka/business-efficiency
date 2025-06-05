package com.technokratos.kirillakhmetov.util;

import com.technokratos.kirillakhmetov.dto.ProductDto;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@UtilityClass
public final class ExcelReader {
    public static List<ProductDto> readAllProductsByColumns(InputStream excelInputStream, Map<String, String> headerNames) {
        try (Workbook workbook = new XSSFWorkbook(excelInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<ProductDto> products = new ArrayList<>();

            Map<String, Integer> columnIndexes = getColumnIndexes(sheet.getRow(0), headerNames);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String productNameValue = getCellValue(row, columnIndexes.get(headerNames.get("productName")), CellType.STRING);
                String unitMeasureValue = getCellValue(row, columnIndexes.get(headerNames.get("unitMeasure")), CellType.STRING);
                Double quantityValue = getCellValue(row, columnIndexes.get(headerNames.get("quantity")), CellType.NUMERIC);
                Double costPerUnitValue = getCellValue(row, columnIndexes.get(headerNames.get("costPerUnit")), CellType.NUMERIC);
                if (productNameValue != null && unitMeasureValue != null && quantityValue != null && costPerUnitValue != null) {
                    products.add(new ProductDto(
                            productNameValue,
                            unitMeasureValue,
                            quantityValue.intValue(),
                            costPerUnitValue));
                }
            }
            return products;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Integer> getColumnIndexes(Row headerRow, Map<String, String> headerNames) {
        Map<String, Integer> columnIndices = new HashMap<>();
        for (int j = 1; j < headerRow.getLastCellNum(); j++) {
            Cell cell = headerRow.getCell(j);
            if (cell != null && cell.getCellType() == CellType.STRING) {
                String headerValue = cell.getStringCellValue();
                for (String header : headerNames.values()) {
                    if (headerValue.equalsIgnoreCase(String.valueOf(header))) {
                        columnIndices.put(header, j);
                    }
                }
            }
        }
        return columnIndices;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getCellValue(Row row, Integer columnIndex, CellType expectedType) {
        if (columnIndex != null && columnIndex >= 0) {
            Cell cell = row.getCell(columnIndex);
            if (cell != null) {
                if (expectedType == CellType.STRING && cell.getCellType() == CellType.STRING) {
                    return (T) cell.getStringCellValue();
                } else if ((expectedType == CellType.NUMERIC) &&
                        (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA)) {
                    return (T) Double.valueOf(cell.getNumericCellValue());
                }
            }
        }
        return null;
    }
}
