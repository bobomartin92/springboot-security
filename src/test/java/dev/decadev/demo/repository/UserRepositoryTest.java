package dev.decadev.demo.repository;

import dev.decadev.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setName("Tony");
        user.setEmail("tony@email.com");
        user.setPassword("1234");

        entityManager.persist(user);
    }

    @Test
    void testFindUserByEmail(){
        User user = userRepository.findByEmail("tony@email.com").get();

        assertEquals(user.getEmail(), "tony@email.com");
    }
}