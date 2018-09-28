package com.skcc.cloudz.zcp.configuration.zuul;

import java.util.Map;

import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Configuration
@EnableZuulProxy
public class ZuulConfiguration {
	/*
	 * eg. zuul config
	 * zuul:
	 *   routes:
	 *     foo-swagger:
	 *       path: /foo-api/**     #matched: /swagger-ui.html, /webjars/*, /swagger-resources/*
	 *       url: http://backend
	 *     foo-api:
	 *       path: /foo/**
	 *       url: http://backend/foo
	 */

	@Component
	public class RemoveProxyHeaderPreFilter extends ZuulFilter {
		public Object run() {
			/*
			 * Avoid to change basePath of '/v2/api-docs' response.
			 * - PreDecorationFilter.addProxyHeaders(..)
			 * - Swagger2Controller.getDocumentation(..)#L85
			 * - ServletUriComponentsBuilder.getForwardedPrefix(..)
			 */
			RequestContext context = RequestContext.getCurrentContext();
			Map<String, String> headers = context.getZuulRequestHeaders();
			headers.remove(FilterConstants.X_FORWARDED_PREFIX_HEADER.toLowerCase());
			return null;
		}

		public boolean shouldFilter() { return true; }
		public String filterType() { return FilterConstants.PRE_TYPE; }
		public int filterOrder() { return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1; }
		
	}
}
