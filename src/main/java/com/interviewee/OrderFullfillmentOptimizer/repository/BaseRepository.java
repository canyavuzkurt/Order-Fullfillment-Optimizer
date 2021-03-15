package com.interviewee.OrderFullfillmentOptimizer.repository;

import com.interviewee.OrderFullfillmentOptimizer.model.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends ModelRepository<T, Long> {}
