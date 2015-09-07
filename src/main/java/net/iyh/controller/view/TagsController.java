package net.iyh.controller.view;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * Created by tsukasa on 2015/09/07.
 */
@RestController public class TagsController {
  @RequestMapping("${}/tags")
  public ResponseBody get() {
    return null;
  }
}
