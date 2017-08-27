package com.tjyw.qmjm;

/**
 * Created by stephen on 6/28/16.
 */
public interface Configure {

    interface Network {

        String HOST = "api.erwanshenghuo.com:8089";

        String SERVER = String.format("http://%s/qm/", HOST);
    }

    interface MI_PUSH {

        String APP_ID = "2882303761517595657";

        String APP_KEY = "5821759533657";
    }

    interface ALI {

        String APP_ID = "2016092801991681";

        String PARTNER = "2088421926647904";

        String SELLER = PARTNER;

        String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL/aivPMKj7/mpT5I9EARWg6piAnbFW4RSFPEW9xUWyUWoHog7AV95gLRrFq8Vc7F/KP99hRrMSiic9NkEIab7i5VBJ8mcnNHbWK6yiQq0hDdKuME6UUk4mrNHNxhmPzqgg3vuLpZqlVT6GkVWPn2hYJCrLrIqNCpfDYpQknkSChAgMBAAECgYEAinLtn24QdpICPctk22h0rfjU9ZM9jMaDrLvgeymtdsvAXMssbSANIv3QMCxaqXaNm4/lbYfTQ7t/88eneOyW/dlcpYjLG/tWrtxuMkIVpHCJgO/ED9gj3Utx6YuoPZJtJpMhzuuRjsAyzlArosxmB1zEQB0mVhyaIZcBO7jkGfkCQQDi8YwlHqWLtS9BEDe1c4LfkaeOek/zM1KLE9eq/MRcwVqbXmWilUOP9k/vs0KatrJ0lJKIkyWfoQcs7oRHEfZXAkEA2Grd30RLHjh03RTrrhXRdchEhFUp+i1Fj2Z2cHkNmUETuBxcmLwlAv50YvD90x11Ku7D+neRzOR3bgMABVaVxwJAMgxdAM6qaY4SlsGx13Va9l0T26sDuLmnyX4dwNqvn1kQ11TRCzLpzHTgmhcpaB9EacteKXpyAKstfu43A8llTwJAVkidNxPSYzUdGrvpjxmxziTPdHdD6jO8hQTT+dm5mz1/8CqWnGp+0yfCHWR5vn8DJN/XEPg1EBySpTDbBEXWJwJBAJPL03JZZt1WFNYmtGG0r726eY1yzsAQGnr0ut4o225g6PMuijZEg6IhJv9ECqY5mhOd9GAEOOl/0q3zn7jhxA4=";// 商户私钥，pkcs8格式

        String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/2orzzCo+/5qU+SPRAEVoOqYgJ2xVuEUhTxFvcVFslFqB6IOwFfeYC0axavFXOxfyj/fYUazEoonPTZBCGm+4uVQSfJnJzR21iusokKtIQ3SrjBOlFJOJqzRzcYZj86oIN77i6WapVU+hpFVj59oWCQqy6yKjQqXw2KUJJ5EgoQIDAQAB";// 支付宝公钥

        String NOTIFY_URL = "http://101.201.109.200:8089/qm/pay/alipayNotify";

//        String NOTIFY_URL = String.format("%s/pay/alipayNotify", Network.SERVER);
    }

    interface WX {

        String APP_ID = "wx6e1cf27e64180e8c";
    }

    interface ACRA {

        String REPORT = "http://erwanshenghuo.com:5984/acralyzer/_design/acralyzer/index.html"; // nuuneoi

        String HOST = "http://www.erwanshenghuo.com:5984/acra-myapp/_design/acra-storage/_update/report";

        String USER = "adrTodayDating";

        String PASS = "adrTodayDating";
    }
}
