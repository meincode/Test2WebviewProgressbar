package com.example.test2progressbar;

import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

	public class MainActivity extends Activity {
		private WebView web;
		private ProgressBar progress;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			ConnectivityManager cm = 
					(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			boolean isWifiConn = ni.isConnected();

			ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

			boolean isMobileConn = ni.isConnected();

			if (!isWifiConn && !isMobileConn) {
				Toast.makeText(this, "인터넷에 접속되어 있지 않습니다!", Toast.LENGTH_SHORT).show();
				finish();//액티비티 종료
			} else {
				setContentView(R.layout.activity_main);

				progress = (ProgressBar) findViewById(R.id.web_progress);

				web = (WebView) findViewById(R.id.web);
				web.getSettings().setJavaScriptEnabled(true);
				web.getSettings().setBuiltInZoomControls(true);
				web.setHorizontalScrollbarOverlay(true);
				web.setVerticalScrollbarOverlay(true);

				web.loadUrl("http://www.nate.com");

				web.setWebViewClient(new WebViewClient() {
					// 링크 클릭에 대한 반응
					@Override
					public boolean shouldOverrideUrlLoading(WebView view, String url) {
						view.loadUrl(url);
						return true;
					}

					// 웹페이지 호출시 오류 발생에 대한 처리
					@Override
					public void onReceivedError(WebView view, int errorcode,
							String description, String fallingUrl) {
						Toast.makeText(MainActivity.this,
								"오류 : " + description, Toast.LENGTH_SHORT).show();
					}
					// 페이지 로딩 시작시 호출
					@Override
					public void onPageStarted(WebView view,String url , Bitmap favicon){
						progress.setVisibility(View.VISIBLE);
					}
					//페이지 로딩 종료시 호출
					public void onPageFinished(WebView view,String Url){
						progress.setVisibility(View.GONE);
					}
				});
			}
		}
		public boolean onKeyDown(int keyCode, KeyEvent event){
			if(keyCode ==KeyEvent.KEYCODE_BACK && web.canGoBack()){
				web.goBack();
			}else if(keyCode ==KeyEvent.KEYCODE_BACK && !web.canGoBack()){
				Toast.makeText(this, "프로그램 종료!!", Toast.LENGTH_SHORT).show();
				finish();
			}
			return true;
		}
	}

