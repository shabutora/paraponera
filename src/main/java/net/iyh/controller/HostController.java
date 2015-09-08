package net.iyh.controller;

import net.iyh.model.RegistryHost;
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
  @RequestMapping(method = RequestMethod.GET)
  public List<RegistryHost> get() {
    // TODO impl
    return new ArrayList<RegistryHost>();
  }

  @RequestMapping(method = RequestMethod.POST, consumes = "applcation/json")
  public String post(@RequestParam ) {
    return null;
  }
}
