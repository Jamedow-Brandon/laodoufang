// $(function () {
//     var requestUrl = '';
//     var jsApiList = [
//         "onMenuShareAppMessage",
//         "onMenuShareWechat",
//         "hideAllNonBaseMenuItem"
//     ];
//     wechat.requestUrl = requestUrl;
//     wechat.jsApiList = jsApiList;
//     wechat.init();
//
// });

var wechat = {
    requestUrl: "",
    jsApiList: [],
    /**
     * 初始化Js-Api 获取JsApiTicket
     * @param _requestUrl 调用JS接口页面的完整URL。 例：http://www.baidu.com
     * @param _jsApiList 必填，需要使用的JS接口列表，所有JS接口列表见附录2
     * @param _successCallback config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，
     *                         所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。
     *                         对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
     * @param _errorCallback config信息验证失败会执行error方法，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，
     *                       也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
     */
    init: function (_successCallback, _errorCallback) {
        $.ajax({
            url: "/wechat/config-wx-js",
            method: "GET",
            data: {requestUrl: wechat.requestUrl},
            success: function (data) {
                wechat.config(true, data.appId, data.timestamp, data.nonceStr, data.signature, wechat.jsApiList, _successCallback, _errorCallback);
            }
        });
    },
    /**
     *
     * @param _debug 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
     * @param _appId 必填，开发者ID
     * @param _timestamp 必填，生成签名的时间戳
     * @param _nonceStr 必填，生成签名的随机串
     * @param _signature 必填，签名，见[附录1](#11974)
     * @param _jsApiList 必填，需要使用的JS接口列表，所有JS接口列表见附录2
     * @param _successCallback config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，
     *                         所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。
     *                         对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
     * @param _errorCallback config信息验证失败会执行error方法，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，
     *                       也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
     * @return {{}} 以键值对的形式返回，可用的api值true，不可用为false
     *              如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
     */
    config: function (_debug, _appId, _timestamp, _nonceStr, _signature, _jsApiList, _successCallback, _errorCallback) {
        var res = {};
        wx.config({
            beta: true,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
            debug: _debug || true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: _appId || '', // 必填，企业微信的corpID
            timestamp: _timestamp || '', // 必填，生成签名的时间戳
            nonceStr: _nonceStr || '', // 必填，生成签名的随机串
            signature: _signature || '',// 必填，签名，见[附录1](#11974)
            jsApiList: _jsApiList || [] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        wx.ready(function () {
            // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，
            // 所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。
            // 对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
            _successCallback();
        });
        wx.error(function (res) {
            // config信息验证失败会执行error方法，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，
            // 也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
            _errorCallback();
        });
        wx.checkJsApi({
            jsApiList: _jsApiList || [], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function (data) {
                res = data;
                // 以键值对的形式返回，可用的api值true，不可用为false
                // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
            }
        });
        return res;
    },
    /*
     分享接口
     */
    /**
     * 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
     * @param _title
     * @param _link
     * @param _imgUrl
     * @param _successCallback
     * @param _cancelCallback
     */
    onMenuShareTimeline: function (_title, _link, _imgUrl, _successCallback, _cancelCallback) {
        wx.onMenuShareTimeline({
            title: _title, // 分享标题
            link: _link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
            imgUrl: _imgUrl, // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
                _successCallback();
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                _cancelCallback();
            }
        });
    },
    /**
     * 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
     * @param _title
     * @param _desc
     * @param _link
     * @param _imgUrl
     * @param _type
     * @param _dataUrl
     * @param _successCallback
     * @param _cancelCallback
     */
    onMenuShareAppMessage: function (_title, _desc, _link, _imgUrl, _type, _dataUrl, _successCallback, _cancelCallback) {
        wx.onMenuShareAppMessage({
            title: _title, // 分享标题
            desc: _desc, // 分享描述
            link: _link, // 分享链接
            imgUrl: _imgUrl, // 分享图标
            type: _type, // 分享类型,music、video或link，不填默认为link
            dataUrl: _dataUrl, // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
                // 用户确认分享后执行的回调函数
                _successCallback();
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                _cancelCallback();
            }
        });
    },
    /**
     * 获取“分享到QQ”按钮点击状态及自定义分享内容接口
     * @param _title
     * @param _desc
     * @param _link
     * @param _imgUrl
     * @param _successCallback
     * @param _cancelCallback
     */
    onMenuShareQQ: function (_title, _desc, _link, _imgUrl, _successCallback, _cancelCallback) {
        wx.onMenuShareQQ({
            title: _title, // 分享标题
            desc: _desc, // 分享描述
            link: _link, // 分享链接
            imgUrl: _imgUrl, // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
                _successCallback();
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                _cancelCallback();
            }
        });
    },
    /**
     * 获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口
     * @param _title
     * @param _desc
     * @param _link
     * @param _imgUrl
     * @param _successCallback
     * @param _cancelCallback
     */
    onMenuShareWeibo: function (_title, _desc, _link, _imgUrl, _successCallback, _cancelCallback) {
        wx.onMenuShareWeibo({
            title: _title, // 分享标题
            desc: _desc, // 分享描述
            link: _link, // 分享链接
            imgUrl: _imgUrl, // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
                _successCallback();
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                _cancelCallback();
            }
        });
    },
    /**
     * 获取“分享到QQ空间”按钮点击状态及自定义分享内容接口
     * @param _title
     * @param _desc
     * @param _link
     * @param _imgUrl
     * @param _successCallback
     * @param _cancelCallback
     */
    onMenuShareQZone: function (_title, _desc, _link, _imgUrl, _successCallback, _cancelCallback) {
        wx.onMenuShareQZone({
            title: _title, // 分享标题
            desc: _desc, // 分享描述
            link: _link, // 分享链接
            imgUrl: _imgUrl, // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
                _successCallback();
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                _cancelCallback();
            }
        });
    },
    /*
     音频接口
     */
    /**
     * 开始录音接口
     */
    startRecord: function () {
        wx.startRecord();
    },
    /**
     * 停止录音接口
     * @return {string} 返回录音的本地ID
     */
    stopRecord: function () {
        var localId = '';
        wx.stopRecord({
            success: function (res) {
                localId = res.localId;
            }
        });
        return localId;
    },
    /**
     * 监听录音自动停止接口
     * @return {string} 返回录音的本地ID
     */
    onVoiceRecordEnd: function () {
        var localId = '';
        wx.onVoiceRecordEnd({
            // 录音时间超过一分钟没有停止的时候会执行 complete 回调
            complete: function (res) {
                localId = res.localId;
            }
        });
        return localId;
    },
    /**
     * 播放语音接口
     * @param _localId 录音的本地ID
     */
    playVoice: function (_localId) {
        wx.playVoice({
            localId: _localId || '' // 需要播放的音频的本地ID，由stopRecord接口获得
        });
    },
    /**
     * 暂停播放接口
     * @param _localId 录音的本地ID
     */
    pauseVoice: function (_localId) {
        wx.pauseVoice({
            localId: _localId || '' // 需要暂停的音频的本地ID，由stopRecord接口获得
        });
    },
    /**
     * 停止播放接口
     * @param _localId 录音的本地ID
     */
    stopVoice: function (_localId) {
        wx.stopVoice({
            localId: _localId || '' // 需要停止的音频的本地ID，由stopRecord接口获得
        });
    },
    /**
     * 监听语音播放完毕接口
     * @return {string} 返回音频的本地ID
     */
    onVoicePlayEnd: function () {
        var localId = '';
        wx.onVoicePlayEnd({
            success: function (res) {
                localId = res.localId; // 返回音频的本地ID
            }
        });
        return localId;
    },
    /**
     * 上传语音接口
     * @param _localId 需要上传的音频的本地ID
     * @param _isShowProgressTips 默认为1，显示进度提示 0：不提示；1：提示；
     * @param _successCallback
     * @return {string} 返回音频的服务器端ID
     *
     * 备注：上传语音有效期3天，可用微信多媒体接口下载语音到自己的服务器，此处获得的 serverId 即 media_id，参考文档
     * .目前多媒体文件下载接口的频率限制为10000次/天，如需要调高频率，请登录微信公众平台，在开发 - 接口权限的列表中，申请提高临时上限。
     */
    uploadVoice: function (_localId, _isShowProgressTips, _successCallback) {
        var serverId = '';
        wx.uploadVoice({
            localId: _localId || '', // 需要上传的音频的本地ID，由stopRecord接口获得
            isShowProgressTips: _isShowProgressTips || 1, // 默认为1，显示进度提示
            success: function (res) {
                serverId = res.serverId; // 返回音频的服务器端ID
                _successCallback();
            }
        });
        return serverId;
    },
    /**
     *
     * @param _serverId 需要下载的音频的服务器端ID，由uploadVoice接口获得
     * @param _isShowProgressTips 默认为1，显示进度提示 0：不提示；1：提示；
     * @param _successCallback
     * @return {string} 返回音频的本地ID
     */
    downloadVoice: function (_serverId, _isShowProgressTips, _successCallback) {
        var localId = '';
        wx.downloadVoice({
            serverId: _serverId || '', // 需要下载的音频的服务器端ID，由uploadVoice接口获得
            isShowProgressTips: _isShowProgressTips || 1, // 默认为1，显示进度提示
            success: function (res) {
                localId = res.localId; // 返回音频的本地ID
                _successCallback();
            }
        });
        return localId;
    },
    /*
     图像接口
     */
    /**
     * 拍照或从手机相册中选图接口
     * @param _count 可选择图片数量，默认9
     * @param _sizeType 可以指定是原图还是压缩图，默认二者都有 original：原图；compressed：压缩图；
     * @param _sourceType 可以指定来源是相册还是相机，默认二者都有 album：相册；camera：相机
     * @param _successCallback
     * @return {Array} 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
     */
    chooseImage: function (_count, _sizeType, _sourceType, _successCallback) {
        var localIds = [];
        wx.chooseImage({
            count: _count || 9, // 默认9
            sizeType: _sizeType || ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: _sourceType || ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                _successCallback();
            }
        });
        return localIds;
    },
    /**
     * 预览图片接口
     * @param _imgUrl 当前显示图片的http链接
     * @param _imgUrlArray 需要预览的图片http链接列表
     */
    previewImage: function (_imgUrl, _imgUrlArray) {
        wx.previewImage({
            current: _imgUrl || '', // 当前显示图片的http链接
            urls: _imgUrlArray || [] // 需要预览的图片http链接列表
        });
    },
    /**
     * 上传图片接口
     * @param _localId  需要上传的图片的本地ID
     * @param _isShowProgressTips 默认为1，显示进度提示 0：不提示；1：提示；
     * @param _successCallback
     * @return {string} 返回图片的服务器端ID
     *
     * 备注：上传图片有效期3天，可用微信多媒体接口下载图片到自己的服务器，此处获得的 serverId 即 media_id。
     */
    uploadImage: function (_localId, _isShowProgressTips, _successCallback) {
        var serverId = '';
        wx.uploadImage({
            localId: _localId || '', // 需要上传的图片的本地ID，由chooseImage接口获得
            isShowProgressTips: _isShowProgressTips || 1, // 默认为1，显示进度提示
            success: function (res) {
                serverId = res.serverId; // 返回图片的服务器端ID
                _successCallback();
            }
        });
        return serverId;
    },
    /**
     * 下载图片接口
     * @param _serverId 需要下载的图片的服务器端ID
     * @param _isShowProgressTips 默认为1，显示进度提示 0：不提示；1：提示；
     * @param _successCallback
     * @return {string} 返回图片下载后的本地ID
     */
    downloadImage: function (_serverId, _isShowProgressTips, _successCallback) {
        var localId = '';
        wx.downloadImage({
            serverId: _serverId || '', // 需要下载的图片的服务器端ID，由uploadImage接口获得
            isShowProgressTips: _isShowProgressTips || 1, // 默认为1，显示进度提示
            success: function (res) {
                localId = res.localId; // 返回图片下载后的本地ID
                _successCallback();
            }
        });
        return localId;
    },
    /*
     智能接口
     */
    /**
     * 识别音频并返回识别结果接口
     * @param _localId 需要识别的音频的本地Id
     * @param _isShowProgressTips 默认为1，显示进度提示 0：不提示；1：提示；
     * @param _successCallback
     * @return {string} 返回语音识别的结果
     */
    translateVoice: function (_localId, _isShowProgressTips, _successCallback) {
        var translateResult = '';
        wx.translateVoice({
            localId: '', // 需要识别的音频的本地Id，由录音相关接口获得
            isShowProgressTips: 1, // 默认为1，显示进度提示
            success: function (res) {
                translateResult = res.translateResult; // 语音识别的结果
                _successCallback();
            }
        });
        return translateResult;
    },
    /*
     设备信息
     */
    /**
     * 获取网络状态接口
     * @returns {string} 返回网络类型2g，3g，4g，wifi
     */
    getNetworkType: function () {
        var networkType = '';
        wx.getNetworkType({
            success: function (res) {
                networkType = res.networkType; // 返回网络类型2g，3g，4g，wifi
            }
        });
        return networkType;
    },
    /*
     地理位置
     */
    /**
     * 使用微信内置地图查看位置接口
     * @param _latitude 纬度，浮点数，范围为90 ~ -90
     * @param _longitude 经度，浮点数，范围为180 ~ -180。
     * @param _name 位置名
     * @param _address 地址详情说明
     * @param _scale 地图缩放级别,整形值,范围从1~28。默认为最大
     * @param _infoUrl 在查看位置界面底部显示的超链接,可点击跳转
     */
    openLocation: function (_latitude, _longitude, _name, _address, _scale, _infoUrl) {
        wx.openLocation({
            latitude: _latitude || 0, // 纬度，浮点数，范围为90 ~ -90
            longitude: _longitude || 0, // 经度，浮点数，范围为180 ~ -180。
            name: _name || '', // 位置名
            address: _address || '', // 地址详情说明
            scale: _scale || 28, // 地图缩放级别,整形值,范围从1~28。默认为最大
            infoUrl: _infoUrl || '' // 在查看位置界面底部显示的超链接,可点击跳转
        });
    },
    /**
     * 获取地理位置接口
     * @param _type 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
     * @return {Object}
     *      latitude 纬度，浮点数，范围为90 ~ -90
     *      longitude 经度，浮点数，范围为180 ~ -180。
     *      speed 速度，以米/每秒计
     *      accuracy 位置精度
     */
    getLocation: function (_type) {
        var res = {};
        wx.getLocation({
            type: _type || 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
            success: function (data) {
                res = data;
                var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                var speed = res.speed; // 速度，以米/每秒计
                var accuracy = res.accuracy; // 位置精度
            }
        });
        return res;
    },
    hideOptionMenu: function () {

    },
    showOptionMenu: function () {

    },
    /*
     界面操作
     */
    /**
     * 关闭当前网页窗口接口
     */
    closeWindow: function () {
        wx.closeWindow();
    },
    /**
     * 批量隐藏功能按钮接口
     * @param _menuList 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
     */
    hideMenuItems: function (_menuList) {
        wx.hideMenuItems({
            menuList: _menuList || [] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
        });
    },
    /**
     * 批量显示功能按钮接口
     * @param _menuList 要显示的菜单项，所有menu项见附录3
     */
    showMenuItems: function (_menuList) {
        wx.showMenuItems({
            menuList: _menuList || [] // 要显示的菜单项，所有menu项见附录3
        });
    },
    /**
     * 隐藏所有非基础按钮接口
     */
    hideAllNonBaseMenuItem: function () {
        wx.hideAllNonBaseMenuItem();
    },
    /**
     * 显示所有功能按钮接口
     */
    showAllNonBaseMenuItem: function () {
        wx.showAllNonBaseMenuItem();
    },
    /*
     微信扫一扫
     */
    /**
     * 调起微信扫一扫接口
     * @param _needResult  默认为0，扫描结果由微信处理，1则直接返回扫描结果，
     * @param _scanType  可以指定扫二维码还是一维码，默认二者都有
     * @param _callback
     * @return {string}
     */
    scanQRCode: function (_needResult, _scanType) {
        var result = '';
        wx.scanQRCode({
            needResult: _needResult || 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
            scanType: _scanType || ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
            success: function (res) {
                result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
            }
        });
        return result;
    },
    /*
     微信支付
     */
    /**
     *
     * @param _timestamp 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。
     *                       但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
     * @param _nonceStr 支付签名随机串，不长于 32 位
     * @param _package 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
     * @param _signType 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
     * @param _paySign 支付签名
     * @param _callback 支付成功后的回调函数
     * 备注：prepay_id 通过微信支付统一下单接口拿到，paySign 采用统一的微信支付 Sign 签名生成方法，注意这里 appId 也要参与签名，
     * appId 与 config 中传入的 appId 一致，即最后参与签名的参数有appId, timeStamp, nonceStr, package, signType
     * 微信支付开发文档：https://pay.weixin.qq.com/wiki/doc/api/index.html
     */
    chooseWXPay: function (_timestamp, _nonceStr, _package, _signType, _paySign, _callback) {
        wx.chooseWXPay({
            timestamp: _timestamp || 0, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
            nonceStr: _nonceStr || '', // 支付签名随机串，不长于 32 位
            package: _package || '', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
            signType: _signType || '', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
            paySign: _paySign || '', // 支付签名
            success: function (res) {
                // 支付成功后的回调函数
                _callback();
            }
        });
    },
    /*
     微信小店
     */
    /**
     * 跳转微信商品页接口
     * @param _productId 商品id
     * @param _viewType 0.默认值，普通商品详情页1.扫一扫商品详情页2.小店商品详情页
     */
    openProductSpecificView: function (_productId, _viewType) {
        wx.openProductSpecificView({
            productId: _productId || '', // 商品id
            viewType: _viewType || '' // 0.默认值，普通商品详情页1.扫一扫商品详情页2.小店商品详情页
        });
    },
    /*
     微信卡券
     */
    //TODO 微信卡券
    addCard: function () {

    },
    chooseCard: function () {

    },
    openCard: function () {

    }
}
