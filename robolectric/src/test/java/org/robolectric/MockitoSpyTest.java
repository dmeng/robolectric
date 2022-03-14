package org.robolectric;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MockitoSpyTest {

  private SpyClass spy;

  @Before
  public void setup() {
    spy = spy(new SpyClass());
    Instance.instance = mock(Instance.class);
    // This fails as-is. But this will pass if when().thenReturn() is replaced with doReturn().when()
    when(spy.getValue()).thenReturn(true);
    // doReturn(true).when(spy).getValue();
  }

  @Test
  public void isTrue() {
    assertThat(spy.getValue()).isTrue();
  }

  static class SpyClass {
    public boolean getValue() {
      // If using when().thenReturn() above: this fails. But if replaced with just one of the two, the test passes
      return Instance.getInstance().getValue() && false;
    }
  }

  static class Instance {
    public static Instance instance;

    public boolean getValue() {
      return false;
    }

    public static Instance getInstance() {
      if (instance == null) {
        instance = new Instance();
      }

      return instance;
    }
  }
}
