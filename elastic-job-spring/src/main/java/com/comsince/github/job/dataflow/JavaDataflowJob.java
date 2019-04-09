package com.comsince.github.job.dataflow;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-9 上午10:31
 **/
import com.comsince.github.fixture.entity.Foo;
import com.comsince.github.fixture.repository.FooRepository;
import com.comsince.github.fixture.repository.FooRepositoryFactory;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JavaDataflowJob implements DataflowJob<Foo> {

    private FooRepository fooRepository = FooRepositoryFactory.getFooRepository();

    @Override
    public List<Foo> fetchData(final ShardingContext shardingContext) {
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
                shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "DATAFLOW FETCH"));
        return fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);
    }

    @Override
    public void processData(final ShardingContext shardingContext, final List<Foo> data) {
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
                shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "DATAFLOW PROCESS"));
        for (Foo each : data) {
            fooRepository.setCompleted(each.getId());
        }
    }
}
