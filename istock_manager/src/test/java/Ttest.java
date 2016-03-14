import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gonghongrui on 15/3/23.
 */
public class Ttest {


    public static String defaultEncoding= "utf-8";

    private static int bufferSize= 1024;

    public void upload(List<String> jsons) throws URISyntaxException, IOException {

        String json = "[{\"code\":\"300118\",\"dataDate\":\"2015-03-23\",\"huanshou\":0,\"id\":\"300118-2015-03-23\",\"junjia\":0,\"kaipanjia\":0,\"liangbi\":0,\"name\":\"东方日升\",\"shoupanjia\":0,\"weibi\":0,\"weicha\":0,\"zhangdiefu\":0,\"zhangdiezhi\":0,\"zhangsu\":0,\"zhenfu\":0,\"zongguben\":379829376,\"zuoshou\":1054,\"zongshou\":0,\"zuidijia\":0,\"zuigaojia\":0,\"zongjine\":0}]";


        HttpEntity entity = EntityBuilder.create().setBinary(json.getBytes("utf-8")).build();
        //HttpEntity entity = EntityBuilder.create().setText(json).build();
        //entity.setContentType("application/json");
        HttpPost post = new HttpPost();
        post.addHeader("Content-Type","application/json");


        post.setEntity(entity);

        URIBuilder builder = null;


        builder = new URIBuilder("http://localhost:9080/istock/service/dataday/stockDataDay");

        post.setURI(builder.build());

        HttpClient httpClient =  HttpClientBuilder.create().build();


        HashMap<String, String> resultParams = null;

        HttpResponse response = httpClient.execute(post);
        String responseText = readStream(response.getEntity().getContent(),"utf-8");
        System.out.println(responseText);


    }

    public String readStream(InputStream in, String encoding) {
        if (in == null) {
            return null;
        }
        try {
            InputStreamReader inReader = null;
            if (encoding == null) {
                inReader = new InputStreamReader(in, defaultEncoding);
            } else {
                inReader = new InputStreamReader(in, encoding);
            }
            char[] buffer = new char[bufferSize];
            int readLen = 0;
            StringBuffer sb = new StringBuffer();
            while ((readLen = inReader.read(buffer)) != -1) {
                sb.append(buffer, 0, readLen);
            }
            inReader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Ttest t = new Ttest();
        t.upload(null);
    }
}
