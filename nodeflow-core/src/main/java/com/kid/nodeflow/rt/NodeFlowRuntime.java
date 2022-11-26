package com.kid.nodeflow.rt;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.kid.nodeflow.config.NodeFlowConfig;
import com.kid.nodeflow.exception.rt.IllegalNodeFlowConfigException;
import com.kid.nodeflow.exception.rt.SystemInitializeException;
import com.kid.nodeflow.parser.FlowParserProvider;
import com.kid.nodeflow.parser.base.FlowParser;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NodeFlow运行时类，控制着NodeFlow的运行时状态
 * @author cwk
 * @version 1.0
 */
public class NodeFlowRuntime {
	private static final Logger log = LoggerFactory.getLogger(NodeFlowRuntime.class);

	// 保存了当前正在运行的ChainExecutor
	private static final Set<ChainExecutor> RUNNING_EXECUTOR = new ConcurrentHashSet<>();
	// 未初始化、未启动
	private static final Integer UN_INITIALIZED = 0;
	// 初始化中、未启动
	private static final Integer INITIALIZING = 1;
	// 已初始化、未启动
	private static final Integer UN_START = 2;
	// 已初始化、已启动
	private static final Integer STARTED = 3;
	/**
	 * NodeFlow状态标志位，只有NodeFlowRuntime和{@link ChainExecutor}可以对其进行操作，并且保证互斥
	 */
	private static final AtomicInteger FLOW_STATE = new AtomicInteger(0);

	private static NodeFlowConfig config;

	/**
	 * 加载NodeFlowConfig配置
	 */
	public static void loadConfig(NodeFlowConfig config) {
		if (config == null) {
			throw new IllegalNodeFlowConfigException("NodeFlowConfig can not be null");
		}
		if (isStart()) {
			log.warn("NodeFlow is starting, can not load config");
			return;
		}
		synchronized (NodeFlowRuntime.class) {
			// 打印日志
			log.info("NodeFlow config loaded successfully");
			NodeFlowRuntime.config = config;
			init();
		}
	}

	/**
	 * 获取NodeFlowConfig
	 */
	public static NodeFlowConfig getConfig() {
		return config;
	}

	public static void registerExecutor(ChainExecutor executor) {
		if (executor != null) {
			RUNNING_EXECUTOR.add(executor);
		}
	}

	static void removeExecutor(ChainExecutor executor) {
		RUNNING_EXECUTOR.remove(executor);
	}

	public static boolean needInit() {
		return FLOW_STATE.get() <= UN_INITIALIZED;
	}

	public static boolean isInitializing() {
		return FLOW_STATE.get() == INITIALIZING;
	}

	public static boolean isInit() {
		return FLOW_STATE.get() >= UN_START;
	}

	public static boolean isStart() {
		return !RUNNING_EXECUTOR.isEmpty();
	}

	// 下列的同步保证了loadConfig操作与状态位设置的互斥
	synchronized static void setUnInit() {
		FLOW_STATE.set(UN_INITIALIZED);
	}

	synchronized static void setInitializing() {
		FLOW_STATE.set(INITIALIZING);
	}

	synchronized static void setInit() {
		FLOW_STATE.set(UN_START);
	}

	synchronized static void setStart() {
		if (!RUNNING_EXECUTOR.isEmpty()) {
			FLOW_STATE.set(STARTED);
		}
	}

	/**
	 * <p>整个NodeFlow的初始化方法</p>
	 * <p>
	 *   主要作用是
	 *   <ol>
	 *     <li>加载配置文件</li>
	 *     <li>读取Rule Source文件，并且解析</li>
	 *   </ol>
	 * </p>
	 */
	static void init() {
		log.info("NodeFlow is starting init");
		// 0. 设置标志位
		NodeFlowRuntime.setInitializing();
		DataBus.init();
		FlowBus.init();
		try {
			// 1. 获取NodeFlowConfig
			NodeFlowConfig config = NodeFlowRuntime.getConfig();
			// 2. 读取和解析RuleSource
			List<String> ruleSourcePath = config.getRuleSourcePath();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			for (String path : ruleSourcePath) {
				InputStream is = NodeFlowRuntime.class.getResourceAsStream(path);
				// 继续遍历其他配置文件
				if (is == null) {
					continue;
				}
				String fileName = FileUtil.getName(path);
				IoUtil.copy(is, os);
				// 根据文件名来判断选择哪种解析器
				FlowParser parser = FlowParserProvider.findParser(fileName);
				// 开始解析
				parser.parse(os.toString());
			}
			// 3. 设置标志位
			NodeFlowRuntime.setInit();
			log.info("NodeFlow init successfully");
		} catch (Exception e) {
			// x. rollback
			DataBus.clear();
			FlowBus.clear();
			NodeFlowRuntime.setUnInit();
			e.printStackTrace();
			throw new SystemInitializeException(e.getMessage());
		}
	}

}
