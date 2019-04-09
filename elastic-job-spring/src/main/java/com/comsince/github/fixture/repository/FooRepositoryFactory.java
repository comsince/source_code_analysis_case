package com.comsince.github.fixture.repository;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-9 上午10:33
 **/
public final class FooRepositoryFactory {

    private static FooRepository fooRepository = new FooRepository();

    public static FooRepository getFooRepository() {
        return fooRepository;
    }

}
