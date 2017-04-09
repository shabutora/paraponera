package net.iyh.helper;

import lombok.val;
import net.iyh.model.RegistryHost;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by tsukasa on 2017/04/09.
 */
public class RegistryHostHelperTest {
  @Test
  public void testPath() throws Exception {
    RegistryHost host = new RegistryHost("test", "test.paraponera.net", 8080, "v3");
    val actual = RegistryHostHelper.createUri(host, "test-path");
    assertThat(actual).isEqualTo("http://test.paraponera.net:8080/v3/test-path");
  }

  @Test
  public void testMultiPath() throws Exception {
    RegistryHost host = new RegistryHost("test", "test.paraponera.net", 8080, "v3");
    val actual = RegistryHostHelper.createUri(host, "test-path", "id", "name");
    assertThat(actual).isEqualTo("http://test.paraponera.net:8080/v3/test-path/id/name");
  }

  @Test
  public void testNoPath() throws Exception {
    RegistryHost host = new RegistryHost("test", "test.paraponera.net", 8080, "v3");
    val actual = RegistryHostHelper.createUri(host);
    assertThat(actual).isEqualTo("http://test.paraponera.net:8080/v3");
  }

  @Test(expected = NullPointerException.class)
  public void testNull() throws Exception {
    RegistryHostHelper.createUri(null);
  }
}
