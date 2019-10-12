package org.polsl.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {

        //SpringApplication.run(InventoryApplication.class, args);

        ConfigurableApplicationContext ctx = SpringApplication.run(InventoryApplication.class, args);
        ctx.close();

    }

}
