package net.iyh.controller;

import net.iyh.model.ContainerImage;
import net.iyh.model.RegistryHost;
import net.iyh.model.response.Manifest;
import net.iyh.repository.ContainerImageRepository;
import net.iyh.repository.ManifestRepository;
import net.iyh.repository.RegistryHostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by tsukasa on 2015/09/07.
 */
@RestController
@RequestMapping("/images")
public class ContainerImageController {
  @Autowired RegistryHostRepository hostRepo;
  @Autowired ContainerImageRepository repo;
  @Autowired ManifestRepository manRepo;

  @RequestMapping(value = "/{host}",
                  method = RequestMethod.GET,
                  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ContainerImage>> get(
    @PathVariable
    String host) {
    return ResponseEntity.ok(this.repo.getByHost(host));
  }

  @RequestMapping(value = "/{host}/flat",
                  method = RequestMethod.GET,
                  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Manifest>> flat(
    @PathVariable
    String host) {
    List<ContainerImage> images = this.repo.getByHost(host);
    List<Manifest> manifests = images.stream().flatMap(i -> {
      return i.getTagList()
              .stream()
              .map(t -> this.manRepo.get(i.getHostName(), i.getImageName(), t))
              .filter(m -> m.isPresent())
              .map(m -> m.get());
    }).collect(Collectors.toList());
    return ResponseEntity.ok(manifests);
  }

  @RequestMapping(value = "/{host}/{image}",
                  method = RequestMethod.DELETE,
                  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> delete(
    @PathVariable String host,
    @PathVariable String image,
    @RequestParam("tag") Optional<String> tag) {
    this.deleteImage(host, Optional.empty(), image, tag);
    return ResponseEntity.ok("success delete container image");
  }

  @RequestMapping(value = "/{host}/{user}/{image}",
                  method = RequestMethod.DELETE,
                  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> delete(
    @PathVariable String host,
    @PathVariable String user,
    @PathVariable String image,
    @RequestParam("tag") Optional<String> tag) {
    this.deleteImage(host, Optional.of(user), image, tag);
    return ResponseEntity.ok("success delete container image");
  }

  /**
   * コンテナイメージ削除
   * @param host
   * @param user
   * @param image
   * @param tag
   * @return
   */
  private void deleteImage(String host, Optional<String> user, String image, Optional<String> tag) {
    RegistryHost h = this.hostRepo.get(RegistryHost.createKey(host));
    StringBuilder i = new StringBuilder(image);
    user.ifPresent(u -> i.insert(0, u).insert(u.length(), "/"));
    Optional<Manifest> manifest = this.manRepo.get(host, i.toString(), tag.orElse("latest"));
    manifest.ifPresent(m -> this.manRepo.delete(h, m));
  }
}
