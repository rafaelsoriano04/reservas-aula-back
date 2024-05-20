package gestorreservasaulas;

import gestorreservasaulas.interfaces.FrmLogin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GestorReservasAulasApplication {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = new SpringApplicationBuilder(GestorReservasAulasApplication.class)
                    .headless(false).run(args);

        FrmLogin appFrame = context.getBean(FrmLogin.class);
        appFrame.setVisible(true);
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}

