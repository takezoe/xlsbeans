package com.github.takezoe.xlsbeans.annotation;

import java.lang.annotation.Annotation;

/**
 * @author Mitsuyoshi Hasegawa
 */
public class HorizontalRecordsForIterateTable implements HorizontalRecords {

  private int _headerColumn = -1;
  private int _headerRow = -1;
  private boolean _optional = false;
  private int _range = -1;
  private Class<?> _recordClass = null;
  private String _tableLabel = "";
  private RecordTerminal _terminal = null;
  private Class<? extends Annotation> _annotationType = null;
  private String _terminateLabel = null;
  private int _bottom = 1;
  private int _headerCount = 0;

  public HorizontalRecordsForIterateTable(HorizontalRecords rec, int headerColumn, int headerRow) {
    this._headerColumn = headerColumn;
    this._headerRow = headerRow;

    this._optional = rec.optional();
    this._range = rec.range();
    this._recordClass = rec.recordClass();
    // Tablelabel is permanent empty.
    this._tableLabel = "";
    this._terminal = rec.terminal();
    this._annotationType = rec.annotationType();
    this._terminateLabel = rec.terminateLabel();
    this._bottom = 1;
    this._headerCount = rec.headerLimit();
  }

  public int headerColumn() {
    return this._headerColumn;
  }

  public int headerRow() {
    return this._headerRow;
  }

  public boolean optional() {
    return this._optional;
  }

  public int range() {
    return this._range;
  }

  public Class<?> recordClass() {
    return this._recordClass;
  }

  public String tableLabel() {
    return this._tableLabel;
  }

  public RecordTerminal terminal() {
    return this._terminal;
  }

  public Class<? extends Annotation> annotationType() {
    return this._annotationType;
  }

  public String terminateLabel() {
    return this._terminateLabel;
  }

  public int bottom() {
    return this._bottom;
  }

  public int headerLimit() {
    return this._headerCount;
  }
}
