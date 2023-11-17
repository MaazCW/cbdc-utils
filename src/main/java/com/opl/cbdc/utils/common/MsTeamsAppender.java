package com.opl.cbdc.utils.common;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.html.HTMLLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.helpers.Transform;

/**
 * 
 * @author hitesh.suthar.crt
 *
 */
public class MsTeamsAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	private static final String APPLICATION_JSON = "application/json";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String METHOD_POST = "POST";
	private static final String TRACE_PREFIX = "<br />&nbsp;&nbsp;&nbsp;&nbsp;";

	private static Layout<ILoggingEvent> defaultLayout = new HTMLLayout();

	private ObjectMapper objectMapper = new ObjectMapper();
	private String appName;
	private String webhookUri;
	private int timeout = 30_000;
	private Layout<ILoggingEvent> layout = defaultLayout;

	@Override
	protected void append(final ILoggingEvent event) {
//		try {
//			String[] parts = layout.doLayout(event).split("\n", 1);
//
//			MsTeamsCard msTeamsCard = new MsTeamsCard();
//			msTeamsCard.setContext("http://schema.org/extensions");
//			msTeamsCard.setType("MessageCard");
//
//			msTeamsCard.setThemeColor(decodeColor(event));
//			msTeamsCard.setTitle(appName + " - " + event.getLevel().toString());
//
//			msTeamsCard.setText(getHTMLTextBody(event));
//
//			final byte[] bytes = objectMapper.writeValueAsBytes(msTeamsCard);
//			postMessage(webhookUri, bytes);
//		} catch (Exception ex) {
//			
//			addError("Error posting log to MS Teams: " + event, ex);
//		}
	}

	private String getHTMLTextBody(ILoggingEvent event) {
		Date eventTime = new Date(event.getTimeStamp());
		String level = event.getLevel().levelStr;
		String loggerName = event.getLoggerName();
		String message = event.getMessage();

		StringBuilder stringBuilder = new StringBuilder();
		render(stringBuilder, event);

		StringBuilder sb = new StringBuilder("<html><table border=\"1\" style=\"width:100%;table-layout:fixed;\">");
		sb.append("<tr bgcolor=\"#6699ff\"><th>Date</th><th>Level</th><th>Logger</th><th>Message</th></tr>");
		sb.append("<tr><td>" + eventTime + "</td><td>" + level + "</td><td>" + loggerName + "</td><td>" + message
				+ "</td></tr>");
		sb.append(stringBuilder.toString());
		sb.append("</table></html>");
		return sb.toString();
	}

	public void render(StringBuilder sbuf, ILoggingEvent event) {
		IThrowableProxy tp = event.getThrowableProxy();
		sbuf.append("<tr><td colspan=\"4\">");
		while (tp != null) {
			render(sbuf, tp);
			tp = tp.getCause();
		}
		sbuf.append("</td></tr>");
	}

	private void render(StringBuilder sbuf, IThrowableProxy tp) {
		printFirstLine(sbuf, tp);

		int commonFrames = tp.getCommonFrames();
		StackTraceElementProxy[] stepArray = tp.getStackTraceElementProxyArray();

		for (int i = 0; i < stepArray.length - commonFrames; i++) {
			StackTraceElementProxy step = stepArray[i];
			sbuf.append(TRACE_PREFIX);
			sbuf.append(Transform.escapeTags(step.toString()));
			sbuf.append(CoreConstants.LINE_SEPARATOR);
		}

		if (commonFrames > 0) {
			sbuf.append(TRACE_PREFIX);
			sbuf.append("\t... ").append(commonFrames).append(" common frames omitted")
					.append(CoreConstants.LINE_SEPARATOR);
		}
	}

	private void printFirstLine(StringBuilder sb, IThrowableProxy tp) {
		int commonFrames = tp.getCommonFrames();
		if (commonFrames > 0) {
			sb.append("<br />").append(CoreConstants.CAUSED_BY);
		}
		sb.append(tp.getClassName()).append(": ").append(Transform.escapeTags(tp.getMessage()));
		sb.append(CoreConstants.LINE_SEPARATOR);
	}

	private String decodeColor(ILoggingEvent evt) {
		switch (evt.getLevel().toString()) {
		case "INFO":
			return "0B6623"; // Forest Green
		case "WARN":
			return "F9A602"; // Gold
		case "ERROR":
			return "FF0800"; // Candy Apple Red
		default:
			return "0080FF"; // Azure Blue
		}
	}

	private void postMessage(String uri, byte[] bytes) throws IOException {
		final HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
		conn.setConnectTimeout(timeout);
		conn.setReadTimeout(timeout);
		conn.setDoOutput(true);
		conn.setRequestMethod(METHOD_POST);

		conn.setFixedLengthStreamingMode(bytes.length);
		conn.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);

		final OutputStream os = conn.getOutputStream();
		os.write(bytes);

		os.flush();
		os.close();
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getWebhookUri() {
		return webhookUri;
	}

	public void setWebhookUri(String webhookUri) {
		this.webhookUri = webhookUri;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
