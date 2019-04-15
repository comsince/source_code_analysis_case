package com.comsince.github.job.simple;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-9 上午10:31
 **/
import com.comsince.github.fixture.entity.Foo;
import com.comsince.github.fixture.repository.FooRepository;
import com.comsince.github.fixture.repository.FooRepositoryFactory;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JavaSimpleJob implements SimpleJob {

    private FooRepository fooRepository = FooRepositoryFactory.getFooRepository();

    @Override
    public void execute(final ShardingContext shardingContext) {
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
                shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "SIMPLE"));
//        List<Foo> data = fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);
//        for (Foo each : data) {
//            fooRepository.setCompleted(each.getId());
//        }
        while (true){
            System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
                    shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "SIMPLE"));
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
