package bag.small.ui.activity;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import bag.small.R;
import bag.small.base.BaseActivity;
import butterknife.BindView;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    //    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initData() {
//声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                switch (errorCode) {
                    case 404:
                        toast("未找到");
                        break;
                }
            }
        });
    }

    @Override
    public void initView() {
        setToolTitle("我的书包", true);
        webView.loadUrl("http://www.wodeshubao.com");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
