package net.iyh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * この画面だけ。
 * @author tsukasa.tamaru
 * @since 1.0.0
 */
@Controller
public class IndexController {

  @RequestMapping("/")
  public String get() {
    return "index";
  }
}
