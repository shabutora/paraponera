package net.iyh.model;

import lombok.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by tsukasa on 2015/09/07.
 */
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class RegistryHost {
  @NonNull
  private String name;

  @NonNull
  private String host;

  private int port = 5000;

  private String version = "v2";

  public static String KEY_FORMAT = "registry:host:%s";

  /**
   * URLの取得
   * @return URL文字列
   */
  public String getUrl() {
    return UriComponentsBuilder.newInstance().scheme("http")
                                             .host(this.host)
                                             .port(this.port)
                                             .pathSegment(this.version)
                                             .build()
                                             .toUriString();
  }

  /**
   * キーの取得
   * @return キー文字列
   */
  public String getKey() {
    return createKey(this.name);
  }

  /**
   * キー文字列作成
   * @param name ホスト名
   * @return キー文字列
   */
  public static String createKey(String name) {
    return String.format(KEY_FORMAT, name);
  }
}
