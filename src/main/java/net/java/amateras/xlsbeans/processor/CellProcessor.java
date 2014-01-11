package net.java.amateras.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import net.java.amateras.xlsbeans.NeedPostProcess;
import net.java.amateras.xlsbeans.Utils;
import net.java.amateras.xlsbeans.XLSBeans;
import net.java.amateras.xlsbeans.XLSBeansConfig;
import net.java.amateras.xlsbeans.annotation.Cell;
import net.java.amateras.xlsbeans.xml.AnnotationReader;
import net.java.amateras.xlsbeans.xssfconverter.WCell;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;

/**
 * The {@link net.java.amateras.xlsbeans.processor.FieldProcessor}
 * inplementation for {@link net.java.amateras.xlsbeans.annotation.Cell}.
 *
 * @author Naoki Takezoe
 * @see net.java.amateras.xlsbeans.annotation.Cell
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
