package net.iyh.scheduller;

import lombok.extern.slf4j.Slf4j;
import net.iyh.helper.RegistryHostHelper;
import net.iyh.model.ContainerImage;
import net.iyh.model.RegistryHost;
import net.iyh.model.response.Catalog;
import net.iyh.model.response.Image;
import net.iyh.repository.ContainerImageRepository;
import net.iyh.repository.RegistryHostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Docker Resitryのインポートスケジューラー
 * Created by tsukasa on 2015/09/07.
 */
@Component
@Slf4j
public class RegistryImportSchedule {

  @Autowired RegistryHostRepository regRepo;
  @Autowired
  ContainerImageRepository imgRepo;

  @Value("${registry.v2.catalog}")
  String catalogUri;

  @Value("${registry.v2.tags}")
  String tagsUri;

  @Scheduled(cron = "${schedule.registry.import}")
//  @Scheduled(fixedRate = 10000) // 実験用に残しとく
  public void importImages() {
    List<RegistryHost> list = this.regRepo.get();
    RestTemplate restTemplate = new RestTemplate();
    list.stream().forEach(h -> {
      String url = RegistryHostHelper.createUri(h, this.catalogUri);
      List<String> repositories = this.getRepositories(restTemplate, url);
      this.tags(restTemplate, h, repositories);
    });
  }

  private List<String> getRepositories(RestTemplate restTemplate, String url) {
    try {
      ResponseEntity<Catalog> res = restTemplate.getForEntity(url, Catalog.class);
      if (res.getStatusCode().is2xxSuccessful()) {
        return res.getBody().getRepositories();
      }
    } catch (ResourceAccessException ex) {
      log.warn("Failed read catalog. " + url, ex);
    }
    return new ArrayList<>();
  }

  private void tags(RestTemplate restTemplate, RegistryHost host, List<String> repositoryNames) {
    repositoryNames.stream().forEach(n -> {
      String uri = UriComponentsBuilder.fromUriString(host.getUrl())
                                       .pathSegment(n).pathSegment(this.tagsUri).build().toUriString();
      log.info(uri);
      try {
        ResponseEntity<Image> res = restTemplate.getForEntity(uri, Image.class);
        if (res.getStatusCode().is2xxSuccessful()) {
          Image image = res.getBody();
          imgRepo.save(new ContainerImage(host.getName(), image.getName(), image.getTags()));
        }
      } catch (HttpClientErrorException ex) {
        log.warn("OOOOOOOps!!! failed import tags. status = " + ex.getStatusCode().toString() + " " + uri);
      }
    });
  }
}
