package com.zy.consumer.dubbo.cluster;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.Merger;
import org.apache.dubbo.rpc.cluster.merger.MergerFactory;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.apache.dubbo.rpc.Constants.ASYNC_KEY;
import static org.apache.dubbo.rpc.Constants.MERGER_KEY;

/**
 * https://blog.csdn.net/u011642663/article/details/81951323
 * https://www.imooc.com/article/33191
 * @param <T>
 */
@SuppressWarnings({"unchecked"})
public class CombineClusterInvoker<T> extends AbstractClusterInvoker<T> {

    private static final Logger logger = LoggerFactory.getLogger(CombineClusterInvoker.class);

    public CombineClusterInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    protected Result doInvoke(Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance) throws RpcException {
        // 1.校验服务提供方是否存在
        checkInvokers(invokers, invocation);

        // 2.异步调用所有服务提供方
        Map<String, Result> results = new HashMap<>(invokers.size());

        for (final Invoker<T> invoker : invokers) {
            RpcInvocation subInvocation = new RpcInvocation(invocation, invoker);
            subInvocation.setAttachment(ASYNC_KEY, Boolean.TRUE.toString());
            results.put(invoker.getUrl().getAddress(), invoker.invoke(subInvocation));
        }

        // TODO 由于只部署了单机, 这里模拟调用多台机器, 然后 merge 结果.
        /*for (final Invoker<T> invoker : invokers) {
            RpcInvocation subInvocation = new RpcInvocation(invocation, invoker);
            subInvocation.setAttachment(ASYNC_KEY, Boolean.TRUE.toString());
            results.put(invoker.getUrl().getAddress() + "--", invoker.invoke(subInvocation));
        }*/

        // 3.获取调用结果: 摒弃异常数据, 将正常结果放入结果列表中
        List<Result> resultList = new ArrayList<>(results.size());
        results.forEach((k, v) -> {
            try {
                Result r = v.get();
                if (r.hasException()) {
                    logger.error("Invoke " + k + " failed: " + r.getException().getMessage(), r.getException());
                } else {
                    resultList.add(r);
                }
            } catch (Exception e) {
                throw new RpcException("Failed to invoke service " + k + ": " + e.getMessage(), e);
            }
        });

        // 4.如果结果列表为空, 或结果只有一条数据, 则处理后返回
        if (resultList.isEmpty()) {
            return AsyncRpcResult.newDefaultAsyncResult(invocation);
        } else if (resultList.size() == 1) {
            // 结果列表中已经过滤了 error 数据, 所以这里直接调用AsyncRpcResult的不带 Throwable 的方法
            return AsyncRpcResult.newDefaultAsyncResult(resultList.iterator().next().getValue(), invocation);
        }

        Class<?> returnType;
        try {
            returnType = getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes()).getReturnType();
        } catch (NoSuchMethodException e) {
            throw new RpcException("failed to get returnType, please check.", e);
        }

        // 5.如果返回值类型是 void, 则处理后返回
        if (returnType == void.class) {
            return AsyncRpcResult.newDefaultAsyncResult(invocation);
        }

        Object result;

        // 6.获取接口方法级别配置的 merge 处理类型, 并基于此合并结果集
        String merger = getUrl().getMethodParameter(invocation.getMethodName(), MERGER_KEY);
        Merger resultMerger;
        if (ConfigUtils.isDefault(merger)) {
            resultMerger = MergerFactory.getMerger(returnType);
        } else {
            resultMerger = ExtensionLoader.getExtensionLoader(Merger.class).getExtension(merger);
        }
        if (resultMerger != null) {
            List<Object> rets = new ArrayList<>(resultList.size());
            for (Result r : resultList) {
                rets.add(r.getValue());
            }
            result = resultMerger.merge(rets.toArray((Object[]) Array.newInstance(returnType, 0)));
        } else {
            throw new RpcException("There is no merger to merge result.");
        }
        return AsyncRpcResult.newDefaultAsyncResult(result, invocation);
    }

}
