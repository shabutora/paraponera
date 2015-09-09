package net.iyh.controller;

import net.iyh.model.RegistryHost;
import net.iyh.repository.RegistryHostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tsukasa.tamaru
 * @since 1.0.0
 */
@RequestMapping("/hosts")
@RestController
public class HostController {

  @Autowired
  RegistryHostRepository repo;

  @RequestMapping(method = RequestMethod.GET)
  public List<RegistryHost> get() {
    // TODO impl
    return new ArrayList<RegistryHost>();
  }

  @RequestMapping(method = RequestMethod.POST, consumes = "applcation/json")
  public String post() {
    RegistryHost host = new RegistryHost();
    this.repo.save(new RegistryHost("localhost", "localhost"));
    return null;
  }
}
