package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdUserSuggestion;
import com.ynyes.lyz.repository.TdUserSuggestionRepo;

@Service
@Transactional
public class TdUserSuggestionService {

	@Autowired
	private TdUserSuggestionRepo repository;

	public TdUserSuggestion save(TdUserSuggestion e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdUserSuggestion findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdUserSuggestion> findAll() {
		return (List<TdUserSuggestion>) repository.findAll();
	}

	/**
	 * 列表页面 按sortId排序 其次时间倒序
	 * 
	 * @author zhangji
	 * @return
	 */
	public Page<TdUserSuggestion> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findAll(pageRequest);
	}

	/*-=====================================
	++++++++++++++++筛选++++++++             +++++++
	================       ========zhangji=======
	=======================================
	   代号：【keywords】【date1】【date2】【categoryId】
	======================================*/

	// 0001
	public Page<TdUserSuggestion> findByCategoryId(Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCategoryId(categoryId, pageRequest);
	}

	// 0010
	public Page<TdUserSuggestion> findByCreateTimeBefore(Date date2, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeBefore(date2, pageRequest);
	}

	// 0011
	public Page<TdUserSuggestion> findByCreateTimeBeforeAndCategoryId(Date date2, Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeBeforeAndCategoryId(date2, categoryId, pageRequest);
	}

	// 0100
	public Page<TdUserSuggestion> findByCreateTimeAfter(Date date1, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeAfter(date1, pageRequest);
	}

	// 0101
	public Page<TdUserSuggestion> findByCreateTimeAfterAndCategoryId(Date date1, Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeAfterAndCategoryId(date1, categoryId, pageRequest);
	}

	// 0110
	public Page<TdUserSuggestion> findByCreateTimeAfterAndCreateTimeBefore(Date date1, Date date2, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeAfterAndCreateTimeBefore(date1, date2, pageRequest);
	}

	// 0111
	public Page<TdUserSuggestion> findByCreateTimeAfterAndCreateTimeBeforeAndCategoryId(Date date1, Date date2,
			Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeAfterAndCreateTimeBeforeAndCategoryId(date1, date2, categoryId, pageRequest);
	}

	// 1000
	public Page<TdUserSuggestion> findBySearch(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByContentContaining(keywords, pageRequest);
	}

	// 1001
	public Page<TdUserSuggestion> findByCategoryIdAndSearch(Long categoryId, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCategoryIdAndContentContaining(categoryId, keywords, pageRequest);
	}

	// 1010
	public Page<TdUserSuggestion> findByCreateTimeBeforeAndSearch(Date date2, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeBeforeAndContentContaining(date2, keywords, pageRequest);
	}

	// 1011
	public Page<TdUserSuggestion> findByCreateTimeBeforeAndCategoryIdAndSearch(Date date2, Long categoryId,
			String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeBeforeAndCategoryIdAndContentContaining(date2, categoryId, keywords,
				pageRequest);
	}

	// 1100
	public Page<TdUserSuggestion> findByCreateTimeAfterAndSearch(Date date1, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeAfterAndContentContaining(date1, keywords, pageRequest);
	}

	// 1101
	public Page<TdUserSuggestion> findByCreateTimeAfterAndCategoryIdAndSearch(Date date1, Long categoryId,
			String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeAfterAndCategoryIdAndContentContaining(date1, categoryId, keywords,
				pageRequest);
	}

	// 1110
	public Page<TdUserSuggestion> findByCreateTimeAfterAndCreateTimeBeforeAndSearch(Date date1, Date date2,
			String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeAfterAndCreateTimeBeforeAndContentContaining(date1, date2, keywords,
				pageRequest);
	}

	// 1111
	public Page<TdUserSuggestion> findByCreateTimeAfterAndCreateTimeBeforeAndCategoryIdAndSearch(Date date1, Date date2,
			Long categoryId, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId")
				.and(new Sort(Direction.DESC, "createTime")).and(new Sort(Direction.ASC, "id")));
		return repository.findByCreateTimeAfterAndCreateTimeBeforeAndCategoryIdAndContentContaining(date1, date2,
				categoryId, keywords, pageRequest);
	}

	// 根据用户id查找其下所有的咨询投诉（未删除的）
	public List<TdUserSuggestion> findByUserIdAndParentIdAndIsDeleteFalseOrderByCreateTimeDesc(Long userId,
			Long parentId) {
		if (null == userId || null == parentId) {
			return null;
		}
		return repository.findByUserIdAndParentIdAndIsDeleteFalseOrderByCreateTimeDesc(userId, parentId);
	}
}
