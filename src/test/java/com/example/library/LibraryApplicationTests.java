package com.example.library;

import com.example.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ExtendWith(MockitoExtension.class)
class LibraryApplicationTests {
	@Mock
	BookPersistenceAdapter bookPersistenceAdapter;
	@InjectMocks
	BookService bookService;

	@Test
	void contextLoads() {
	}
}
