package cn.bdqfork.rpc.consumer.context;

import cn.bdqfork.rpc.common.exception.RpcException;

/**
 * @author bdq
 * @date 2019-03-01
 */
public interface RpcFuture<T> {
    /**
     * 是否已完成
     *
     * @return
     */
    boolean isDone();

    /**
     * 获取结果
     *
     * @param timeout
     * @return
     * @throws RpcException
     */
    T get(long timeout) throws RpcException;

    /**
     * 设置结果
     *
     * @param t
     */
    void setResult(T t);
}
