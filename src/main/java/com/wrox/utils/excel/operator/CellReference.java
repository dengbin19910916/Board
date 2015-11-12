package com.wrox.utils.excel.operator;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单元格在Excel工作表中的位置。<br/>
 * 一个完整的单元格索引由行索引和列索引组成。其格式为：列索引名称+行索引，例如：A1。<br/>
 * 这个类支持单独处理行索引或列索引。当行索引/列索引的值为0时，表示行索引/列索引不存在，
 * 并且当列索引不存在时，其列索引名称为“@”。
 *
 * @author dengb
 * @version 1.0
 */
public class CellReference implements Comparable<CellReference> {

    private static final char[] alphabet = {
            '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static final Map<Character, Integer> alphabetIndex = new HashMap<>(26);

    static {
        for (int i = 1; i <= 26; i++) {
            alphabetIndex.put(alphabet[i], i);
        }
    }

    /**
     * 单元格行索引。
     */
    private final int row;
    /**
     * 单元格列索引。
     */
    private final int col;

    /**
     * 通过单元格名称构造CellReference对象。
     *
     * @param r 单元格名称，格式为列名称+行索引，例如：A1。
     */
    public CellReference(String r) {
        this.row = getRowIndex(r);
        this.col = getColIndex(r);
    }

    /**
     * 通过行索引和列索引构造CellReference对象。
     *
     * @param row 行索引
     * @param col 列索引
     */
    public CellReference(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * 通过行索引和列索引名构造CellReference对象。
     *
     * @param row 行索引
     * @param colName 列索引名
     */
    public CellReference(int row, String colName) {
        this.row = row;
        this.col = toNumber(colName);
    }

    /* Public Method */

    /**
     * 返回单元格的列索引的数字形式。
     *
     * @return 列索引的数字形式。
     */
    public int getCol() {
        return col;
    }

    /**
     * 返回单元格的列索引的字母形式。
     *
     * @return 列索引的数字形式。
     */
    public String getColName() {
        return toAlphabet();
    }

    /**
     * 返回单元格的行索引。
     *
     * @return 行索引。
     */
    public int getRow() {
        return row;
    }

    /**
     * 返回当前单元格的列索引加1后的索引。
     *
     * @param curR 当前单元格的字母形式。
     * @return 当前列的下一列的索引。
     */
    public static CellReference increment(String curR) {
        return new CellReference(curR).increment();
    }

    /**
     * 返回当前单元格的列索引加1后的索引。
     *
     * @return 当前列的下一列的索引。
     */
    public CellReference increment() {
        return new CellReference(row, col + 1);
    }

    /**
     * 返回其值为 (this + val) 的 CellReference。
     *
     * @param val 将添加到此 CellReference 中的值。
     * @return this + val
     */
    public CellReference add(CellReference val) {
        return new CellReference(row + val.row, col + val.col);
    }

    /**
     * 返回其值为 (this.row + val) 的 CellReference。
     *
     * @param val 将添加到此 CellReference 中的值。
     * @return this.row + val
     */
    public CellReference addRow(int val) {
        return new CellReference(row + val, col);
    }

    /**
     * 返回其值为 (this.col + val) 的 CellReference。
     *
     * @param val 将添加到此 CellReference 中的值。
     * @return this.col + val
     */
    public CellReference addCol(int val) {
        return new CellReference(row, col + val);
    }

    /**
     * 返回其值为 (this.col + val) 的 CellReference。
     *
     * @param val 将添加到此 CellReference 中的值。
     * @return this.col + val
     */
    public CellReference addCol(String val) {
        return addCol(toNumber(val));
    }

    /**
     * 返回其值为 (this - val) 的 CellReference。
     *
     * @param val 从此 CellReference 中减去的值。
     * @return this - val
     */
    public CellReference subtract(CellReference val) {
        return new CellReference(row - val.row, col - val.col);
    }

    /**
     * 返回其值为 (this.row - val) 的 CellReference。
     *
     * @param val 从此 CellReference 中减去的值。
     * @return this.row - val
     */
    public CellReference subtractRow(int val) {
        return addRow(-val);
    }

    /**
     * 返回其值为 (this.col - val) 的 CellReference。
     *
     * @param val 从此 CellReference 中减去的值。
     * @return this.col - val
     */
    public CellReference subtractCol(int val) {
        return addCol(-val);
    }

    /**
     * 返回其值为 (this.col - val) 的 CellReference。
     *
     * @param val 从此 CellReference 中减去的值。
     * @return this.col - val
     */
    public CellReference subtractCol(String val) {
        return addCol(- toNumber(val));
    }

    /**
     * 比较两个单元格的位置是否连续。
     *
     * @param r1 单元格1
     * @param r2 单元格2
     * @return 两个单元格的位置是否连续。true - 连续，false - 不连续。
     */
    public static boolean isContinuous(String r1, String r2) {
        return new CellReference(r1).isContinuous(r2);
    }

    /**
     * 比较两个单元格的位置是否连续。
     *
     * @param other 需要比较的单元格
     * @return 两个单元格的位置是否连续。true - 连续，false - 不连续。
     */
    public boolean isContinuous(String other) {
        return this.isContinuous(new CellReference(other));
    }

    /**
     * 比较两个单元格的位置是否连续。
     *
     * @param other 需要比较的单元格
     * @return 两个单元格的位置是否连续。true - 连续，false - 不连续。
     */
    public boolean isContinuous(CellReference other) {
        return this.row == other.row && Math.abs(this.col - other.col) == 1;
    }

    /**
     * 返回两个单元格之间所有的缺失的单元格的名称集合。
     *
     * @param r1 单元格1
     * @param r2 单元格2
     * @return 两个单元格之间所有的缺失的单元格的名称集合。
     */
    public static List<String> getMissingColName(String r1, String r2, int lastColNum) {
        return getMissingColName(new CellReference(r1), new CellReference(r2), lastColNum);
    }

    /**
     * 返回两个单元格之间所有的缺失的单元格的名称集合。
     *
     * @param cr1 单元格1
     * @param cr2 单元格2
     * @return 两个单元格之间所有的缺失的单元格的名称集合。
     */
    public static List<String> getMissingColName(CellReference cr1, CellReference cr2, int lastColNum) {
        List<String> list = new ArrayList<>();
        CellReference cr = getMin(cr1, cr2);

        if (Math.abs(cr1.col - cr2.col) > 0) {
            int count = Math.abs(cr1.col - cr2.col) - 1;
            for (int i = 0; i < count; i++) {
                cr = cr.increment();
                list.add(cr.toString());
            }
        }
        return list;
    }

    /**
     * 返回两个单元格之间所有的缺失的单元格的名称集合。
     *
     * @param cr   单元格。
     * @param span 有效最大列的索引。
     * @return 两个单元格之间所有的缺失的单元格的名称集合。
     */
    public static List<String> getMissingColName(CellReference cr, int span) {
        List<String> list = new ArrayList<>();

        int count = Math.abs(span - cr.col) - 1;
        for (int i = 0; i < count; i++) {
            cr = cr.increment();
            list.add(cr.toString());
        }
        return list;
    }

    public static CellReference getMin(CellReference... crs) {
        List<CellReference> list = Arrays.asList(crs);
        list.sort(CellReference::compareTo);
        return list.get(0);
    }

    public static CellReference getMax(CellReference... crs) {
        List<CellReference> list = Arrays.asList(crs);
        list.sort(CellReference::compareTo);
        return list.get(list.size() - 1);
    }

    @Override
    public int compareTo(CellReference o) {
        if (this.row == o.row && this.col == o.col) {
            return 0;
        } else if (this.row < o.row || (this.row == o.row && this.col < o.col)) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return toAlphabet() + row;
    }

    /* Private Method */

    /**
     * 返回c标签r属性中的列索引值。
     *
     * @param r c标签的r属性，由列索引+行索引组成，例如：A1。
     * @return c标签r属性中的列索引值。
     */
    private int getColIndex(String r) {
        Matcher matcher = Pattern.compile("[0-9]").matcher(r);
        if (!matcher.find()) {
            return toNumber(r);
        }
        return toNumber(r.substring(0, r.indexOf(matcher.group())));
    }

    /**
     * 返回c标签r属性中行列索引值。
     *
     * @param r c标签的r属性，由列索引+行索引组成，例如：A1。
     * @return c标签r属性中的行索引值，没有行索引则返回0。
     */
    private int getRowIndex(String r) {
        assert r != null;
        Matcher matcher = Pattern.compile("[0-9]").matcher(r);
        if (!matcher.find()) {
            return 0;
        }
        return Integer.parseInt(r.substring(r.indexOf(matcher.group())));
    }

    /**
     * 返回列索引的数字表现形式。
     *
     * @param col 列索引的字母表现形式。
     * @return 列索引的数字表现形式。
     */
    private int toNumber(String col) {
        assert col != null;
        if (col.length() == 0 || !col.matches("^[a-zA-Z]+[0-9]*$")) {
            throw new ExcelParseException("无效的单元格名称！");
        }
        col = col.toUpperCase();

        int idxNum = 0;
        for (int i = 0; i < col.length(); i++) {
            idxNum += alphabetIndex.get(col.charAt(col.length() - i - 1)) * Math.pow(26, i);
        }

        return idxNum;
    }

    /**
     * 返回列索引的字母表现形式。
     *
     * @return 列索引的字母表现形式。
     */
    private String toAlphabet() {
        if (col <= 0) {
            return String.valueOf(alphabet[0]);
        }

        StringBuilder sb = new StringBuilder();
        int condition = col;
        while (condition > 0) {
            sb.append(alphabet[condition % alphabetIndex.size()]);
            condition /= alphabetIndex.size();
        }

        return StringUtils.reverse(sb.toString());
    }
}
