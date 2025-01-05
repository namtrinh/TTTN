package org.hotfilm.identityservice.ServiceImp;

import org.hotfilm.identityservice.Exception.AppException;
import org.hotfilm.identityservice.Exception.ErrorCode;
import org.hotfilm.identityservice.Mapper.UserMapper;
import org.hotfilm.identityservice.Model.User;
import org.hotfilm.identityservice.ModelDTO.Request.UserRequest;
import org.hotfilm.identityservice.ModelDTO.Response.UserResponse;
import org.hotfilm.identityservice.Repository.UserRepository;
import org.hotfilm.identityservice.Service.UserService;
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
public class UserServiceImp implements UserService {

    private static final String HASH_KEY = "User";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, UserResponse> hashOperations;

    @Autowired
    private UserRepository customerRepository;

    @Autowired
    private UserMapper userMapper;

    @Scheduled(fixedRate = 300000)
    public void preloadCache() {
        System.out.println("Preloading cache...");
        List<User> customers = customerRepository.findAll();
        for (User customer : customers) {
            hashOperations.put(HASH_KEY, customer.getUserId(), userMapper.toUserResponse(customer));
        }
        setTTL(HASH_KEY);
    }

    private void setTTL(String hashKey) {
        redisTemplate.expire(HASH_KEY, 5, TimeUnit.MINUTES);
    }

    private UserServiceImp(RedisTemplate<String, Object> redisTemplate) {
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
            List<User> customers = customerRepository.findAll();
            for (User customer : customers) {
                hashOperations.put(HASH_KEY, customer.getUserId(), userMapper.toUserResponse(customer));
                setTTL(HASH_KEY);
            }
            return customers.stream()
                    .map(userMapper::toUserResponse)
                    .collect(Collectors.toList());
        }
    }

    public User save(User entity) {
        if (customerRepository.existsByEmail(entity.getEmail())) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        entity.setRole(User.Role.USER);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        User customer = customerRepository.save(entity);
        hashOperations.put(HASH_KEY, entity.getUserId(), userMapper.toUserResponse(entity));
        if (hashOperations.hasKey(HASH_KEY, entity.getUserId())) {
            setTTL(HASH_KEY);
            System.out.println("save to redis");
        }
        return customer;
    }

    public UserResponse findById(String UserId) {
        if (hashOperations.hasKey(HASH_KEY, UserId)) {
            System.out.println("get from redis");
            return hashOperations.get(HASH_KEY, UserId);
        } else {
            System.out.println("get from database");
            User customer = customerRepository.findById(UserId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
            hashOperations.put(HASH_KEY, UserId, userMapper.toUserResponse(customer));
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
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    }

    public UserResponse updateById(String customerId, User customer) {
        if (hashOperations.hasKey(HASH_KEY, customerId) || customerRepository.existsById(customerId)) {
            hashOperations.put(HASH_KEY, customerId, userMapper.toUserResponse(customer));
            setTTL(HASH_KEY);
            customer.setUserId(customerId);
            return userMapper.toUserResponse(customerRepository.save(customer));
        } else {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public void updatePasswordByEmail(UserRequest userRequest){
        var user = customerRepository.findByEmail(userRequest.getEmail());
        if (user == null){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        customerRepository.save(user);
    }
}
