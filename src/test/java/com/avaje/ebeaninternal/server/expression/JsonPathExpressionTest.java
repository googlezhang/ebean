package com.avaje.ebeaninternal.server.expression;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class JsonPathExpressionTest {

  @NotNull
  private JsonPathExpression exp(String propertyName, String path, Op operator, Object value) {
    return new JsonPathExpression(propertyName, path, operator, value);
  }

  @Test
  public void isSameByPlan_when_same() {

    assertThat(exp("a", "path", Op.EQ, 10).isSameByPlan(exp("a", "path", Op.EQ, 10))).isTrue();
  }

  @Test
  public void isSameByPlan_when_diffBind_same() {

    assertThat(exp("a", "path", Op.EQ, 10).isSameByPlan(exp("a", "path", Op.EQ, 20))).isTrue();
  }

  @Test
  public void isSameByPlan_when_diffPath() {

    assertThat(exp("a", "path", Op.EQ, 10).isSameByPlan(exp("a", "pathDiff", Op.EQ, 10))).isFalse();
  }

  @Test
  public void isSameByPlan_when_diffProperty_diff() {

    assertThat(exp("a", "path", Op.EQ, 10).isSameByPlan(exp("b", "path", Op.EQ, 10))).isFalse();
  }

  @Test
  public void isSameByPlan_when_diffOperator_diff() {

    assertThat(exp("a", "path", Op.EQ, 10).isSameByPlan(exp("a", "path", Op.LT, 10))).isFalse();
  }

  @Test
  public void isSameByPlan_when_diffType_diff() {

    assertThat(exp("a", "path", Op.EQ, 10).isSameByPlan(new NoopExpression())).isFalse();
  }

  @Test
  public void isSameByBind_when_sameBindValues() {

    assertThat(exp("a", "path", Op.EQ, 10).isSameByBind(exp("a", "path", Op.LT, 10))).isTrue();
  }

  @Test
  public void isSameByBind_when_sameNullBindValues() {

    assertThat(exp("a", "path", Op.EQ, null).isSameByBind(exp("a", "path", Op.LT, null))).isTrue();
  }

  @Test
  public void isSameByBind_when_diffBindValues() {

    assertThat(exp("a", "path", Op.EQ, 10).isSameByBind(exp("a", "path", Op.EQ, "junk"))).isFalse();
  }

  @Test
  public void isSameByBind_when_diffFirstNullBindValues() {

    assertThat(exp("a", "path", Op.EQ, null).isSameByBind(exp("a", "path", Op.LT, 10))).isFalse();
  }

  @Test
  public void isSameByBind_when_diffLastNullBindValues() {

    assertThat(exp("a", "path", Op.EQ, 10).isSameByBind(exp("a", "path", Op.LT, null))).isFalse();
  }

}