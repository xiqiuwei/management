package com.management.warehouse.core.service.impl;

import com.management.warehouse.core.entity.Stock;
import com.management.warehouse.core.dao.StockMapper;
import com.management.warehouse.core.service.StockService;
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
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

}
