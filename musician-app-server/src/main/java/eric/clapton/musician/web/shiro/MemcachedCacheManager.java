package eric.clapton.musician.web.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

public class MemcachedCacheManager extends AbstractCacheManager {

	@SuppressWarnings("rawtypes")
	@Override
	protected Cache createCache(String name) throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}

}
