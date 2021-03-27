package hr.tvz.keepthechange.service;

import hr.tvz.keepthechange.config.SpelExpressionEvaluator;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Contains methods used to transform JXLS templates.
 */
@Service
public class JxlsService {
    private final ObjectFactory<SpelExpressionEvaluator> spelExpressionEvaluatorObjectFactory;

    public JxlsService(ObjectFactory<SpelExpressionEvaluator> spelExpressionEvaluatorObjectFactory) {
        this.spelExpressionEvaluatorObjectFactory = spelExpressionEvaluatorObjectFactory;
    }

    /**
     * Transforms the given input stream template into a resulting workbook and writes it to output stream.
     *
     * @param is      template input stream
     * @param os      result output stream
     * @param context evaluation context with a variable map
     * @throws IOException if an error occurs during transformation
     */
    public void transform(InputStream is, OutputStream os, Context context) throws IOException {
        PoiTransformer transformer = createConfiguredPoiTransformer(is, os);
        JxlsHelper.getInstance().processTemplate(context, transformer);
    }

    /**
     * Creates a transformer which transforms the given workbook template, using a SpEL expression evaluator,
     * and writes the result workbook into a given output stream.
     *
     * @param is template input stream
     * @param os result output stream
     * @return configured transformer
     */
    private PoiTransformer createConfiguredPoiTransformer(InputStream is, OutputStream os) {
        SpelExpressionEvaluator spelExpressionEvaluator = spelExpressionEvaluatorObjectFactory.getObject();
        PoiTransformer transformer = PoiTransformer.createTransformer(is);
        transformer.setOutputStream(os);
        transformer.getTransformationConfig().setExpressionEvaluator(spelExpressionEvaluator);
        return transformer;
    }
}
