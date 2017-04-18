package com.common.zookeeper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class ZkTool {
	private static ZooKeeper zooKeeper;

	private static ZkTool instance;

	private Map<String, Configuration> configs = new ConcurrentHashMap<String, Configuration>();

	private static final Logger LOGGER = LoggerFactory.getLogger(ZkTool.class);

	private final static Watcher defaultWatch = new Watcher() {
		public void process(WatchedEvent e) {
			LOGGER.info(e.getPath(), "", "");
			if (e.getState() == Watcher.Event.KeeperState.Expired) {
				LOGGER.warn("zookeeper expired!", e.toString(), "");
				handleExpired();
			} else if (e.getState() == Watcher.Event.KeeperState.Disconnected) {
				LOGGER.warn("zookeeper disconnected!", e.toString(), "");
				handleDisConnected();
			} else if (e.getState() == Watcher.Event.KeeperState.SyncConnected) {
				LOGGER.warn("zookeeper sync connected!", e.toString(), "");
				handleSynConnected();
			}
		}
	};

	private ZkTool() {
	}

	/**
	 * 配置工具类实例化
	 * 
	 * @return
	 */
	public static ZkTool getInstance() {
		if (instance == null) {
			synchronized (ZkTool.class) {
				if (instance == null) {
					try {
						zooKeeper = new ZooKeeper(BaseConfig.ZK_HOSTS, 10000, defaultWatch);
						instance = new ZkTool();
					} catch (IOException e) {
						LOGGER.error("zk error", e);
					}
				}
			}
		}
		return instance;
	}

	/**
	 * 配置工具类实例化
	 * 
	 * @param hostsIps
	 * @return
	 */
	public static ZkTool getInstance(String hostsIps) {
		if (instance == null) {
			synchronized (ZkTool.class) {
				if (instance == null) {
					try {
						zooKeeper = new ZooKeeper(hostsIps, 10000, defaultWatch);
						instance = new ZkTool();
					} catch (IOException e) {
						LOGGER.error("zk error", e);
					}
				}
			}
		}
		return instance;
	}

	private static void handleSynConnected() {
	}

	private static void handleDisConnected() {
	}

	private static void handleExpired() {
		try {
			zooKeeper = new ZooKeeper(BaseConfig.ZK_HOSTS, 10000, defaultWatch);
		} catch (IOException e) {
		}
	}

	private void handleDeleted(String path) {
		configs.remove(path);
	}

	private void handleChanged(String path) {
		try {
			byte[] content = getDataWatch(path);
			loadConfig(path, content);
		} catch (Exception e) {
			LOGGER.error("zk error", e);
		}
	}

	private byte[] getDataWatch(final String path) throws KeeperException, InterruptedException {
		return zooKeeper.getData(path, new Watcher() {
			public void process(WatchedEvent event) {
				LOGGER.info(event.getPath(), "", "");
				if (event.getState() == Watcher.Event.KeeperState.Expired) {
					LOGGER.warn("zookeeper expired!", event.toString(), "");
					handleExpired();
				} else if (event.getType() == Event.EventType.NodeDataChanged) {
					LOGGER.warn("zookeeper node data changed!");
					handleChanged(path);
				} else if (event.getType() == Event.EventType.NodeDeleted) {
					LOGGER.warn("zookeeper node data deleted!");
					handleDeleted(path);
				}

			}
		}, null);
	}

	private void loadConfig(final String path, byte[] content) {
		String configType = ".properties";
		if (path != null && path.endsWith(".xml")) {
			configType = ".xml";
		}
		if (configType.equals(".xml")) {
			Configuration conf = new XMLConfiguration();
			try {
				((XMLConfiguration) conf).load(new ByteArrayInputStream(content), "UTF-8");
			} catch (ConfigurationException e) {
				LOGGER.error("parse config : " + path + " exception!", e);
			}
			configs.put(path, conf);
		} else if (configType.endsWith(".properties")) {
			Configuration conf = new PropertiesConfiguration();
			try {
				((PropertiesConfiguration) conf).load(new ByteArrayInputStream(content), "UTF-8");
			} catch (ConfigurationException e) {
				LOGGER.error("parse config : " + path + " exception!", e);
			}
			configs.put(path, conf);
		}
	}

	private Configuration getConfig(String path) {
		try {
			if (configs.containsKey(path)) {
				return configs.get(path);
			} else {
				byte[] content = getDataWatch(path);
				if (content == null) {
					configs.remove(path);
					return null;
				}
				loadConfig(path, content);
				return configs.get(path);
			}
		} catch (KeeperException e) {
			LOGGER.error(" visit zookeeper KeeperException!", e);
		} catch (InterruptedException e) {
			LOGGER.error(" visit zookeeper InterruptedException!", e);
		}
		return null;
	}

	/**
	 * 更改配置 不建议使用
	 * 
	 * @param path
	 * @param data
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public Stat setData(String path, byte data[]) {
		path = "/" + BaseConfig.BASE_PATH + "/" + path;
		try {
			return zooKeeper.setData(path, data, -1);
		} catch (KeeperException e) {
			LOGGER.error("zk error", e);
		} catch (InterruptedException e) {
			LOGGER.error("zk error", e);
		}
		return null;
	}

	/**
	 * 获取配置中参数
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public String getValue(String path, String key) {
		path = "/" + BaseConfig.BASE_PATH + "/" + path;

		Configuration conf = getConfig(path);
		if (conf == null) {
			return null;
		}
		String value = null;
		String[] values = conf.getStringArray(key);
		if (values != null && values.length > 1) {
			value = StringUtils.join(values, ",");
		} else {
			value = conf.getString(key);
		}
		return value;
	}

	/**
	 * 获取配置中参数（逗号分隔,数组）
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public String[] getArrayValue(String path, String key) {
		path = "/" + BaseConfig.BASE_PATH + "/" + path;
		Configuration conf = getConfig(path);
		if (conf == null) {
			return null;
		}
		String[] values = conf.getStringArray(key);
		return values;
	}

	/**
	 * 配置文件导入zk专用
	 * 
	 * @param path
	 * @return
	 */
	public InputStream getInputStream(String path) {
		try {
			path = "/" + BaseConfig.BASE_PATH + "/" + path;

			byte[] buf = zooKeeper.getData(path, false, null);
			return new ByteArrayInputStream(buf);
		} catch (KeeperException e) {
			LOGGER.error(" visit zookeeper KeeperException!", e);
		} catch (InterruptedException e) {
			LOGGER.error(" visit zookeeper InterruptedException!", e);
		}
		return null;
	}

}
