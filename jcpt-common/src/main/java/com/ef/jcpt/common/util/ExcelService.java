/**
 * Project Name:jcpt-common <br>
 * File Name:ExcelUtil.java <br>
 * Package Name:com.hehenian.jcpt.util <br>
 * @author anxymf
 * Date:2017年1月10日上午9:40:19 <br>
 * Copyright (c) 2017, 深圳市彩付宝网络技术有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class ExcelService {

	public static String NO_DEFINE = "no_define";// 未定义的字段

	public static String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";// 默认日期格式

	public static int DEFAULT_COLOUMN_WIDTH = 17;

	public static ExcelService excelUtil;

	@PostConstruct
	public void init() {
		excelUtil = this;
	}

	/**
	 * 
	 * createTitleStyle:设置表头样式. <br>
	 *
	 * @author anxymf Date:2017年1月10日下午4:27:16 <br>
	 * @param workbook
	 * @return
	 */
	public static CellStyle createTitleStyle(SXSSFWorkbook workbook) {
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBoldweight((short) 700);
		titleStyle.setFont(titleFont);
		return titleStyle;
	}

	/**
	 * 
	 * createHeaderStyle:设置列头样式. <br>
	 *
	 * @author anxymf Date:2017年1月10日下午4:27:31 <br>
	 * @param workbook
	 * @return
	 */
	public static CellStyle createHeaderStyle(SXSSFWorkbook workbook) {
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());// 设置背景色
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);
		return headerStyle;
	}

	/**
	 * 
	 * createCellStyle:设置单元格样式. <br>
	 *
	 * @author anxymf Date:2017年1月10日下午4:27:34 <br>
	 * @param workbook
	 * @return
	 */
	public static CellStyle createCellStyle(SXSSFWorkbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		Font cellFont = workbook.createFont();
		cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle.setFont(cellFont);
		return cellStyle;
	}

	/**
	 * 
	 * setWidth:设置列宽. <br>
	 *
	 * @author anxymf Date:2017年1月10日下午4:27:41 <br>
	 * @param colWidth
	 * @param sheet
	 * @param headMap
	 * @param properties
	 * @param headers
	 */
	public static void setWidth(int colWidth, SXSSFSheet sheet, Map<String, String> headMap, String[] properties,
			String[] headers) {
		int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH : colWidth;// 至少字节数
		int[] arrColWidth = new int[headMap.size()];
		int ii = 0;
		for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();) {
			String fieldName = iter.next();
			properties[ii] = fieldName;
			headers[ii] = headMap.get(fieldName);
			int bytes = headers[ii].getBytes().length;
			arrColWidth[ii] = bytes < minBytes ? minBytes : bytes;
			sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
			ii++;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void exportExcel(String title, Map<String, String> headMap, List list, String datePattern,
			int colWidth, OutputStream out) {
		JSONArray ja = new JSONArray();
		ja.addAll(list);
		exportExcel(title, headMap, ja, datePattern, colWidth, out);
	}

	/**
	 * 导出Excel 2007 OOXML (.xlsx)格式
	 * 
	 * @param title
	 *            标题行
	 * @param headMap
	 *            属性-列头
	 * @param jsonArray
	 *            数据集
	 * @param datePattern
	 *            日期格式，传null值则默认 年月日
	 * @param colWidth
	 *            列宽 默认 至少17个字节
	 * @param out
	 *            输出流
	 */
	public static void exportExcel(String title, Map<String, String> headMap, JSONArray jsonArray, String datePattern,
			int colWidth, OutputStream out) {
		if (datePattern == null)
			datePattern = DEFAULT_DATE_PATTERN;

		// 字典集合
		Map<String, Map<String, String>> codeListMap = new HashMap<String, Map<String, String>>();
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);// 缓存
		workbook.setCompressTempFiles(true);
		XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();
		// 表头样式
		CellStyle titleStyle = createTitleStyle(workbook);
		// 列头样式
		CellStyle headerStyle = createHeaderStyle(workbook);
		// 单元格样式
		CellStyle cellStyle = createCellStyle(workbook);
		// 生成一个(带标题)表格
		SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
		// 设置列宽
		String[] properties = new String[headMap.size()];
		String[] headers = new String[headMap.size()];
		setWidth(colWidth, sheet, headMap, properties, headers);
		// 遍历集合数据，产生数据行
		int rowIndex = 0;
		for (Object obj : jsonArray) {
			if (rowIndex == 65535 || rowIndex == 0) {
				if (rowIndex != 0) {
					sheet = (SXSSFSheet) workbook.createSheet();// 如果数据超过了，则在第二页显示
					setWidth(colWidth, sheet, headMap, properties, headers);
				}
				SXSSFRow titleRow = (SXSSFRow) sheet.createRow(0);// 表头 rowIndex=0
				titleRow.createCell(0).setCellValue(title);
				titleRow.getCell(0).setCellStyle(titleStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));
				SXSSFRow headerRow = (SXSSFRow) sheet.createRow(1); // 列头 rowIndex =1
				for (int i = 0; i < headers.length; i++) {
					headerRow.createCell(i).setCellValue(headers[i]);
					headerRow.getCell(i).setCellStyle(headerStyle);
				}
				rowIndex = 2;// 数据内容从 rowIndex=2开始
			}
			JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
			SXSSFRow dataRow = (SXSSFRow) sheet.createRow(rowIndex);
			for (int i = 0; i < properties.length; i++) {
				SXSSFCell newCell = (SXSSFCell) dataRow.createCell(i);
				String[] props = properties[i].split(";");
				String[] attrs = props[0].split("\\.");
				JSONObject jsobj = null;
				Object o = null;
				for (int x = 0; x < attrs.length; x++) {
					if (attrs.length == 1) {
						o = jo.get(attrs[x]);
					} else {
						if (x == 0) {
							jsobj = (JSONObject) jo.get(attrs[x]);
							if (jsobj == null) {
								break;
							}
						} else if (x == attrs.length - 1) {
							if (jsobj == null) {
								break;
							}
							o = jsobj.get(attrs[x]);
						} else {
							if (jsobj == null) {
								break;
							}
							jsobj = (JSONObject) jsobj.get(attrs[x]);
						}
					}
				}
				// cellStyle.setDataFormat(format.getFormat("@"));
				if (props.length == 1) {
					if (o == null)
						newCell.setCellValue("");
					else if (o instanceof Date) {
						newCell.setCellValue(new SimpleDateFormat(datePattern).format(o));
					} else if (o instanceof Float || o instanceof Double || o instanceof BigDecimal) {
						newCell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
						cellStyle.setDataFormat(format.getFormat("0.00"));
						newCell.setCellValue(
								new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					} else {
						newCell.setCellValue(o.toString());
					}
				} else {
					o = String.valueOf(o);
					/*
					 * if(!codeListMap.containsKey(props[1])){ codeListMap.put(props[1],
					 * excelUtil.dictSystemInfoComponent.queryCodeListToMap(props[1])); }
					 * Map<String, String> codeList = codeListMap.get(props[1]);
					 * newCell.setCellValue(codeList.get(o));
					 */
					newCell.setCellValue(o.toString());
				}
				newCell.setCellStyle(cellStyle);
			}
			rowIndex++;
		}
		try {
			workbook.write(out);
			workbook.dispose();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * createSheetHead:创建excel表头. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年11月9日下午5:35:19 <br>
	 * @param title
	 * @param sheet
	 * @param titleStyle
	 * @param headerStyle
	 * @param excelColMapperList
	 */
	static SXSSFSheet createSheetWithHead(String title, SXSSFWorkbook workbook, CellStyle titleStyle, CellStyle headerStyle,
			List<ExcelColMapper> excelColMapperList) {
		// 生成一个(带标题)表格
		SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
		int colSize = excelColMapperList.size();
		SXSSFRow titleRow = (SXSSFRow) sheet.createRow(0);// 表头 rowIndex=0
		titleRow.createCell(0).setCellValue(title);
		titleRow.getCell(0).setCellStyle(titleStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colSize - 1));
		SXSSFRow headerRow = (SXSSFRow) sheet.createRow(1); // 列头 rowIndex =1
		for (int i = 0; i < colSize; i++) {
			headerRow.createCell(i).setCellValue(excelColMapperList.get(i).getName());
			headerRow.getCell(i).setCellStyle(headerStyle);
			int byteLen = excelColMapperList.get(i).getName().getBytes().length;
			byteLen = byteLen > DEFAULT_COLOUMN_WIDTH ? byteLen : DEFAULT_COLOUMN_WIDTH;
			sheet.setColumnWidth(i, byteLen * 256);
		}
		return sheet;
	}

	static <T> void writeDataToExcel(SXSSFWorkbook workbook, SXSSFSheet sheet, CellStyle cellStyle,List<ExcelColMapper> excelColMapperList,
			List<T> list) {
		XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();
		int colSize = excelColMapperList.size();
		// 遍历集合数据，产生数据行
		int rowIndex = 2;
		for (T rowData : list) {
			JSONObject json = (JSONObject) JSONObject.toJSON(rowData);
			SXSSFRow dataRow = (SXSSFRow) sheet.createRow(rowIndex);
			for (int i = 0; i < colSize; i++) {
				SXSSFCell newCell = (SXSSFCell) dataRow.createCell(i);
				newCell.setCellStyle(cellStyle);
				ExcelColMapper excelColMapper = excelColMapperList.get(i);
				Object cellValue = json.get(excelColMapper.getKey());
				if (cellValue == null) {
					newCell.setCellValue("");
				} else {
					ExcelColMapper.ExcelColEnum type = excelColMapper.getType();
					switch (type) {
					case STRING:
						newCell.setCellValue(cellValue.toString());
						break;
					case CODELIST:
						newCell.setCellValue(MapUtil.getValue(cellValue.toString(), excelColMapper.getCodelist()));
						break;
					case DATE:
						String datePattern = DEFAULT_DATE_PATTERN;
						if (StringUtil.isNotEmpty(excelColMapper.getFormat())) {
							datePattern = excelColMapper.getFormat();
						}
						newCell.setCellValue(DateUtil.format((Date) cellValue, datePattern));
						break;
					case NUMBER:
						newCell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
						String numberFormat = "##0.00";
						if (StringUtil.isNotEmpty(excelColMapper.getFormat())) {
							numberFormat = excelColMapper.getFormat();
						}
						cellStyle.setDataFormat(format.getFormat(numberFormat));
						DecimalFormat df = new DecimalFormat(numberFormat);
						String str = df.format(cellValue);
						newCell.setCellValue(new Double(str));
						break;
					default:
						newCell.setCellValue(cellValue.toString());
					}
				}
			}
			rowIndex++;
		}
	}

	public static <T> void exportExcel(String title, List<ExcelColMapper> excelColMapperList, List<T> list,
			OutputStream out) {
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);// 缓存
		workbook.setCompressTempFiles(true);
		// 表头样式
		CellStyle titleStyle = createTitleStyle(workbook);
		// 列头样式
		CellStyle headerStyle = createHeaderStyle(workbook);
		// 单元格样式
		CellStyle cellStyle = createCellStyle(workbook);
		
		if (CollectionUtils.isEmpty(list)) {
			SXSSFSheet sheet = createSheetWithHead(title, workbook, titleStyle, headerStyle, excelColMapperList);
			SXSSFRow dataRow = (SXSSFRow) sheet.createRow(2);
			SXSSFCell newCell = (SXSSFCell) dataRow.createCell(0);
			newCell.setCellStyle(cellStyle);
			newCell.setCellValue("无数据");
		}
		int dataSize = list.size();
		int sheetSize = 65536;
		int sheetNum = dataSize / sheetSize+((dataSize % sheetSize) > 0 ? 1 : 0);
		for(int num=0;num<sheetNum;num++) {
			int toIndex = (num+1)*sheetSize;
			toIndex = toIndex>dataSize?dataSize:toIndex;
			List<T> data = list.subList(num*sheetSize,toIndex);
			SXSSFSheet sheet = createSheetWithHead(title, workbook, titleStyle, headerStyle, excelColMapperList);
			writeDataToExcel(workbook, sheet, cellStyle, excelColMapperList, data);
		}
		try {
			workbook.write(out);
			workbook.dispose();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
