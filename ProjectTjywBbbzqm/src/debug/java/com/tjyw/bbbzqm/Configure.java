package com.tjyw.bbbzqm;

/**
 * Created by stephen on 6/28/16.
 */
public interface Configure {

    interface Network {

        String HOST = "api.qimingjieming.com";

        String SERVER = String.format("http://%s/qm/", HOST);
    }

    interface MI_PUSH {

        String APP_ID = "2882303761517595657";

        String APP_KEY = "5821759533657";
    }

    interface ALI {

        String APP_ID = "2017090508564999";

        String PARTNER = "2088721954149255";

        String SELLER = PARTNER;

        String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDGtdXSJ9prJfLueUWUEimS68qPzVhPlbP3F8HO8yqeWmE62IeriG+TEU72cHA63Hv1GvEgip2wNM5fcgVvHc7zj+6X1YIYUltKEwhpgmIhpaOuorskjdSuXEgHOu4QWIJzYywMe2I2lLcYDLeptqNFTMQUBb5g3sBxv9To6GJtRpuU8mbWEoxOWzZ+sDXpsvQ+9JFe1pSwEBLQCZFDTV9s4UF3YRPKJQYrH0072bcCkyd05ZEUUSYO9qXD/ehDrhrKjO2g7T51pLuu5cWaJbLGHYqqw4ejuCKl/D7eUYQ5TqsL5g1A0C+rsVizPRAca9NgSIst8WHsTXz05mETd7uxAgMBAAECggEAfu7Yz441sAxlyoAdOiD//aIxmvqJVw1CEK1UWGZs6wrA4V5ADArzevargjEBABin9kJnlWn2oQCac83aOsjH0j43IzZ4DCFPv1m5Hezz0pqEnKHG+eq/2UGJoVS7jiV770mCcda/Pi5xZyN4jyjj0vjeqAujOS4RsJfh4YXQmgxHOGhX7fdQaqIamu6Hw5PvKQnkR6ReFiMEVLlGWgpU5bspVfvYdHbKF5QaDlHKztaQOnguwrmnlFlmXl56i+JTs0/St3M5TlxnR6lZFQt5vIZiBXo25gwM2Oq2qltBCWbyLWAJWMUprVCct1N8+SRhWHrcyAUhvPjN0+pGlUx1oQKBgQD2owUhHMNwIf3UIg+G4zTVv4ZZDI0lBXLRgMFJeoGEJjVgcb8+JH2BwcSqomixJWR8e1q1r+3/sq2XiHpw5WvfspaM6qVTKojjINi9+XxIF5WVcFzWf1qWRywShoZahPpSRGtZtRpD4OSysjQggqalY1yY6PHML1xuf/XZW5SR0wKBgQDOQQid43ujLnP0EIy1LSp/YOXYuMVdgHAvNfTNtTlYwZuYH6zNYK5sf95RkpGx3SyFZczw2bIX1+apBIvf/opwk/QwPIfm+o0P2+EYRTOsSK2irQthQOq28xfkmEJDW+46bBUmjW6UjVdsR0iBjPHgqgZhTQ7763MFR6ODqRhF6wKBgQCXPn79wELFN8MNXFnDMP+MeGetofnIEyBHAzsEkUBtRnUDEJ3jQMNKWAX/UZr7zt9hSs/kjN9ZJzw2qbXUF4xgYIbcIWSCrJnHFrRzQ1QIbZYITyBPVUe60ihfr+kiB1vuKRDfsSRLLdVxE8DNkvEYiOcWyfrFaOG4zXV49iUUiwKBgDOgK5v5mLTbp8JrcgzXZU/E3UNF3IDu6SWoB8KFnfkNXPjS1NoDXSVjTC521WkDx2UMeiO/GcjGDcI4lRpYCCbtoa2BOTMje7OAgfMlPy3ccznIvpR1P0rTxiM9yTg7BoIxE4o21HrydfNwVEG5GcH2TOqF8z4zTPMBX3hRFvZJAoGAd/3J7jBf5i/2NX/Tt+11+5eJidGU7y0Wn2Fm661Ih2oKfx8Asszp27Z9iekl8wp+pcEaqINAAwk8uiFWjFmPJk5ZfgxluHdmU8Xw286LpZJkk/F2WCgghDj3kGutyY/Lt3oomjZRuANI/pIi4hhZctOqKGSA31QT2NjX0jJJcC0=";

        String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/2orzzCo+/5qU+SPRAEVoOqYgJ2xVuEUhTxFvcVFslFqB6IOwFfeYC0axavFXOxfyj/fYUazEoonPTZBCGm+4uVQSfJnJzR21iusokKtIQ3SrjBOlFJOJqzRzcYZj86oIN77i6WapVU+hpFVj59oWCQqy6yKjQqXw2KUJJ5EgoQIDAQAB";// 支付宝公钥

        String NOTIFY_URL = "http://47.93.177.203:8088/qm/pay/alipayNotify";

//        String NOTIFY_URL = String.format("%s/pay/alipayNotify", Network.SERVER);
    }

    interface WX {

        String APP_ID = "wx6e1cf27e64180e8c";
    }

    interface UMeng {

        String APP_KEY = "5a61c53ba40fa3364e00013e";
    }
}
