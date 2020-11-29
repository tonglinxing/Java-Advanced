package com.tonglxer.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 关于Java异步编程、回调函数
 *
 * 因为公司项目中有使用很多CompletableFuture的场景，当时没看明白
 * 这次统一整理一下用法和技巧
 *
 * <>https://colobu.com/2016/02/29/Java-CompletableFuture/</>
 *
 * @Author Tong LinXing
 * @date 2020/11/28
 */
@Slf4j
public class CompletableFutureDemo {
    private static final CompletableFutureDemo instance = new CompletableFutureDemo();

    private ExecutorService threadPool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        try {
            instance.anyOfOrAllOfDemo();
        } finally {
            instance.threadPool.shutdown();
        }
    }

    /**
     * runAsync()无返回值
     *
     * CompletableFuture可以从全局的 ForkJoinPool.commonPool()
     * 获得一个线程中执行这些任务
     * 若主线程执行结束且异步任务未完成，commonPool()会随着主线程结束而关闭
     * 则该任务永远不会被完成
     *
     * 所以需要使用自定义的线程池作为第二个参数传入来避免这种问题。
     * 参照supplyAsyncDemo()的写法。
     * */
    private void runAsyncDemo() {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("runAsyncDemo is interrupted.");
            }
        }).thenRun(() -> {
            // 回调函数在任务结束后会立即执行
            System.out.println("run任务执行完毕，执行Run回调函数..." );
        });
        System.out.println("run任务已创建，进入主线程...");
        try {
            completableFuture.get(); // get()方法会阻塞当前进程直至任务完成
            System.out.println("run任务完成，主线程阻塞结束...");
        } catch (InterruptedException | ExecutionException e) {
            log.error("runAsyncDemo is interrupted or execution error.");
        }

    }

    /**
     * supplyAsync()有返回值
     *
     * */
    private void supplyAsyncDemo() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("supplyAsyncDemo is interrupted.");
                }
                return "supplyAsyncDemo is done.";
            }, executor).thenApply(r -> {
                System.out.println("supply任务完成，正在执行Apply回调函数...");
                return "Apply: " + r;
            });
            future.thenRun(() -> {
                System.out.println("Apply回调执行结束，执行Run回调函数...");
            });
            // 回调函数也可异步
            future.thenAcceptAsync(r -> {
                System.out.println("Run回调执行结束，执行Accept异步回调函数...");
            }, executor);
            System.out.println("supply任务已创建，进入主线程..");
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("supplyAsyncDemo is interrupted or execution error.");
        } finally {
            // 关闭线程池
            executor.shutdown();
        }
    }

    private CompletableFuture<Integer> getTask1() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("Task1 is interrupted.");
            }
            return 24;
        }, threadPool);
    }

    private CompletableFuture<String> getTask2() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("Task2 is interrupted.");
            }

            return "tonglxer";

        }, threadPool);
    }

    /**
     * 1. 当两个CompletionStage都正常完成计算的时候
     * 就会执行提供的action，用来组合另外一个异步的结果
     *
     * 2. runAfterBoth是当两个CompletionStage都正常完成计算的时候,
     * 执行一个Runnable，这个Runnable并不使用计算的结果
     * */
    private void thenAcceptOrRunAfterBothDemo() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            return 100;
        }, threadPool);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            return 10;
        }, threadPool);
        future1.thenAcceptBoth(future2,
                (x, y) -> System.out.println(x + " " + y));
        future1.runAfterBoth(future2, () -> System.out.println("Two future all done."));
    }

    /**
     * future执行完成后，依赖future的结果进行另一个future的执行
     * thenCompose有返回值
     *
     * */
    private void thenComposeDemo() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 10;
        }, threadPool);
        future.thenCompose( i -> {
            return CompletableFuture.supplyAsync(() -> {
                int ans = i*i;
                System.out.println(ans);
                return (ans);
            }, threadPool);
        });
    }

    /**
     * 功能类似thenAcceptBoth()和runAfterBoth()
     * 但是thenCombine()有返回值
     *
     * */
    private void thenCombineDemo() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            return 100;
        }, threadPool);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "abc";
        }, threadPool);
        future1.thenCombine(future2, (x,y) -> {
            System.out.println(y + "-" + x);
            return "Combine done.";
        });
    }


    /**
     * acceptEither方法是当任意一个CompletionStage完成的时候，action这个消费者就会被执行
     * 这个方法返回CompletableFuture<Void>
     *
     * applyToEither方法是当任意一个CompletionStage完成的时候，fn会被执行
     * 它的返回值会当作新的CompletableFuture<U>的计算结果。
     *
     * */
    private void acceptEitherOrApplyToEitherDemo() {
        Random rand = new Random();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "future1";
        }, threadPool);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "future2";
        }, threadPool);
        // 有返回值
        future1.applyToEither(future2,i -> {
            System.out.println("applyToEither: " + i);
            return "One future done.";
        });

        // 无返回值
        future1.acceptEither(future2, i -> {
            System.out.println("acceptEither: " + i);
        });
    }

    /**
     * 1. allOf方法是当所有的CompletableFuture都执行完后执行计算。
     *
     * 2. anyOf方法是当任意一个CompletableFuture执行完后就会执行计算，无论哪个future结束
     * 计算的结果均相同
     *
     * */
    private void anyOfOrAllOfDemo() {
        Random rand = new Random();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        }, threadPool);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        }, threadPool);
        CompletableFuture.allOf(future1,future2).thenRun(() -> {
            System.out.println("All of future is done.");
        });
        CompletableFuture.anyOf(future1,future2).thenRun(() -> {
            System.out.println("One of future is done.");
        });
    }
}
