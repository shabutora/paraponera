package net.iyh.repository;

import net.iyh.ParaponeraApplication;
import net.iyh.model.RegistryHost;
import net.iyh.model.response.Image;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * Before:
 * ```
 * docker run -d -p 5000:5000 --name registry registry:2.2
 * docker pull hello-world
 * docker pull busybox:1.23.2
 * docker tag hello-world localhost:5000/hello-world:1
 * docker tag busybox:1.23.2 localhost:5000/busybox:1.23.2
 * docker push localhost:5000/hello-world:1 localhost:5000/busybox:1.23.2
 * ```
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ParaponeraApplication.class)
@WebAppConfiguration
public class ContainerImageRepositoryTest {

  @Autowired
  ContainerImageRepository repo;

  private static final RegistryHost TEST_REGISTRY = new RegistryHost("localhost", "localhost");

  @Test
  public void testRequestCatalog() throws Exception {
    List<String> actual = this.repo.requestCatalog(TEST_REGISTRY);
    assertThat(actual).contains("hello-world", "busybox");
  }
  @Test
  public void testRequestNoImage() throws Exception {
    Optional<Image> actual = this.repo.requestImage(TEST_REGISTRY, "aaaaaaa");
    assertThat(actual).isEmpty();
  }

  @Test
  public void testRequestImage() throws Exception {
    Optional<Image> actual = this.repo.requestImage(TEST_REGISTRY, "busybox");
    assertThat(actual.get().getName()).isEqualTo("busybox");
    assertThat(actual.get().getTags()).contains("1.23.2");
  }
}
