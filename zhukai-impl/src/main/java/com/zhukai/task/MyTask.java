package com.zhukai.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhukai
 * @date 2018/11/20
 */
public class MyTask implements SimpleJob {

    private static final Logger log = LoggerFactory.getLogger(MyTask.class);

    @Override
    public void execute(ShardingContext context) {
        log.info("" + context);
        switch (context.getShardingItem()) {
            case 0:
                log.info(context.getShardingParameter());
                break;
            case 1:
                log.info(context.getShardingParameter());
                break;
            case 2:
                log.info(context.getShardingParameter());
                break;
            // case n: ...
        }
    }

}
