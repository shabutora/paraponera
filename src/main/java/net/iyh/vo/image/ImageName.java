package net.iyh.vo.image;

import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * コンテナイメージ名
 * Created by tsukasa on 2015/09/07.
 */
@AllArgsConstructor
public class ImageName {
  @NonNull
  private String name;

  public String get() {
    return this.name;
  }
}
