package alessia;

import alessia.payloads.NewActivityDTO;
import alessia.services.ActivitiesService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CapstoneProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneProjectApplication.class, args);

		System.out.println("Hello World!");
		ActivitiesService activitiesService = new ActivitiesService();
		        activitiesService.saveActivity(new NewActivityDTO("titolo", "blabla",  );
	}

}
