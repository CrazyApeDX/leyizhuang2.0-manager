package com.ynyes.fitment.core.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.ynyes.fitment.core.constant.Global;

/**
 * 可分页服务类
 * 该类提供了默认页数和默认每页行数的属性，默认值可以通过构造函数的方式进行修改
 * 同时提供了4个获取分页对象的方法
 * @author dengxiao
 */
public abstract class PageableService {

	private Integer defaultPage = Global.DEFAULT_PAGE;
	private Integer defaultSize = Global.DEFAULT_SIZE;

	protected PageableService() {
		super();
	}

	protected PageableService(Integer defaultPage, Integer defaultSize) {
		super();
		this.defaultPage = (null != defaultPage && defaultPage >= 0) ? defaultPage : Global.DEFAULT_PAGE;
		this.defaultSize = (null != defaultSize && defaultSize >= 1) ? defaultSize : Global.DEFAULT_SIZE;
	}

	protected Pageable initPage() {
		return new PageRequest(this.defaultPage, this.defaultSize);
	}

	protected Pageable initPage(Integer page, Integer size) {
		if (null == page || page < 0) {
			page = this.defaultPage;
		}
		if (null == size || size < 1) {
			size = this.defaultSize;
		}
		return new PageRequest(page, size);
	}
	
	protected Pageable initPage(Integer page, Integer size, Sort sort) {
		if (null == page || page < 0) {
			page = this.defaultPage;
		}
		if (null == size || size < 1) {
			size = this.defaultSize;
		}
		return new PageRequest(page, size, sort);
	}
	
	protected Pageable initPage(Integer page, Integer size, Direction direction, String properties) {
		if (null == page || page < 0) {
			page = this.defaultPage;
		}
		if (null == size || size < 1) {
			size = this.defaultSize;
		}
		return new PageRequest(page, size, new Sort(direction, properties));
	}
}
