package io.arukas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.StringTokenizer;

@RestController
@SpringBootApplication
public class WenshuApplication {

    public static void main(String[] args) {
        SpringApplication.run(WenshuApplication.class, args);
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public RestTemplate restTemplate;

    @GetMapping("")
    public String download() {


        HttpHeaders iheaders = new HttpHeaders();
        iheaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");

        String cidUrl = "http://wenshu.court.gov.cn/WZWSREL0xpc3QvTGlzdD9zb3J0dHlwZT0xJmNvbmRpdGlvbnM9c2VhcmNoV29yZCsxK0FKTFgrKyVFNiVBMSU4OCVFNCVCQiVCNiVFNyVCMSVCQiVFNSU5RSU4QjolRTUlODglOTElRTQlQkElOEIlRTYlQTElODglRTQlQkIlQjY=?wzwschallenge=V1pXU19DT05GSVJNX1BSRUZJWF9MQUJFTDQ1MzMyOTg=";
        HttpEntity<String> cidRequestEntity = new HttpEntity<>(null, iheaders);

        ResponseEntity<String> cidResponseEntity = restTemplate.exchange(cidUrl, HttpMethod.GET, cidRequestEntity, String.class);

        List<String> cookies = cidResponseEntity.getHeaders().get("Set-Cookie");
        String wzws_cid = "";
        for (String cookie : cookies) {
            StringTokenizer tokenizer = new StringTokenizer(cookie, ";");
            while (tokenizer.hasMoreElements()) {
                String c = tokenizer.nextToken().trim();
                if (c.startsWith("wzws_cid")) {
                    wzws_cid = c;

                }

            }
        }
        System.out.println(wzws_cid);

        String u = "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+1+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E5%88%91%E4%BA%8B%E6%A1%88%E4%BB%B6";

        iheaders.add("Cookie", wzws_cid);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, iheaders);
//
        ResponseEntity<String> responseEntity = restTemplate.exchange(u, HttpMethod.GET, requestEntity, String.class);
//
        System.out.println(responseEntity.getHeaders());

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        headers.add("Cookie", "ASP.NET_SessionId=qpg5l3gr5woh3gslvsoy3ukt; wzws_cid=c259ab44b297695742f55a50796893273746e5a531d9692e843cc2879cc00ebcbbc782e9f81af998f60956bf9e95f0d04ac04204b0c203d3d44311761d374c71; vjkl5=a178220ca95ff2c904001c9180f14ed2e0727c97");
//        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
//        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("Param", "案件类型:刑事案件");
//        params.add("Index", "1");
//        params.add("Page", "10");
//        params.add("Order", "法院层级");
//        params.add("Direction", "asc");
//        params.add("vl5x", "5faa772046746b663b6db291");
//        params.add("number", "wens");
//        params.add("guid", UUID.randomUUID().toString());
//
//        String url = "http://wenshu.court.gov.cn/List/ListContent";
//        HttpEntity<MultiValueMap<String, String>> postEntity = new HttpEntity<>(params, headers);
//
//        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);
//
//        logger.debug("response: {}", exchange.getBody());

        return "ok";
    }

}
