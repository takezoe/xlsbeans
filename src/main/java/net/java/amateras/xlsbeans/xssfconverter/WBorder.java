package net.java.amateras.xlsbeans.xssfconverter;

/**
 * Excel border-wrapper.
 * (Java Excel API Wrapper.)
 * 
 * @author Mitsuyoshi Hasegawa
 *
 */
public class WBorder {

	private String description = null;
    public static final WBorder NONE = new WBorder("none");
    public static final WBorder ALL = new WBorder("all");
    public static final WBorder TOP = new WBorder("top");
    public static final WBorder BOTTOM = new WBorder("bottom");
    public static final WBorder LEFT = new WBorder("left");
    public static final WBorder RIGHT = new WBorder("right");

    /**
     * border-wrapper
     * 
     * @param description border description
     */
    public WBorder(String description) {
    	this.description = description;
    }
    
    /**
     * get border-description.
     * 
     * @return border-description.
     */
    public String getDescription() {
    	return description;
    }
}
