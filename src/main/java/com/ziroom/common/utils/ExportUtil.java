package com.ziroom.common.utils;


import com.ziroom.common.exception.ServiceException;

import java.util.concurrent.*;

/**
 * 数据导出
 *
 * @author weilinzi
 * @date 2019/7/12 10:18
 */
public class ExportUtil {

    /*
        数据导出线程池
        业务逻辑:
            因为导出数量较大,所以并行最大数量限制为20,所以核心线程数限制为10,最大线程数为20,队列长度为10
     */
    /**
     * 核心线程数
     */
    private static final int CORE_SIZE = 10;
    /**
     * 最大线程数
     */
    private static final int MAX_SIZE = 20;
    /**
     * 队列长度
     */
    public static final int QUEUE_SIZE = 10;
    /**
     * 队列
     */
    public static final ArrayBlockingQueue LIST = new ArrayBlockingQueue<>(QUEUE_SIZE);

    public static ExecutorService POOL;

    static {
        POOL = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE,
                60, TimeUnit.SECONDS, LIST, new PropertyReportExportPolicy());
    }

    private static class PropertyReportExportPolicy implements RejectedExecutionHandler {
        /**
         * Method that may be invoked by a {@link ThreadPoolExecutor} when
         * {@link ThreadPoolExecutor#execute execute} cannot accept a
         * task.  This may occur when no more threads or queue slots are
         * available because their bounds would be exceeded, or upon
         * shutdown of the Executor.
         * <p>
         * <p>In the absence of other alternatives, the method may throw
         * an unchecked {@link RejectedExecutionException}, which will be
         * propagated to the caller of {@code execute}.
         *
         * @param r        the runnable task requested to be executed
         * @param executor the executor attempting to execute this task
         * @throws RejectedExecutionException if there is no remedy
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new ServiceException("请求排队中,请稍后再试!");
        }
    }
}

