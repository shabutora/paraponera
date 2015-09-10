package net.iyh;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import net.iyh.model.response.Catalog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ParaponeraApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {

  @Autowired ObjectMapper mapper;

  @Test
  public void contextLoads() throws Exception {
    String uri = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(80).pathSegment("aaa").pathSegment("bbb").build().toUriString();
    System.out.println(uri);
  }
}
