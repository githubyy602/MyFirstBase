//console.log("获取vue:"+parent.indexVue);

var contentVue = new Vue({
    el : "#container",

    methods : {
        goDetail : function () {
            parent.indexVue.content = parent.basePath+"/user/gotoDetail.do";
            //parent.indexVue.resetFrameHeight(parent.document.getElementById('content'));

            //页面内容加载后，content层内容高度自适应
            function setIframeHeight(iframe) {
                if (iframe) {
                    var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
                    if (iframeWin.document.body) {
                        iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
                    }
                }
            };

            window.onload = function () {
                setIframeHeight(parent.document.getElementById('content'));
            };
        }
    }
});
