package com.kingzoo.kingcat.project.dataTransfer2.istock;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by gonghongrui on 16/6/1.
 */
public interface TransferLogRepository extends MongoRepository<TransferLog, String> {
}
