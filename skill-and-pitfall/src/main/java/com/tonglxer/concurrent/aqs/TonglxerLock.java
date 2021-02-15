package com.tonglxer.concurrent.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义的一个可重入锁
 *
 * @Author Tong LinXing
 * @date 2021/2/15
 */
public class TonglxerLock implements Lock {
    // 同步器实例
    private Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    /**
     * 自定义同步锁（基于AQS)
     */
    private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            // CAS操作
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            } else if (getExclusiveOwnerThread() == Thread.currentThread()) {
                // 同线程可重入
                setState(getState() + 1);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            // 非法释放
            if (getExclusiveOwnerThread() != Thread.currentThread()) {
                throw new IllegalMonitorStateException();
            }
            // 非法释放
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            // 每释放一次对状态进行减一
            setState(getState() - 1);
            // 若状态为0，则抹去线程信息
            if (getState() == 0) {
                setExclusiveOwnerThread(null);
            }
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() > 0;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }
}
