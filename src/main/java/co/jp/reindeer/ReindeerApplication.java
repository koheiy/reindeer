package co.jp.reindeer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "co.jp.*"})
public class ReindeerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReindeerApplication.class, args);
    }
}
