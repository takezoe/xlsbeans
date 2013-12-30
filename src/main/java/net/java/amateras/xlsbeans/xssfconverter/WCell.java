package net.java.amateras.xlsbeans.xssfconverter;

/**
 * Cell wapper.
 * (Java Excel API Wrapper Interface.)
 * 
 * @author Mitsuyoshi Hasegawa
 *
 */
public interface WCell {

	/**
	 * get cell contents.
	 * @return cell contents
	 */
	String getContents();
	
	/**
	 * get column index.(0-start).
	 * @return column index
	 */
	int getColumn();
	
	/**
	 * get row index.(0-start).
	 * @return row index
	 */
	int getRow();
	
	/**
	 * get cell format.
	 * @return cell format wrapper-object.
	 */
	WCellFormat getCellFormat();
}
