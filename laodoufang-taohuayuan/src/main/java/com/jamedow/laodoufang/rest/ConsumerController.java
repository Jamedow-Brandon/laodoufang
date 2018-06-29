package com.jamedow.laodoufang.rest;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.iot.model.v20180120.QueryProductListRequest;
import com.aliyuncs.iot.model.v20180120.QueryProductListResponse;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

/**
 * Description
 * <p>
 * Created by Administrator on 2017/11/21.
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
//        public static void main(String[] args) throws IOException {
//            /**
//             * 设置当前用户私有的 MQTT 的接入点。例如此处示意使用 XXX，实际使用请替换用户自己的接入点。接入点的获取方法是，在控制台创建 MQTT 实例，每个实例都会分配一个接入点域名。
//             */
//            final String broker ="tcp://post-cn-4590o3plc0w.mqtt.aliyuncs.com:1883";
//            /**
//             * 设置阿里云的 AccessKey，用于鉴权
//             */
//            final String acessKey ="LTAIuJEQSWXcQgOZ";
//            /**
//             * 设置阿里云的 SecretKey，用于鉴权
//             */
//            final String secretKey ="hscS01ryn5Ykk57NmXyfEBVMif0gFr";
//            /**
//             * 发消息使用的一级 Topic，需要先在 MQ 控制台里创建
//             */
//            final String topic ="sichuan_wolong";
//
//            /**
//             * MQTT 的 ClientID，一般由两部分组成，GroupID@@@DeviceID
//             * 其中 GroupID 在 MQ 控制台里创建
//             * DeviceID 由应用方设置，可能是设备编号等，需要唯一，否则服务端拒绝重复的 ClientID 连接
//             */
//            final String clientId ="GID_sichuan_wolong@@@CID_server_001";
//            String sign;
//            MemoryPersistence persistence = new MemoryPersistence();
//            try {
//                final MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
//                final MqttConnectOptions connOpts = new MqttConnectOptions();
//                System.out.println("Connecting to broker: " + broker);
//                /**
//                 * 计算签名，将签名作为 MQTT 的 password
//                 * 签名的计算方法，参考工具类 MacSignature，第一个参数是 ClientID 的前半部分，即 GroupID
//                 * 第二个参数阿里云的 SecretKey
//                 */
//                sign = MacSignature.macSignature(clientId.split("@@@")[0], secretKey);
//                /**
//                 * 设置订阅方订阅的 Topic 集合，此处遵循 MQTT 的订阅规则，可以是一级 Topic，二级 Topic，P2P 消息请订阅/p2p
//                 */
//                final String[] topicFilters=new String[]{topic+"/notice/",topic+"/p2p"};
//                final int[]qos={0,0};
//                connOpts.setUserName(acessKey);
//                connOpts.setServerURIs(new String[] { broker });
//                connOpts.setPassword(sign.toCharArray());
//                connOpts.setCleanSession(true);
//                connOpts.setKeepAliveInterval(90);
//                connOpts.setAutomaticReconnect(true);
//                sampleClient.setCallback(new MqttCallbackExtended() {
//                    public void connectComplete(boolean reconnect, String serverURI) {
//                        System.out.println("connect success");
//                        //连接成功，需要上传客户端所有的订阅关系
//                        try {
//                            sampleClient.subscribe(topicFilters,qos);
//                        } catch (MqttException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    public void connectionLost(Throwable throwable) {
//                        System.out.println("mqtt connection lost");
//                    }
//                    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
//                        System.out.println("messageArrived:" + topic + "------" + new String(mqttMessage.getPayload()));
//                    }
//                    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//                        System.out.println("deliveryComplete:" + iMqttDeliveryToken.getMessageId());
//                    }
//                });
//                //客户端每次上线都必须上传自己所有涉及的订阅关系，否则可能会导致消息接收延迟
//                sampleClient.connect(connOpts);
//                //每个客户端最多允许存在30个订阅关系，超出限制可能会丢弃导致收不到部分消息
//                sampleClient.subscribe(topicFilters,qos);
//                Thread.sleep(Integer.MAX_VALUE);
//            } catch (Exception me) {
//                me.printStackTrace();
//            }
//        }

    public static void main(String[] args) throws Exception {

        String accessKey = "LTAIuJEQSWXcQgOZ";
        String accessKeySecret = "hscS01ryn5Ykk57NmXyfEBVMif0gFr";
        String regionId = "cn-shanghai";

        String productCode = "Iot";
        String regionIdDomain = String.format("iot.%s.aliyuncs.com", regionId);
        //1.获取client
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessKeySecret);
        DefaultProfile.addEndpoint(regionId, regionId, productCode, regionIdDomain);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        //2.构造iot API的request
        QueryProductListRequest request = new QueryProductListRequest();
        request.setCurrentPage(1);
        request.setPageSize(20);
        //3.发起调用
        QueryProductListResponse response = (QueryProductListResponse) client.getAcsResponse(request);

        System.out.println(JSONObject.toJSONString(response));
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String play() {
        Properties properties = new Properties();
        // 您在控制台创建的 Consumer ID
        properties.put(PropertyKeyConst.ConsumerId, "CID_server_001");
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, "LTAIuJEQSWXcQgOZ");
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, "hscS01ryn5Ykk57NmXyfEBVMif0gFr");
        // 设置 TCP 接入域名（此处以公共云生产环境为例）
        properties.put(PropertyKeyConst.ONSAddr,
                "http://onsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal");
        // 集群订阅方式 (默认)
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        // 广播订阅方式
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("sichuan_wolong", "test||wolong", new MessageListener() { //订阅多个 Tag
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                return Action.CommitMessage;
            }
        });
//        //订阅另外一个 Topic
//        consumer.subscribe("TopicTestMQ-Other", "*", new MessageListener() { //订阅全部 Tag
//            public Action consume(Message message, ConsumeContext context) {
//                System.out.println("Receive: " + message);
//                return Action.CommitMessage;
//            }
//        });
        consumer.start();
        System.out.println("Consumer Started");
        return "index";
    }

//    public static void main(String[] args) throws Exception{
//        Properties properties = new Properties();
//        // 您在控制台创建的 Consumer ID
//        properties.put(PropertyKeyConst.ConsumerId, "CID_taohuayuan_server_001");
//        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
//        properties.put(PropertyKeyConst.AccessKey, "LTAIuJEQSWXcQgOZ");
//        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
//        properties.put(PropertyKeyConst.SecretKey, "hscS01ryn5Ykk57NmXyfEBVMif0gFr");
//        // 设置 TCP 接入域名（此处以公共云生产环境为例）
//        properties.put(PropertyKeyConst.ONSAddr,
//                "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet");
//
//        Consumer consumer =ONSFactory.createConsumer(properties);
//        /**
//         * 此处 MQ 客户端只需要订阅 MQTT 的一级 Topic 即可
//         */
//        consumer.subscribe("gongwang_taohuayuan", "*", new MessageListener() {
//            public Action consume(Message message, ConsumeContext consumeContext) {
//                System.out.println("recv msg:"+message);
//                return Action.CommitMessage;
//            }
//        });
//        consumer.start();
//        System.out.println("[Case Normal Consumer Init]   Ok");
//        Thread.sleep(Integer.MAX_VALUE);
//        consumer.shutdown();
//        System.exit(0);
//    }
}