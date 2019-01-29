package com.comsince.github;

import com.comsince.github.service.CommonService;
import com.comsince.github.service.SpringPojoService;
import com.comsince.github.type.ShardingType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-28 下午5:18
 **/
public class SpringNamespaceExample {
    private static ShardingType type = ShardingType.SHARDING_DATABASES;
//    private static ShardingType type = ShardingType.SHARDING_TABLES;
//    private static ShardingType type = ShardingType.SHARDING_DATABASES_AND_TABLES;
//    private static ShardingType type = ShardingType.MASTER_SLAVE;
//    private static ShardingType type = ShardingType.SHARDING_MASTER_SLAVE;

//        private static boolean isRangeSharding = true;
    private static boolean isRangeSharding = false;

    public static void main(final String[] args) {
        try (ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(isRangeSharding ? getApplicationFileRange() : getApplicationFilePrecise())) {
            process(applicationContext);
        }
    }

    private static String getApplicationFilePrecise() {
        switch (type) {
            case SHARDING_DATABASES:
                return "application-sharding-databases-precise.xml";
//                return "META-INF/application-sharding-databases-precise_jdbc.xml";
            case SHARDING_TABLES:
                return "application-sharding-tables-precise.xml";
            case SHARDING_DATABASES_AND_TABLES:
                return "application-sharding-databases-tables-precise.xml";
            case MASTER_SLAVE:
                return "application-master-slave.xml";
            case SHARDING_MASTER_SLAVE:
                return "application-sharding-master-slave-precise.xml";
            default:
                throw new UnsupportedOperationException(type.name());
        }
    }

    private static String getApplicationFileRange() {
        switch (type) {
            case SHARDING_DATABASES:
                return "application-sharding-databases-range.xml";
            case SHARDING_TABLES:
                return "application-sharding-tables-range.xml";
            case SHARDING_DATABASES_AND_TABLES:
                return "application-sharding-databases-tables-range.xml";
            case MASTER_SLAVE:
                return "application-master-slave.xml";
            case SHARDING_MASTER_SLAVE:
                return "application-sharding-master-slave-range.xml";
            default:
                throw new UnsupportedOperationException(type.name());
        }
    }

    private static void process(final ConfigurableApplicationContext applicationContext) {
        CommonService commonService = getCommonService(applicationContext);
        commonService.initEnvironment();
        commonService.processSuccess(isRangeSharding);
        try {
            commonService.processFailure();
        } catch (final Exception ex) {
            System.out.println(ex.getMessage());
            commonService.printData(isRangeSharding);
        } finally {
            commonService.cleanEnvironment();
        }
    }

    private static CommonService getCommonService(final ConfigurableApplicationContext applicationContext) {
        return applicationContext.getBean(SpringPojoService.class);
    }
}
