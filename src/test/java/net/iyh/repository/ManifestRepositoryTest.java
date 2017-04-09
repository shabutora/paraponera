package net.iyh.repository;

import net.iyh.ParaponeraApplication;
import net.iyh.model.RegistryHost;
import net.iyh.model.response.Manifest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


/**
 * TODO Registryのデータの事前準備未実装(docker java clientを使うか)
 * TODO 依存関係のDocker Registryの起動もセットアップでやれたらいいなあ
 *
 * Require: Run docker registry on local.(ver.2.2)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ParaponeraApplication.class)
@WebAppConfiguration
public class ManifestRepositoryTest {

  @Autowired
  ManifestRepository repo;

  private static final RegistryHost TEST_REGISTRY = new RegistryHost("localhost", "localhost");

  @Test
  public void testRequestNormal() throws Exception {
    Optional<Manifest> manifest = this.repo.request(TEST_REGISTRY, "busybox", "1.23.2");
    assertThat(manifest.get().getHost()).isEqualTo("localhost");
    assertThat(manifest.get().getName()).isEqualTo("busybox");
    assertThat(manifest.get().getTag()).isEqualTo("1.23.2");
  }

  @Test
  public void testRequestNotFound() throws Exception {
    Optional<Manifest> actual = this.repo.request(TEST_REGISTRY, "not-found-image", "tagtagtag");
    assertThat(actual).isEmpty();
  }

  @Test
  public void testSaveManifest() throws Exception {
    Manifest data = new Manifest();
    data.setHost("test-host");
    data.setName("test-image");
    data.setTag("test-tag");
    this.repo.save(data);
  }
}
