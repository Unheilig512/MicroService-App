package Service.Data;

import Service.Data.DTO.ERole;
import Service.Data.Models.Role;
import Service.Data.Repositories.Jpa.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@EnableJpaRepositories(basePackages = "Service.Data.Repositories.Jpa")
@EnableRedisRepositories(basePackages = "Service.Data.Repositories.Redis")
@SpringBootApplication
public class ServiceDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDataApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			// Проверяем наличие ROLE_USER
			if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
				Role userRole = new Role();
				userRole.setName(ERole.ROLE_USER);
				roleRepository.save(userRole);
				System.out.println("✅ Роль ROLE_USER успешно добавлена в БД");
			}

			// Проверяем наличие ROLE_ADMIN
			if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
				Role adminRole = new Role();
				adminRole.setName(ERole.ROLE_ADMIN);
				roleRepository.save(adminRole);
				System.out.println("✅ Роль ROLE_ADMIN успешно добавлена в БД");
			}
		};
	}
}
