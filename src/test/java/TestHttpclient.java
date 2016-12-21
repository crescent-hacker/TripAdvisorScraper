import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tools.ant.filters.StringInputStream;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.io.IOException;
import java.security.cert.CertificateException;

/**
 * Author: Difan Chen
 * Date: 20/12/2016
 */
public class TestHttpclient {
    @Test
    public void jUnitTest() {
        String url = "https://www.tripadvisor.com.au/ExpandedUserReviews-g293986-d2069729?target=444926720&context=1&reviews=444926720,443203098,442861074,442475588,442150860,440087838,439656494,438204925,438085431,435856311&servlet=Attraction_Review&expand=1";
        String html = getUrlHtml(url);
        try{
            Dom4jReader drb=new Dom4jReader();
            html = preprocessing(html);
//            System.out.println(html);
            drb.getInfo(new StringInputStream(html));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    String getUrlHtml(String url) {
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
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        //exception
        if (sslContext == null)
            return html;

        //build http client
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        // create http request with get method
        HttpGet httpget = new HttpGet(url);
        System.out.println("executing request" + httpget.getRequestLine());
        HttpResponse response;
        try {
            response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            System.out.println("----------------------------------------");
//            System.out.println(response.getStatusLine());
            if (entity != null) {
//                System.out.println("Response content length: " + entity.getContentLength());
                html = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    String preprocessing(String html){
        //1.remove script
        html = html.replaceAll("(?is)<script[^>]*?>.*?<\\/script>","");
        //2. fix onclick in a link
        String unfixedALink = "<a onclick=>";
        String fixedALink = "<a onclick=\"\">";

        html = html.replaceAll(unfixedALink,fixedALink);

        //3. remove unclosed image
        html = html.replaceAll("<img(.*?)></img>","");
        html = html.replaceAll("<img(.*?)>","");

        //4. remove &nbsp;
        html = html.replaceAll("&nbsp;","");

        //5. add root
        html = "<reviews>"+html+"</reviews>";

        return html;
    }
}
