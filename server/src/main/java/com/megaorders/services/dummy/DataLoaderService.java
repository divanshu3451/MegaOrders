package com.megaorders.services.dummy;

import com.megaorders.dtos.dummy.*;
import com.megaorders.models.*;
import com.megaorders.models.embeddables.DeliveryInfo;
import com.megaorders.models.embeddables.ReturnInfo;
import com.megaorders.models.enums.PaymentMode;
import com.megaorders.repositories.*;
import com.megaorders.utils.FieldUtils;
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

import java.lang.reflect.Method;
import java.util.*;

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
    private final OrderProductRepository orderProductRepository;

    @Autowired
    public DataLoaderService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, VendorRepository vendorRepository, SupplierRepository supplierRepository, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, ProductSupplierRepository productSupplierRepository, ItemRepository itemRepository, PaymentDetailRepository paymentDetailRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
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
        this.orderProductRepository = orderProductRepository;
    }

    private static Map<String, String> getStringStringMap() {
        Map<String, String> csvMethodMap = new LinkedHashMap<>(); // Maintain insertion order
        csvMethodMap.put("data/users.csv", "loadUsersFromCsv");
        csvMethodMap.put("data/vendors.csv", "loadVendorsFromCsv");
        csvMethodMap.put("data/suppliers.csv", "loadSuppliersFromCsv");
        csvMethodMap.put("data/product_categories.csv", "loadProductCategoriesFromCsv");
        csvMethodMap.put("data/products.csv", "loadProductsFromCsv");
        csvMethodMap.put("data/product_suppliers.csv", "loadProductSuppliersFromCsv");
        csvMethodMap.put("data/items.csv", "loadItemsFromCsv");
        csvMethodMap.put("data/payment_details.csv", "loadPaymentDetailsFromCsv");
        csvMethodMap.put("data/orders.csv", "loadOrdersFromCsv");
        csvMethodMap.put("data/order_products.csv", "loadOrderProductsFromCsv");
        csvMethodMap.put("data/item_for_orders.csv", "updateItemsAsPerOrdersFromCsv");
        return csvMethodMap;
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> csvMethodMap = getStringStringMap();
        Class<?> clazz = this.getClass();

        for (Map.Entry<String, String> entry : csvMethodMap.entrySet()) {
            String csvPath = entry.getKey();
            String methodName = entry.getValue();

            log.info("Loading dummy {} data from CSV...", methodName);
            try {
                Method method = clazz.getDeclaredMethod(methodName, String.class);
                method.setAccessible(true);
                method.invoke(this, csvPath);
                log.info("Dummy {} data loading completed.", methodName);
            } catch (Exception e) {
                log.error("Error loading {} from CSV: {}", methodName, e.getMessage(), e);
            }
        }
    }

    private void loadUsersFromCsv(String path) {
        try {
            List<UserCsvDTO> userCsvDTOs = DataLoaderServiceUtils.csvToBeanMapping(path, UserCsvDTO.class);
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

    private void loadVendorsFromCsv(String path) {
        try {
            List<VendorCsvDTO> vendorCsvDTOs = DataLoaderServiceUtils.csvToBeanMapping(path, VendorCsvDTO.class);
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

    private void loadSuppliersFromCsv(String path) {
        try {
            List<SupplierCsvDTO> supplierCsvDTOs = DataLoaderServiceUtils.csvToBeanMapping(path, SupplierCsvDTO.class);
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

    private void loadProductCategoriesFromCsv(String path) {
        try {
            List<ProductCategoryCsvDTO> categoryCsvDTOs = DataLoaderServiceUtils.csvToBeanMapping(path, ProductCategoryCsvDTO.class);

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

    private void loadProductsFromCsv(String path) {
        try {
            List<ProductCsvDTO> productCsvDTOs = DataLoaderServiceUtils.csvToBeanMapping(path, ProductCsvDTO.class);
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

    private void loadProductSuppliersFromCsv(String path) {
        try {
            List<ProductSupplierCsvDTO> productSupplierCsvDTOs = DataLoaderServiceUtils.csvToBeanMapping(path, ProductSupplierCsvDTO.class);

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

    private void loadItemsFromCsv(String path) {
        try {
            List<ItemCsvDTO> itemCsvDTOs = DataLoaderServiceUtils.csvToBeanMapping(path, ItemCsvDTO.class);
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

    private void loadPaymentDetailsFromCsv(String path) {
        try {
            List<PaymentDetailsCsvDTO> paymentDetailCsvDTOs = DataLoaderServiceUtils.csvToBeanMapping(path, PaymentDetailsCsvDTO.class);
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
                existing = DataLoaderServiceUtils.getPaymentDetailFromPaymentMode(paymentInfo, existing, paymentDetailRepository);

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

    private void loadOrdersFromCsv(String path) {
        try {
            List<OrderCsvDTO> orderCsvDTOs = DataLoaderServiceUtils.csvToBeanMapping(path, OrderCsvDTO.class);
            for (OrderCsvDTO dto : orderCsvDTOs) {
                FieldUtils.replaceEmptyStringsWithNulls(dto);
                Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    Optional<PaymentDetail> paymentDetailOpt = Optional.empty();
                    PaymentInfo paymentInfo = modelMapper.map(dto, PaymentInfo.class);
                    paymentDetailOpt = DataLoaderServiceUtils.getPaymentDetailFromPaymentMode(paymentInfo, paymentDetailOpt, paymentDetailRepository);
                    String txnId = dto.getTransactionId() == null ? null : dto.getTransactionId().trim();
                    if (paymentDetailOpt.isPresent() && orderRepository.findByTransactionId(txnId).isEmpty()) {
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

    private void loadOrderProductsFromCsv(String path) {
        try {
//            List<Order> orders = orderRepository.findAll();
            List<OrderProductCsvDTO> orderProductCsvDTOs = DataLoaderServiceUtils.csvToBeanMapping(path, OrderProductCsvDTO.class);
            for (OrderProductCsvDTO dto : orderProductCsvDTOs) {
                FieldUtils.replaceEmptyStringsWithNulls(dto);

                String txnId = dto.getTransactionId() == null ? null : dto.getTransactionId().trim();
                String categoryName = dto.getCategoryName() == null ? null : dto.getCategoryName().trim();
                String productName = dto.getProductName() == null ? null : dto.getProductName().trim();

                // Find order
                Optional<Order> orderOpt = orderRepository.findByTransactionId(txnId);
                if (orderOpt.isEmpty()) {
                    log.warn("Order with transactionId={} not found. Skipping.", txnId);
                    continue;
                }

                // Find category
                Optional<ProductCategory> categoryOpt = productCategoryRepository.findByName(categoryName);
                if (categoryOpt.isEmpty()) {
                    log.warn("Category={} not found for order txnId={}. Skipping.", categoryName, txnId);
                    continue;
                }

                // Find product
                Optional<Product> productOpt = productRepository.findByNameAndCategory(productName, categoryOpt.get());
                if (productOpt.isEmpty()) {
                    log.warn("Product={} not found in category={} for order txnId={}. Skipping.", productName, categoryName, txnId);
                    continue;
                }

                // Map to entity
                OrderProduct orderProduct = modelMapper.map(dto, OrderProduct.class);

                // Establish relationships
                Order order = orderOpt.get();
                Product product = productOpt.get();

                order.addOrderProduct(orderProduct);
                product.addOrderProduct(orderProduct);

                // Link items
                List<String> serialNumbers = Arrays.stream(dto.getSerialNumbers().split(";"))
                        .map(String::trim)
                        .filter(s -> !s.isBlank())
                        .toList();

                for (String serialNumber : serialNumbers) {
                    Optional<Item> item = itemRepository.findBySerialNumber(serialNumber);
                    if (item.isPresent()) {
                        orderProduct.addItem(item.get());
                    }
                }

                orderProductRepository.save(orderProduct);
            }
        } catch (Exception e) {
            log.error("Error loading orders from CSV: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateItemsAsPerOrdersFromCsv(String path) {
        try {
            List<Order> orders = orderRepository.findAll();
            List<OrderProduct> orderProducts = orderProductRepository.findAll();
            List<OrderedItemTrackingCsvDTO> orderedItemTrackingCsvDTOS = DataLoaderServiceUtils.csvToBeanMapping(path, OrderedItemTrackingCsvDTO.class);
            for (OrderedItemTrackingCsvDTO dto : orderedItemTrackingCsvDTOS) {
                FieldUtils.replaceEmptyStringsWithNulls(dto);

                String transactionId = dto.getTransactionId().trim();
                String serialFromCsv = dto.getSerialNumber() == null ? null : dto.getSerialNumber().trim();

                if (transactionId == null || serialFromCsv == null) {
                    log.warn("Skipping DTO with missing transactionId or serialNumber: {}", dto);
                    continue;
                }

                Optional<Order> orderOpt = orderRepository.findByTransactionId(transactionId);
                if (orderOpt.isEmpty()) {
                    log.warn("Order not found for transactionId {}. Skipping serial {}", transactionId, serialFromCsv);
                    continue;
                }

                Order order = orderOpt.get();
                OrderProduct foundOrderProduct = null;
                int itemIndex = -1;

                // Find the OrderProduct which contains the serial
                for (OrderProduct op : order.getOrderedProducts()) {
                    if (op.getItems() == null) continue;
                    for (int idx = 0; idx < op.getItems().size(); idx++) {
                        Item itm = op.getItems().get(idx);
                        if (itm.getSerialNumber() != null && serialFromCsv.equals(itm.getSerialNumber().trim())) {
                            foundOrderProduct = op;
                            itemIndex = idx;
                            break;
                        }
                    }
                    if (foundOrderProduct != null) break;
                }

                Optional<Item> itemOpt = itemRepository.findBySerialNumber(serialFromCsv);
                if (itemOpt.isEmpty()) {
                    log.warn("Item with serial {} not found in DB for transaction {}.", serialFromCsv, transactionId);
                    continue;
                }

                Item item = itemOpt.get();

                if (foundOrderProduct == null) {
                    // Log mismatch but still update item if you want; here we skip update to avoid inconsistent linking
                    log.warn("Serial {} exists in DB but not linked to order {}'s orderProducts. Skipping linking.", serialFromCsv, transactionId);
                    // Optionally: continue; or still update delivery/return info without linking
                    continue;
                }

                // Map delivery/return info and status
                item.setDeliveryInfo(modelMapper.map(dto, DeliveryInfo.class));
                item.setReturnInfo(modelMapper.map(dto, ReturnInfo.class));
                item.setStatus(dto.getStatus());
                item.getDeliveryInfo().setOrderPlacedDate(order.getOrderDate());
                item.getDeliveryInfo().setOrderPlacedTime(order.getOrderTime());
                foundOrderProduct.getItems().remove(itemIndex);
                foundOrderProduct.addItem(item);


                // persist changes explicitly for clarity and safety
                itemRepository.save(item);

                log.info("Updated item {} for transaction {}", serialFromCsv, transactionId);
            }
        } catch (Exception e) {
            log.error("Error loading orders from CSV: {}", e.getMessage());
        }
    }
}
