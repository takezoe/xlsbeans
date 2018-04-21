package com.github.takezoe.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.github.takezoe.xlsbeans.NeedPostProcess;
import com.github.takezoe.xlsbeans.Utils;
import com.github.takezoe.xlsbeans.XLSBeansConfig;
import com.github.takezoe.xlsbeans.annotation.Cell;
import com.github.takezoe.xlsbeans.xml.AnnotationReader;
import com.github.takezoe.xlsbeans.xssfconverter.WCell;
import com.github.takezoe.xlsbeans.xssfconverter.WSheet;

/**
 * The {@link FieldProcessor}
 * inplementation for {@link Cell}.
 *
 * @author Naoki Takezoe
 * @see Cell
 */
public class CellProcessor implements FieldProcessor {

	public void doProcess(WSheet wSheet, Object obj, Method setter, Annotation ann, AnnotationReader reader,
												XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {
		Cell cell = (Cell)ann;
		Utils.setPosition(cell.column(), cell.row(), obj, Utils.toPropertyName(setter.getName()));

		WCell wCell = wSheet.getCell(cell.column(), cell.row());
		Utils.invokeSetter(setter, obj, wCell.getContents(), config);
	}

	public void doProcess(WSheet wSheet, Object obj, Field field, Annotation ann, AnnotationReader reader,
			XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {
		Cell cell = (Cell)ann;
		Utils.setPosition(cell.column(), cell.row(), obj, field.getName());

		WCell wCell = wSheet.getCell(cell.column(), cell.row());
		Utils.setField(field, obj, wCell.getContents(), config);
	}

}
