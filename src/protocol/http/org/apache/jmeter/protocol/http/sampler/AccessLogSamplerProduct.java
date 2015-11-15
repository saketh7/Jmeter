package org.apache.jmeter.protocol.http.sampler;


import org.apache.jmeter.protocol.http.util.accesslog.Filter;
import java.io.Serializable;

public class AccessLogSamplerProduct implements Serializable {
	private String filterClassName;

	public String getFilterClassName() {
		return filterClassName;
	}

	public void setFilterClassName(String filterClassName) {
		this.filterClassName = filterClassName;
	}

	public void initFilter(AccessLogSampler accessLogSampler) {
		if (accessLogSampler.getFilter() == null && filterClassName != null && filterClassName.length() > 0) {
			try {
				accessLogSampler.setFilter((Filter) Class.forName(filterClassName).newInstance());
			} catch (Exception e) {
				AccessLogSampler.log.warn("Couldn't instantiate filter '" + filterClassName + "'", e);
			}
		}
	}
}