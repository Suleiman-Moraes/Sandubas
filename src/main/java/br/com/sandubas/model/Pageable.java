package br.com.sandubas.model;

import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pageable {
	private Sort sort;
	private Integer pageSize;
	private Integer pageNumber;
	private Integer offset;
	private Boolean paged;
	private Boolean unpaged;
}
