package gestorreservasaulas;

import gestorreservasaulas.interfaces.FrmLogin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GestorReservasAulasApplication {

	public static void main(String[] args) {
		// SpringApplication.run(GestorReservasAulasApplication.class, args);

		ConfigurableApplicationContext context = new SpringApplicationBuilder(
				GestorReservasAulasApplication.class).headless(false).run(args);

		FrmLogin appFrame = context.getBean(FrmLogin.class);
		appFrame.setVisible(true);
                
                
	}

}
