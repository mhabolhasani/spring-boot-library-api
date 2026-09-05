package com.example.library;

import com.example.library.exception.ValidationException;
import com.example.library.persistence.adapter.BookPersistenceAdapter;
import com.example.library.service.BookService;
import com.example.library.service.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	private BookPersistenceAdapter bookPersistenceAdapter;

	@InjectMocks
	private BookService bookService;

	private Book sampleBook;

	@BeforeEach
	void setUp(){
		sampleBook = Book.builder()
				.id(1L)
				.title("Clean Code")
				.isbn("11")
				.publishedYear(2000)
				.authorId(1L)
				.categories(List.of(1L, 2L))
				.build();
	}

	@Test
	void addTest(){
		when(bookPersistenceAdapter.save(sampleBook)).thenReturn(sampleBook);

		Book result = bookService.add(sampleBook);

		assertEquals(result,sampleBook);
		verify(bookPersistenceAdapter).save(sampleBook);
	}

	@Test
	void getAllTest(){
		List<Book> books = List.of(sampleBook);
		when(bookPersistenceAdapter.findAll()).thenReturn(books);

		List<Book> result = bookService.getAll();

		assertThat(result).isEqualTo(books);
		verify(bookPersistenceAdapter).findAll();
	}

	@Test
	void getTest(){
		when(bookPersistenceAdapter.findById(1L)).thenReturn(Optional.of(sampleBook));

		when(bookPersistenceAdapter.findById(99L)).thenReturn(Optional.empty());

		Book result = bookService.get(1L);

		assertThat(result).isEqualTo(sampleBook);

		assertThatThrownBy(() -> bookService.get(99L))
				.isInstanceOf(ValidationException.class);
		assertThrows(ValidationException.class ,
				() -> bookService.get(99L));
		verify(bookPersistenceAdapter).findById(1L);
		verify(bookPersistenceAdapter).findById(99L);
	}

	@Test
	void updateTest(){
		when(bookPersistenceAdapter.findById(1L)).thenReturn(Optional.of(sampleBook));

		Book updateRequest = Book.builder()
				.title("Clean Code")
				.isbn("22")
				.publishedYear(2001)
				.authorId(10L)
				.categories(List.of(1L,2L))
				.build();

		when(bookPersistenceAdapter.save(any(Book.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		Book result = bookService.update(1L, updateRequest);

		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);

		assertEquals(result.getId() , 1L);
		assertEquals(result.getIsbn() ,"22");
		verify(bookPersistenceAdapter).findById(1L);
		verify(bookPersistenceAdapter).save(bookCaptor.capture());
	}

	@Test
	void patchTest(){
		when(bookPersistenceAdapter.findById(1L)).thenReturn(Optional.of(sampleBook));

		Book patchRequest = Book.builder()
				.title("new")
				.build();

		when(bookPersistenceAdapter.save(any(Book.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		Book result = bookService.patch(1L, patchRequest);

		assertThat(result.getTitle()).isEqualTo("new");
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getIsbn()).isEqualTo(sampleBook.getIsbn());
		assertThat(result.getCategories()).isEqualTo(sampleBook.getCategories());

		verify(bookPersistenceAdapter).findById(1L);
		verify(bookPersistenceAdapter).save(any(Book.class));
	}

	@Test
	void deleteTest() {
		bookService.delete(1L);
		verify(bookPersistenceAdapter).deleteById(1L);
		verifyNoMoreInteractions(bookPersistenceAdapter);
	}
}