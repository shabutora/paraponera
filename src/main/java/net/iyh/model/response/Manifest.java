package net.iyh.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author tsukasa.tamaru
 * @since 1.0.0
 */
@Data
public class Manifest {
  private String host;
  private String name;
  private String tag;
  private String architecture;
  private List<Map<String, String>> fsLayers;
  private List<Map<String, String>> history;
  private int schemaVersion;
  private String digest;
  private List<Signature> signatures;

  private static final String KEY_FORMAT = "manifest:%s:%s:%s";

  public String getKey() {
    return createKye(this.host, this.name, this.tag);
  }

  public  static String createKye(String host, String image, String tag) {
    return String.format(KEY_FORMAT, host, image, tag);
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  private static class Signature {
    private String signature;
    @JsonProperty("protected")
    private String protectedValue;
  }
}
