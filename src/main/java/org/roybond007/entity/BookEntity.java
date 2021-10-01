package org.roybond007.entity;

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
public class BookEntity {

	private String id;
	private String title;
	private int pages;
	private double price;
	private String coverPhotoName;
	
}
