package org.apache.jmeter.protocol.http.sampler;


import org.apache.jorphan.util.JOrphanUtils;

public class HTTPSamplerBaseProduct {
	public String getPath(HTTPSamplerBase hTTPSamplerBase) {
		String p = hTTPSamplerBase.getPropertyAsString(HTTPSamplerBase.PATH);
		return encodeSpaces(p);
	}

	public String encodeSpaces(String path) {
		return JOrphanUtils.replaceAllChars(path, ' ', "%20");
	}
}