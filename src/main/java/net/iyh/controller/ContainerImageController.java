package net.iyh.controller;

import net.iyh.model.ContainerImage;
import net.iyh.repository.ContainerImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

  @RequestMapping(method = RequestMethod.GET,
                  consumes = MediaType.APPLICATION_JSON_VALUE,
                  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ContainerImage>> get() {
    return ResponseEntity.ok(this.repo.getAll());
  }
}
