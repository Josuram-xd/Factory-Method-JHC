package com.globaldocs.extraction;

import com.globaldocs.model.DocumentFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

/** Extrae el contenido de hojas .xlsx (celda a celda) usando Apache POI. */
@Component
public class ExcelContentExtractor implements ContentExtractor {

    @Override
    public Set<DocumentFormat> supportedFormats() {
        return EnumSet.of(DocumentFormat.XLSX);
    }

    @Override
    public String extract(MultipartFile file) throws IOException {
        StringBuilder text = new StringBuilder();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            for (Sheet sheet : workbook) {
                text.append("# ").append(sheet.getSheetName()).append('\n');
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        text.append(cellToString(cell)).append('\t');
                    }
                    text.append('\n');
                }
            }
        }
        return text.toString();
    }

    private String cellToString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }
}
