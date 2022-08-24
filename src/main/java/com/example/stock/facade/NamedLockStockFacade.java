package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.repository.StockRepository;
import com.example.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class NamedLockStockFacade {
    private final LockRepository lockRepository;
    private final StockService stockService;

    public NamedLockStockFacade(LockRepository lockRepository, StockService stockService) {
        this.lockRepository = lockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public void decrease(Long id, Long quantity){
        try {
            lockRepository.getLock(id.toString());
            stockService.decrease(id,quantity);

        }finally {
            lockRepository.releaseLock(id.toString());
        }
    }
}
