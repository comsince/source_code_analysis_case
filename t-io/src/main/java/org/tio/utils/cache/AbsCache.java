package org.tio.utils.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.utils.hutool.StrUtil;

/**
 * @author tanyaowu 
 * 2018年10月21日 下午3:45:26
 */
public abstract class AbsCache implements ICache {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(AbsCache.class);

	protected String cacheName = null;
	
	private Long timeToLiveSeconds;
	
	private Long timeToIdleSeconds;

	/**
	 * 
	 * @author tanyaowu
	 */
	public AbsCache(String cacheName) {
		if (StrUtil.isBlank(cacheName)) {
			throw new RuntimeException("cacheName不允许为空");
		}
		this.setCacheName(cacheName);
	}
	
	public AbsCache(String cacheName, Long timeToLiveSeconds, Long timeToIdleSeconds) {
		if (StrUtil.isBlank(cacheName)) {
			throw new RuntimeException("cacheName不允许为空");
		}
		this.setCacheName(cacheName);
		this.setTimeToLiveSeconds(timeToLiveSeconds);
		this.setTimeToIdleSeconds(timeToIdleSeconds);
	}

	/**
	 * @return the cacheName
	 */
	public String getCacheName() {
		return cacheName;
	}

	/**
	 * @param cacheName the cacheName to set
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public Long getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public void setTimeToLiveSeconds(Long timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	public Long getTimeToIdleSeconds() {
		return timeToIdleSeconds;
	}

	public void setTimeToIdleSeconds(Long timeToIdleSeconds) {
		this.timeToIdleSeconds = timeToIdleSeconds;
	}

}
