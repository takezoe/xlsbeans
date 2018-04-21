package com.github.takezoe.xlsbeans.annotation;

import java.lang.annotation.Annotation;

/**
 *
 * @author Mitsuyoshi Hasegawa
 */
public class LabelledCellForIterateTable implements LabelledCell{

    private String _label = null;
    private int _labelColumn = -1;
    private int _labelRow = -1;
    private boolean _optional = false;
    private int _range = -1;
    private LabelledCellType _type = null;
    private Class<? extends Annotation> _annotationType = null;
    private String _headerLabel = null;
    private int _skip = 0;

    public LabelledCellForIterateTable (LabelledCell labelledCell, int labelRow, int labelColumn) {
        this._label = "";
        this._labelColumn = labelColumn;
        this._labelRow = labelRow;
        this._optional = labelledCell.optional();
        this._range = labelledCell.range();
        this._type = labelledCell.type();
        this._annotationType = labelledCell.annotationType();
        this._headerLabel = labelledCell.headerLabel();
        this._skip = labelledCell.skip();
    }

    public String label() {
        return this._label;
    }

    public int labelColumn() {
        return this._labelColumn;
    }

    public int labelRow() {
        return this._labelRow;
    }

    public boolean optional() {
        return this._optional;
    }

    public int range() {
        return this._range;
    }

    public LabelledCellType type() {
        return this._type;
    }

    public Class<? extends Annotation> annotationType() {
        return this._annotationType;
    }

    public String headerLabel(){
        return this._headerLabel;
    }

    public int skip() {
    	return this._skip;
    }
}
