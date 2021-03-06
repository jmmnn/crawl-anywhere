package fr.eolya.crawler.connectors.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.eolya.crawler.connectors.ISource;
import fr.eolya.crawler.connectors.Source;
import fr.eolya.crawler.connectors.web.StartingUrls;


public class SourceWeb extends Source implements ISource {

	protected StartingUrls startingUrls = null;

	public SourceWeb (int id, String className, String crawlMode, Map<String,Object> srcData) throws IOException {
		super(id, className, crawlMode, srcData);
	}

	public String getHost() {
		return getSrcDataString("url_host");
	}

	public String getUserAgent() {
		return getSrcDataString("user_agent");
	}

	public boolean isOptimized() {
		StartingUrls startingUrls = getStartingUrls();
		if (startingUrls==null) return false;
		return startingUrls.isOptimized();
	}

	public StartingUrls getStartingUrls() {
		if (startingUrls==null) {
			try {
				startingUrls = new StartingUrls(getSrcDataStringAsXml("url"));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return startingUrls;
	}

	public String getUrlIgnoreFields(){
		// fields which ARE session id
		return getSrcDataString("url_ignore_fields");
	}
	
	public String getUrlIgnoreFieldsNoSessionId(){
		// fields which ARE NOT session id
		return getSrcDataString("url_ignore_fields_no_session_id");
	}

	/**
	 * Provide the strategy for urls with both http and https protocol
	 * @return 0 means we want both urls, 1 means we want only the http url, 2 means we want only the https url 
	 */
	public int getProtocolStrategy(){
		String value = getSrcDataString("protocol_strategy");
		if (value==null || "".equals(value)) return 0;
		return Integer.parseInt(value);
	}

    public int getCheckDeletedStrategy(){
        String value = getSrcDataString("checkdeleted_strategy");
        if (value==null || "".equals(value)) return 0;
        return Integer.parseInt(value);
    }

    public List<String> getHostAliases() {
		return Arrays.asList(getSrcDataString("alias_host").split(","));
	}

	public String getAutomaticCleaning(){
		return getSrcDataString("automatic_cleaning");
	}

	public String getDescription() {
		return getHost() + "(" + getId() + ")";
	}
}
