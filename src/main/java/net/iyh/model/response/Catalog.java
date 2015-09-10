package net.iyh.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

/**
 * Docker RegistryのCatalogレスポンス
 * @author tsukasa.tamaru
 * @since 1.0.0
 */
@Data
public class Catalog {
  private List<String> repositories;
}
