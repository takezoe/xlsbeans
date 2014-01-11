package net.java.amateras.xlsbeans;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.java.amateras.xlsbeans.annotation.PostProcess;
import net.java.amateras.xlsbeans.annotation.Sheet;
import net.java.amateras.xlsbeans.processor.FieldProcessor;
import net.java.amateras.xlsbeans.processor.FieldProcessorFactory;
import net.java.amateras.xlsbeans.xml.AnnotationReader;
import net.java.amateras.xlsbeans.xml.XMLInfo;
import net.java.amateras.xlsbeans.xml.XMLLoader;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;
import net.java.amateras.xlsbeans.xssfconverter.WWorkbook;
import net.java.amateras.xlsbeans.xssfconverter.WorkbookFinder;

/**
 * This is the entry point of XLSBeans.
 *
 * @author Naoki Takezoe
 */
public class XLSBeans {

    private XLSBeansConfig config = new XLSBeansConfig();

	/**
	 * Constructor.
	 */
	public XLSBeans(){
		super();
	}

    public void setConfig(XLSBeansConfig config){
        this.config = config;
    }

	/**
	 * Loads one sheet as the single object from the Excel file.
	 * using default JExcel API.
	 *
	 * @param <P> the type parameter for the object type
	 * @param xlsIn the InputStream of the Excel file
	 * @param clazz the object type
	 * @return the loaded object
	 * @throws XLSBeansException
	 */
	public <P> P load(InputStream xlsIn, Class<P> clazz) throws XLSBeansException {
		return load(xlsIn, null, clazz);
	}

	/**
	 * Loads one sheet as the single object from the Excel file.
	 *
	 * @param <P> the type parameter for the object type
	 * @param xlsIn the InputStream of the Excel file
	 * @param clazz the object type
	 * @param bookType (WorkbookFinder.TYPE_HSSF/TYPE_XSSF/TYPE_JXL)
	 * @return the loaded object
	 * @throws XLSBeansException
	 */
	public <P> P load(InputStream xlsIn, Class<P> clazz, String bookType) throws XLSBeansException {
		return load(xlsIn, null, clazz, bookType);
	}

	private <P> P loadSheet(Class<P> clazz, WSheet wSheet, AnnotationReader reader) throws Exception {

		List<NeedPostProcess> needPostProcess = new ArrayList<NeedPostProcess>();
		P obj = clazz.newInstance();

		try {
			for(Method method : clazz.getMethods()){
				for(Annotation ann : reader.getAnnotations(clazz, method)){
					FieldProcessor processor = FieldProcessorFactory.getProcessor(ann);
					if(processor != null){
						processor.doProcess(wSheet, obj, method, ann, reader, config, needPostProcess);
						break;
					} else if(ann instanceof PostProcess){
						NeedPostProcess post = new NeedPostProcess(obj, method);
						needPostProcess.add(post);
						break;
					}
				}
			}

			for(Field field : clazz.getFields()){
				for(Annotation ann : reader.getAnnotations(clazz, field)){
					FieldProcessor processor = FieldProcessorFactory.getProcessor(ann);
					if(processor != null){
						processor.doProcess(wSheet, obj, field, ann, reader, config, needPostProcess);
						break;
					}
				}
			}

			for(NeedPostProcess need : needPostProcess){
				need.getMethod().invoke(need.getTarget(), new Object[0]);
			}

		} catch(InvocationTargetException ex){
			Throwable t = ex.getCause();
			if(t != null && t instanceof Exception){
				throw (Exception)t;
			}
			throw ex;
		}

		return obj;
	}

	private WSheet[] findSheet(WWorkbook w, Sheet sheet) throws XLSBeansException {
		if(sheet == null){
			throw new XLSBeansException("Can't find @Sheet.");
		}

//		jxl.Sheet jxlSheet = null;
		if(sheet.name().length() > 0){
			WSheet wSheet = w.getSheet(sheet.name());
			if(wSheet == null){
				throw new SheetNotFoundException(sheet.name());
			}
			return new WSheet[]{wSheet};

		} else if(sheet.number() != -1){
			if(sheet.number() >= w.getSheets().length){
				throw new SheetNotFoundException(sheet.number());
			}
			WSheet wSheet = w.getSheet(sheet.number());
			return new WSheet[]{wSheet};

		} else if(sheet.regex().length() > 0){
			Pattern pattern = Pattern.compile(sheet.regex());
			List<WSheet> matches = new ArrayList<WSheet>();
			for(WSheet wSheet: w.getSheets()){
				if(pattern.matcher(wSheet.getName()).matches()){
					matches.add(wSheet);
				}
			}
			if(matches.size() > 0){
				return matches.toArray(new WSheet[matches.size()]);
			}
			throw new SheetNotFoundException(sheet.number());
		}

		throw new XLSBeansException("@Sheet requires name or number parameter.");
	}

	/**
	 * Loads one sheet as the single object from the Excel file.
	 * using default JExcel API.
	 *
	 * @param <P> the type parameter for the object type
	 * @param xlsIn the InputStream of the Excel file
	 * @param xmlIn the InputStream of the External annotation definition file
	 * @param clazz the object type
	 * @return the loaded object
	 * @throws XLSBeansException
	 */
	public <P> P load(InputStream xlsIn, InputStream xmlIn, Class<P> clazz) throws XLSBeansException {
		return load(xlsIn, xmlIn, clazz, WorkbookFinder.TYPE_JXL);
	}

	/**
	 * Loads one sheet as the single object from the Excel file.
	 *
	 * @param <P> the type parameter for the object type
	 * @param xlsIn the InputStream of the Excel file
	 * @param xmlIn the InputStream of the External annotation definition file
	 * @param clazz the object type
	 * @param bookType (WorkbookFinder.TYPE_HSSF/TYPE_XSSF/TYPE_JXL)
	 * @return the loaded object
	 * @throws XLSBeansException
	 */
	public <P> P load(InputStream xlsIn, InputStream xmlIn, Class<P> clazz, String bookType) throws XLSBeansException {
		try {
			XMLInfo info = null;
			if(xmlIn != null){
				info = XMLLoader.load(xmlIn);
			}

			AnnotationReader reader = new AnnotationReader(info);

			WWorkbook w = WorkbookFinder.find(xlsIn, bookType);

			Sheet sheet = reader.getAnnotation(clazz, Sheet.class);
			if(sheet == null){
				throw new XLSBeansException("Can't find @Sheet.");
			}
			try {
				WSheet[] jxlSheets = findSheet(w, sheet);
				return loadSheet(clazz, jxlSheets[0], reader);

			} catch(SheetNotFoundException ex){
				if(config.isIgnoreSheetNotFound()){
					return null;
				} else {
					throw ex;
				}
			}
		} catch(XLSBeansException ex){
			throw ex;
		} catch(Exception ex){
			throw new XLSBeansException(ex);
		}
	}

	/**
	 * Loads sheets as multiple objects from the Excel file.
	 * using default JExcel API.
	 *
	 * @param <P> the type parameter for the object type
	 * @param xlsIn the InputStream of the Excel file
	 * @param clazz the object type
	 * @return the loaded objects
	 * @throws XLSBeansException
	 */
	public <P> P[] loadMultiple(InputStream xlsIn, Class<P> clazz) throws XLSBeansException {
		return loadMultiple(xlsIn, null, clazz);
	}

	/**
	 * Loads sheets as multiple objects from the Excel file.
	 * using default JExcel API.
	 *
	 * @param <P> the type parameter for the object type
	 * @param xlsIn the InputStream of the Excel file
	 * @param xmlIn the InputStream of the External annotation definition file
	 * @param clazz the object type
	 * @return the loaded objects
	 * @throws XLSBeansException
	 */
	public <P> P[] loadMultiple(InputStream xlsIn, InputStream xmlIn, Class<P> clazz) throws XLSBeansException {
		return loadMultiple(xlsIn, xmlIn, clazz, WorkbookFinder.TYPE_JXL);
	}

	/**
	 * Loads sheets as multiple objects from the Excel file.
	 *
	 * @param <P> the type parameter for the object type
	 * @param xlsIn the InputStream of the Excel file
	 * @param xmlIn the InputStream of the External annotation definition file
	 * @param clazz the object type
 	 * @param bookType (WorkbookFinder.TYPE_HSSF/TYPE_XSSF/TYPE_JXL)
	 * @return the loaded objects
	 * @throws XLSBeansException
	 */
	@SuppressWarnings("unchecked")
	public <P> P[] loadMultiple(InputStream xlsIn, InputStream xmlIn, Class<P> clazz, String bookType) throws XLSBeansException {
		try {
			XMLInfo info = null;
			if(xmlIn != null){
				info = XMLLoader.load(xmlIn);
			}

			AnnotationReader reader = new AnnotationReader(info);
			WWorkbook w = WorkbookFinder.find(xlsIn, bookType);

			Sheet sheet = reader.getAnnotation(clazz, Sheet.class);
			if(sheet == null){
				throw new XLSBeansException("Can't find @Sheet.");
			}

			List<P> list = new ArrayList<P>();

			if(sheet.number() == -1 && sheet.name().length() == 0 && sheet.regex().length() == 0){
				// If sheet isn't specified
				WSheet[] sheets = w.getSheets();
				for(WSheet wSheet : sheets){
					list.add(loadSheet(clazz, wSheet, reader));
				}
			} else {
				// If sheet is specified
				try {
					for(WSheet wSheet: findSheet(w, sheet)){
						list.add(loadSheet(clazz, wSheet, reader));
					}
				} catch(SheetNotFoundException ex){
					if(!config.isIgnoreSheetNotFound()){
						throw ex;
					}
				}
			}

			return list.toArray((P[])Array.newInstance(clazz, list.size()));

		} catch(XLSBeansException ex){
			throw ex;
		} catch(Exception ex){
			throw new XLSBeansException(ex);
		}
	}

	/**
	 * Loads sheets as multiple type objects from the Excel file.
	 * using default JExcel API.
	 *
	 * @param xlsIn the InputStream of the Excel file
	 * @param classes the object types
	 * @return the loaded objects
	 * @throws XLSBeansException
	 */
	public Object[] loadMultiple(InputStream xlsIn, Class<?>[] classes) throws XLSBeansException {
		return loadMultiple(xlsIn, null, classes);
	}

	/**
	 * Loads sheets as multiple type objects from the Excel file.
	 *
	 * @param xlsIn the InputStream of the Excel file
	 * @param classes the object types
 	 * @param bookType (WorkbookFinder.TYPE_HSSF/TYPE_XSSF/TYPE_JXL)
	 * @return the loaded objects
	 * @throws XLSBeansException
	 */
	public Object[] loadMultiple(InputStream xlsIn, Class<?>[] classes, String bookType) throws XLSBeansException {
		return loadMultiple(xlsIn, null, classes, bookType);
	}

	/**
	 * Loads sheets as multiple type objects from the Excel file.
	 * using default JExcel API.
	 *
	 * @param xlsIn the InputStream of the Excel file
	 * @param xmlIn the InputStream of the External annotation definition file
	 * @param classes the object types
	 * @return the loaded objects
	 * @throws XLSBeansException
	 */
	public Object[] loadMultiple(InputStream xlsIn, InputStream xmlIn, Class<?>[] classes) throws XLSBeansException {
		return loadMultiple(xlsIn, xmlIn, classes, WorkbookFinder.TYPE_JXL);
	}

	/**
	 * Loads sheets as multiple type objects from the Excel file.
	 *
	 * @param xlsIn the InputStream of the Excel file
	 * @param xmlIn the InputStream of the External annotation definition file
	 * @param classes the object types
 	 * @param bookType (WorkbookFinder.TYPE_HSSF/TYPE_XSSF/TYPE_JXL)
	 * @return the loaded objects
	 * @throws XLSBeansException
	 */
	public Object[] loadMultiple(InputStream xlsIn, InputStream xmlIn, Class<?>[] classes, String bookType) throws XLSBeansException {
		try {
			XMLInfo info = null;
			if(xmlIn != null){
				info = XMLLoader.load(xmlIn);
			}

			AnnotationReader reader = new AnnotationReader(info);

			WWorkbook w = WorkbookFinder.find(xlsIn, bookType);

			List<Object> list = new ArrayList<Object>();

			for(Class<?> clazz : classes){
				Sheet sheet = reader.getAnnotation(clazz, Sheet.class);
				if(sheet == null){
					throw new XLSBeansException("Can't find @Sheet.");
				}
				try {
					for(WSheet wSheet: findSheet(w, sheet)){
						list.add(loadSheet(clazz, wSheet, reader));
					}
				} catch(SheetNotFoundException ex){
					if(!config.isIgnoreSheetNotFound()){
						throw ex;
					}
				}
			}

			return list.toArray();

		} catch(XLSBeansException ex){
			throw ex;
		} catch(Exception ex){
			throw new XLSBeansException(ex);
		}
	}

}
