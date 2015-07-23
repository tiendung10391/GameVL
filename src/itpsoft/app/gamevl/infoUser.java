package itpsoft.app.gamevl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class infoUser extends Activity {
	private ImageView imvAvatarFB;
	private TextView tvName;

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();

	// url to create new product
	private static String url_create_product = "http://gamevl.freezoy.com/create_users.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_user);

		Bundle extras = getIntent().getExtras();
		byte[] b = extras.getByteArray("picture");
		String name = extras.getString("name");
		Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);

		tvName = (TextView) findViewById(R.id.tvNameFB);
		imvAvatarFB = (ImageView) findViewById(R.id.imvAvatarFBTest);
		imvAvatarFB.setImageBitmap(getclip(bmp));
		tvName.setText(name);
		
		// creating new product in background thread
		new CreateNewProduct().execute();

	}

	public static Bitmap getclip(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
				bitmap.getWidth() / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	
	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(infoUser.this);
			pDialog.setMessage("Tạo người dùng mới ..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			Bundle extras = getIntent().getExtras();
			String name = extras.getString("name");
			String avatar = extras.getString("urlAvatar");
			int point = 0;
			boolean status = true;

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("avatar", avatar));
			params.add(new BasicNameValuePair("point", String.valueOf(point)));
			params.add(new BasicNameValuePair("status", String.valueOf(status)));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
//					Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
//					startActivity(i);
					
					// closing this screen
					Toast.makeText(getApplicationContext(), "tạo mới người dùng thành công", Toast.LENGTH_SHORT).show();
//					finish();
				} else {
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}
