package net.iyh.repository;

import net.iyh.ParaponeraApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by tsukasa on 2015/09/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ParaponeraApplication.class)
@WebAppConfiguration
public class ManifestRepositoryTest {
}
