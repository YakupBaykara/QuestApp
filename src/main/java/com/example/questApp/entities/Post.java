 package com.example.questApp.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.Data;

@Entity
@Table(name="post")
@Data
public class Post {
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
//  Post çekildiğinde user nesnesi dönmemesi için LAZY seçilir. 
//  EAGER seçilirse user nesnesi de döner
	
	@JoinColumn(name = "user_id", nullable = false)
	
	@OnDelete(action = OnDeleteAction.CASCADE)
//	Eğer bir user silinirse onun tüm postları da silinmesi anlamına gelir.
	
//	@JsonIgnore    //Serialization sırasında hata vermemesi için
	private User user;
	
	private String title;
	
	@Lob
	@Column(columnDefinition = "text")
	private String text;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date createDate;
}
