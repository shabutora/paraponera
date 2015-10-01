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

/**
 * Created by tsukasa on 2015/09/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ParaponeraApplication.class)
@WebAppConfiguration
public class ManifestRepositoryTest {

  @Autowired
  ManifestRepository repo;

  @Test
  public void testRequestNormal() throws Exception {
    RegistryHost host = new RegistryHost("localhost", "localhost");
    Optional<Manifest> manifest = this.repo.request(host, "busybox", "1.23.2");
  }
}
