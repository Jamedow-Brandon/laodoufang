package com.jamedow.laodoufang.config;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Description
 * <p>
 * Created by 365 on 2016/12/26 0026.
 */
public class WeChatConstant {

    public static final String WE_CHAT_URL = "https://qyapi.weixin.qq.com/cgi-bin/";
    public static final String WE_CHAT_ACCESS_TOKEN = "access_token";
    public static final String WE_CHAT_CROP_ID = "cropId";
    public static final String WE_CHAT_SECRET = "secret";
    public static final String AGENT_ID = "agentid";
    public static final String GET_ACCESS_TOKEN_URL = WE_CHAT_URL + "gettoken";
    /**
     * 获取企业号应用
     * 该API用于获取企业号某个应用的基本信息，包括头像、昵称、帐号类型、认证类型、可见范围等信息
     */
    public static final String AGENT_GET = getWeChatUrl("agent/get");
    /**
     * 设置企业号应用
     * 该API用于设置企业应用的选项设置信息，如：地理位置上报等。第三方服务商不能调用该接口设置授权的主页型应用。
     */
    public static final String AGENT_SET = getWeChatUrl("agent/set");
    /**
     * 获取应用概况列表
     * 该API 用于获取secret所在管理组内的应用概况，会返回管理组内应用的id及名称、头像等信息
     */
    public static final String AGENT_LIST = getWeChatUrl("agent/list");
    /**
     * 创建应用菜单
     */
    public static final String MENU_CREATE = getWeChatUrl("menu/create");
    /**
     * 删除菜单
     */
    public static final String MENU_DELETE = getWeChatUrl("menu/delete");
    /**
     * 获取菜单列表
     */
    public static final String MENU_GET = getWeChatUrl("menu/get");
    /**
     * 成员关注企业号二次验证
     */
    public static final String USER_AUTHSUCC = getWeChatUrl("user/authsucc");
    /**
     * 创建成员
     */
    public static final String USER_CREATE = getWeChatUrl("user/create");
    /**
     * 更新成员
     */
    public static final String USER_UPDATE = getWeChatUrl("user/update");
    /**
     * 删除成员
     */
    public static final String USER_DELETE = getWeChatUrl("user/delete");
    /**
     * 批量删除成员
     */
    public static final String USER_BATCH_DELETE = getWeChatUrl("user/batchdelete");
    /**
     * 获取成员
     */
    public static final String USER_GET = getWeChatUrl("user/get");
    /**
     * 获取部门成员
     */
    public static final String USER_SIMPLE_LIST = getWeChatUrl("user/simplelist");
    /**
     * 获取部门成员(详情)
     */
    public static final String USER_LIST = getWeChatUrl("user/list");
    /**
     * 创建部门
     */
    public static final String DEPARTMENT_CREATE = getWeChatUrl("department/create");
    /**
     * 更新部门
     */
    public static final String DEPARTMENT_UPDATE = getWeChatUrl("department/update");
    /**
     * 删除部门
     */
    public static final String DEPARTMENT_DELETE = getWeChatUrl("department/delete");
    /**
     * 获取部门列表
     */
    public static final String DEPARTMENT_LIST = getWeChatUrl("department/list");
    /**
     * 创建标签
     */
    public static final String TAG_CREATE = getWeChatUrl("tag/create");
    /**
     * 更新标签名字
     */
    public static final String TAG_UPDATE = getWeChatUrl("tag/update");
    /**
     * 删除标签
     */
    public static final String TAG_DELETE = getWeChatUrl("tag/delete");
    /**
     * 获取标签成员
     */
    public static final String TAG_GET = getWeChatUrl("tag/get");
    /**
     * 增加标签成员
     */
    public static final String TAG_ADD_TAG_USERS = getWeChatUrl("tag/addtagusers");
    /**
     * 删除标签成员
     */
    public static final String TAG_DEL_TAG_USERS = getWeChatUrl("tag/deltagusers");
    /**
     * 获取标签列表
     */
    public static final String TAG_LIST = getWeChatUrl("tag/list");
    /**
     * 增量更新成员
     */
    public static final String BATCH_SYNC_USER = getWeChatUrl("batch/syncuser");
    /**
     * 全量覆盖成员
     */
    public static final String BATCH_REPLACE_USER = getWeChatUrl("batch/replaceuser");
    /**
     * 全量覆盖部门
     */
    public static final String BATCH_REPLACE_PARTY = getWeChatUrl("batch/replaceparty");
    /**
     * 获取异步任务结果
     */
    public static final String BATCH_GET_RESULT = getWeChatUrl("batch/getresult");

    public static final String MESSAGE_SEND = getWeChatUrl("message/send");

    public static String getWeChatPostUrl(String path, List<NameValuePair> params) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(path);
        builder.setParameters(params);
        return String.valueOf(builder.build());
    }

    private static String getWeChatUrl(String method) {
        return WE_CHAT_URL + method;
    }

//    /**
//     * 上传临时素材文件
//     */
//    public static final String MEDIA_UPLOAD = getWeChatUrl("media/upload");
//    /**
//     * 获取临时素材文件
//     */
//    public static final String MEDIA_GET = getWeChatUrl("media/get");
//    /**
//     * 上传图文消息内的图片
//     */
//    public static final String MATERIAL_UPLOAD_IMG = getWeChatUrl("media/uploadimg");
//    /**
//     * 上传永久图文素材
//     */
//    public static final String MATERIAL_ADD_MPNEWS = getWeChatUrl("material/add_mpnews");
//    /**
//     * 上传其他类型永久素材
//     */
//    public static final String MATERIAL_ADD_MATERIAL = getWeChatUrl("material/add_material");
//    /**
//     * 获取永久素材
//     */
//    public static final String MATERIAL_GET = getWeChatUrl("material/get");
//    /**
//     * 删除永久素材
//     */
//    public static final String MATERIAL_DEL = getWeChatUrl("material/del");
//    /**
//     * 修改永久图文素材
//     */
//    public static final String MATERIAL_UPDATE_MPNEWS = getWeChatUrl("material/update_mpnews");
//    /**
//     * 获取素材总数
//     */
//    public static final String MATERIAL_GET_COUNT = getWeChatUrl("material/get_count");
//    /**
//     * 获取素材列表
//     */
//    public static final String MATERIAL_BATCH_GET = getWeChatUrl("material/batchget");

    //身份验证 根据code获取成员信息
    public static String getUserInfo(final String accessToken, final String code) {
        return "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=" + accessToken + "&code=" + code;
    }

    //userid转换成openid接口
    public static String getOpenIdURL(String accessToken) {
        return "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=" + accessToken;
    }

    //获取jsapi_ticket
    public static String getJsapiTicket(String accessToken) {
        return "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=" + accessToken;
    }

}
