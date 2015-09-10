package net.iyh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.iyh.vo.image.ImageName;

import java.util.List;

/**
 * Created by tsukasa on 2015/09/07.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContainerImage {
  @JsonProperty("host_name")
  private String hostName;
  @JsonProperty("image_name")
  private String imageName;
  @JsonProperty("tag_list")
  private List<String> tagList;

  public static final String KEY_FORMAT = "image:%s:%s";

  public String getKey() {
    return createKey(this.hostName, this.imageName);
  }

  public static String createKey(String hostName, String imageName) {
    return String.format(KEY_FORMAT, hostName, imageName);
  }
}
