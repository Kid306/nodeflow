package com.kid.nodeflow.rt;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.kid.nodeflow.config.NodeFlowConfig;
import com.kid.nodeflow.context.FlowInitializerHolder;
import com.kid.nodeflow.context.PathResolverHolder;
import com.kid.nodeflow.exception.rt.IllegalNodeFlowConfigException;
import com.kid.nodeflow.exception.rt.SystemInitializeException;
import com.kid.nodeflow.parser.FlowParserProvider;
import com.kid.nodeflow.parser.base.FlowParser;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
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

	// 未初始化、未启动
	private static final Integer UN_INITIALIZED = 0;
	// 初始化中、未启动
	private static final Integer INITIALIZING = 1;
	// 已初始化、未启动
	private static final Integer INIT = 2;

	/**
	 * NodeFlow状态标志位，只有NodeFlowRuntime和{@link ChainExecutor}可以对其进行操作，并且保证互斥
	 */
	private static final AtomicInteger FLOW_STATE = new AtomicInteger(0);

	private static NodeFlowConfig config;

	/**
	 * 加载NodeFlowConfig配置，并且返回ChainExecutor。
	 * 该方法主要用于非SpringBoot环境下NodeFlowConfig的加载和ChainExecutor的获取
	 */
	public static ChainExecutor loadConfig(NodeFlowConfig config) {
		if (!isUnInitialized()) {
			throw new SystemInitializeException("NodeFlow is start initial, can not do loadConfig");
		}
		if (config == null) {
			throw new IllegalNodeFlowConfigException("NodeFlowConfig can not be null");
		}
		// 同步初始化NodeFlow
		synchronized (NodeFlowRuntime.class) {
			if (!isUnInitialized()) {
				// 打印日志
				log.info("NodeFlow config loaded successfully");
				NodeFlowRuntime.config = config;
				init();
				return new ChainExecutor(config);
			}
			throw new SystemInitializeException("NodeFlow has init, can not load config");
		}
	}

	/**
	 * <p>整个NodeFlow的初始化方法</p>
	 * <p>
	 *   主要作用是
	 *   <ol>
	 *     <li>设置初始化开始状态位</li>
	 *     <li>初始化DataBus和FlowBus</li>
	 *     <li>读取规则文件以外的Node信息，并且加载Node</li>
	 *     <li>读取规则文件的Node信息，并且加载Node</li>
	 *     <li>读取规则文件的Chain信息，并且加载Chain</li>
	 *     <li>设置初始化状态位</li>
	 *   </ol>
	 * </p>
	 */
	public static void init() {
		init(config);
	}

	/**
	 * <p>整个NodeFlow的初始化方法</p>
	 * <p>
	 *   主要作用是
	 *   <ol>
	 *     <li>设置NodeFlow运行配置</li>
	 *     <li>设置初始化开始状态位</li>
	 *     <li>初始化DataBus和FlowBus</li>
	 *     <li>读取规则文件以外的Node信息，并且加载Node</li>
	 *     <li>读取规则文件的Node信息，并且加载Node</li>
	 *     <li>读取规则文件的Chain信息，并且加载Chain</li>
	 *     <li>设置初始化状态位</li>
	 *   </ol>
	 * </p>
	 */
	public static void init(NodeFlowConfig config) {
		if (config == null) {
			throw new SystemInitializeException("NodeFlowConfig is null, cannot init");
		}
		setConfig(config);
		if (isUnInitialized()) {
			synchronized (NodeFlowRuntime.class) {
				if (isUnInitialized()) {
					log.info("NodeFlow is starting init");
					// 0. 设置标志位
					NodeFlowRuntime.setInitializing();
					DataBus.init();
					FlowBus.init();
					try {
						// 初始化NodeFlow，主要是解析和加载所有Node
						// 对于Local环境，不需要任何初始化，因为在后续的parse阶段完成了
						// 对于Spring环境，需要将注册到IOC中到NodeComponent解析为Node对象加载到FlowBus中
						FlowInitializerHolder.getInitializer().initialize();
						// 2. 读取和解析RuleSource
						List<String> ruleSourcePath = config.getSourcePath();
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						int count = 0;
						for (String path : ruleSourcePath) {
							// 获取路径下的规则文件
							// 在Local环境下，该路径是一个准确的相对路径，并且没有通配符。所以一个path对应一个文件
							// 在Spring环境下，该路径是一个可能带有通配符的相对路径。所以一个path对应一到多个文件
							List<InputStream> resources = PathResolverHolder.getResolver().getResources(path);
							for (InputStream is : resources) {
								// 继续遍历其他配置文件
								if (is == null) {
									log.warn("rule source not found: {}", path);
									continue;
								}
								String fileName = FileUtil.getName(path);
								IoUtil.copy(is, os);
								// 根据文件名来判断选择哪种解析器
								FlowParser parser = FlowParserProvider.findParser(fileName);
								// 开始解析
								log.info("parse rule source: {}", path);
								parser.parse(os.toString());
								count ++;
							}
						}
						if (count == 0) {
							throw new SystemInitializeException("no rule source find");
						}
						// 3. 设置标志位
						NodeFlowRuntime.setInit();
						log.info("NodeFlow init successfully");
					} catch (Exception e) {
						// x. rollback
						NodeFlowRuntime.setUnInitialized();
						DataBus.clear();
						FlowBus.clear();
						e.printStackTrace();
						throw new SystemInitializeException(e.getMessage());
					}
				}
			}
		}
	}



	// alias for isUnInitialized
	public static boolean needInit() {
		return isUnInitialized();
	}

	public static boolean isUnInitialized() {
		return FLOW_STATE.get() <= UN_INITIALIZED;
	}

	public static boolean isInitializing() {
		return FLOW_STATE.get() == INITIALIZING;
	}

	public static boolean isInit() {
		return FLOW_STATE.get() >= INIT;
	}

	// 下列的同步保证了loadConfig操作与状态位设置的互斥
	synchronized static void setUnInitialized() {
		FLOW_STATE.set(UN_INITIALIZED);
	}

	synchronized static void setInitializing() {
		FLOW_STATE.set(INITIALIZING);
	}

	synchronized static void setInit() {
		FLOW_STATE.set(INIT);
	}

	public static NodeFlowConfig getConfig() {
		return config;
	}

	public static void setConfig(NodeFlowConfig config) {
		NodeFlowRuntime.config = config;
	}
}
