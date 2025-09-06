package com.megaorders.services.dummy;

import com.megaorders.dtos.dummy.SupplierCsvDTO;
import com.megaorders.dtos.dummy.UserCsvDTO;
import com.megaorders.dtos.dummy.VendorCsvDTO;
import com.megaorders.models.Supplier;
import com.megaorders.models.User;
import com.megaorders.models.Vendor;
import com.megaorders.repositories.SupplierRepository;
import com.megaorders.repositories.UserRepository;
import com.megaorders.repositories.VendorRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.NoArgsConstructor;
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
    private final VendorRepository vendorRepository;
    private final SupplierRepository supplierRepository;


    @Autowired
    public DataLoaderService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, VendorRepository vendorRepository, SupplierRepository supplierRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.vendorRepository = vendorRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading dummy user data from CSV...");
        loadUsersFromCsv();
        log.info("Dummy user data loading completed.");
        log.info("Loading dummy vendor data from CSV...");
        loadVendorsFromCsv();
        log.info("Dummy vendor data loading completed.");
        log.info("Loading dummy supplier data from CSV...");
        loadSuppliersFromCsv();
        log.info("Dummy supplier data loading completed.");
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

    private void loadVendorsFromCsv() {
        try {
            ClassPathResource resource = new ClassPathResource("data/vendors.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            CsvToBean<VendorCsvDTO> csvToBean = new CsvToBeanBuilder<VendorCsvDTO>(reader)
                    .withType(VendorCsvDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<VendorCsvDTO> vendorCsvDTOs = csvToBean.parse();

            for (VendorCsvDTO dto : vendorCsvDTOs) {
                Optional<Vendor> existingVendor = vendorRepository.findByGstNumber(dto.getGstNumber());
                if (existingVendor.isEmpty()) {
                    Optional<User> user = userRepository.findByEmail(dto.getEmail());
                    if (user.isPresent()) {
                        Vendor vendor = new Vendor();
                        vendor.setUser(user.get());
                        vendor.setCompanyName(dto.getCompanyName());
                        vendor.setCompanyAddress(dto.getCompanyAddress());
                        vendor.setGstNumber(dto.getGstNumber());
                        vendorRepository.save(vendor);
                        log.info("Inserted new vendor for user: {}", dto.getEmail());
                    } else {
                        log.warn("User not found for vendor: {}", dto.getEmail());
                    }
                } else {
                    log.info("Vendor already exists for user, skipping: {}", dto.getEmail());
                }
            }
        } catch (Exception e) {
            log.error("Error loading vendors from CSV: {}", e.getMessage());
        }
    }

    private void loadSuppliersFromCsv() {
        try {
            ClassPathResource resource = new ClassPathResource("data/suppliers.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            CsvToBean<SupplierCsvDTO> csvToBean = new CsvToBeanBuilder<SupplierCsvDTO>(reader)
                    .withType(SupplierCsvDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<SupplierCsvDTO> supplierCsvDTOs = csvToBean.parse();

            for (SupplierCsvDTO dto : supplierCsvDTOs) {
                Optional<Supplier> existingSupplier = supplierRepository.findByLicenseNumber(dto.getLicenseNumber());
                if (existingSupplier.isEmpty()) {
                    Optional<User> user = userRepository.findByEmail(dto.getEmail());
                    if (user.isPresent()) {
                        Supplier supplier = new Supplier();
                        supplier.setUser(user.get());
                        supplier.setLicenseNumber(dto.getLicenseNumber());
                        supplier.setSupplierType(dto.getSupplierType());
                        supplierRepository.save(supplier);
                        log.info("Inserted new supplier for user: {}", dto.getEmail());
                    } else {
                        log.warn("User not found for supplier: {}", dto.getEmail());
                    }
                } else {
                    log.info("Supplier already exists for user, skipping: {}", dto.getEmail());
                }
            }

        } catch (Exception e) {
            log.error("Error loading suppliers from CSV: {}", e.getMessage());
        }
    }
}
