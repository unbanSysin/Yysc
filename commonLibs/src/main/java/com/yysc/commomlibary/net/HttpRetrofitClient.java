package com.yysc.commomlibary.net;

import android.content.Context;

import com.yysc.commomlibary.base.BaseApplication;
import com.yysc.commomlibary.net.interceptor.HttpCommonInterceptor;
import com.yysc.commomlibary.net.interceptor.HttpsInterceptor;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by kj00037 on 2017/12/6.
 */

public class HttpRetrofitClient {

    private  Context mContext;
    private Retrofit retrofit;
    private static final String TAG = "HttpRetrofitClient";
    private        SSLSocketFactory   mSocketFactory;
    private        X509TrustManager   mTrustManager;
    private static HttpRetrofitClient instance;
    private Interceptor interceptor;

    public HttpRetrofitClient() {

    }

    public HttpRetrofitClient(Context context) {
        this.mContext = context;
    }
    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    private Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
        return retrofit;
    }


    public <T> T getApiService(Class<T> serviceCls) {
        return getRetrofit().create(serviceCls);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                                                         .addInterceptor(getLogInterceptor())
                                                         .addInterceptor(new HttpsInterceptor())
                                                         .addNetworkInterceptor(new HttpCommonInterceptor())
                                                         .connectTimeout(3000,TimeUnit.MILLISECONDS)
                                                         .writeTimeout(3000, TimeUnit.MILLISECONDS)
                                                         .readTimeout(3000, TimeUnit.MILLISECONDS)
                                                         .cache(getCache())
                                                         .cookieJar(mCookieJar)
                                                         .sslSocketFactory(getSSlSockFactory());
        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        OkHttpClient mOkHttpClient = builder.build();
        return mOkHttpClient;
    }


    private Interceptor getLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    private Cache getCache() {
        File file = new File((BaseApplication.getContext() == null ? mContext : BaseApplication.getContext()).getCacheDir(), "cache");
        int size = 10*1024*1024;
        Cache cache = new Cache(file, size);
        return cache;
    }

    private CookieJar mCookieJar = new CookieJar() {
        ConcurrentHashMap<String,List<Cookie>> cookieStore = new ConcurrentHashMap();
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(),cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookieList = cookieStore.get(url.host());
            return cookieList == null ? new ArrayList<Cookie>() : cookieList;
        }
    };

    private SSLSocketFactory getSSlSockFactory() {
        if(mSocketFactory == null) {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null,new TrustManager[]{getTrustManager()},null);
                mSocketFactory = sslContext.getSocketFactory();
            }   catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
        return mSocketFactory;
    }

    private X509TrustManager getTrustManager() {
        if(mTrustManager == null) {
//            try {
//                // 获取自签名证书集合，由证书工厂管理
//                InputStream                       inputStream        = BaseApplication.getContext().getAssets().open("srca.cer");
//                CertificateFactory                certificateFactory = CertificateFactory.getInstance("X.509");
//                Collection<? extends Certificate> certificates       = certificateFactory.generateCertificates(inputStream);
//                if (certificates.isEmpty()) {
//                    throw new IllegalArgumentException("expected non-empty set of trusted certificates");
//                }
//                // 将证书保存到 KeyStore 中
//                char[] password = "password".toCharArray();
//                KeyStore keyStore = null;
//                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//                keyStore.load(null, password);
//                int index = 0;
//                for (Certificate certificate : certificates) {
//                    String certificateAlias = String.valueOf(index++);
//                    keyStore.setCertificateEntry(certificateAlias, certificate);
//                }
//                // 使用包含自签名证书的 KeyStore 构建一个 X509TrustManager
//                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//                keyManagerFactory.init(keyStore, password);
//                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//                trustManagerFactory.init(keyStore);
//            } catch (KeyStoreException e) {
//                e.printStackTrace();
//            } catch (CertificateException e) {
//                e.printStackTrace();
//            } catch (UnrecoverableKeyException e) {
//                e.printStackTrace();
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            mTrustManager = new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }
            };
//            try {
//                TrustManagerFactory mTrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//                mTrustManagerFactory.init((KeyStore) null);
//                TrustManager[] trustManagers = mTrustManagerFactory.getTrustManagers();
//                if(trustManagers.length != 1 || (trustManagers[0] instanceof X509TrustManager)) {
//                    throw new IllegalStateException("Unexpected default trust managers:"
//                                                            + Arrays.toString(trustManagers));
//                }
//                mTrustManager = (X509TrustManager) trustManagers[0];
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//                mTrustManager = new X509TrustManager() {
//                    @Override
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
//                    }
//
//                    @Override
//                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
//                    }
//                };
//            } catch (KeyStoreException e) {
//                e.printStackTrace();
//            }
        }
        return mTrustManager;
    }

}
