package org.mozi.xzd.api.common.http;

import org.springframework.lang.Nullable;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/29 14:58
 */
public enum HttpStatus   {

    CONTINUE(100, "Continue"),
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),
    PROCESSING(102, "Processing"),
    CHECKPOINT(103, "Checkpoint"),
    OK(200, "操作成功"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
    NO_CONTENT(204, "No Content"),
    RESET_CONTENT(205, "Reset Content"),
    PARTIAL_CONTENT(206, "Partial Content"),
    MULTI_STATUS(207, "Multi-Status"),
    ALREADY_REPORTED(208, "Already Reported"),
    IM_USED(226, "IM Used"),
    MULTIPLE_CHOICES(300, "Multiple Choices"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),

    SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),

    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    PERMANENT_REDIRECT(308, "Permanent Redirect"),
    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
    REQUEST_TIAMEOUT(408, "Request Timeout"),
    CONFLICT(409, "Conflict"),
    GONE(410, "Gone"),
    LENGTH_REQUIRED(411, "Length Required"),
    PRECONDITION_FAILED(412, "Precondition Failed"),
    PAYLOAD_TOO_LARGE(413, "Payload Too Large"),

    URI_TOO_LONG(414, "URI Too Long"),

    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable"),
    EXPECTATION_FAILED(417, "Expectation Failed"),
    I_AM_A_TEAPOT(418, "I'm a teapot"),

    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    LOCKED(423, "Locked"),
    FAILED_DEPENDENCY(424, "Failed Dependency"),
    TOO_EARLY(425, "Too Early"),
    UPGRADE_REQUIRED(426, "Upgrade Required"),
    PRECONDITION_REQUIRED(428, "Precondition Required"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),
    INTERNAL_SERVER_ERROR(500, "服务器处理异常！"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported"),
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
    LOOP_DETECTED(508, "Loop Detected"),
    BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded"),
    NOT_EXTENDED(510, "Not Extended"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required"),


    SQL_EXCEPTION(1010,"执行数据库语句异常！"),

    NULL_POINT_EREXCEPTION(1002,"数据对象未被实例化！"),
    CLASS_NOT_FOUND_EXCEPTION(1020,"实体对象（class)未被系统加载!"),
    NO_SUCH_METHOD_ERROR(1021,"访问的函名未能在对象中找到！"),
    ILLEGALARGUMENT_EXCEPTION(1022,"方法的参数错误！"),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(1023," 输入参数校验异常"),

    DATE_TIME_EXCEPTION(1024,"日期转换失败"),

    CLASS_CAST_EXCEPTION(10024,"不能被转换的类型！"),
    ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION(10025,"数组下标越界!"),

    Serialization_Exception(10026,"序列化转换失败"),

    IO_EXCEPTION(1003,"IO文件操作失败！"),
    EXCEL_IMPORT_EXCEPTION(10030,"不合法的Excel模板导入！"),

    ARITHMETIC_EXCEPTION(1004,"数学运算发生错误！"),
    SECURITY_EXCEPTION(1005,"非法操作系统！"),

    INTERNAL_ERROR(1006,"JVM级别错误!"),

    TOKEN_ERROR(10070,"token异常!"),
    SECURITY_ERROR(1071,"用户权限异常!"),
    LOGIN_ERROR(1072,"用户登录异常!"),
    SIGNATURE_ERROR(1073,"用户验答异常!"),
    //上面的验证码已经和文娟对过了，慎重修改！！

    PASSWORD_INCORRECT(2002, "账号或密码有误"),
    PASSWORD_TOO_SHORT(2003, "密码长度太短,至少输入6位密码"),
    CAPTCHA_INCORRECT(2004, "验证码错误"),
    CAPTCHA_EMPTY(2005, "请输入验证码"),
    ACCOUNT_LOCKED(2006, "您的账户已被锁定，时长1分钟"),
    PASSWORD_FORMAT_ERROR(2007, "密码格式不正确"),
    START_BEFORE_NOW(3001, "开始时间早于当前时间"),
    END_BEFORE_START(3002, "结束时间早于当前时间"),
    ONLINE_TIME_REPEAT(3003, "上线时间与其他广告页时间重叠"),
    LOGIN_NOT_PERMITTED(3004, "该用户没有登录权限"),
    PARAMETERS_ERROR(3005, "参数非法或格式有误"),
    PARAMETERS_NULL(3006, "参数不能为空"),
    PARAMETERS(3016, "参数错误"),
    ACCESS_DENIED(3007, "没有权限"),
    FILE_NOT_EXISTED(3009, "文件不存在"),
    OBJECT_NOT_EXISTED(3010, "对象不存在"),
    PRIORITY_REPEATED(3011, "优先级重复"),
    STATUS_UPDATE_FAILED(3012, "状态更新失败"),
    STATUS_NOT_EDIT(3013, "状态不可编辑"),
    OFFLINE_NOT_PERMITTED(3014, "最后一个执行中Banner,不能下线"),
    NEW_VERSION_CODE_INVALID(3015, "新增版本号必须高于该平台已存在的所有有效版本号"),
    IMAGE_FORMAT_ERROR(3016, "上传图片格式不符"),
    PARAMETER_SIGN_ERROR(3017, "参数签名出错"),
    PERMISSION_DENY(3018, "权限不足"),


    UNDEFINED_ERROR(99999, "未定义错误类型"),

    //对外API验证提示
    ACTION_NULL(3101, "X-Action不能为空"),
    TIMESTAMP_NULL(3102, "X-Timestamp不能为空"),
    VERSION_NULL(3103, "X-Version不能为空"),
    NONCE_NULL(3104, "X-Nonce不能为空"),
    SECRETE_NULL(3105, "X-SecretId不能为空"),
    SIGNATURE_METHOD_NULL(3106, "X-SignatureMethod不能为空"),
    SIGNATURE_NULL(3107, "Signature不能为空"),
    SIGNATURE_NOT_PASS(3108, "signature验证不通过，请检查"),
    VERSION_NOT_PASS(3109, "接口版本号不一致，请检查"),
    TIMESTAMP_NOT_PASS(3110, "接口请求超过5分钟，请重新请求"),
    ACTION_NOT_PASS(3111, "接口方法名不正确，请检查"),
    INTERFACE_NULL(3112, "接口信息不存在，请检查");



    private final int value;
    private final String reasonPhrase;

    HttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }


    @Nullable
    public static HttpStatus resolve(int statusCode) {
        for (HttpStatus status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }
}
