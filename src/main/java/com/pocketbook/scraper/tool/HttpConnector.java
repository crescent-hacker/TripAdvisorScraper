package com.pocketbook.scraper.tool;

import com.pocketbook.scraper.config.ConstantConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Author: Difan Chen
 * Date: 20/12/2016
 */
public class HttpConnector {
    //logger
    private static final Logger logger = Logger.getLogger(HttpConnector.class);

    /**
     * Get the html content from given url
     *
     * @param url valid url
     * @return html content
     */
    public static String getUrlHtml(String url) {
        //return html
        String html = "";
        //get ssl context
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        } catch (KeyManagementException e) {
            logger.error(e);
        } catch (KeyStoreException e) {
            logger.error(e);
        }
        //exception
        if (sslContext == null)
            return html;

        //build http client
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).setSocketTimeout(ConstantConfig.TIME_OUT).setConnectTimeout(ConstantConfig.TIME_OUT)
                .setConnectionRequestTimeout(ConstantConfig.TIME_OUT).build();
        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        // create http request with get method
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(requestConfig);
        HttpResponse response;
        try {
            response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                html = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }
}
