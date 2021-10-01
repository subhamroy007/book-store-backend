package org.roybond007.helper;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookUploadRequestBody {

	private String title;
	private int pages;
	private double price;
	private MultipartFile coverPhoto;
	
}
