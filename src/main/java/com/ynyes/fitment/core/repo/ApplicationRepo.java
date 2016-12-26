package com.ynyes.fitment.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

/**
 * 该接口并没有实际上的意思，只是为了提供一个公共的接口
 * 在未来可能会扩展JPA公共方法
 * @author dengxiao
 * @param <Entity> 接口对应的CRUD模型类
 */
@NoRepositoryBean
public interface ApplicationRepo<Entity extends TableEntity>
		extends JpaRepository<Entity, Long>, JpaSpecificationExecutor<Entity> {
}
