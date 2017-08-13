package baway.com.dyouyuan.bean;

import java.util.List;

public class XinXianBean {

    /**
     * result_message :
     * result_code : 200
     * list : [{"width":0,"dynamicTime":"1501040389000","id":1,"type":1,"content":"今天是中国人民解放军90岁生日，90岁正当壮年。在生日庆祝会上，习大大的讲话掷地有声。他说，人民军队从胜利走向胜利，彰显了战斗精神的伟大力量。敢于斗争、敢于胜利，一不怕苦、二不怕死，是人民军队血性胆魄的生动写照。他说，要推进强军事业，必须始终聚焦备战打仗，锻造召之即来、来之能战、战之必胜的精兵劲旅。大大还说了很多，听得刀哥热血沸腾","height":0},{"width":0,"dynamicTime":"1501040389001","id":2,"pic":"http://p1.pstatp.com/large/31db00049d04149ad457","type":2,"content":"目前苹果手机在中国的市场份额持续下滑，跌至第五，而国产手机份额达到惊人的87%。另外苹果在中国口碑持续下滑，性价比不够高、打赏分成引纠纷，苹果手机在中国市场的口碑和影响力下跌趋势显现。三星则因安全故障和态度问题早早失去民心。反观国产品牌，牢牢占据前四席，逐渐把苹果和三星甩得越来越远。苹果似乎看到了中国市场的下滑趋势，逐渐向印度市场靠拢，并且要在印度建厂","height":0}]
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
         * width : 0
         * dynamicTime : 1501040389000
         * id : 1
         * type : 1
         * content : 今天是中国人民解放军90岁生日，90岁正当壮年。在生日庆祝会上，习大大的讲话掷地有声。他说，人民军队从胜利走向胜利，彰显了战斗精神的伟大力量。敢于斗争、敢于胜利，一不怕苦、二不怕死，是人民军队血性胆魄的生动写照。他说，要推进强军事业，必须始终聚焦备战打仗，锻造召之即来、来之能战、战之必胜的精兵劲旅。大大还说了很多，听得刀哥热血沸腾
         * height : 0
         * pic : http://p1.pstatp.com/large/31db00049d04149ad457
         */

        private int width;
        private String dynamicTime;
        private int id;
        private int type;
        private String content;
        private int height;
        private String pic;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getDynamicTime() {
            return dynamicTime;
        }

        public void setDynamicTime(String dynamicTime) {
            this.dynamicTime = dynamicTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
