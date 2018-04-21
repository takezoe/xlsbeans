package com.github.takezoe.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.github.takezoe.xlsbeans.NeedPostProcess;
import com.github.takezoe.xlsbeans.Utils;
import com.github.takezoe.xlsbeans.XLSBeansConfig;
import com.github.takezoe.xlsbeans.XLSBeansException;
import com.github.takezoe.xlsbeans.annotation.*;
import com.github.takezoe.xlsbeans.xml.AnnotationReader;
import com.github.takezoe.xlsbeans.xssfconverter.WCell;
import com.github.takezoe.xlsbeans.xssfconverter.WSheet;

/**
 *
 * @author Mitsuyoshi Hasegawa
 * @see com.github.takezoe.xlsbeans.annotation.IterateTables
 */
public class IterateTablesProcessor implements FieldProcessor {

    public void doProcess(WSheet wSheet, Object obj, Method setter, Annotation ann, AnnotationReader reader,
                          XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {

        IterateTables iterateTables = (IterateTables) ann;

        Class<?>[] setterArgClzArray = setter.getParameterTypes();
        if (setterArgClzArray.length != 1) {
            // isn't setter attribute
            throw new XLSBeansException("Arguments of '" + setter.toString() + "' is invalid.");
        }

        // create multi-table objects.
        List<?> value = createTables(wSheet, iterateTables, reader, config, needPostProcess);
        if (List.class.isAssignableFrom(setterArgClzArray[0])) {
            setter.invoke(obj, new Object[]{value});

        } else if (setterArgClzArray[0].isArray()) {
        	Class<?> type = setterArgClzArray[0].getComponentType();
        	Object array = Array.newInstance(type, value.size());
        	for(int i = 0; i < value.size(); i++){
        		Array.set(array, i, value.get(i));
        	}
        	setter.invoke(obj, new Object[] { array });

        } else {
            throw new XLSBeansException("Arguments of '" + setter.toString() + "' is invalid.");
        }
    }


	public void doProcess(WSheet wSheet, Object obj, Field field, Annotation ann, AnnotationReader reader,
                          XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {

        IterateTables iterateTables = (IterateTables) ann;

        Class<?> fieldType = field.getType();

        // create multi-table objects.
        List<?> value = createTables(wSheet, iterateTables, reader, config, needPostProcess);
        if (List.class.isAssignableFrom(fieldType)) {
            field.set(obj, value);

        } else if (fieldType.isArray()) {
        	Class<?> type = fieldType.getComponentType();
        	Object array = Array.newInstance(type, value.size());
        	for(int i = 0; i < value.size(); i++){
        		Array.set(array, i, value.get(i));
        	}
        	field.set(obj, array);

        } else {
            throw new XLSBeansException("Arguments of '" + field.toString() + "' is invalid.");
        }
	}

    protected List<?> createTables(WSheet wSheet, IterateTables iterateTables, AnnotationReader reader,
                                   XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {
        List<Object> resultTableList = new ArrayList<Object>();

        WCell after = null;
        WCell currentCell = null;
        String label = iterateTables.tableLabel();
        currentCell = Utils.getCell(wSheet, label, after, false, !iterateTables.optional(), config);
        while (currentCell != null) {
            // 1 table object instance
            Object tableObj = iterateTables.tableClass().newInstance();

            // process single label.
            processSingleLabelledCell(wSheet, tableObj, currentCell, reader, config, needPostProcess);
            // process horizontal table.
            processMultipleTableCell(wSheet, tableObj, currentCell, reader, iterateTables, config, needPostProcess);

            // TODO process vertical table

            resultTableList.add(tableObj);
            after = currentCell;
            currentCell = Utils.getCell(wSheet, label, after, false, false, config);
        }
        return resultTableList;
    }

    protected void processSingleLabelledCell(WSheet wSheet, Object tableObj, WCell headerCell, AnnotationReader reader,
                                             XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {

        List<Object> properties = Utils.getPropertiesWithAnnotation(tableObj, reader, LabelledCell.class);

        LabelledCellProcessor labelledCellProcessor = new LabelledCellProcessor();
        for (Object property : properties) {
            LabelledCell ann = null;
            if(property instanceof Method){
            	ann = reader.getAnnotation(tableObj.getClass(), (Method) property, LabelledCell.class);

            } else if(property instanceof Field){
            	ann = reader.getAnnotation(tableObj.getClass(), (Field) property, LabelledCell.class);
            }

            WCell titleCell = null;
            try {
                titleCell = Utils.getCell(wSheet, ann.label(), headerCell, config);
            } catch (XLSBeansException e) {
                if (ann.optional()) {
                    continue;
                } else {
                    throw e;
                }
            }

            LabelledCell labelledCell = new LabelledCellForIterateTable(ann, titleCell.getRow(), titleCell.getColumn());

            if(property instanceof Method){
	            labelledCellProcessor.doProcess(wSheet, tableObj, (Method) property, labelledCell, reader, config, needPostProcess);

            } else if(property instanceof Field){
	            labelledCellProcessor.doProcess(wSheet, tableObj, (Field) property, labelledCell, reader, config, needPostProcess);
            }
        }
    }

    protected void processMultipleTableCell(WSheet wSheet, Object tableObj, WCell headerCell, AnnotationReader reader,
            IterateTables iterateTables, XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {

        List<Object> properties = Utils.getPropertiesWithAnnotation(tableObj, reader, HorizontalRecords.class);

        int headerColumn = headerCell.getColumn();
        int headerRow = headerCell.getRow();

        if (iterateTables.bottom() > 0) {
            // if positive value set to bottom(), row index of table header move
            headerRow += iterateTables.bottom();
        }

        HorizontalRecordsProcessor processor = new HorizontalRecordsProcessor();
        for (Object property : properties) {
            HorizontalRecords ann = null;
            if(property instanceof Method){
            	ann = reader.getAnnotation(tableObj.getClass(), (Method) property, HorizontalRecords.class);

            } else if(property instanceof Field){
            	ann = reader.getAnnotation(tableObj.getClass(), (Field) property, HorizontalRecords.class);
            }

            if (iterateTables.tableLabel().equals(ann.tableLabel())) {
            	//
                HorizontalRecords records = new HorizontalRecordsForIterateTable(ann, headerColumn, headerRow);

                // horizontal record-mapping
                if(property instanceof Method){
	                processor.doProcess(wSheet, tableObj, (Method) property, records, reader, config, needPostProcess);

                } else if(property instanceof Field){
	                processor.doProcess(wSheet, tableObj, (Field) property, records, reader, config, needPostProcess);
                }
            }
        }
    }

}
