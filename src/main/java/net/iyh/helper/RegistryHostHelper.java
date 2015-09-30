package net.iyh.helper;

import lombok.experimental.UtilityClass;
import net.iyh.model.RegistryHost;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Stream;

/**
 * Created by tsukasa on 2015/09/07.
 */
@UtilityClass
public class RegistryHostHelper {
  public static String createUri(RegistryHost host, String... path) {
    return UriComponentsBuilder.fromUriString(host.getUrl()).pathSegment(path).build().toUriString();
  }
}
