package com.shop.userManager.util;


import com.shop.userManager.enums.DataSources;

/**
 * 通过 TheadLocal 来保存每个线程选择哪个数据源的标志
 */

public class DataSourceTypeManager {
    private static final ThreadLocal<DataSources> dataSourceTypes = new ThreadLocal<DataSources>() {
        @Override
        protected DataSources initialValue() {
            return DataSources.WRITE;
        }
    };

    public static DataSources get() {
        return dataSourceTypes.get();
    }

    public static void set(DataSources dataSourceType) {
        dataSourceTypes.set(dataSourceType);
    }

    public static void reset() {
        dataSourceTypes.set(DataSources.WRITE);
    }
}
