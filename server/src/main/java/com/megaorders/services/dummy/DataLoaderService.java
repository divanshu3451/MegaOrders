package com.megaorders.services.dummy;

import com.megaorders.dtos.dummy.*;
import com.megaorders.models.*;
import com.megaorders.models.enums.PaymentMode;
import com.megaorders.repositories.*;
import com.megaorders.utils.FieldUtils;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
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
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductSupplierRepository productSupplierRepository;
    private final ItemRepository itemRepository;
    private final PaymentDetailRepository paymentDetailRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public DataLoaderService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, VendorRepository vendorRepository, SupplierRepository supplierRepository, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, ProductSupplierRepository productSupplierRepository, ItemRepository itemRepository, PaymentDetailRepository paymentDetailRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.vendorRepository = vendorRepository;
        this.supplierRepository = supplierRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.productSupplierRepository = productSupplierRepository;
        this.itemRepository = itemRepository;
        this.paymentDetailRepository = paymentDetailRepository;
        this.orderRepository = orderRepository;
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
        log.info("Loading dummy product category data from CSV...");
        loadProductCategoriesFromCsv();
        log.info("Dummy product category data loading completed.");
        log.info("Loading dummy product data from CSV...");
        loadProductsFromCsv();
        log.info("Dummy product data loading completed.");
        log.info("Loading dummy product supplier data from CSV...");
        loadProductSuppliersFromCsv();
        log.info("Dummy product supplier data loading completed.");
        log.info("Loading dummy items data from CSV...");
        loadItemsFromCsv();
        log.info("Dummy items data loading completed.");
        log.info("Loading dummy payment detail data from CSV...");
        loadPaymentDetailsFromCsv();
        log.info("Dummy payment detail data loading completed.");
        log.info("Loading dummy order data from CSV...");
        loadOrdersFromCsv();
        log.info("Dummy order data loading completed.");
    }

    private void loadUsersFromCsv() {
        try {
            List<UserCsvDTO> userCsvDTOs = csvToBeanMapping("data/users.csv", UserCsvDTO.class);
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
            List<VendorCsvDTO> vendorCsvDTOs = csvToBeanMapping("data/vendors.csv", VendorCsvDTO.class);
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
            List<SupplierCsvDTO> supplierCsvDTOs = csvToBeanMapping("data/suppliers.csv", SupplierCsvDTO.class);
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

    private void loadProductCategoriesFromCsv() {
        try {
            List<ProductCategoryCsvDTO> categoryCsvDTOs = csvToBeanMapping("data/product_categories.csv", ProductCategoryCsvDTO.class);

            for (ProductCategoryCsvDTO dto : categoryCsvDTOs) {
                Optional<ProductCategory> existingCategory = productCategoryRepository.findByName(dto.getName());
                if (existingCategory.isEmpty()) {
                    ProductCategory category = new ProductCategory();
                    category.setName(dto.getName());
                    category.setDescription(dto.getDescription());
                    productCategoryRepository.save(category);
                    log.info("Inserted new product category: {}", dto.getName());
                } else {
                    log.info("Product category already exists, skipping: {}", dto.getName());
                }
            }

        } catch (Exception e) {
            log.error("Error loading product categories from CSV: {}", e.getMessage());
        }
    }

    private void loadProductsFromCsv() {
        try {
            List<ProductCsvDTO> productCsvDTOs = csvToBeanMapping("data/products.csv", ProductCsvDTO.class);
            for (ProductCsvDTO dto : productCsvDTOs) {
                Optional<ProductCategory> category = productCategoryRepository.findByName(dto.getCategoryName());
                if (category.isPresent()) {
                    Optional<Product> existingProduct = productRepository.findByNameAndCategory(dto.getName(), category.get());
                    if (existingProduct.isEmpty()) {
                        Product product = modelMapper.map(dto, Product.class);
                        product.setScore(0L); // Default value
                        product.setScoreResetInDays(30L); // Default value
                        category.get().addProduct(product);
                        productRepository.save(product);

                        log.info("Inserted new product: {}", dto.getName());
                    } else {
                        log.warn("Category not found for product: {}", dto.getCategoryName());
                    }
                } else {
                    log.info("Product already exists, skipping: {}", dto.getName());
                }
            }

        } catch (Exception e) {
            log.error("Error loading products from CSV: {}", e.getMessage());
        }
    }

    private void loadProductSuppliersFromCsv() {
        try {
            List<ProductSupplierCsvDTO> productSupplierCsvDTOs = csvToBeanMapping("data/product_suppliers.csv", ProductSupplierCsvDTO.class);

            for (ProductSupplierCsvDTO dto : productSupplierCsvDTOs) {
                Optional<ProductCategory> category = productCategoryRepository.findByName(dto.getCategoryName());
                if (category.isPresent()) {
                    Optional<Product> productOpt = productRepository.findByNameAndCategory(dto.getProductName(), category.get());
                    Optional<Supplier> supplierOpt = supplierRepository.findByLicenseNumber(dto.getLicenseNumber());

                    if (productOpt.isPresent() && supplierOpt.isPresent()) {
                        Product product = productOpt.get();
                        Supplier supplier = supplierOpt.get();

                        Optional<ProductSupplier> existing = productSupplierRepository.findByProductAndSupplier(product, supplier);
                        if (existing.isEmpty()) {
                            ProductSupplier productSupplier = new ProductSupplier();
                            product.addProductSupplier(productSupplier);
                            supplier.addProductSupplier(productSupplier);
                            productSupplier.setCostPrice(dto.getCostPrice());
                            productSupplierRepository.save(productSupplier);
                            log.info("Inserted new product-supplier link: {} ({}) - {} ({})", product.getName(), dto.getCategoryName(), supplier.getLicenseNumber(), supplier.getUser().getEmail());
                        } else {
                            log.info("Product-supplier link already exists: {} ({}) - {} ({})", product.getName(), dto.getCategoryName(), supplier.getLicenseNumber(), supplier.getUser().getEmail());
                        }
                    } else {
                        log.warn("Product or Supplier not found for product-supplier link: {} ({}) - {}", dto.getProductName(), dto.getCategoryName(), dto.getLicenseNumber());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error loading product suppliers from CSV: {}", e.getMessage());
        }
    }

    private void loadItemsFromCsv() {
        try {
            List<ItemCsvDTO> itemCsvDTOs = csvToBeanMapping("data/items.csv", ItemCsvDTO.class);
            for (ItemCsvDTO dto : itemCsvDTOs) {
                FieldUtils.replaceEmptyStringsWithNulls(dto);
                Optional<Item> existingItem = itemRepository.findBySerialNumber(dto.getSerialNumber());
                if (existingItem.isEmpty()) {
                    Optional<ProductCategory> category = productCategoryRepository.findByName(dto.getCategoryName());
                    if (category.isPresent()) {
                        Optional<Product> productOpt = productRepository.findByNameAndCategory(dto.getProductName(), category.get());
                        Optional<Supplier> supplierOpt = supplierRepository.findByLicenseNumber(dto.getSupplierLicenseNumber());

                        if (productOpt.isPresent() && supplierOpt.isPresent()) {
                            Product product = productOpt.get();
                            Supplier supplier = supplierOpt.get();

                            Optional<ProductSupplier> productSupplierOpt = productSupplierRepository.findByProductAndSupplier(product, supplier);
                            if (productSupplierOpt.isPresent()) {
                                ProductSupplier productSupplier = productSupplierOpt.get();

                                Item item = modelMapper.map(dto, Item.class);
                                product.addItem(item);
                                productSupplier.addItem(item);
//                                item.setOrderProduct(null); // Set to null for inventory items

                                itemRepository.save(item);
                                log.info("Inserted new item: {}", dto.getSerialNumber());
                            } else {
                                log.warn("ProductSupplier not found for item: {} - {} ({}) - {}", dto.getSerialNumber(), dto.getProductName(), dto.getCategoryName(), dto.getSupplierLicenseNumber());
                            }
                        } else {
                            log.warn("Product or Supplier not found for item: {} - {} ({}) - {}", dto.getSerialNumber(), dto.getProductName(), dto.getCategoryName(), dto.getSupplierLicenseNumber());
                        }
                    } else {
                        log.warn("Category not found for item: {} - {}", dto.getSerialNumber(), dto.getCategoryName());
                    }
                } else {
                    log.info("Item already exists, skipping: {}", dto.getSerialNumber());
                }
            }

        } catch (Exception e) {
            log.error("Error loading items from CSV: {}", e.getMessage());
        }
    }

    private void loadPaymentDetailsFromCsv() {
        try {
            List<PaymentDetailsCsvDTO> paymentDetailCsvDTOs = csvToBeanMapping("data/payment_details.csv", PaymentDetailsCsvDTO.class);
            for (PaymentDetailsCsvDTO dto : paymentDetailCsvDTOs) {
                FieldUtils.replaceEmptyStringsWithNulls(dto);
                Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());

                if (userOpt.isEmpty()) {
                    log.warn("User not found for email: {}. Skipping payment detail.", dto.getEmail());
                    continue;
                }
                User user = userOpt.get();
                Optional<PaymentDetail> existing = Optional.empty();
                PaymentInfo paymentInfo = modelMapper.map(dto, PaymentInfo.class);
                existing = getPaymentDetailFromPaymentMode(paymentInfo, existing);

                if (existing.isPresent()) {
                    log.info("PaymentDetail already exists for user {}. Skipping insertion.", dto.getEmail());
                    continue;
                }
                PaymentDetail paymentDetail = modelMapper.map(dto, PaymentDetail.class);
                user.addPaymentDetail(paymentDetail);
                paymentDetailRepository.save(paymentDetail);
                log.info("Inserted new PaymentDetail for user {}.", dto.getEmail());
            }
        } catch (Exception e) {
            log.error("Error loading payment details from CSV: {}", e.getMessage());
        }
    }


    private void loadOrdersFromCsv() {
        try {
            List<OrderCsvDTO> orderCsvDTOs = csvToBeanMapping("data/orders.csv", OrderCsvDTO.class);
            for (OrderCsvDTO dto : orderCsvDTOs) {
                FieldUtils.replaceEmptyStringsWithNulls(dto);
                Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    Optional<PaymentDetail> paymentDetailOpt = Optional.empty();
                    PaymentInfo paymentInfo = modelMapper.map(dto, PaymentInfo.class);
                    paymentDetailOpt = getPaymentDetailFromPaymentMode(paymentInfo, paymentDetailOpt);
                    if (paymentDetailOpt.isPresent() && orderRepository.findByTransactionId(dto.getTransactionId()).isEmpty()) {
                        Order order = modelMapper.map(dto, Order.class);
                        user.addOrder(order);
                        paymentDetailOpt.get().addOrder(order);
                        orderRepository.save(order);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error loading orders from CSV: {}", e.getMessage());
        }
    }

    private Optional<PaymentDetail> getPaymentDetailFromPaymentMode(PaymentInfo paymentInfo, Optional<PaymentDetail> existing) {
        if (paymentInfo.getPaymentMode() == PaymentMode.UPI) {
            existing = paymentDetailRepository.findByUpiId(paymentInfo.getUpiId());
        }
        if (paymentInfo.getPaymentMode() == PaymentMode.CREDIT_CARD || paymentInfo.getPaymentMode() == PaymentMode.DEBIT_CARD) {
            existing = paymentDetailRepository.findByCardNumber(paymentInfo.getCardNumber());
        }
        if (paymentInfo.getPaymentMode() == PaymentMode.NET_BANKING) {
            existing = paymentDetailRepository.findByBankAccountNumber(paymentInfo.getBankAccountNumber());
        }

        return existing;
    }

    private <T> List<T> csvToBeanMapping(String path, Class<T> type) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withType(type)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse();
    }

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class PaymentInfo {
    private PaymentMode paymentMode;
    private String upiId;
    private String cardNumber;
    private String bankAccountNumber;
}
