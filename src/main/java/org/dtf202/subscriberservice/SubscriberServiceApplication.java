package org.dtf202.subscriberservice;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.dtf202.subscriberservice.entity.*;
import org.dtf202.subscriberservice.entity.Package;
import org.dtf202.subscriberservice.repository.*;
import org.dtf202.subscriberservice.service.RevenueScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class SubscriberServiceApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRefRepository userRefRepository;
	@Autowired
	private RefRepository refRepository;
	@Autowired
	private AppConfigRepository appConfigRepository;
	@Autowired
	private RevenueScheduler revenueScheduler;
	@Autowired
	private PackageRepository packageRepository;
	@Autowired
	private PaymentTypeRepository paymentTypeRepository;


	public static void main(String[] args) {
		SpringApplication.run(SubscriberServiceApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {

		if(appConfigRepository.findByProperty("DISABLE_REG_AND_NEW_PKG").isEmpty()) {
			AppConfig appConfig = new AppConfig();
			appConfig.setProperty("DISABLE_REG_AND_NEW_PKG");
			appConfig.setValue("FALSE");
			appConfigRepository.save(appConfig);
		}

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

			Ref ref = Ref.builder().isActive(false).build();
			UserRef userRef = UserRef.builder().ref(ref).user(user).level(0).build();



			roleRepository.save(roleAdmin);
			roleRepository.save(roleUser);
			userRepository.save(userAdmin);
			userRepository.save(user);
			refRepository.save(ref);
			userRefRepository.save(userRef);
			revenueScheduler.revScheduler();

		}
		if (packageRepository.count() == 0){
			Package bronzeI = Package.builder()
					.name("Bronze I")
					.price(100.0)
					.isActive(true)
					.build();
			Package bronzeII = Package.builder()
					.name("Bronze II")
					.price(250.0)
					.isActive(true)
					.build();
			Package bronzeIII = Package.builder()
					.name("Bronze III")
					.price(500.0)
					.isActive(true)
					.build();
			Package silverI = Package.builder()
					.name("Silver I")
					.price(1000.0)
					.isActive(true)
					.build();
			Package silverII = Package.builder()
					.name("Silver II")
					.price(2500.0)
					.isActive(true)
					.build();
			Package silverIII = Package.builder()
					.name("Silver III")
					.price(5000.0)
					.isActive(true)
					.build();
			Package goldI = Package.builder()
					.name("Gold I")
					.price(10000.0)
					.isActive(true)
					.build();
			Package goldII = Package.builder()
					.name("Gold II")
					.price(25000.0)
					.isActive(true)
					.build();
			Package goldIII = Package.builder()
					.name("Gold III")
					.price(50000.0)
					.isActive(true)
					.build();
			Package platinumI = Package.builder()
					.name("Platinum I")
					.price(100000.0)
					.isActive(true)
					.build();
			Package platinumII = Package.builder()
					.name("Platinum II")
					.price(250000.0)
					.isActive(true)
					.build();
			Package platinumIII = Package.builder()
					.name("Platinum III")
					.price(500000.0)
					.isActive(true)
					.build();

			packageRepository.save(bronzeI);
			packageRepository.save(bronzeII);
			packageRepository.save(bronzeIII);
			packageRepository.save(silverI);
			packageRepository.save(silverII);
			packageRepository.save(silverIII);
			packageRepository.save(goldI);
			packageRepository.save(goldII);
			packageRepository.save(goldIII);
			packageRepository.save(platinumI);
			packageRepository.save(platinumII);
			packageRepository.save(platinumIII);
		}
		if(paymentTypeRepository.count()==0){
			PaymentType withdrawal = PaymentType.builder()
					.type("Withdrawal").build();
			PaymentType recharge = PaymentType.builder()
					.type("Recharge").build();
			PaymentType refcom = PaymentType.builder()
					.type("RefCom").build();
			paymentTypeRepository.save(recharge);
			paymentTypeRepository.save(withdrawal);
			paymentTypeRepository.save(refcom);
		}


	}

}
