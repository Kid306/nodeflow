package com.kid.nodeflow.config;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@link NodeFlowConfig}的持有器，提供了Config的初始化和获取方法，保证整个程序实例中只有一个Config对象
 *
 * @author cwk
 * @version 1.0
 */
public class NodeFlowConfigHolder {
	public static NodeFlowConfig config;

	private static class CASLock {

		private final AtomicBoolean lock = new AtomicBoolean(false);

		public CASLock() {
		}

		// 不响应中断
		public void lock() {
			while (!tryLock()) {}
		}

		public void release() {
			lock.compareAndSet(true, false);
		}

		private boolean tryLock() {
			return lock.compareAndSet(false, true);
		}
	}

	private static final CASLock lock = new CASLock();

	/**
	 * 获取全局唯一实例NodeFlowConfig
	 */
	public static NodeFlowConfig getConfig() {
		if (config == null) {
			// TODO 同步加载NodeFlowConfig
			// block until get the lock
			lock.lock();
			try {
				if (config == null) {
					config = new NodeFlowConfig();
				}
				return config;
			} finally {
				// release the lock
				lock.release();
			}
		} else {
			return config;
		}
	}
}
