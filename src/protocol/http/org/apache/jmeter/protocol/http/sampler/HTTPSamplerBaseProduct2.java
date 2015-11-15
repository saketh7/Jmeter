package org.apache.jmeter.protocol.http.sampler;


import org.apache.jmeter.protocol.http.util.HTTPFileArg;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.apache.jmeter.protocol.http.util.HTTPConstantsInterface;

public class HTTPSamplerBaseProduct2 {
	/**
	* Determine if the file should be sent as the entire Content body, i.e. without any additional wrapping.
	* @return  true if specified file is to be sent as the body, i.e. there is a single file entry which has a non-empty path and an empty Parameter name.
	*/
	public boolean getSendFileAsPostBody(HTTPSamplerBase hTTPSamplerBase) {
		HTTPFileArg[] files = hTTPSamplerBase.getHTTPFiles();
		return (files.length == 1) && (files[0].getPath().length() > 0) && (files[0].getParamName().length() == 0);
	}

	/**
	* Determine if we should use multipart/form-data or application/x-www-form-urlencoded for the post
	* @return  true if multipart/form-data should be used and method is POST
	*/
	public boolean getUseMultipartForPost(HTTPSamplerBase hTTPSamplerBase) {
		HTTPFileArg[] files = hTTPSamplerBase.getHTTPFiles();
		if (HTTPConstants.POST.equals(hTTPSamplerBase.getMethod()) && (hTTPSamplerBase.getDoMultipartPost()
				|| (files.length > 0 && !getSendFileAsPostBody(hTTPSamplerBase)))) {
			return true;
		}
		return false;
	}

	/**
	* Gets the protocol, with default.
	* @return  the protocol
	*/
	public String getProtocol(HTTPSamplerBase hTTPSamplerBase) {
		String protocol = hTTPSamplerBase.getPropertyAsString(HTTPSamplerBase.PROTOCOL);
		if (protocol == null || protocol.length() == 0) {
			return HTTPSamplerBase.DEFAULT_PROTOCOL;
		}
		return protocol;
	}

	/**
	* Tell whether the default port for the specified protocol is used
	* @return  true if the default port number for the protocol is used, false otherwise
	*/
	public boolean isProtocolDefaultPort(HTTPSamplerBase hTTPSamplerBase) {
		final int port = getPortIfSpecified(hTTPSamplerBase);
		final String protocol = getProtocol(hTTPSamplerBase);
		if (port == HTTPSamplerBase.UNSPECIFIED_PORT
				|| (HTTPConstants.PROTOCOL_HTTP.equalsIgnoreCase(protocol) && port == HTTPConstants.DEFAULT_HTTP_PORT)
				|| (HTTPConstants.PROTOCOL_HTTPS.equalsIgnoreCase(protocol)
						&& port == HTTPConstants.DEFAULT_HTTPS_PORT)) {
			return true;
		}
		return false;
	}

	/**
	* Get the port; apply the default for the protocol if necessary.
	* @return  the port number, with default applied if required.
	*/
	public int getPort(HTTPSamplerBase hTTPSamplerBase) {
		final int port = getPortIfSpecified(hTTPSamplerBase);
		if (port == HTTPSamplerBase.UNSPECIFIED_PORT) {
			String prot = getProtocol(hTTPSamplerBase);
			if (HTTPConstants.PROTOCOL_HTTPS.equalsIgnoreCase(prot)) {
				return HTTPConstants.DEFAULT_HTTPS_PORT;
			}
			if (!HTTPConstants.PROTOCOL_HTTP.equalsIgnoreCase(prot)) {
				HTTPSamplerBase.log.warn("Unexpected protocol: " + prot);
			}
			return HTTPConstants.DEFAULT_HTTP_PORT;
		}
		return port;
	}

	/**
	* Get the port number from the port string, allowing for trailing blanks.
	* @return  port number or UNSPECIFIED_PORT (== 0)
	*/
	public int getPortIfSpecified(HTTPSamplerBase hTTPSamplerBase) {
		String port_s = hTTPSamplerBase.getPropertyAsString(HTTPSamplerBase.PORT,
				HTTPSamplerBase.UNSPECIFIED_PORT_AS_STRING);
		try {
			return Integer.parseInt(port_s.trim());
		} catch (NumberFormatException e) {
			return HTTPSamplerBase.UNSPECIFIED_PORT;
		}
	}
}