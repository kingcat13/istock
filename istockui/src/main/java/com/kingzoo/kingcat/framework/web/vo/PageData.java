package com.kingzoo.kingcat.framework.web.vo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gonghongrui on 16/6/15.
 */
public class PageData<T> {

	private final long total;
	private final long totalPages;
	private final int currentPage;
	private final Sort sort;


	private final List<T> content = new ArrayList<>();


	public PageData(Page<T> page) {

		this.content.addAll(page.getContent());

		this.total = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.currentPage = page.getNumber();

		this.sort = page.getSort();
	}

	public long getTotal() {
		return total;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public Sort getSort() {
		return sort;
	}

	public List<T> getContent() {
		return content;
	}
}
