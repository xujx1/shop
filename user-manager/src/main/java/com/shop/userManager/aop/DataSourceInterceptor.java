package com.shop.userManager.aop;

import com.shop.userManager.enums.DataSources;
import com.shop.userManager.util.DataSourceTypeManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Aspect
public class DataSourceInterceptor {

    // 依赖注入需要切换到从库(读库)的方法名前缀
    private final static  Logger logger = LoggerFactory.getLogger(DataSourceInterceptor.class);

    private List<String> slaveMethods;

    @Before(value = "execution(public * com.shop.userManager.mapper.UserMapper.*(..)))")
    public void chooseDataSource(JoinPoint joinPoint) {
        DataSourceTypeManager.reset();
        if (null != joinPoint) {
            String methodName = joinPoint.getSignature().getName();
            if (!CollectionUtils.isEmpty(slaveMethods)) {
                for (String slaveMethod : slaveMethods) {
                    if (methodName.startsWith(slaveMethod)) {
                        DataSourceTypeManager.set(DataSources.READ);
                        break;
                    }
                }
            }
            logger.info("Data_Source:{}", DataSourceTypeManager.get().name());
        }
    }

    public List<String> getSlaveMethods() {
        return slaveMethods;
    }

    public void setSlaveMethods(List<String> slaveMethods) {
        this.slaveMethods = slaveMethods;
    }
}
