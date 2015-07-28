package itpsoft.app.gamevl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.mupdf.MuPDFActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class pdfActivity extends Activity implements OnClickListener{
	
	private String lop10cb = "congthuclop10coban.pdf";
	private String lop10nc = "congthuclop10nangcao.pdf";
	private String lop11 = "congthuclop11.pdf";
	private String lop12cb = "congthuclop12full.pdf";
	private String lop12nc = "congthuclop12tonghop.pdf";
	Button btLop10cb;
	Button btLop10nc;
	Button btLop11;
	Button btLop12cb;
	Button btLop12nc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_pdf);
		chay();
		init();
	}
	private void init() {
		btLop10cb = (Button) findViewById(R.id.congthuclop10);
		btLop10nc = (Button) findViewById(R.id.congthuclop10nc);
		btLop11 = (Button) findViewById(R.id.congthuclop11);
		btLop12cb = (Button) findViewById(R.id.congthuclop12);
		btLop12nc = (Button) findViewById(R.id.congthuclop12nc);
		//
		btLop10cb.setOnClickListener(this);
		btLop10nc.setOnClickListener(this);
		btLop11.setOnClickListener(this);
		btLop12cb.setOnClickListener(this);
		btLop12nc.setOnClickListener(this);
	}

	private void chay() {
		File fileBrochure = new File("//sdcard/dcim/congthuclop10coban.pdf");
		if (!fileBrochure.exists()) {
			CopyAssetsbrochure(lop10cb);
		}
		File fileBrochure1 = new File("//sdcard/dcim/" + lop10nc);
		if (!fileBrochure1.exists()) {
			CopyAssetsbrochure(lop10nc);
		}
		File fileBrochure2 = new File("//sdcard/dcim/" + lop11);
		if (!fileBrochure2.exists()) {
			CopyAssetsbrochure(lop11);
		}
		File fileBrochure3 = new File("//sdcard/dcim/" + lop12cb);
		if (!fileBrochure3.exists()) {
			CopyAssetsbrochure(lop12cb);
		}
		File fileBrochure4 = new File("//sdcard/dcim/ "+ lop12nc);
		if (!fileBrochure4.exists()) {
			CopyAssetsbrochure(lop12nc);
		}
	}

	//
	// method to write the PDFs file to sd card
	private void CopyAssetsbrochure(String filename) {
		AssetManager assetManager = getAssets();
		String[] files = null;
		try {
			files = assetManager.list("");
		} catch (IOException e) {
			Log.e("tag", e.getMessage());
		}
		for (int i = 0; i < files.length; i++) {
			String fStr = files[i];
			if (fStr.equalsIgnoreCase(filename)) {
				InputStream in = null;
				OutputStream out = null;
				try {
					in = assetManager.open(files[i]);
					out = new FileOutputStream("//sdcard/dcim/" + files[i]);
					copyFile(in, out);
					in.close();
					in = null;
					out.flush();
					out.close();
					out = null;
					break;
				} catch (Exception e) {
					Log.e("tag", e.getMessage());
				}
			}
		}
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.congthuclop10:
			 /** PDF reader code */ 
			/** PDF reader code */ 
		    File file = new File("//sdcard/dcim/"+lop10cb);  
		    if(file.exists()) {
		    	String i = "0";
	           Intent intent = new Intent(pdfActivity.this,MuPDFActivity.class);
	           intent.putExtra("i", i);
	           startActivity(intent);
	            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	        }else{
	            Log.i("duong dan", "file khong ton tai");
	        }
			break;
		case R.id.congthuclop10nc:
			 /** PDF reader code */ 
		    File file1 = new File("//sdcard/dcim/"+lop10nc);  
		    if(file1.exists()) {
		    	String i = "1";
		           Intent intent = new Intent(pdfActivity.this,MuPDFActivity.class);
		           intent.putExtra("i", i);
		           startActivity(intent);
	           
	            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	        }else{
	            Log.i("duong dan", "file khong ton tai");
	        }
			break;
		case R.id.congthuclop11:
			 /** PDF reader code */ 
		    File file2 = new File("//sdcard/dcim/"+lop11);  
		    if(file2.exists()) {
		    	String i = "2";
		           Intent intent = new Intent(pdfActivity.this,MuPDFActivity.class);
		           intent.putExtra("i", i);
		           startActivity(intent);
	           
	            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	        }else{
	            Log.i("duong dan", "file khong ton tai");
	        }
			break;
		case R.id.congthuclop12:
			 /** PDF reader code */ 
		    File file3 = new File("//sdcard/dcim/"+lop12cb);  
		    if(file3.exists()) {
		    	String i = "3";
		           Intent intent = new Intent(pdfActivity.this,MuPDFActivity.class);
		           intent.putExtra("i", i);
		           startActivity(intent);
	           
	            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	        }else{
	            Log.i("duong dan", "file khong ton tai");
	        }
			break;
		case R.id.congthuclop12nc:
			 /** PDF reader code */ 
		    File file4 = new File("//sdcard/dcim/"+lop12nc);  
		    if(file4.exists()) {
		    	   String i = "4";
		           Intent intent = new Intent(pdfActivity.this,MuPDFActivity.class);
		           intent.putExtra("i", i);
		           startActivity(intent);
	           
	            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	        }else{
	            Log.i("duong dan", "file khong ton tai");
	        }
			break;
		default:
			break;
		}
	}
}
