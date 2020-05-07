package br.com.spring_data_jpa.multiple_datasources.datasources.postgresql.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "client", name = "client")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Integer id;
	
	@NotNull
	@Size(min = 3, max = 45)
	@Getter @Setter
	private String name;
	
}
