package baway.com.dyouyuan.bean;

import java.util.List;

/**
 * Created by : Xunqiang
 * 2017/8/7 18:34
 */

public class GuanZhongBean {

    /**
     * result_message :
     * result_code : 200
     * list : [{"streamKey":"HXX3fR3X","uid":"2","livepic":"http://img.sc115.com/uploads/sc/jpgs/1206apic2389_sc115.com.jpg","id":17,"time":1502100371497,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/HXX3fR3X?e=1502103971&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:fV5A9bVAXulZBrB0sMk9C0kUZmA=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/HXX3fR3X"},{"streamKey":"56OK9H03","uid":"0","livepic":"http://pic.qiantucdn.com/58pic/17/96/08/83I58PICjwm_1024.jpg","id":16,"time":1502100262626,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/56OK9H03?e=1502103862&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:M9yZJZhddu5BELUSyNIXhPamq-c=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/56OK9H03"},{"streamKey":"1a07A80G","uid":"5","livepic":"http://img06.tooopen.com/images/20160823/tooopen_sy_176456148363.jpg","id":15,"time":1502099786645,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/1a07A80G?e=1502103386&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:b0uZEirpBF-t5_A6P_wpLNvJU6c=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/1a07A80G"},{"streamKey":"j67o7g9Q","uid":"2","livepic":"http://img.sc115.com/uploads/sc/jpgs/1206apic2389_sc115.com.jpg","id":14,"time":1502099683245,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/j67o7g9Q?e=1502103283&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:hMFlBgEMN4g7Ma401KU0FRJQGpQ=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/j67o7g9Q"},{"streamKey":"gL441W73","uid":"5","livepic":"http://img06.tooopen.com/images/20160823/tooopen_sy_176456148363.jpg","id":13,"time":1502099495269,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/gL441W73?e=1502103095&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:7-2ywM2RDEk6ZW8Hs-YDC0Ix1GU=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/gL441W73"},{"streamKey":"7IHQQ026","uid":"0","livepic":"http://pic.qiantucdn.com/58pic/17/96/08/83I58PICjwm_1024.jpg","id":12,"time":1502099139693,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/7IHQQ026?e=1502102739&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:Kgq2I9DLG9pKDnSk2VKBia6-2wk=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/7IHQQ026"},{"streamKey":"p6yUX9TC","uid":"4","livepic":"http://pic.58pic.com/58pic/12/86/21/20758PICfV6.jpg","id":11,"time":1502098915429,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/p6yUX9TC?e=1502102515&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:irU5cmsEda_FH-iWXBj5NcoCzqI=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/p6yUX9TC"},{"streamKey":"9x17Neg8","uid":"2","livepic":"http://img.sc115.com/uploads/sc/jpgs/1206apic2389_sc115.com.jpg","id":10,"time":1502098898349,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/9x17Neg8?e=1502102498&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:voGoJxq0w9OoJSjkDqkXHsbd5tE=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/9x17Neg8"},{"streamKey":"fWmzRE0l","uid":"5","livepic":"http://img06.tooopen.com/images/20160823/tooopen_sy_176456148363.jpg","id":9,"time":1502098760271,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/fWmzRE0l?e=1502102360&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:yQszMbNZNJJhLcoGuN8iLOjvc9M=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/fWmzRE0l"},{"streamKey":"3f50v894","uid":"0","livepic":"http://pic.qiantucdn.com/58pic/17/96/08/83I58PICjwm_1024.jpg","id":8,"time":1502098686092,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/3f50v894?e=1502102286&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:lZ0G-rY8d1MpQUyBmxOxcqqKyFM=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/3f50v894"}]
     */

    private String result_message;
    private int result_code;
    private List<ListBean> list;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * streamKey : HXX3fR3X
         * uid : 2
         * livepic : http://img.sc115.com/uploads/sc/jpgs/1206apic2389_sc115.com.jpg
         * id : 17
         * time : 1502100371497
         * type : 1
         * content : randomString
         * publishUrl : rtmp://pili-publish.2dyt.com/1503d/HXX3fR3X?e=1502103971&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:fV5A9bVAXulZBrB0sMk9C0kUZmA=
         * playUrl : rtmp://pili-live-rtmp.2dyt.com/1503d/HXX3fR3X
         */

        private String streamKey;
        private String uid;
        private String livepic;
        private int id;
        private long time;
        private int type;
        private String content;
        private String publishUrl;
        private String playUrl;

        public String getStreamKey() {
            return streamKey;
        }

        public void setStreamKey(String streamKey) {
            this.streamKey = streamKey;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getLivepic() {
            return livepic;
        }

        public void setLivepic(String livepic) {
            this.livepic = livepic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublishUrl() {
            return publishUrl;
        }

        public void setPublishUrl(String publishUrl) {
            this.publishUrl = publishUrl;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }
    }
}
