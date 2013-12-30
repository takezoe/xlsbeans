package net.java.amateras.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.java.amateras.xlsbeans.NeedPostProcess;
import net.java.amateras.xlsbeans.Utils;
import net.java.amateras.xlsbeans.XLSBeansException;
import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.HorizontalRecordsForIterateTable;
import net.java.amateras.xlsbeans.annotation.IterateTables;
import net.java.amateras.xlsbeans.annotation.LabelledCell;
import net.java.amateras.xlsbeans.annotation.LabelledCellForIterateTable;
import net.java.amateras.xlsbeans.xml.AnnotationReader;
import net.java.amateras.xlsbeans.xssfconverter.WCell;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;

/**
 *
 * @author Mitsuyoshi Hasegawa
 * @see net.java.amateras.xlsbeans.annotation.IterateTables
 */
public class IterateTablesProcessor implements FieldProcessor {

    public void doProcess(WSheet wSheet, Object obj, Method setter,
            Annotation ann, AnnotationReader reader,
            List<NeedPostProcess> needPostProcess) throws Exception {

        IterateTables iterateTables = (IterateTables) ann;

        Class<?>[] setterArgClzArray = setter.getParameterTypes();
        if (setterArgClzArray.length != 1) {
            // isn't setter attribute
            throw new XLSBeansException("Arguments of '" + setter.toString() + "' is invalid.");
        }

        // create multi-table objects.
        List<?> value = createTables(wSheet, iterateTables, reader, needPostProcess);
        if (List.class.isAssignableFrom(setterArgClzArray[0])) {
            setter.invoke(obj, new Object[]{value});

        } else if (setterArgClzArray[0].isArray()) {
        	Class<?> type = setterArgClzArray[0].getComponentType();
        	Object array = Array.newInstance(type, value.size());
        	for(int i=0;i<value.size();i++){
        		Array.set(array, i, value.get(i));
        	}
        	setter.invoke(obj, new Object[] { array });

        } else {
            throw new XLSBeansException(
            		"Arguments of '" + setter.toString() + "' is invalid.");
        }
    }


	public void doProcess(WSheet wSheet, Object obj, Field field, Annotation ann,
			AnnotationReader reader, List<NeedPostProcess> needPostProcess)
			throws Exception {

        IterateTables iterateTables = (IterateTables) ann;

        Class<?> fieldType = field.getType();

        // create multi-table objects.
        List<?> value = createTables(wSheet, iterateTables, reader, needPostProcess);
        if (List.class.isAssignableFrom(fieldType)) {
            field.set(obj, value);

        } else if (fieldType.isArray()) {
        	Class<?> type = fieldType.getComponentType();
        	Object array = Array.newInstance(type, value.size());
        	for(int i=0;i<value.size();i++){
        		Array.set(array, i, value.get(i));
        	}
        	field.set(obj, array);

        } else {
            throw new XLSBeansException(
            		"Arguments of '" + field.toString() + "' is invalid.");
        }
	}

    protected List<?> createTables(WSheet wSheet, IterateTables iterateTables ,
            AnnotationReader reader,  List<NeedPostProcess> needPostProcess) throws Exception {
        List<Object> resultTableList = new ArrayList<Object>();

        WCell after = null;
        WCell currentCell = null;
        String label = iterateTables.tableLabel();
        currentCell = Utils.getCell(wSheet, label, after, false, !iterateTables.optional());
        while (currentCell != null) {
            // 1 table object instance
            Object tableObj = iterateTables.tableClass().newInstance();

            // process single label.
            processSingleLabelledCell(wSheet, tableObj, currentCell, reader,
                    needPostProcess);
            // process horizontal table.
            processMultipleTableCell(wSheet, tableObj, currentCell, reader,
                    iterateTables, needPostProcess);

            // TODO process vertical table

            resultTableList.add(tableObj);
            after = currentCell;
            currentCell = Utils.getCell(wSheet, label, after, false, false);
        }
        return resultTableList;
    }

    protected void processSingleLabelledCell(WSheet wSheet, Object tableObj,
            WCell headerCell, AnnotationReader reader,
            List<NeedPostProcess> needPostProcess) throws Exception {

        List<Object> properties = Utils.getPropertiesWithAnnotation(
        		tableObj, reader, LabelledCell.class);

        LabelledCellProcessor labelledCellProcessor = new LabelledCellProcessor();
        for (Object property : properties) {
            LabelledCell ann = null;
            if(property instanceof Method){
            	ann = reader.getAnnotation(
            			tableObj.getClass(), (Method) property, LabelledCell.class);

            } else if(property instanceof Field){
            	ann = reader.getAnnotation(
            			tableObj.getClass(), (Field) property, LabelledCell.class);
            }

            WCell titleCell = null;
            try {
                titleCell = Utils.getCell(wSheet, ann.label(), headerCell);
            } catch (XLSBeansException e) {
                if (ann.optional()) {
                    continue;
                } else {
                    throw e;
                }
            }

            LabelledCell labelledCell = new LabelledCellForIterateTable(ann,
                    titleCell.getRow(), titleCell.getColumn());

            if(property instanceof Method){
	            labelledCellProcessor.doProcess(wSheet, tableObj,
	            		(Method) property, labelledCell, reader, needPostProcess);

            } else if(property instanceof Field){
	            labelledCellProcessor.doProcess(wSheet, tableObj,
	            		(Field) property, labelledCell, reader, needPostProcess);
            }
        }
    }

    protected void processMultipleTableCell(WSheet wSheet, Object tableObj,
            WCell headerCell, AnnotationReader reader,
            IterateTables iterateTables,
            List<NeedPostProcess> needPostProcess) throws Exception {

        List<Object> properties = Utils.getPropertiesWithAnnotation(
        		tableObj, reader, HorizontalRecords.class);

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
            	ann = reader.getAnnotation(
            			tableObj.getClass(), (Method) property, HorizontalRecords.class);

            } else if(property instanceof Field){
            	ann = reader.getAnnotation(
            			tableObj.getClass(), (Field) property, HorizontalRecords.class);
            }

            if (iterateTables.tableLabel().equals(ann.tableLabel())) {
            	//
                HorizontalRecords records = new HorizontalRecordsForIterateTable(
                        ann,headerColumn, headerRow);

                // horizontal record-mapping
                if(property instanceof Method){
	                processor.doProcess(wSheet, tableObj, (Method) property, records,
	                        reader, needPostProcess);

                } else if(property instanceof Field){
	                processor.doProcess(wSheet, tableObj, (Field) property, records,
	                        reader, needPostProcess);
                }
            }
        }
    }

}
