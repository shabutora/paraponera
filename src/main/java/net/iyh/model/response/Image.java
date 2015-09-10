package net.iyh.model.response;

import lombok.Data;

import java.util.List;

/**
 * Docker RegistryのTagsレスポンス
 * @author tsukasa.tamaru
 * @since 1.0.0
 */
@Data
public class Image {
  private String name;
  private List<String> tags;
}
