package org.roybond007.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.roybond007.entity.BookEntity;
import org.roybond007.helper.BookUploadRequestBody;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/books")
public class BookController {

	private ConcurrentHashMap<String, BookEntity> bookStore = new ConcurrentHashMap<>();
	
	@PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> uploadMovie(@ModelAttribute BookUploadRequestBody requestBody){
		
		System.out.println("a new upload request is made with following data: ");
		
		System.out.println("book title : " + requestBody.getTitle());
		System.out.println("book pages : " + requestBody.getPages());
		System.out.println("book price : " + requestBody.getPrice());
		
		System.out.println("cover photo size : " + requestBody.getCoverPhoto().getSize());
		
		BookEntity newBook = new BookEntity();
		newBook.setId("Book@" + System.currentTimeMillis());
		newBook.setPages(requestBody.getPages());
		newBook.setPrice(requestBody.getPrice());
		newBook.setTitle(requestBody.getTitle());
		newBook.setCoverPhotoName(getImageName(requestBody.getCoverPhoto()));
		
		bookStore.put(newBook.getId(), newBook);
		
		return ResponseEntity.noContent().build();
	}
	
	private String getImageName(MultipartFile coverPhoto) {
				
		
		String fileName = "Photo@" + System.currentTimeMillis() + coverPhoto.getOriginalFilename();
		
		Path destinationDirectory = new File("/cover/").toPath().resolve(fileName);
		
		try {
			Files.copy(coverPhoto.getInputStream(), destinationDirectory, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println("something went wrong while uploading file " + e.getLocalizedMessage());
		}
		
		return fileName;
	}

	@GetMapping
	public ResponseEntity<?> fetchMovie(){
		
		List<BookEntity> books = new ArrayList<>();
		
		Enumeration<BookEntity> target = bookStore.elements();
		
		String originalCoverPhoto = null;
		while (target.hasMoreElements()) {
			BookEntity bookEntity = (BookEntity) target.nextElement();
			originalCoverPhoto = 
					ServletUriComponentsBuilder
					.fromCurrentContextPath()
					.path("/" + bookEntity.getCoverPhotoName())
					.toUriString();
			
			
			BookEntity resultBookEntity = new BookEntity(bookEntity.getId(), bookEntity.getTitle()
					, bookEntity.getPages(), bookEntity.getPrice(), originalCoverPhoto);
			books.add(resultBookEntity);
		}
		
		
		return ResponseEntity.ok(books);
	}
	
}
