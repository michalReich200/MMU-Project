package server;


import java.util.Map;

public class Request<T>
        extends java.lang.Object
        implements java.io.Serializable {

    private Map<java.lang.String, java.lang.String> headers;
    private T body;

    /**
     *
     * @param headers of the request
     * @param body of the request
     */
    public Request(java.util.Map<java.lang.String,java.lang.String> headers, T body){
        this.headers = headers;
        this.body = body;
    }
    public java.util.Map<java.lang.String,java.lang.String> getHeaders() {
        return this.headers;
    }

    public T getBody(){
        return this.body;
    }

    @Override
    public java.lang.String toString(){
        return "Request [headers" + headers + ", body=" + body + "]";
    }
}

