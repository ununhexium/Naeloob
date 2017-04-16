import net.lab0.antlr.naeloob.NaeloobBaseVisitor;
import net.lab0.antlr.naeloob.NaeloobParser;
import org.antlr.v4.runtime.Token;

import java.util.Map;

class EvalVisitor
    extends NaeloobBaseVisitor<Object> {

  private final Map<String, Object> variables;

  public EvalVisitor(Map<String, Object> variables) {
    this.variables = variables;
  }

  @Override
  public Object visitParse(NaeloobParser.ParseContext ctx) {
    return super.visit(ctx.expression());
  }

  @Override
  public Object visitDecimalExpression(NaeloobParser.DecimalExpressionContext ctx) {
    return Double.valueOf(ctx.DECIMAL().getText());
  }

  @Override
  public Object visitNotExpression(NaeloobParser.NotExpressionContext ctx) {
    return !((Boolean) this.visit(ctx.expression()));
  }

  @Override
  public Object visitParenExpression(NaeloobParser.ParenExpressionContext ctx) {
    return super.visit(ctx.expression());
  }

  @Override
  public Object visitComparatorExpression(NaeloobParser.ComparatorExpressionContext ctx) {
    if (ctx.op.EQ() != null) {
      return this.visit(ctx.left).equals(this.visit(ctx.right));
    }
    throw new RuntimeException("not implemented: comparator operator " + ctx.op.getText());
  }

  @Override
  public Object visitBinaryExpression(NaeloobParser.BinaryExpressionContext ctx) {
    if (ctx.op.AND() != null) {
      return asBoolean(ctx.left) && asBoolean(ctx.right);
    } else if (ctx.op.OR() != null) {
      return asBoolean(ctx.left) || asBoolean(ctx.right);
    }
    throw new RuntimeException("not implemented: binary operator " + ctx.op.getText());
  }

  @Override
  public Object visitBoolExpression(NaeloobParser.BoolExpressionContext ctx) {
    return Boolean.valueOf(ctx.getText());
  }

  private boolean asBoolean(NaeloobParser.ExpressionContext ctx) {
    return (Boolean) visit(ctx);
  }

  @Override
  public Object visitComparisonExpression(NaeloobParser.ComparisonExpressionContext ctx) {
    Object identifier = variables.get(ctx.IDENTIFIER().getText());
    String unquote = unquote(ctx.right.getText());
    return identifier.equals(unquote);
  }

  private String unquote(String text) {
    return text.substring(1, text.length()-1);
  }
}
