package eric.clapton.infrastructure.util;

import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class POIUtils {
    protected POIUtils() {

    }

    private static final String TRUE_STRING = "true";
    private static final String FALSE_STRING = "false";
    private static final CellValue EMPTY_STRING_CELL_VALUE = new CellValue("");

    /**
     * 冻结工作表首行。
     * 
     * @param sheet
     *            要冻结首行的工作表。
     */
    public static final void freezeTopRow(Sheet sheet) {
        freezeTopNRows(sheet, 1);
    }

    /**
     * 冻结工作表前 N 行。
     * 
     * @param sheet
     * @param n
     */
    public static final void freezeTopNRows(Sheet sheet, int n) {
        sheet.createFreezePane(0, n, 0, n);
    }

    /**
     * 判断指定的行是否为空行，即：该行的所有的单元格都是空的。
     * 
     * @param row
     *            要判断是否为空行的 Excel 行。
     * @return
     */
    public static boolean isEmpty(Row row) {
        for (Cell cell : row) {
            int cellType = cell.getCellType();
            if (cellType != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isEmpty(Cell cell) {
        return cell.getCellType() == Cell.CELL_TYPE_BLANK;
    }

    public static final boolean isNullOrEmpty(Cell cell) {
        return cell == null || isEmpty(cell);
    }

    public static CellValue getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        int cellType = cell.getCellType();

        switch (cellType) {
            case Cell.CELL_TYPE_FORMULA:
                return evaluateFormulaCell(cell);
            case Cell.CELL_TYPE_BLANK:
                return EMPTY_STRING_CELL_VALUE;
            case Cell.CELL_TYPE_STRING:
                return new CellValue(cell.getStringCellValue());
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? CellValue.TRUE : CellValue.FALSE;
            case Cell.CELL_TYPE_ERROR:
                return CellValue.getError(cell.getErrorCellValue());
            case Cell.CELL_TYPE_NUMERIC:
                return new CellValue(cell.getNumericCellValue());
            default:
                throw new IllegalStateException("Unknown cell type.");
        }
    }

    public static final String getStringCellValue(Cell cell) {
        return getStringCellValue(cell, false);
    }

    public static final String getStringCellValue(Cell cell, boolean trim) {
        return StringUtils.makeSafe(getStringCellValue(getCellValue(cell)), trim);
    }

    public static final String getStringCellValueAndTrim(Cell cell) {
        return getStringCellValue(cell, true);
    }

    public static String getStringCellValue(CellValue value) {
        if (value == null) {
            return null;
        }
        switch (value.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                return String.valueOf(value.getNumberValue());
            case Cell.CELL_TYPE_STRING:
                return value.getStringValue();
            case Cell.CELL_TYPE_BOOLEAN:
                return value.getBooleanValue() ? TRUE_STRING : FALSE_STRING;
            case Cell.CELL_TYPE_ERROR:
                return ErrorEval.getText(value.getErrorValue());
        }
        throw new IllegalStateException("Unknown cell type.");
    }

    /**
     * <p>
     * 返回单元格的数值表示形式。
     * </p>
     * <p>
     * <ol>
     * <li>如果单元格类型为 布尔值、错误，则返回 <code>{@linkplain Double#NaN NaN}</code>。</li>
     * <li>如果单元格类型为字符串，则尝试将单元格内容解析为数字并返回，如果解析失败，返回
     * <code>{@linkplain Double#NaN NaN}</code>。</li>
     * <li>如果单元格类型为空、数值或公式，则尝试调用对象上 <code>{@linkplain Cell#getNumericCellValue
     * getNumericCellValue()}</code> 方法并返回，如果调用失败，返回
     * <code>{@linkplain Double#NaN NaN}</code>。</li>
     * <li>否则返回 <code>{@linkplain Double#NaN NaN}</code>。</li>
     * </ol>
     * </p>
     * 
     * @param cell
     *            要获取数值的单元格。
     * @return 与单元格值等效的数值，或者为 <code>{@linkplain Double#NaN NaN}</code>。
     */
    public static Double getNumericCellValue(Cell cell) {
        return getNumericCellValue(getCellValue(cell));
    }

    public static Double getNumericCellValue(CellValue value) {
        if (value == null) {
            return null;
        }

        switch (value.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                return value.getNumberValue();
            case Cell.CELL_TYPE_STRING:
                Double d = Convert.toDouble(value.getStringValue());
                return d == null ? Double.NaN : d;
            case Cell.CELL_TYPE_BOOLEAN:
            case Cell.CELL_TYPE_ERROR:
            default:
                return Double.NaN;
        }
    }

    public static final Sheet getSheet(Cell cell) {
        if (cell == null) {
            return null;
        }
        Row row = cell.getRow();
        return row == null ? null : row.getSheet();
    }

    public static final Workbook getWorkbook(Cell cell) {
        Sheet sheet = getSheet(cell);
        return sheet == null ? null : sheet.getWorkbook();
    }

    public static final CellValue evaluateFormulaCell(Cell cell) {
        if (cell.getCellType() != Cell.CELL_TYPE_FORMULA) {
            return null;
        }

        Workbook workbook = getWorkbook(cell);
        if (workbook == null) {
            return null;
        }

        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

        return evaluator.evaluate(cell);
    }

    public static String toString(Cell cell) {
        return getStringCellValue(cell);
    }
}
