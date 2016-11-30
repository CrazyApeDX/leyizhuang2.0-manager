package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdCashReturnNote;

/**
 * <p>标题：TdCashReturnNoteRepository.java</p>
 * <p>描述：退款单据仓库类</p>
 * <p>公司：http://www.ynsite.com</p>
 * @author 作者：DengXiao
 * @version 版本：下午12:48:06
 */
public interface TdCashReturnNoteRepository
		extends PagingAndSortingRepository<TdCashReturnNote, Long>, JpaSpecificationExecutor<TdCashReturnNote> {
	
	/**
	 * 查找指定用户的退款单据信息（不分页）
	 * @author 作者：DengXiao
	 * @version 版本：2016年6月16日下午12:52:46
	 */
	List<TdCashReturnNote> findByUsernameOrderByCreateTimeDesc(String username);
	
	/**
	 * 查找指定用户的退款单据信息（分页）
	 * @author 作者：DengXiao
	 * @version 版本：2016年6月16日下午12:53:03
	 */
	Page<TdCashReturnNote> findByUsernameOrderByCreateTimeDesc(String username,Pageable page);
	
	/**
	 * 查看指定用户的指定状态的退款单据信息（不分页）
	 * @author 作者：DengXiao
	 * @version 版本：2016年6月16日下午12:53:20
	 */
	List<TdCashReturnNote> findByUsernameAndIsOperatedOrderByCreateTimeDesc(String username,Boolean isOperated);
	
	/**
	 * 查看指定用户的指定状态的退款单据信息（分页）
	 * @author 作者：DengXiao
	 * @version 版本：2016年6月16日下午12:53:42
	 */
	Page<TdCashReturnNote> findByUsernameAndIsOperatedOrderByCreateTimeDesc(String username,Boolean isOperated,Pageable page);
	
	TdCashReturnNote findByOrderNumber(String orderNumber);
	
	Page<TdCashReturnNote> findByUsernameContainingOrOrderNumberContainingOrMainOrderNumberContainingOrReturnNoteNumberContaining(String keywords1, String keywords2, String keywords3, String keywords4, Pageable page);
	
	Page<TdCashReturnNote> findByUsernameContainingAndIsOperatedOrOrderNumberContainingAndIsOperatedOrMainOrderNumberContainingAndIsOperatedOrReturnNoteNumberContainingAndIsOperated(String keywords1,Boolean IsOperated1,  String keywords2, Boolean IsOperated2, String keywords3, Boolean IsOperated3, String keywords4, Boolean IsOperated4, Pageable page);
	
	Page<TdCashReturnNote> findByIsOperated(Boolean IsOperated, Pageable page);
	
	
}
