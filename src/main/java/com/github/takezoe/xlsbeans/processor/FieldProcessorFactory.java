package com.github.takezoe.xlsbeans.processor;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.github.takezoe.xlsbeans.annotation.*;

/**
 * @author Naoki Takezoe
 */
public class FieldProcessorFactory {

  private static ConcurrentHashMap<Class<? extends Annotation>, FieldProcessor> map = new ConcurrentHashMap<Class<? extends Annotation>, FieldProcessor>();

  static {
    map.put(Cell.class, new CellProcessor());
    map.put(LabelledCell.class, new LabelledCellProcessor());
    map.put(HorizontalRecords.class, new HorizontalRecordsProcessor());
    map.put(VerticalRecords.class, new VerticalRecordsProcessor());
    map.put(SheetName.class, new SheetNameProcessor());
    map.put(IterateTables.class, new IterateTablesProcessor());

    try {
      InputStream in = FieldProcessorFactory.class.getResourceAsStream("/xlsbeans.properties");
      if (in != null) {
        Properties props = new Properties();
        props.load(in);

        ClassLoader clsLoader = Thread.currentThread().getContextClassLoader();
        if (clsLoader == null) {
          clsLoader = FieldProcessorFactory.class.getClassLoader();
        }

        for (Map.Entry<Object, Object> entry : props.entrySet()) {
          try {
            Class<? extends Annotation> annoClazz = clsLoader.loadClass((String) entry.getKey()).asSubclass(Annotation.class);
            Class<? extends FieldProcessor> procClazz = clsLoader.loadClass((String) entry.getValue()).asSubclass(FieldProcessor.class);
            map.put(annoClazz, procClazz.newInstance());
          } catch (Exception ex) {
            // TODO Logging or throw exception
            ex.printStackTrace();
          }
        }
      }
    } catch (Exception ex) {
      // TODO Logging or throw exception
      ex.printStackTrace();
    }
  }

  /**
   * Registers the annotation and the field processor to this factory.
   *
   * @param ann       the annotation type
   * @param processor the field processor which process the given annotation
   */
  public static void registerProcessor(Class<? extends Annotation> ann, FieldProcessor processor) {
    map.put(ann, processor);
  }

  /**
   * Returns the <code>FieldProcessor</code> which corresponds to the given annotation.
   *
   * @param ann the field annotation
   * @return the field processor. If processors which correspond to the
   * given annotation are not registered, this method returns <code>null</code>.
   */
  public static FieldProcessor getProcessor(Annotation ann) {
    return map.get(ann.annotationType());
  }

}
