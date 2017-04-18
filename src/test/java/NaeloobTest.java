import net.lab0.antlr.naeloob.NaeloobLexer;
import net.lab0.antlr.naeloob.NaeloobParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by uuh11 on 16/04/2017.
 */
@RunWith(Parameterized.class)
public class NaeloobTest {

  private String expression;
  private boolean expected;

  private Map<String, Object> variables;

  @Parameterized.Parameters(name = "{0} is {1}")
  public static List<Object[]> data() {
    return Arrays.asList(new Object[][]{
        // or
        {"TRUE|TRUE", true},
        {"TRUE|FALSE", true},
        {"FALSE|TRUE", true},
        {"FALSE|FALSE", false},

        // not
        {"!TRUE", false},
        {"!FALSE", true},

        // equal
        {"TRUE/FALSE", false},
        {"TRUE/TRUE", true},
        {"FALSE/FALSE", true},
        {"WORD/'value'", true},
        {"WORD/'else'", false},
        {"WORD2/\"WORD2\"", true},
        {"KEY/unquoted", true},
    });
  }

  public NaeloobTest(String expression, boolean expected) {
    this.expression = expression;
    this.expected = expected;
  }

  @Before
  public void before() {
    variables = new HashMap<String, Object>() {{
      put("WORD", "value");
      put("WORD2", "WORD2");
      put("KEY", "unquoted");
    }};
  }

  @Test
  public void eval_shouldEqualExpectedResult()
  throws Exception {
    NaeloobLexer lexer = new NaeloobLexer(new ANTLRInputStream(expression));
    NaeloobParser parser = new NaeloobParser(new CommonTokenStream(lexer));
    Object result = new EvalVisitor(variables).visit(parser.parse());
    Assert.assertEquals(expected, result);
  }
}
