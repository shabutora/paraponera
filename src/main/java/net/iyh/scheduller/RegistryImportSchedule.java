package net.iyh.scheduller;

import lombok.extern.slf4j.Slf4j;
import net.iyh.model.ContainerImage;
import net.iyh.model.RegistryHost;
import net.iyh.model.response.Image;
import net.iyh.repository.ContainerImageRepository;
import net.iyh.repository.ManifestRepository;
import net.iyh.repository.RegistryHostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Docker Resitryのインポートスケジューラー
 * Created by tsukasa on 2015/09/07.
 */
@Component
@Slf4j
public class RegistryImportSchedule {

  @Autowired
  RegistryHostRepository regRepo;
  @Autowired
  ContainerImageRepository imgRepo;
  @Autowired
  ManifestRepository maniRepo;

  @Scheduled(cron = "${schedule.registry.import}")
//  @Scheduled(fixedRate = 10000) // 実験用に残しとく
  public void importImages() {
    List<RegistryHost> list = this.regRepo.get();
    list.stream().forEach(h -> {
      List<String> catalog = this.imgRepo.requestCatalog(h);
      this.containerImages(h, catalog);
    });
  }

  /**
   * コンテナイメージの登録
   * @param host Registryホスト
   * @param catalog カタログ
   */
  private void containerImages(RegistryHost host, List<String> catalog) {
    catalog.stream().forEach(n -> {
      Optional<Image> image = this.imgRepo.requestImage(host, n);
      image.ifPresent(i -> {
        imgRepo.save(new ContainerImage(host.getName(), i.getName(), i.getTags()));
        this.manifests(host, i);
      });
    });
  }

  /**
   * イメージマニフェストの登録
   * @param host Registryホスト
   * @param image コンテナイメージ
   */
  private void manifests(RegistryHost host, Image image) {
    image.getTags().stream().map(t -> this.maniRepo.request(host, image.getName(), t)).forEach(m -> {
      m.ifPresent(mf -> {
        this.maniRepo.save(mf);
      });
    });
  }
}
