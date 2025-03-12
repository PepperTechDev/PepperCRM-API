package peppertech.crm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PepperCrmApiApplication {

    public static void main(String[] args) {
        loadEnv();
        SpringApplication.run(PepperCrmApiApplication.class, args);
    }

    private static void loadEnv() {
        try {
            Dotenv dotenv = Dotenv.load();

            dotenv.entries().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();

                if (value != null) {
                    System.setProperty(key, value);
                }
            });
        } catch (Exception e) {
            log.warn("No se pudo cargar el archivo .env. Se continuará con las variables de entorno ya presentes.");
            log.debug("Detalles del error: ", e);
        }
    }
}
