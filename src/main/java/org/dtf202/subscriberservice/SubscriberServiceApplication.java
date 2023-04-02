package org.dtf202.subscriberservice;

import java.time.LocalDateTime;
import org.dtf202.subscriberservice.entity.Role;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.entity.UserRef;
import org.dtf202.subscriberservice.repository.RoleRepository;
import org.dtf202.subscriberservice.repository.UserRefRepository;
import org.dtf202.subscriberservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SubscriberServiceApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRefRepository userRefRepository;

	public static void main(String[] args) {
		SpringApplication.run(SubscriberServiceApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {

		if(userRepository.count() == 0) {
			Role roleAdmin = new Role(null, "ADMIN");
			Role roleUser = new Role(null, "USER");

			User userAdmin = User.builder()
				.firstName("Test")
				.lastName("Admin")
				.email("testadmin@gmail.com")
				.password(passwordEncoder.encode("1234"))
				.isDeleted(false)
				.isActive(true)
				.role(roleAdmin)
				.registeredDateTime(LocalDateTime.now())
				.totalBalance(0.0)
				.build();

			User user = User.builder()
				.firstName("Test")
				.lastName("User")
				.email("testuser@gmail.com")
				.password(passwordEncoder.encode("1234"))
				.isDeleted(false)
				.isActive(true)
				.role(roleUser)
				.registeredDateTime(LocalDateTime.now())
				.totalBalance(0.0)
				.build();

			UserRef userRef = UserRef.builder()
					.ref("TESTUSERREF")
						.user(user)
							.build();

			roleRepository.save(roleAdmin);
			roleRepository.save(roleUser);
			userRepository.save(userAdmin);
			userRepository.save(user);
			userRefRepository.save(userRef);
		}
	}
}
