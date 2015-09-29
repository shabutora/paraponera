package net.iyh.controller;

import net.iyh.model.ContainerImage;
import net.iyh.model.response.Manifest;
import net.iyh.repository.ContainerImageRepository;
import net.iyh.repository.ManifestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tsukasa on 2015/09/07.
 */
@RestController
@RequestMapping("/images")
public class ContainerImageController {
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
}
