package itpsoft.app.gamevl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.Profile;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class loginFacebook extends Activity {
	private LoginButton loginBtn;
	private UiLifecycleHelper uiHelper;
	private ImageView imvAvataFB;
	private String name;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_thach_dau);
		imvAvataFB = (ImageView)findViewById(R.id.imvAvatar);
		loginBtn = (LoginButton)findViewById(R.id.fb_login_button);
		loginBtn.setReadPermissions(Arrays.asList("email"));
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
		
			@Override
			public void onUserInfoFetched(GraphUser user) {
				// TODO Auto-generated method stub
				if(user!= null){
//					tvNameFB.setText(user.getId() + " - " + user.getName());
					name = user.getName();
					url = "https://graph.facebook.com/" + user.getId() + "/picture?type=large";
					new getAvatar(user).execute(url);
				}else{
//					tvNameFB.setText("Bạn chưa đăng nhập");
				}
			}
		});
	}
	
	
	
	class getAvatar extends AsyncTask<String, Void, Void> {
        GraphUser profile;
        Bitmap myBitmap;

        public getAvatar(GraphUser user) {
            this.profile = user;
        }

        @Override
        protected Void doInBackground(String... arg0) {

            try {
                URL image_value = new URL(arg0[0]);


                HttpURLConnection conn = (HttpURLConnection) image_value.openConnection();


                conn.setDoInput(true);
                conn.connect();
                InputStream input = conn.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
                conn.disconnect();

            } catch (Exception e) {
                Log.e("loi", e.getMessage());
            }
            return null;
        }
        
        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(loginFacebook.this, infoUser.class);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            i.putExtra("picture", b);
            i.putExtra("name", name);
            i.putExtra("urlAvatar", url);
            
            startActivity(i);
            finish();
        }
	}
	
	
	
	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
				Log.d("MainActivity", "Facebook session opened.");
			} else if (state.isClosed()) {
				Log.d("MainActivity", "Facebook session closed.");
			}
		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}
}
