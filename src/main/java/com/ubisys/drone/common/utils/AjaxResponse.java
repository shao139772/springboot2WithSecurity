package com.ubisys.drone.common.utils;

import java.util.Map;

public class AjaxResponse {

    private String msg = "操作成功";// 提示信息
    private Object obj = null;// 其他信息
    private int code;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();
    private Map<String, Object> attributes;// 其他参数

    private Object rows;

    private int total;


    public AjaxResponse() {
    }

    public AjaxResponse(int code, String msg) {
        this.timestamp = timestamp;
        this.code = code;
        this.msg = msg;
    }

    public AjaxResponse(int code, String msg, Map<String, Object> attributes) {
        this.code = code;
        this.msg = msg;
        this.timestamp = timestamp;
        this.attributes = attributes;
    }

    public static AjaxResponse success() {
        AjaxResponse json = new AjaxResponse();
        json.setCode(200);
        json.timestamp = System.currentTimeMillis();
        json.setMsg("ok");
        return json;
    }


    public static AjaxResponse success(Map<String, Object> attributes) {
        AjaxResponse json = success();
        json.setAttributes(attributes);
        json.timestamp = System.currentTimeMillis();
        return json;
    }


    public static AjaxResponse success(Object obj) {
        AjaxResponse json = success();
        json.setObj(obj);
        json.timestamp = System.currentTimeMillis();
        return json;
    }

    public static AjaxResponse failed(String msg) {
        AjaxResponse json = new AjaxResponse();
        json.setCode(500);
        json.setMsg(msg);
        json.timestamp = System.currentTimeMillis();
        return json;
    }


    public static AjaxResponse failed() {
        AjaxResponse json = new AjaxResponse();
        json.setCode(500);
        json.timestamp = System.currentTimeMillis();
        json.setMsg(" o(╯□╰)o，好像发生了不可描述的事情！！！");
        return json;
    }


    public static AjaxResponse build(int code, String msg) {
        AjaxResponse json = new AjaxResponse(code, msg);
        return json;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }


    /*public String getJsonStr(){
        JSONObject obj = new JSONObject();
        obj.put("success", this.isSuccess());
        obj.put("msg", this.getMsg());
        obj.put("obj", this.obj);
        obj.put("attributes", this.attributes);
        return obj.toJSONString();
    }*/
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
