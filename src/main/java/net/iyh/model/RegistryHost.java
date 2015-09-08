package net.iyh.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by tsukasa on 2015/09/07.
 */
@AllArgsConstructor
@Data
public class RegistryHost {
  private String name;
  private int port;
  private String version = "v2";
}
