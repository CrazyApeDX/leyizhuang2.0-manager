package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdCashReturnNote;
import com.ynyes.lyz.repository.TdCashReturnNoteRepository;

/**
 * <p>
 * 标题：TdCashReturnNoteService.java
 * </p>
 * <p>
 * 描述：用户退款单据服务类
 * </p>
 * <p>
 * 公司：http://www.ynsite.com
 * </p>
 * 
 * @author 作者：DengXiao
 * @version 版本：下午12:56:17
 */

@Service
@Transactional
public class TdCashReturnNoteService {

	@Autowired
	private TdCashReturnNoteRepository repository;

	public TdCashReturnNote save(TdCashReturnNote e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	/**
	 * 删除
	 * 
	 * @param e
	 *            菜单项
	 */
	public void delete(TdCashReturnNote e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdCashReturnNote findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdCashReturnNote> findAll() {
		return (List<TdCashReturnNote>) repository.findAll();
	}

	public Page<TdCashReturnNote> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		return repository.findAll(pageRequest);
	}

	/**
	 * 查找指定用户的退款单据信息（不分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：2016年6月16日下午12:52:46
	 */
	public List<TdCashReturnNote> findByUsernameOrderByCreateTimeDesc(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderByCreateTimeDesc(username);
	}

	/**
	 * 查找指定用户的退款单据信息（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：2016年6月16日下午12:53:03
	 */
	public Page<TdCashReturnNote> findByUsernameOrderByCreateTimeDesc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameOrderByCreateTimeDesc(username, pageRequest);
	}

	/**
	 * 查看指定用户的指定状态的退款单据信息（不分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：2016年6月16日下午12:53:20
	 */
	public List<TdCashReturnNote> findByUsernameAndIsOperatedOrderByCreateTimeDesc(String username,
			Boolean isOperated) {
		if (null == username || null == isOperated) {
			return null;
		}
		return repository.findByUsernameAndIsOperatedOrderByCreateTimeDesc(username, isOperated);
	}

	/**
	 * 查看指定用户的指定状态的退款单据信息（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：2016年6月16日下午12:53:42
	 */
	public Page<TdCashReturnNote> findByUsernameAndIsOperatedOrderByCreateTimeDesc(String username, Boolean isOperated,
			int page, int size) {
		if (null == username || null == isOperated) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameAndIsOperatedOrderByCreateTimeDesc(username, isOperated, pageRequest);
	}

	public TdCashReturnNote findByOrderNumber(String orderNumber) {
		if (orderNumber == null) {
			return null;
		}
		return repository.findByOrderNumber(orderNumber);
	}

	public Page<TdCashReturnNote> findByUsernameContainingOrOrderNumberContainingOrMainOrderNumberContainingOrReturnNoteNumberContaining(
			String keywords, Integer page, Integer size) {
		if (null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository
				.findByUsernameContainingOrOrderNumberContainingOrMainOrderNumberContainingOrReturnNoteNumberContaining(
						keywords, keywords, keywords, keywords, pageRequest);
	}

	public Page<TdCashReturnNote> findByUsernameContainingAndIsOperatedOrOrderNumberContainingAndIsOperatedOrMainOrderNumberContainingAndIsOperatedOrReturnNoteNumberContainingAndIsOperated(
			String keywords, Boolean isOperated, int page, int size) {
		if (null == keywords || null == isOperated) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository
				.findByUsernameContainingAndIsOperatedOrOrderNumberContainingAndIsOperatedOrMainOrderNumberContainingAndIsOperatedOrReturnNoteNumberContainingAndIsOperated(
						keywords, isOperated, keywords, isOperated, keywords, isOperated, keywords, isOperated,
						pageRequest);
	}

	public Page<TdCashReturnNote> findByIsOperated(Boolean isOperated, int page, int size) {
		if (null == isOperated) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByIsOperated(isOperated, pageRequest);
	}
}
