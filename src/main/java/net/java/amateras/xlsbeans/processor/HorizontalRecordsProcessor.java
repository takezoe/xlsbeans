package net.java.amateras.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import net.java.amateras.xlsbeans.NeedPostProcess;
import net.java.amateras.xlsbeans.Utils;
import net.java.amateras.xlsbeans.XLSBeansException;
import net.java.amateras.xlsbeans.annotation.Column;
import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.MapColumns;
import net.java.amateras.xlsbeans.annotation.PostProcess;
import net.java.amateras.xlsbeans.annotation.RecordTerminal;
import net.java.amateras.xlsbeans.xml.AnnotationReader;
import net.java.amateras.xlsbeans.xssfconverter.WBorder;
import net.java.amateras.xlsbeans.xssfconverter.WBorderLineStyle;
import net.java.amateras.xlsbeans.xssfconverter.WCell;
import net.java.amateras.xlsbeans.xssfconverter.WCellFormat;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;

/**
 * The {@link net.java.amateras.xlsbeans.processor.FieldProcessor}
 * inplementation for {@link net.java.amateras.xlsbeans.annotation.HorizontalRecords}.
 *
 * @author Naoki Takezoe
 * @author Masayuki Matsumoto
 * @see net.java.amateras.xlsbeans.annotation.HorizontalRecords
 */
public class HorizontalRecordsProcessor implements FieldProcessor {

	public void doProcess(WSheet wSheet, Object obj, Method setter,
			Annotation ann, AnnotationReader reader,
			List<NeedPostProcess> needPostProcess) throws Exception {

		HorizontalRecords records = (HorizontalRecords)ann;

		Class<?>[] clazzes = setter.getParameterTypes();
		if(clazzes.length!=1){
			throw new XLSBeansException("Arguments of '" + setter.toString() + "' is invalid.");
		} else if(List.class.isAssignableFrom(clazzes[0])){
			List<?> value = createRecords(wSheet, records, reader, needPostProcess);
			if(value!=null){
				setter.invoke(obj, new Object[]{value});
			}
		} else if(clazzes[0].isArray()){
			List<?> value = createRecords(wSheet, records, reader, needPostProcess);
			if(value!=null){
	        	Class<?> type = clazzes[0].getComponentType();
	        	Object array = Array.newInstance(type, value.size());
	        	for(int i=0;i<value.size();i++){
	        		Array.set(array, i, value.get(i));
	        	}
				setter.invoke(obj, new Object[]{ array });
			}
		} else {
			throw new XLSBeansException("Arguments of '" + setter.toString() + "' is invalid.");
		}
	}


	public void doProcess(WSheet wSheet, Object obj, Field field, Annotation ann,
			AnnotationReader reader, List<NeedPostProcess> needPostProcess)
			throws Exception {

		HorizontalRecords records = (HorizontalRecords)ann;

		Class<?> clazz = field.getType();
		if(List.class.isAssignableFrom(clazz)){
			List<?> value = createRecords(wSheet, records, reader, needPostProcess);
			if(value!=null){
				field.set(obj, value);
			}
		} else if(clazz.isArray()){
			List<?> value = createRecords(wSheet, records, reader, needPostProcess);
			if(value!=null){
	        	Class<?> type = clazz.getComponentType();
	        	Object array = Array.newInstance(type, value.size());
	        	for(int i=0;i<value.size();i++){
	        		Array.set(array, i, value.get(i));
	        	}
				field.set(obj, array);
			}
		} else {
			throw new XLSBeansException("Arguments of '" + field.toString() + "' is invalid.");
		}
	}

	private List<?> createRecords(WSheet wSheet, HorizontalRecords records, AnnotationReader reader,
			List<NeedPostProcess> needPostProcess) throws Exception {

		List<Object> result = new ArrayList<Object>();
		List<HeaderInfo> headers = new ArrayList<HeaderInfo>();

		// get header
		int initColumn = -1;
		int initRow = -1;

		if(records.tableLabel().equals("")){
			initColumn = records.headerColumn();
			initRow = records.headerRow();
		} else {
			try {
				WCell labelCell = Utils.getCell(wSheet, records.tableLabel(), 0);
				initColumn = labelCell.getColumn();
				initRow = labelCell.getRow() + records.bottom();
			} catch(XLSBeansException ex){
				if(records.optional()){
					return null;
				} else {
					throw ex;
				}
			}
		}

		int hColumn = initColumn;
		int hRow = initRow;
		int rangeCount = 1;

		while(true){
			try {
				WCell cell = wSheet.getCell(hColumn, hRow);
				while(cell.getContents().equals("") && rangeCount < records.range()){
					cell = wSheet.getCell(hColumn + rangeCount, hRow);
					rangeCount++;
				}
				if(cell.getContents().equals("")){
					break;
				}
				headers.add(new HeaderInfo(cell.getContents(), rangeCount - 1));
				hColumn = hColumn + rangeCount;
				rangeCount = 1;
			} catch(ArrayIndexOutOfBoundsException ex){
				break;
			}
			if(records.headerLimit() > 0 && headers.size() >= records.headerLimit()){
				break;
			}
		}

		// Check for columns
		RecordsProcessorUtil.checkColumns(records.recordClass(), headers, reader);

		RecordTerminal terminal = records.terminal();
		if(terminal==null){
			terminal = RecordTerminal.Empty;
		}

		// get records
		hRow++;
		while(hRow < wSheet.getRows()){
			hColumn = initColumn;
			boolean emptyFlag = true;
			Object record = records.recordClass().newInstance();
			processMapColumns(wSheet, headers, hColumn, hRow, record, reader);

			for(int i=0;i<headers.size() && hRow < wSheet.getRows();i++){
				HeaderInfo headerInfo = headers.get(i);
				hColumn = hColumn + headerInfo.getHeaderRange();
				WCell wCell = wSheet.getCell(hColumn, hRow);

				// find end of the table
				if(!wCell.getContents().equals("")){
					emptyFlag = false;
				}
				if(terminal==RecordTerminal.Border && i==0){
					WCellFormat wFormat = wCell.getCellFormat();
					if(wFormat!=null && !wFormat.getBorder(WBorder.LEFT).equals(WBorderLineStyle.NONE)){
						emptyFlag = false;
					} else {
						emptyFlag = true;
						break;
					}
				}
				if(!records.terminateLabel().equals("")){
					if(wCell.getContents().equals(records.terminateLabel())){
						emptyFlag = true;
						break;
					}
				}

				List<Object> properties = Utils.getColumnProperties(record, headerInfo.getHeaderLabel(), reader);
				for(Object property: properties){
					WCell valueCell = wCell;

					Column column = null;
					if(property instanceof Method){
						column = reader.getAnnotation(record.getClass(), (Method) property, Column.class);
					} else if(property instanceof Field){
						column = reader.getAnnotation(record.getClass(), (Field) property, Column.class);
					}

					if(column.headerMerged() > 0){
						hColumn = hColumn + column.headerMerged();
						valueCell = wSheet.getCell(hColumn, hRow);
					}
					if(valueCell.getContents().equals("")){
						WCellFormat valueCellFormat = valueCell.getCellFormat();
						if(column.merged() && (valueCellFormat==null || valueCellFormat.getBorder(WBorder.TOP).equals(WBorderLineStyle.NONE))){
							for(int k=hRow-1;k>initRow;k--){
								WCell tmpCell = wSheet.getCell(hColumn, k);
								WCellFormat tmpCellFormat = tmpCell.getCellFormat();
								if(tmpCellFormat!=null && !tmpCellFormat.getBorder(WBorder.BOTTOM).equals(WBorderLineStyle.NONE)){
									break;
								}
								if(!tmpCell.getContents().equals("")){
									valueCell = tmpCell;
									break;
								}
							}
						}
					}
					if(column.headerMerged() > 0){
						hColumn = hColumn - column.headerMerged();
					}

					if(property instanceof Method){
						Utils.setPosition(valueCell.getColumn(), valueCell.getRow(), record, Utils.toPropertyName(((Method) property).getName()));
						Utils.invokeSetter((Method) property, record, valueCell.getContents());

					} else if(property instanceof Field){
						Utils.setPosition(valueCell.getColumn(), valueCell.getRow(), record, ((Field) property).getName());
						Utils.setField((Field) property, record, valueCell.getContents());
					}
				}
				hColumn++;
			}
			if(emptyFlag){
				break;
			}
			result.add(record);
			for(Method method : record.getClass().getMethods()){
				PostProcess ann = reader.getAnnotation(record.getClass(), method, PostProcess.class);
				if(ann!=null){
					needPostProcess.add(new NeedPostProcess(record, method));
				}
			}
			hRow++;
		}

		return result;
	}

	private void processMapColumns(WSheet wSheet, List<HeaderInfo> headerInfos,
			int begin, int row, Object record, AnnotationReader reader) throws Exception {

		List<Object> properties = Utils.getMapColumnProperties(record, reader);

		for(Object property : properties){
			MapColumns ann = null;
			if(property instanceof Method){
				ann = reader.getAnnotation(record.getClass(), (Method) property, MapColumns.class);
			} else if(property instanceof Field){
				ann = reader.getAnnotation(record.getClass(), (Field) property, MapColumns.class);
			}

			boolean flag = false;
			Map<String, String> map = new LinkedHashMap<String, String>();
			for(HeaderInfo headerInfo : headerInfos){
				if(headerInfo.getHeaderLabel().equals(ann.previousColumnName())){
					flag = true;
					begin++;
					continue;
				}
				if(flag){
					WCell cell = wSheet.getCell(begin + headerInfo.getHeaderRange(), row);
					map.put(headerInfo.getHeaderLabel(), cell.getContents());
				}
				begin = begin + headerInfo.getHeaderRange() + 1;
			}

			if(property instanceof Method){
				((Method) property).invoke(record, map);
			} else if(property instanceof Field){
				((Field) property).set(record, map);
			}
		}
	}

}
