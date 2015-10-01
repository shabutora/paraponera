package net.iyh.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.iyh.model.RegistryHost;
import net.iyh.repository.RegistryHostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tsukasa.tamaru
 * @since 1.0.0
 */
@RequestMapping("/hosts")
@RestController
public class HostController {

  @Autowired RegistryHostRepository repo;

  /**
   * Registryホスト情報の取得
   * @return Registryホスト情報リスト
   */
  @RequestMapping(method = RequestMethod.GET,
                  produces = MediaType.APPLICATION_JSON_VALUE)
  public List<RegistryHost> get() {
    return this.repo.get();
  }

  /**
   * Registryホスト情報の登録
   * @param registryHost Registryホスト情報
   * @return 処理結果
   */
  @RequestMapping(method = RequestMethod.POST,
                  consumes = MediaType.APPLICATION_JSON_VALUE,
                  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Result> post(
    @RequestBody
    RegistryHost registryHost) {
    this.repo.save(registryHost);
    return ResponseEntity.ok(new Result("success registry host saving"));
  }

  /**
   * Registryホスト情報の削除
   * @param name Registryホスト名
   * @return 処理結果
   */
  @RequestMapping(value = "/{name}",
                  method = RequestMethod.DELETE,
                  consumes = MediaType.APPLICATION_JSON_VALUE,
                  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Result> delete(@PathVariable("name") String name) {
    this.repo.delete(name);
    return ResponseEntity.ok(new Result("success registry host deleting"));
  }

  @AllArgsConstructor
  @Data
  private static class Result {
    private String message;
  }
}
