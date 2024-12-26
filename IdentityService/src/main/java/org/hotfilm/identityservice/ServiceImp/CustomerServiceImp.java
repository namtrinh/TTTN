package org.hotfilm.identityservice.ServiceImp;

import org.hotfilm.identityservice.Mapper.UserMapper;
import org.hotfilm.identityservice.Model.Customer;
import org.hotfilm.identityservice.ModelDTO.Response.UserResponse;
import org.hotfilm.identityservice.Repository.CustomerRepository;
import org.hotfilm.identityservice.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImp implements CustomerService {

    private static final String HASH_KEY = "Customer";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, UserResponse> hashOperations;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserMapper userMapper;

    @Scheduled(fixedRate = 300000)
    public void preloadCache() {
        System.out.println("Preloading cache...");
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {

            hashOperations.put(HASH_KEY, customer.getCustomerId(), userMapper.toUserResponse(customer));
        }
        setTTL(HASH_KEY);
    }

    private void setTTL(String hashKey){
        redisTemplate.expire(HASH_KEY, 5, TimeUnit.MINUTES);
    }

    private CustomerServiceImp(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public List<UserResponse> findAll() {
        if (redisTemplate.hasKey(HASH_KEY)) {
            System.out.println("get from redis");
            return hashOperations.values(HASH_KEY);
            //.values() trả về tập giá trị dạng list<Object> trong hash có khóa HASH_KEY
            //entries() lấy tất cả các cặp key-value trong hash trả về dạng Map<Object, Object>
        } else {
            System.out.println("get from database");
            List<Customer> customers = customerRepository.findAll();
            for (Customer customer : customers) {
                hashOperations.put(HASH_KEY, customer.getCustomerId(), userMapper.toUserResponse(customer));
                setTTL(HASH_KEY);
            }
            return customers.stream()
                    .map(userMapper::toUserResponse)
                    .collect(Collectors.toList());
        }
    }

    public Customer save(Customer entity) {
        entity.setRoles(Customer.ROLE.USER);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        Customer customer = customerRepository.save(entity);
        hashOperations.put(HASH_KEY, entity.getCustomerId(), userMapper.toUserResponse(entity));
        if (hashOperations.hasKey(HASH_KEY, entity.getCustomerId())) {
            setTTL(HASH_KEY);
            System.out.println("save to redis");
        }
        return customer;
    }

    public UserResponse findById(String CustomerId) {
        if (hashOperations.hasKey(HASH_KEY, CustomerId)) {
            System.out.println("get from redis");
            return hashOperations.get(HASH_KEY, CustomerId);
        } else {
            System.out.println("get from database");
            Customer customer = customerRepository.findById(CustomerId).orElseThrow(() -> new RuntimeException("Couldn't find'"));
            hashOperations.put(HASH_KEY, CustomerId, userMapper.toUserResponse(customer));
           setTTL(HASH_KEY);
            return userMapper.toUserResponse(customer);
        }
    }

    public void deleteById(String customerId) {
        if (hashOperations.hasKey(HASH_KEY, customerId)) {
            hashOperations.delete(HASH_KEY, customerId);
        }
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    public UserResponse updateById(String customerId, Customer customer){
        if (hashOperations.hasKey(HASH_KEY, customerId) || customerRepository.existsById(customerId)) {
            hashOperations.put(HASH_KEY, customerId, userMapper.toUserResponse(customer));
            setTTL(HASH_KEY);
            customer.setCustomerId(customerId);
            return userMapper.toUserResponse(customerRepository.save(customer));
        } else {
            throw new RuntimeException("Customer not found");
        }
    }
}
