package info.lamth.biddingsystem.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class MessageChannelConfiguration {
    private static final String TASK_EXECUTOR = "task-executor";

    @Bean(name = TASK_EXECUTOR)
    public TaskExecutor getTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("Pub-Sub-");
        taskExecutor.setCorePoolSize(2);
        return taskExecutor;
    }

    @Bean
    SubscribableChannel newBiddingPrice(@Qualifier(TASK_EXECUTOR) TaskExecutor taskExecutor) {
        return MessageChannels.publishSubscribe(taskExecutor).get();
    }
}
