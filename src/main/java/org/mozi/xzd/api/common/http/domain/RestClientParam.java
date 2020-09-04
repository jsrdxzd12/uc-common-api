package org.mozi.xzd.api.common.http.domain;

import org.mozi.xzd.api.common.crypto.domain.vo.SignatureMethod;
import org.mozi.xzd.api.common.profile.properties.ApiClientSignatureProperties;
import org.springframework.http.*;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/7 15:35
 */
public class RestClientParam {

    private String accessKey;
    private String secretKey;
    private String serverUrl;
    private String instances;
    private String path;
    private String version;
    private HttpHeaders headers;
    private Object body;
    private HttpMethod httpMethod;
    private MediaType mediaType;
    private String signatureSelectConfigKey;


    private SignatureMethod signatureMethod;
    private ApiClientSignatureProperties signatureProperties;

    public RestClientParam signatureSelectConfigKey(String signatureSelectConfigKey){
        this.signatureSelectConfigKey=signatureSelectConfigKey;
        return this;
    }


    public String getSignatureConfigKey(String signatureSelectConfigKey){
        if(!StringUtils.hasLength(this.signatureSelectConfigKey)){
            this.signatureSelectConfigKey=signatureSelectConfigKey;
        }
        return this.signatureSelectConfigKey;
    }

    public ApiClientSignatureProperties getSignatureProperties(){
        return this.signatureProperties;
    }


    public RestClientParam instances(String instances){
        this.instances=instances;return this;
    }

    public RestClientParam accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public RestClientParam secretKey(String secretKey) {
        this.secretKey = secretKey;return this;
    }

    public RestClientParam signatureMethod(SignatureMethod signatureMethod){
        this.signatureMethod=signatureMethod; return this;
    }


    public RestClientParam serverUrl(String serverUrl) {
        this.serverUrl = serverUrl; return this;
    }
    public RestClientParam path(String path) {
        this.path = path; return this;
    }

    public RestClientParam version(String version) {
        this.version = version; return this;
    }


    public RestClientParam headers(HttpHeaders headers) {
        this.headers = headers; return this;
    }


    public RestClientParam body(Object body) {
        this.body = body; return this;
    }



    public RestClientParam httpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod; return this;
    }

    public RestClientParam mediaType(MediaType mediaType) {
        this.mediaType = mediaType; return this;
    }

    public RestClientParam signatureProperties(ApiClientSignatureProperties signatureProperties) {
        this.signatureProperties = signatureProperties; return this;
    }



    public HttpMethod getHttpMethod(){
        if(Objects.isNull(this.httpMethod)){
            this.httpMethod= HttpMethod.POST;
        }
        return this.httpMethod;
    }

    public SignatureMethod getSignatureMethod(){
        if(Objects.isNull(this.signatureMethod)){
            this.signatureMethod= SignatureMethod.HMAC_SHA256;
        }

        return this.signatureMethod;
    }

    public MediaType getMediaType() {
        if (Objects.isNull(this.mediaType)) {
            this.mediaType=MediaType.APPLICATION_JSON;
        }
        if (Objects.isNull(this.headers)) {
            this.headers = new HttpHeaders();
        }
        return this.mediaType;
    }

    public HttpHeaders getHeaders(){
        return this.headers;
    }

    public String getAccessKey(){
        return this.accessKey;
    }

    public Object getBody(){
        return this.body;
    }

    public String getSecretKey(){
        return this.secretKey;
    }

    public String getServerUrl() {
        return this.serverUrl;
    }

    public String getPath(){
        if(Objects.isNull(path)){
            this.path="";
        }
        return this.path;}

    public String getInstances(){ return this.instances;}

}
