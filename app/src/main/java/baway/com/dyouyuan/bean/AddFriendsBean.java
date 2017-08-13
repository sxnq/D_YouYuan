package baway.com.dyouyuan.bean;

import java.util.List;

/**
 * Created by : Xunqiang
 * 2017/7/14 13:52
 */

public class AddFriendsBean {


    /**
     * result_message : success
     * data : [{"area":"北京市 北京市 昌平区","picWidth":720,"createtime":1500289752618,"picHeight":718,"gender":"男","lng":116.294148,"introduce":"Excalibur","imagePath":"http://qhb.2dyt.com/MyInterface/images/1a66a16c-6d5a-4183-a50c-446a7d54e288.jpg","userId":83,"relationtime":1500291825564,"yxpassword":"dQ35WW82","relation":0,"password":"698d51a19d8a121ce581499d7b701668","lasttime":1500291176182,"phone":"18739281111","nickname":"Saber","age":"20","lat":40.039394},{"area":"台湾-台北市-台北市区","picWidth":720,"createtime":1500286040927,"picHeight":960,"gender":"男","lng":116.294148,"introduce":"wo","imagePath":"http://qhb.2dyt.com/MyInterface/images/8f13bec8-bb1e-400c-89b6-1fc0d7d33b63.jpg","userId":82,"relationtime":1500291817563,"yxpassword":"G2815R3h","relation":0,"password":"698d51a19d8a121ce581499d7b701668","lasttime":1500286150974,"phone":"13911111111","nickname":"xunxunxun","age":"20","lat":40.039394},{"area":"江苏省常州市天宁区","picWidth":510,"createtime":1500207026183,"picHeight":507,"gender":"男","lng":116.293924,"introduce":"跌倒了再爬起来，没什么可怕的。","imagePath":"http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_man.jpg","userId":57,"relationtime":1500291811779,"yxpassword":"wT74825K","relation":0,"password":"0a113ef6b61820daa5611c870ed8d5ee","lasttime":1500289991861,"phone":"15210034888","nickname":"帅的网线都断了","age":"20","lat":40.039232},{"area":"安徽省-安庆市-枞阳县","picWidth":720,"createtime":1500078889891,"picHeight":540,"gender":"男","lng":0,"introduce":"法师打发多少发斯蒂芬","imagePath":"http://qhb.2dyt.com/MyInterface/images/5d9d4f4e-2138-434c-bc75-d8a8a088860d.jpg","userId":12,"relationtime":1500291796484,"yxpassword":"18S58RA8","relation":0,"password":"e10adc3949ba59abbe56e057f20f883e","lasttime":1500290791182,"phone":"13699181606","nickname":"嘎嘎嘎","age":"22","lat":0},{"area":"江苏省常州市天宁区","picWidth":510,"createtime":1500079277610,"picHeight":507,"gender":"男","lng":116.293859,"introduce":"哈哈哈哈哈哈哈","imagePath":"http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_man.jpg","userId":17,"relationtime":1500291784952,"yxpassword":"Tt75sDZG","relation":0,"password":"cf63547fadc1aa6e897a62291e0cb124","lasttime":1500296140701,"phone":"15210034889","nickname":"帅的网线都断了","age":"20","lat":40.039181},{"area":"安徽省安庆市枞阳县","picWidth":720,"createtime":1500291713620,"picHeight":1084,"gender":"男","lng":116.299659,"introduce":"一句话描述一下自己","imagePath":"http://qhb.2dyt.com/MyInterface/images/3a4d49b3-21b5-42af-b98f-2f82a67addcf.jpg","userId":86,"relationtime":1500291729399,"yxpassword":"w93S0Ym0","relation":0,"password":"202cb962ac59075b964b07152d234b70","lasttime":1500299017815,"phone":"13934722582","nickname":"123","age":"22","lat":40.040441}]
     * result_code : 200
     */

    private String result_message;
    private int result_code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area : 北京市 北京市 昌平区
         * picWidth : 720
         * createtime : 1500289752618
         * picHeight : 718
         * gender : 男
         * lng : 116.294148
         * introduce : Excalibur
         * imagePath : http://qhb.2dyt.com/MyInterface/images/1a66a16c-6d5a-4183-a50c-446a7d54e288.jpg
         * userId : 83
         * relationtime : 1500291825564
         * yxpassword : dQ35WW82
         * relation : 0
         * password : 698d51a19d8a121ce581499d7b701668
         * lasttime : 1500291176182
         * phone : 18739281111
         * nickname : Saber
         * age : 20
         * lat : 40.039394
         */

        private String area;
        private int picWidth;
        private long createtime;
        private int picHeight;
        private String gender;
        private double lng;
        private String introduce;
        private String imagePath;
        private int userId;
        private long relationtime;
        private String yxpassword;
        private int relation;
        private String password;
        private long lasttime;
        private String phone;
        private String nickname;
        private String age;
        private double lat;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getPicWidth() {
            return picWidth;
        }

        public void setPicWidth(int picWidth) {
            this.picWidth = picWidth;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getPicHeight() {
            return picHeight;
        }

        public void setPicHeight(int picHeight) {
            this.picHeight = picHeight;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getRelationtime() {
            return relationtime;
        }

        public void setRelationtime(long relationtime) {
            this.relationtime = relationtime;
        }

        public String getYxpassword() {
            return yxpassword;
        }

        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
