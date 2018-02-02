$(function () {
    $.ajax({
        url: "/user/getShareUrl",
        method: "post",
        data: {},
        success: function (data) {
            wx.config({
                beta: true,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
                debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.appId, // 必填，企业微信的corpID
                timestamp: data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.nonceStr, // 必填，生成签名的随机串
                signature: data.signature,// 必填，签名，见[附录1](#11974)
                jsApiList: ["onMenuShareAppMessage", "onMenuShareWechat", "hideAllNonBaseMenuItem"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
        }
    });

    wx.ready(function () {
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    });

    wx.error(function (res) {
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    });

    wx.checkJsApi({
        jsApiList: ["onMenuShareAppMessage", "onMenuShareWechat", "hideAllNonBaseMenuItem"], // 需要检测的JS接口列表，所有JS接口列表见附录2,
        success: function (res) {
            // 以键值对的形式返回，可用的api值true，不可用为false
            // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
        }
    });

});

wx.hideAllNonBaseMenuItem();

wx.onMenuShareAppMessage({
    title: data.title, // 分享标题
    desc: data.desc, // 分享描述
    link: data.link, // 分享链接
    imgUrl: data.imgUrl, // 分享图标
    success: function () {
        // 用户确认分享后执行的回调函数
        alert("分享成功");
    },
    cancel: function () {
        // 用户取消分享后执行的回调函数
    }
});

wx.onMenuShareWechat({
    title: data.title, // 分享标题
    desc: data.desc, // 分享描述
    link: data.link, // 分享链接
    imgUrl: data.imgUrl, // 分享图标
    success: function () {
        // 用户确认分享后执行的回调函数
        alert("分享成功");
    },
    cancel: function () {
        // 用户取消分享后执行的回调函数
    }
});