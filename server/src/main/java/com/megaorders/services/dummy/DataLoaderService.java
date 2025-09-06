package com.megaorders.services.dummy;

import com.megaorders.dtos.dummy.UserCsvDTO;
import com.megaorders.models.User;
import com.megaorders.repositories.UserRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(force = true)
@Service
@Transactional
@Slf4j
public class DataLoaderService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public DataLoaderService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading dummy user data from CSV...");
        loadUsersFromCsv();
        log.info("Dummy user data loading completed.");
    }

    private void loadUsersFromCsv() {
        try {
            ClassPathResource resource = new ClassPathResource("data/users.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            CsvToBean<UserCsvDTO> csvToBean = new CsvToBeanBuilder<UserCsvDTO>(reader)
                    .withType(UserCsvDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<UserCsvDTO> userCsvDTOs = csvToBean.parse();

            for (UserCsvDTO dto : userCsvDTOs) {
                Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
                if (existingUser.isEmpty()) {
                    User user = modelMapper.map(dto, User.class);
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    userRepository.save(user);
                    log.info("Inserted new user: {}", dto.getEmail());
                } else {
                    log.info("User already exists, skipping: {}", dto.getEmail());
                }
            }

        } catch (Exception e) {
            log.error("Error loading users from CSV: {}", e.getMessage());
        }
    }
}
