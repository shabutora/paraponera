package net.iyh.controller;

import net.iyh.model.ContainerImage;
import net.iyh.repository.ContainerImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by tsukasa on 2015/09/07.
 */
@RestController
@RequestMapping("/images")
public class ContainerImageController {
  @Autowired
  ContainerImageRepository repo;

  @RequestMapping(value = "/{host}",
                  method = RequestMethod.GET,
                  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ContainerImage>> get(@PathVariable String host) {
    return ResponseEntity.ok(this.repo.getByHost(host));
  }
}
