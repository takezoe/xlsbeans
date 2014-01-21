package net.java.amateras.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import net.java.amateras.xlsbeans.NeedPostProcess;
import net.java.amateras.xlsbeans.Utils;
import net.java.amateras.xlsbeans.XLSBeansConfig;
import net.java.amateras.xlsbeans.XLSBeansException;
import net.java.amateras.xlsbeans.annotation.LabelledCell;
import net.java.amateras.xlsbeans.xml.AnnotationReader;
import net.java.amateras.xlsbeans.xssfconverter.WCell;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;

/**
 * The {@link net.java.amateras.xlsbeans.processor.FieldProcessor}
 * inplementation for {@link net.java.amateras.xlsbeans.annotation.LabelledCell}.
 *
 * @author Naoki Takezoe
 * @see net.java.amateras.xlsbeans.annotation.LabelledCell
 */
public class LabelledCellProcessor implements FieldProcessor {

	public void doProcess(WSheet wSheet, Object obj, Method setter, Annotation ann, AnnotationReader reader,
                          XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {
		doProcess(wSheet, obj, setter, null, ann, reader, config, needPostProcess);
	}

	public void doProcess(WSheet wSheet, Object obj, Field field, Annotation ann, AnnotationReader reader,
                          XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {
		doProcess(wSheet, obj, null, field, ann, reader, config, needPostProcess);
	}

	private void doProcess(WSheet wSheet, Object obj, Method setter, Field field, Annotation ann, AnnotationReader reader,
                           XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {

		LabelledCell cell = (LabelledCell) ann;
		WCell targetCell = null;

		int column = -1;
		int row = -1;

		if(cell.label().equals("")){
			column = cell.labelColumn();
			row = cell.labelRow();
		} else {
			try {
				if(cell.headerLabel().equals("")){
					WCell labelCell = Utils.getCell(wSheet, cell.label(), 0, config);
					column = labelCell.getColumn();
					row = labelCell.getRow();
				} else {
					WCell headerCell = Utils.getCell(wSheet, cell.headerLabel(), 0, config);
					WCell labelCell = Utils.getCell(wSheet, cell.label(), headerCell.getRow() + 1, config);
					column = labelCell.getColumn();
					row = labelCell.getRow();
				}
			} catch(XLSBeansException ex){
				if(cell.optional()){
					return;
				} else {
					throw ex;
				}
			}
		}

		int range = cell.range();
		if(range < 1){
			range = 1;
		}

		for(int i = cell.skip(); i < range; i++){
			switch(cell.type()){
			case Left:
				targetCell = wSheet.getCell(column - (1 * (i + 1)), row);
				break;
			case Right:
				targetCell = wSheet.getCell(column + (1 * (i + 1)), row);
				break;
			case Bottom:
				targetCell = wSheet.getCell(column, row + (1 * (i + 1)));
				break;
			default:
				throw new XLSBeansException("Cell type is invalid.");
			}
			if(targetCell.getContents().length()>0){
				break;
			}
		}
		if(setter != null){
			Utils.setPosition(targetCell.getColumn(), targetCell.getRow(), obj, Utils.toPropertyName(setter.getName()));
			Utils.invokeSetter(setter, obj, targetCell.getContents(), config);
		}

		if(field != null){
			Utils.setPosition(targetCell.getColumn(), targetCell.getRow(), obj, field.getName());
			Utils.setField(field, obj, targetCell.getContents(), config);
		}
	}

}
