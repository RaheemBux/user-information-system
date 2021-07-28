package com.sapphire.testsapphire.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ProdProfileCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String profile = conditionContext.getEnvironment().getProperty("spring.profiles.active");
        return profile.equalsIgnoreCase("prod");
    }
}
