package net.iyh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by tsukasa on 2015/09/07.
 */
@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class RegistryHost {
  @NonNull
  private String name;

  @NonNull
  private String host;

  private int port = 5000;

  private String version = "v2";

  public static String KEY_FORMAT = "registry:host:%s";

  public String getKey() {
    return createKey(this.name);
  }

  /**
   * キー文字列作成
   * @param name
   * @return
   */
  public static String createKey(String name) {
    return String.format(KEY_FORMAT, name);
  }
}
