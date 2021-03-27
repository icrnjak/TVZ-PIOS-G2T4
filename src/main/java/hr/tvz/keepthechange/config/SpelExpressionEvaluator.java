package hr.tvz.keepthechange.config;

import org.jxls.expression.ExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * An implementation of JXLS {@link ExpressionEvaluator} which uses Spring
 * expression language to evaluate expressions in templates.
 * <p>
 * Every {@link Transformer} should get their own evaluator. More info:
 * jxls.sourceforge.net/reference/expression_language.html
 */
public class SpelExpressionEvaluator implements ExpressionEvaluator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpelExpressionEvaluator.class);
    private final String expressionString;
    private final SpelExpressionParser parser;
    private final StandardEvaluationContext evaluationContext;

    public SpelExpressionEvaluator(SpelExpressionParser parser, StandardEvaluationContext evaluationContext) {
        this(null, parser, evaluationContext);
    }

    public SpelExpressionEvaluator(String expressionString, SpelExpressionParser parser,
                                   StandardEvaluationContext evaluationContext) {
        this.expressionString = expressionString;
        this.parser = parser;
        this.evaluationContext = evaluationContext;
    }

    @Override
    public Object evaluate(String expressionString, Map<String, Object> context) {
        evaluationContext.setVariables(context);
        Expression expression = parser.parseExpression(expressionString);
        Object result;
        try {
            result = expression.getValue(evaluationContext);
        } catch (EvaluationException e) {
            LOGGER.error("An error occurred while evaluating expression >>{}<<", expressionString);
            throw e;
        }
        return result;
    }

    @Override
    public Object evaluate(Map<String, Object> context) {
        return this.evaluate(expressionString, context);
    }

    @Override
    public String getExpression() {
        return expressionString;
    }
}