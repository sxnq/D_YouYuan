package baway.com.dyouyuan.bean;

import java.util.List;

public class UserInfoBean {

    /**
     * result_message : success
     * data : {"area":"河南省 焦作市 孟州市","lasttime":1499851060564,"createtime":1499830426052,"gender":"男","introduce":"死肥宅就是我。","imagePath":"http://qhb.2dyt.com/MyInterface/images/8618fc75-4efa-4f17-a777-aad915fe249b.jpg","nickname":"Reinhardt","userId":21,"photolist":[{"picWidth":720,"timer":1499842558230,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/e2335022-7880-4a24-a987-4bba7abea51f.jpg","albumId":5,"userId":21},{"picWidth":720,"timer":1499842685715,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/533919ea-541b-45fb-971e-9e8af5c87c42.jpg","albumId":6,"userId":21},{"picWidth":720,"timer":1499842719930,"picHeight":639,"imagePath":"http://qhb.2dyt.com/MyInterface/images/9d0f042d-267e-4850-b7d4-aee8a4ec65c5.jpg","albumId":7,"userId":21},{"picWidth":720,"timer":1499842733598,"picHeight":604,"imagePath":"http://qhb.2dyt.com/MyInterface/images/b94d09a0-00b9-4ae3-bf82-6efc26a3e8b5.jpg","albumId":8,"userId":21},{"picWidth":720,"timer":1499842742076,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/582dcdc1-3721-4b74-9204-eb171cade580.jpg","albumId":9,"userId":21}]}
     * result_code : 200
     */

    private String result_message;
    private DataBean data;
    private int result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public static class DataBean {

        private String area;
        private long lasttime;
        private long createtime;
        private String gender;
        private String introduce;
        private String imagePath;
        private String nickname;
        private int userId;
        private List<PhotolistBean> photolist;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<PhotolistBean> getPhotolist() {
            return photolist;
        }

        public void setPhotolist(List<PhotolistBean> photolist) {
            this.photolist = photolist;
        }

        public static class PhotolistBean {
            /**
             * picWidth : 720
             * timer : 1499842558230
             * picHeight : 720
             * imagePath : http://qhb.2dyt.com/MyInterface/images/e2335022-7880-4a24-a987-4bba7abea51f.jpg
             * albumId : 5
             * userId : 21
             */

            private int picWidth;
            private long timer;
            private int picHeight;
            private String imagePath;
            private int albumId;
            private int userId;

            public int getPicWidth() {
                return picWidth;
            }

            public void setPicWidth(int picWidth) {
                this.picWidth = picWidth;
            }

            public long getTimer() {
                return timer;
            }

            public void setTimer(long timer) {
                this.timer = timer;
            }

            public int getPicHeight() {
                return picHeight;
            }

            public void setPicHeight(int picHeight) {
                this.picHeight = picHeight;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public int getAlbumId() {
                return albumId;
            }

            public void setAlbumId(int albumId) {
                this.albumId = albumId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
