package com.management.warehouse.core.service.impl;

import com.management.warehouse.core.entity.Category;
import com.management.warehouse.core.dao.CategoryMapper;
import com.management.warehouse.core.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiqiuwei
 * @since 2019-07-12
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
