package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TableUtils {
    private static final Logger log = LoggerFactory.getLogger(TableUtils.class);
    private final Page page;
    private final Locator tableContainer;
    private final Locator headerCells;
    private final Locator rows;

    public TableUtils(Page page, String tableSelector) {
        this.page = page;
        this.tableContainer = page.locator(tableSelector);
        this.headerCells = tableContainer.locator(".oxd-table-header .oxd-table-header-cell");
        this.rows = tableContainer.locator(".oxd-table-body > .oxd-table-card > .oxd-table-row");
    }

    // Click sort trên column (OrangeHRM có icon sort trong header cell)
    public void clickToSort(String columnName) {
        int index = getHeaderIndex(columnName);
        if (index == -1) {
            log.error("Column {} not found", columnName);
            return;
        }
        // Click vào div sort icon trong header cell
        headerCells.nth(index - 1).locator(".oxd-table-header-sort-dropdown").click();
        // Hoặc click trực tiếp header để toggle sort nếu không có dropdown
        // headerCells.nth(index - 1).click();
        log.info("Clicked sort on column: {}", columnName);
    }

    public void clickEditButtonByRowIndex(int rowIndex) {
        Locator row = rows.nth(rowIndex - 1);
        row.locator("button.oxd-icon-button:has(i.oxd-icon.bi-pencil-fill)").click();
    }

    public void clickDeleteButtonByRowIndex(int rowIndex) {
        Locator row = rows.nth(rowIndex - 1);
        row.locator("button.oxd-icon-button:has(i.oxd-icon.bi-trash)").click();
    }

    public void clickActionButtonByRowValues(Map<String, String> columnValueMap, String action) {
        int rowIndex = findRowIndexByValues(columnValueMap);
        System.out.printf("Row index result: %d", rowIndex);

        if (rowIndex == -1) {
            System.out.printf("Action %s aborted. No matching row.", action);
            return;
        }

        if ("Edit".equalsIgnoreCase(action)) {
            clickEditButtonByRowIndex(rowIndex);
        } else if ("Delete".equalsIgnoreCase(action)) {
            clickDeleteButtonByRowIndex(rowIndex);
        }
    }


    private int findRowIndexByValues(Map<String, String> columnValueMap) {
        int numRows = getNumOfRows();
        System.out.printf("Total rows found: %d", numRows);
        System.out.printf("Expected column-value map: %s", columnValueMap);

        for (int i = 1; i <= numRows; i++) {
            Map<String, String> rowData = getRowDataAsMap(i);
            System.out.printf("Row %d data: %s", i, rowData);

            boolean match = true;

            for (Map.Entry<String, String> entry : columnValueMap.entrySet()) {
                String column = entry.getKey();
                String expected = entry.getValue();
                String actual = rowData.get(column);

                System.out.printf(
                        "Compare row %s | column=%s | expected=%s | actual=%s",
                        i, column, expected, actual
                );

                if (expected == null) {
                    System.out.printf("Expected value is NULL for column %s", column);
                    match = false;
                    break;
                }

                if (actual == null) {
                    System.out.printf("Column %s NOT FOUND in row %d", column, i);
                    match = false;
                    break;
                }

                if (!expected.equalsIgnoreCase(actual)) {
                    System.out.printf(
                            "Mismatch row %d | column=%s | expected=%s | actual=%s%n",
                            i, column, expected, actual
                    );
                    match = false;
                    break;
                }
            }

            if (match) {
                System.out.printf("MATCH FOUND at row %d", i);
                return i;
            }
        }

        System.out.printf("NO MATCHING ROW found for values: %s", columnValueMap);
        return -1;
    }

    public boolean verifyRowValuesArePresent(List<String> values){
        int numOfRows = getNumOfRows();
        for(int i = 0; i<numOfRows; i++) {
            if(new HashSet<>(getRowValuesByIndex(i + 1)).containsAll(values)) return true;
        }
        return false;
    }

    public int getNumOfRows() {
        return rows.count();
    }

    public List<String> getAllHeaderValues() {
        return headerCells.allTextContents().stream()
                .map(h -> h
                        .replace("Ascending", "")
                        .replace("Descending", "")
                        .trim())
                .toList();
    }

    public int getHeaderIndex(String columnName) {
        List<String> headers = getAllHeaderValues();
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).equalsIgnoreCase(columnName.trim())) {
                return i + 1;
            }
        }
        return -1;
    }

    public List<String> getAllValuesInColumn(String columnName) {
        int colIndex = getHeaderIndex(columnName);
        if (colIndex == -1) {
            log.error("Column {} not found", columnName);
            return Collections.emptyList();
        }
        return rows.locator(String.format(":nth-match(.oxd-table-cell, %d)", colIndex)).allTextContents();
    }

    public String getCellValue(int rowIndex, String columnName) {
        int colIndex = getHeaderIndex(columnName);
        if (colIndex == -1) return "";
        return rows.nth(rowIndex - 1).locator(String.format(".oxd-table-cell:nth-child(%d)", colIndex)).textContent().trim();
    }

    public List<String> getRowValuesByIndex(int rowIndex) {
        return rows.nth(rowIndex - 1).locator(".oxd-table-cell").allTextContents();
    }

    public HashMap<String, String> getRowDataAsMap(int rowIndex) {
        HashMap<String, String> map = new HashMap<>();
        List<String> headers = getAllHeaderValues();
        List<String> values = getRowValuesByIndex(rowIndex);
        for (int i = 0; i < Math.min(headers.size(), values.size()); i++) {
            map.put(headers.get(i).trim(), values.get(i).trim());
        }
        System.out.println("Headers: " + headers);
        System.out.println("Values : " + values);
        return map;
    }

    public List<HashMap<String, String>> getAllTableData() {
        List<HashMap<String, String>> data = new ArrayList<>();
        int rowsCount = getNumOfRows();
        for (int i = 1; i <= rowsCount; i++) {
            data.add(getRowDataAsMap(i));
        }
        return data;
    }

    // Verify sort (tương tự cũ nhưng dùng data mới)
    public boolean verifyColumnSorted(String columnName, boolean ascending) {
        List<String> values = getAllValuesInColumn(columnName);
        List<String> sorted = new ArrayList<>(values);
        if (ascending) {
            Collections.sort(sorted, String.CASE_INSENSITIVE_ORDER);
        } else {
            sorted.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        }
        return values.equals(sorted);
    }

    // Checkbox operations
    public void checkCheckboxByRowIndex(int rowIndex) {
        Locator checkbox = rows.nth(rowIndex - 1).locator(".oxd-checkbox-input");
        if (!checkbox.isChecked()) {
            checkbox.check();
        }
    }

    public void checkCheckboxByRowValues(HashMap<String, String> columnValueMap) {
        int rowIndex = findRowIndexByValues(columnValueMap);
        if (rowIndex != -1) {
            checkCheckboxByRowIndex(rowIndex);
        } else {
            log.error("Row not found for checkbox check");
        }
    }

    // Pagination - chọn số rows per page
    public void selectRowsPerPage(int rowsPerPage) {
        Locator dropdown = page.locator(".oxd-pagination select");
        dropdown.selectOption(String.valueOf(rowsPerPage));
        page.waitForLoadState(); // chờ reload table
    }

    public boolean verifyRowsCountMatches(int expectedRows) {
        return getNumOfRows() <= expectedRows;
    }
}