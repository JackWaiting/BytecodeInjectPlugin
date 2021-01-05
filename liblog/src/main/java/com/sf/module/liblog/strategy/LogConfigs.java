package com.sf.module.liblog.strategy;

public class LogConfigs {

    public class Module {
        public static final String PICK_UP = "取件";
        public static final String TEMPORARY_SAVE = "暂存";
        public static final String NET = "网络";
        public static final String PUSH = "推送";
        public static final String LOG = "日志";
        public static final String UTIL = "工具类";
        public static final String WIDGET = "widget";
        public static final String OCTOPUS = "octopus";
        public static final String DB = "db";
        public static final String HAL = "hal";
        public static final String AD = "ad";
        public static final String BACK_BOX = "backBox";
        public static final String ENTRY = "entry";
        public static final String MANAGER = "manager";
        public static final String OPERATOR = "operator";
        public static final String CLIENT_SEND = "clintSend";
        public static final String REFUND = "refund";
        public static final String RETENION = "retention";
        public static final String BASE = "base";
        public static final String REPEAT_REQUEST = "repeatRequest";
        public static final String LIB_BASE = "libBase";
        public static final String STAY_FEE = "stay_Fee";
    }

    public  class Fun {
        public static final String NET = "网络";
        public static final String PUSH = "推送";
        public static final String LOG = "日志";
        public static final String OCTOPUS = "octopus";
        public static final String DB = "db";
        public static final String RETENION = "retention";
        public static final String BASE = "base";
        public static final String REPEAT_REQUEST = "repeatRequest";
        public static final String LIB_BASE = "libBase";
        public static final String STAY_FEE = "stay_Fee";


        public class Pickup {

            public static final String PICKUP_MAIN =  "取件主页";

            public static final String PAY =  "支付";

            public static final String OE_PAY =  "oePay";

            public static final String OCTOPUS_PAY =  "八达通支付";

            public static final String SWIFT_PASS =  "Swift支付";

            public static final String REWARD =  "赞赏";

            public static final String REBATE =  "回赠";

            public static final String BOX_DOOR_OPEN =  "客户取件开门";

            public static final String CHOOSE_PAY_TYPE =  "选择支付方式";

            public static final String PAY_FAIL =  "支付失败";

            public static final String OTHER =  "other";

        }

        public class TemporarySave {

            public static final String TS_MAIN = "暂存主页";

            public static final String TS_POST_VERIFY_TEL = "暂存存件校验";
            public static final String TS_POST_SELECT_CELL = "暂存存件选格口";
            public static final String TS_POST_PAY = "暂存存件支付";
            public static final String TS_POST_PAY_SUCCESS = "暂存存件支付成功";
            public static final String TS_POST_PAY_FAIL = "暂存存件支付失败";
            public static final String TS_POST_PAY_TIMEOUT = "暂存存件支付超时";
            public static final String TS_POST_OPEN_DOOR = "暂存存件开格口";
            public static final String TS_POST_SUCCESS = "暂存存件成功";

            public static final String TS_GET = "暂存取件";
            public static final String TS_GET_LIST = "暂存取件列表";
            public static final String TS_GET_CONTACT_CS = "暂存取件联系客服";
            public static final String TS_GET_FORGET_CODE = "暂存取件忘记取件码";
            public static final String TS_GET_OPEN_DOOR = "暂存取件开格口";
            public static final String TS_GET_PAY = "暂存取件支付";
            public static final String TS_GET_PAY_SUCCESS = "暂存取件支付成功";
            public static final String TS_GET_PAY_FAIL = "暂存取件支付失败";
            public static final String TS_GET_PAY_TIMEOUT = "暂存取件支付超时";
            public static final String TS_GET_SUCCESS = "暂存取件成功";

            public static final String TS_CANCEL_REFUND_PAY = "暂存取消退款";
            public static final String TS_CANCEL_OPEN_DOOR = "暂存取消开门";
            public static final String TS_CANCEL_CLOSE_DOOR_SUCCESS = "暂存取消关门成功";
            public static final String TS_CANCEL_NO_OPEN_REFUND_SUCCESS = "暂存取消未开门退款成功";
            public static final String TS_CANCEL_REFUND_FAIL = "暂存取消退款失败";
            public static final String TS_CANCEL_REFUND_TIMEOUT = "暂存取消退款超时";
            public static final String TS_CANCEL_SUCCESS = "暂存取消成功";

            public static final String TS_REFUND = "暂存退款";
            public static final String TS_REFUND_FAIL = "暂存退款失败";
            public static final String TS_REFUND_PAY = "暂存退款支付";
            public static final String TS_REFUND_LIST = "暂存退款列表";
            public static final String TS_REFUND_SUCCESS = "暂存退款成功";
            public static final String TS_REFUND_TIMEOUT = "暂存退款超时";

        }

        public class Utils {
            public static final String ADV = "广告";
            public static final String ANALYSIS = "页面生命周期分析";
            public static final String BOX = "box格口";
            public static final String GIVE_BACK = "回馈";
            public static final String REMOTE_GET = "远程开门";
            public static final String CAMERA = "相机";
            public static final String COMMON = "common工具";
            public static final String DIALOG = "dialog";
            public static final String HEART_BEAT = "心跳";
            public static final String MEDIA_PLAYER = "媒体播放";
            public static final String PAY = "支付";
            public static final String PROTO = "proto";
            public static final String PUSH = "推送";
            public static final String NS_CFG = "号段配置";
            public static final String THREAD = "线程管理";
            public static final String U_DISK = "文件管理";
            public static final String UPGRADE = "app升级";
            public static final String WIDGET = "widget";
            public static final String CRASH = "crash";
            public static final String BROADCAST = "broadcast";
            public static final String GLOBAL_BUS = "GlobalBus";
            public static final String APPLICATION = "application";
            public static final String DAEMON = "daemon";
            public static final String BOX_POSITION = "boxPosition";
        }

        public class Hal {
            public static final String ADV = "adv";
            public static final String BROADCAST = "broadcast";
            public static final String ID_READ = "idRead";
            public static final String ROTATE = "rotate";
            public static final String SCAN = "scan";
        }

        public class Ad {
            public static final String SCREEN = "screen";
            public static final String TAKE = "take";
        }

        public class BackBox {
            public static final String OPEN_DOOR = "openDoor";
            public static final String CHOICE_DOOR = "choiceDoor";
            public static final String BACK_REASON = "backReason";
        }

        public class Entry {
            public static final String ENTRY = "entry";
            public static final String INPUT1 = "entryInput1";
            public static final String INPUT2 = "entryInput2";
            public static final String LOAD = "entryLoad";
        }

        public class Manager {
            public static final String BOX_INFO = "boXInfo";
            public static final String CLICK_ASSIST = "clickAssist";
            public static final String BOX_NEW = "managerNew";
            public static final String MAIN = "managerMain";
        }

        public class OperatorLogin {
            public static final String AGREEMENT = "agreement";
            public static final String CARD_CERTIFY = "cardCertify";
            public static final String CHANGE_PWD = "changePwd";
            public static final String FORGET_PWD = "forgetPwd";
            public static final String LOGIN = "login";
            public static final String REGISTER = "register";
        }

        public class OperatorPost {
            public static final String CHOICE = "postChoice";
            public static final String INPUT = "postInput";
            public static final String ROTATE = "postRotate";
            public static final String SAVE_BOX = "postSaveBox";
            public static final String SCAN = "postScan";
            public static final String SUCCESS = "postSuccess";
        }

        public class OperatorPrint {
            public static final String PRINT = "waybillPrint";
        }

        public class OperatorReceive {
            public static final String RECEIVE = "receive";
            public static final String RECEIVE_OPEN = "receiveOpen";
            public static final String RECEIVE_ROTATE = "receiveRotate";
            public static final String RECEIVE_SUCCESS = "receiveSuccess";
        }

        public class OperateRecover {
            public static final String RECOVER = "recoverCode";
        }

        public class OperatorTrack {
            public static final String GET = "get";
            public static final String ROTATE = "getRotate";
            public static final String LIST = "getList";
        }

        public class OperatorOther {
            public static final String REMOTE_DOOR = "remoteDoor";
            public static final String HOME_EFLOCKER = "homeEflocker";
            public static final String HOME_OTHER = "homeOther";
            public static final String HOME_SF = "homeSF";
        }

        public class ClintSend {
            public static final String SEND_LIST = "sendList";
            public static final String CANCEL_OPEN = "cancelOpen";
            public static final String CANCEL_PAY = "cancelPay";
            public static final String CANCEL_TIMEOUT = "cancelTimeout";
            public static final String SEND_PAY = "sendPay";
            public static final String OPEN_OR_CLOSE = "openOrClose";
            public static final String PAY_FAIL = "payFail";
            public static final String SELECT_BOX = "selectBox";
            public static final String SEND_COUPON = "sendCoupon";
            public static final String SEND_EXPRESS = "sendExpress";
        }

        public class Refund {
            public static final String REFUND = "refund";
            public static final String REFUND_LIST = "refundList";
            public static final String REFUND_PAY = "refundPay";
            public static final String COMMON_PAY = "commonPay";
        }

    }
}
