package superlib.cjt.co.openlibrary.threadpool;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wupeitao on 16/3/10.
 */
public class MyThreadPool {
    private final static int POOL_SIZE = Runtime.getRuntime().availableProcessors()+1;// 线程池的大小最好设置成为CUP核数的2N
    private final static int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors()*2+1;// 设置线程池的最大线程数
    private final static int KEEP_ALIVE_TIME = 4;// 设置线程的存活时间
    private final Executor mExecutor;
    private static MyThreadPool myThreadPool;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public MyThreadPool() {
        // 创建线程池工厂
        ThreadFactory factory = new PriorityThreadFactory("thread-pool", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        // 创建工作队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
        mExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, factory);
    }

    public static synchronized MyThreadPool getInstance() {
        if (myThreadPool == null) {
            myThreadPool = new MyThreadPool();
        }
        return myThreadPool;
    }


    // 在线程池中执行线程
    public void submit(Runnable command) {
        mExecutor.execute(command);
    }
}
