package com.sly.demo100.model;

import java.io.Serializable;

public class Order implements Serializable {

	private static final long serialVersionUID = 8563301197811270679L;

	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
